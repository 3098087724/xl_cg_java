package com.xlproject.modules.service.outbound.impl;

import com.xlproject.modules.common.exception.BizException;
import com.xlproject.modules.common.exception.BizExceptionEnum;
import com.xlproject.modules.dao.mapper.*;
import com.xlproject.modules.dto.outboundDto.OutBoundWithItemDTO;
import com.xlproject.modules.entity.bean.Inventory;
import com.xlproject.modules.entity.bean.OutboundOrder;
import com.xlproject.modules.entity.bean.OutboundOrderItem;
import com.xlproject.modules.entity.bean.Product;
import com.xlproject.modules.service.outbound.OutboundService;
import com.xlproject.modules.service.Generator.BizNoGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class OutboundServiceImpl implements OutboundService {
    //<editor-fold desc="资源">
    private InventoryMapper inventoryMapper;
    private BizNoGenerator bizNoGenerator;
    private WarehouseMapper warehouseMapper;
    private UserMapper userMapper;
    private ProductMapper productMapper;
    private OutboundOrderMapper outboundOrderMapper;
    private OutboundOrderItemMapper outboundOrderItemMapper;

    @Autowired
    public void setInventoryMapper(InventoryMapper inventoryMapper){this.inventoryMapper=inventoryMapper;}
    @Autowired
    public void setBizNoGenerator(BizNoGenerator bizNoGenerator){this.bizNoGenerator=bizNoGenerator;}
    @Autowired
    public void setWarehouseMapper(WarehouseMapper warehouseMapper){this.warehouseMapper=warehouseMapper;}
    @Autowired
    public void setUserMapper(UserMapper userMapper){this.userMapper=userMapper;}
    @Autowired
    public void setProductMapper(ProductMapper productMapper){this.productMapper=productMapper;}
    @Autowired
    public void setOutboundOrderMapper(OutboundOrderMapper outboundOrderMapper){this.outboundOrderMapper=outboundOrderMapper;}
    @Autowired
    public void setOutboundOrderItemMapper(OutboundOrderItemMapper outboundOrderItemMapper){this.outboundOrderItemMapper=outboundOrderItemMapper;}
    //</editor-fold>

    @Override
    public Object outBound(OutBoundWithItemDTO outBoundWithItemDTO) {
        validateInventory(outBoundWithItemDTO);

        // 创建出库单
        OutboundOrder outboundOrder = new OutboundOrder();
        outboundOrder.setOrderNo(bizNoGenerator.generate("CK"));
        outboundOrder.setType(outBoundWithItemDTO.getType());
        outboundOrder.setOperatorId(outBoundWithItemDTO.getOperatorId());
        outboundOrder.setRemark(outBoundWithItemDTO.getRemark());
        outboundOrder.setCreateTime(LocalDateTime.now());
        outboundOrder.setStatus("APPROVED");

        // 计算总金额
        double totalAmount = outBoundWithItemDTO.getInboundOrderItems().stream()
                .mapToDouble(item -> item.getTotalPrice() != null ? item.getTotalPrice() : 0.0)
                .sum();
        outboundOrder.setTotalAmount(totalAmount);

        // 保存出库单
        outboundOrderMapper.addOutboundOrder(outboundOrder);

        // 处理出库明细和库存更新，获取出库结果
        List<Object> outboundResults = processOutboundItems(outBoundWithItemDTO, outboundOrder.getId());

        // 如果只有一个结果且是字符串，直接返回
        if (outboundResults.size() == 1) {
            Object firstResult = outboundResults.get(0);
            if (firstResult instanceof String) {
                return firstResult;
            }
        }

        // 否则返回详细列表
        return outboundResults;
    }

    private void validateInventory(OutBoundWithItemDTO outBoundWithItemDTO) {
        String type = outBoundWithItemDTO.getType();

        // 判断操作员
        if (outBoundWithItemDTO.getOperatorId() == null) {
            throw new BizException(BizExceptionEnum.USER_ID_ERROR);
        }
        if (userMapper.queryUserById(outBoundWithItemDTO.getOperatorId()) == null) {
            throw new BizException(BizExceptionEnum.USER_NOT_EXIT);
        }

        // 遍历出库的每一个商品，将相同商品的数量合并到一起
        Map<BigInteger, Integer> productWithCount = new HashMap<>();
        outBoundWithItemDTO.getInboundOrderItems().forEach(item -> {
            // 判断仓库存不存在
            if (item.getWarehouseId() != null) {
                if (warehouseMapper.getWarehouseById(item.getWarehouseId()) == null) {
                    throw new BizException(BizExceptionEnum.WAREHOUSE_ID_NOT_SEARCH);
                }
            }

            if ("TRANSFER".equals(type)) {
                if (item.getTargetWarehouseId() == null ||
                        warehouseMapper.getWarehouseById(item.getTargetWarehouseId()) == null) {
                    throw new BizException(BizExceptionEnum.WAREHOUSE_ID_ERROR);
                }
            }

            productWithCount.merge(item.getProductId(), item.getQuantity(), Integer::sum);
            Product productById = productMapper.getProductById(item.getProductId());

            // 判断出库商品是否存在
            if (productById == null) {
                throw new BizException(BizExceptionEnum.OUTBOUND_PRODUCT_NOTEXIT);
            }

            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new BizException(BizExceptionEnum.OUTBOUND_QUANTITY_ERROR);
            }

            item.setUnitPrice(productById.getPrice());
            item.setTotalPrice(item.getUnitPrice() * item.getQuantity());
            item.setCreateTime(LocalDateTime.now());
        });

        // 验证库存是否充足
        productWithCount.forEach((productId, requiredQuantity) -> {
            // 获取该产品的总库存
            BigInteger totalQuantity = inventoryMapper.getTotalInventoryByProductId(productId).getTotalQuantity();

            // 检查是否为null
            if (totalQuantity == null) {
                throw new BizException(BizExceptionEnum.OUTBOUND_INVENTORY);
            }

            // 将 Integer 转换为 BigInteger 进行比较
            if (totalQuantity.compareTo(BigInteger.valueOf(requiredQuantity)) < 0) {
                // 库存不足，抛出异常
                throw new BizException(BizExceptionEnum.OUTBOUND_INVENTORY);
            }
        });
    }

    //判断按照哪种方式出库，返回出库结果信息
    private List<Object> processOutboundItems(OutBoundWithItemDTO outBoundWithItemDTO, BigInteger orderId) {
        List<Object> results = new ArrayList<>();

        outBoundWithItemDTO.getInboundOrderItems().forEach(item -> {
            item.setOrderId(orderId);
            // 如果指定了批次号，则按指定批次出库
            if (item.getBatchNo() != null && !item.getBatchNo().isEmpty()) {
                outboundBySpecifiedBatch(item);
                // 保存指定批次的出库明细
                outboundOrderItemMapper.addOutboundOrderItem(item);
                // 返回成功字符串
                results.add("出库成功");
            } else {
                // 否则按最低保质期优先出库
                List<String> expirationResults = outboundByExpirationPriority(item);
                results.addAll(expirationResults);
            }
        });

        return results;
    }

    /**
     * 按指定批次出库
     */
    private void outboundBySpecifiedBatch(OutboundOrderItem item) {
        Inventory inventory = inventoryMapper.getInventoryByProductAndBatch(
                item.getProductId(), item.getWarehouseId(), item.getBatchNo());

        if (inventory == null) {
            throw new BizException(BizExceptionEnum.OUTBOUND_BATCH_NOT_FOUND);
        }

        if (inventory.getQuantity() < item.getQuantity()) {
            throw new BizException(BizExceptionEnum.OUTBOUND_INVENTORY); // 库存不足异常
        }

        // 更新库存
        inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
        inventory.setUpdateTime(LocalDateTime.now());
        inventoryMapper.updateInventory(inventory);
    }

    /**
     * 按保质期优先顺序出库，返回商品+批次号+仓库+出库数量的字符串信息
     * @return List<String> 出库详细信息列表
     */
    private List<String> outboundByExpirationPriority(OutboundOrderItem originalItem) {
        List<String> outboundInfoList = new ArrayList<>();

        // 所需出库库存
        int remainingQuantity = originalItem.getQuantity();
        BigInteger orderId = originalItem.getOrderId();

        // 查询该商品在指定仓库的所有库存，按过期日期升序排列（最早过期的在前）
        List<Inventory> inventories = inventoryMapper.getInventoryByProductIdAndWarehouseIdOrderByExpiration(
                originalItem.getProductId(), originalItem.getWarehouseId());

        // 判断是否有此库存
        if (inventories == null || inventories.isEmpty()) {
            throw new BizException(BizExceptionEnum.OUTBOUND_INVENTORY);
        }

        // 按保质期优先顺序出库
        for (Inventory inventory : inventories) {
            // 如果所需出库库存为<=0则退出
            if (remainingQuantity <= 0) break;

            // 获取此批次的剩余库存量
            int availableQuantity = inventory.getQuantity();
            if (availableQuantity <= 0) continue;

            // 计算本次出库数量
            int outboundQuantity = Math.min(availableQuantity, remainingQuantity);

            // 更新库存
            inventory.setQuantity(availableQuantity - outboundQuantity);
            inventory.setUpdateTime(LocalDateTime.now());
            inventoryMapper.updateInventory(inventory);

            // 为每个实际出库的批次创建独立的出库明细记录
            OutboundOrderItem batchItem = new OutboundOrderItem();
            batchItem.setOrderId(orderId);
            batchItem.setProductId(originalItem.getProductId());
            batchItem.setWarehouseId(originalItem.getWarehouseId());
            batchItem.setTargetWarehouseId(originalItem.getTargetWarehouseId());
            batchItem.setQuantity(outboundQuantity);
            batchItem.setUnitPrice(originalItem.getUnitPrice());
            batchItem.setTotalPrice(originalItem.getUnitPrice() * outboundQuantity);
            batchItem.setBatchNo(inventory.getBatchNo());
            batchItem.setCreateTime(LocalDateTime.now());

            // 保存该批次的出库明细
            outboundOrderItemMapper.addOutboundOrderItem(batchItem);

            // 添加出库信息到返回列表：商品+批次号+仓库+出库数量
            Product product = productMapper.getProductById(originalItem.getProductId());
            String productInfo = (product != null) ? product.getName() : "未知商品";
            String outboundInfo = String.format("商品：%s，批次号：%s，仓库ID：%s，出库数量：%d",
                    productInfo, inventory.getBatchNo(), originalItem.getWarehouseId(), outboundQuantity);
            outboundInfoList.add(outboundInfo);

            remainingQuantity -= outboundQuantity;
        }

        if (remainingQuantity > 0) {
            throw new BizException(BizExceptionEnum.OUTBOUND_INVENTORY);
        }

        return outboundInfoList;
    }
}
