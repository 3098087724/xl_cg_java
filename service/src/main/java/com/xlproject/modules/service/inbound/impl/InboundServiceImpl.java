package com.xlproject.modules.service.inbound.impl;

import com.xlproject.modules.common.exception.BizException;
import com.xlproject.modules.common.exception.BizExceptionEnum;
import com.xlproject.modules.dao.mapper.*;
import com.xlproject.modules.dto.inboundDto.InboundWithItemDTO;
import com.xlproject.modules.entity.bean.*;
import com.xlproject.modules.service.inbound.InboundService;
import com.xlproject.modules.service.Generator.BatchNoGenerator;
import com.xlproject.modules.service.Generator.BizNoGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
public class InboundServiceImpl implements InboundService {
    //资源
    //<editor-fold desc="Description">
    private InboundOrderMapper inboundOrderMapper;
    private BizNoGenerator bizNoGenerator;
    private WarehouseMapper warehouseMapper;
    private SupplierMapper supplierMapper;
    private UserMapper userMapper;
    private ProductMapper productMapper;
    private BatchNoGenerator batchNoGenerator;
    private InboundOrderItemMapper inboundOrderItemMapper;
    private InventoryMapper inventoryMapper;

    @Autowired
    public void setInboundOrderMapper(InboundOrderMapper inboundOrderMapper) {this.inboundOrderMapper = inboundOrderMapper;}

    @Autowired
    public void setBizNoGenerator(BizNoGenerator bizNoGenerator) {this.bizNoGenerator = bizNoGenerator;}

    @Autowired
    public void setWarehouseMapper(WarehouseMapper warehouseMapper) {
        this.warehouseMapper = warehouseMapper;
    }

    @Autowired
    public void setSupplierMapper(SupplierMapper supplierMapper) {
        this.supplierMapper = supplierMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Autowired
    public void setBatchNoGenerator(BatchNoGenerator batchNoGenerator) {
        this.batchNoGenerator = batchNoGenerator;
    }

    @Autowired
    public void setInboundOrderItemMapper(InboundOrderItemMapper inboundOrderItemMapper) {
        this.inboundOrderItemMapper = inboundOrderItemMapper;
    }

    @Autowired
    public void setInventoryMapper(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }
    //</editor-fold>

    /**
     * 商品入库
     * @param inboundWithItem 入库详细信息
     * @return 返回受影响行数
     */
    @Override
    public boolean inbound(InboundWithItemDTO inboundWithItem) {
        //对入库对象进行校验判断并处理
        validateInboundWithItem(inboundWithItem);

        // 创建入库单
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setOrderNo(bizNoGenerator.generate("RK"));
        inboundOrder.setType(inboundWithItem.getType());
        inboundOrder.setWarehouseId(inboundWithItem.getWarehouseId());
        inboundOrder.setSupplierId(inboundWithItem.getSupplierId());
        inboundOrder.setOperatorId(inboundWithItem.getOperatorId());
        inboundOrder.setRemark(inboundWithItem.getRemark());
        inboundOrder.setCreateTime(LocalDateTime.now());
        inboundOrder.setStatus("APPROVED");

        // 计算总金额并设置到入库单
        double totalAmount = inboundWithItem.getInboundOrderItems().stream()
                .mapToDouble(InboundOrderItem::getTotalPrice)
                .sum();
        inboundOrder.setTotalAmount(totalAmount);

        // 保存入库单
        inboundOrderMapper.AddInboundOrder(inboundOrder);

        // 处理入库明细和库存
        BigInteger warehouseId = inboundWithItem.getWarehouseId();

        Map<String,InboundOrderItem>mergedItems=new HashMap<>();

        //判断入库商品有没有相同批次且商品一样
        inboundWithItem.getInboundOrderItems().forEach(item -> {
            //Product productById = productMapper.getProductById(item.getProductId());
            //创建合并键
            String mergeKey=item.getProductId().toString()+"_"+item.getBatchNo();
            //判断是否存在相同项
            if (mergedItems.containsKey(mergeKey)){
                //存在的话，就将之前的存进去的订单详细取出来和现在进行处理的订单详细数量进行累加
                InboundOrderItem existingItem=mergedItems.get(mergeKey);
                existingItem.setQuantity(existingItem.getQuantity()+ item.getQuantity());
                existingItem.setTotalPrice(existingItem.getTotalPrice()+item.getTotalPrice());
            }else {
                // 如果不存在相同项，直接添加
                mergedItems.put(mergeKey, item);
            }

        });

        mergedItems.values().forEach(item->{
            item.setOrderId(inboundOrder.getId());
            inboundOrderItemMapper.addInboundOrderItem(item);
            //更新库存
           updateInventory(item,warehouseId);
        });
        return true;
    }

    // 更新库存信息
    private void updateInventory(InboundOrderItem item, BigInteger warehouseId) {
        Product product = productMapper.getProductById(item.getProductId());

        // 检查是否已存在相同批次的库存
        Inventory existingInventory = inventoryMapper.getInventoryByProductAndBatch(
                item.getProductId(), warehouseId, item.getBatchNo());

        if (existingInventory != null) {
            // 检查是否为同一商品（额外的安全检查）
            if (!existingInventory.getProductId().equals(item.getProductId())) {
                throw new BizException(BizExceptionEnum.INBOUND_PRODUCT_BATCHNO_EXIT);
            }
            // 如果是同一商品同一批次，更新库存数量
            existingInventory.setQuantity(existingInventory.getQuantity() + item.getQuantity());
            existingInventory.setUpdateTime(LocalDateTime.now());
            inventoryMapper.updateInventory(existingInventory);
        }
        else {
            // 如果不存在，创建新的库存记录
            Inventory inventory = new Inventory();
            inventory.setProductId(item.getProductId());
            inventory.setWarehouseId(warehouseId);
            inventory.setQuantity(item.getQuantity());
            inventory.setCreateTime(LocalDateTime.now());
            inventory.setUpdateTime(LocalDateTime.now());
            inventory.setBatchNo(item.getBatchNo());
            inventory.setProductionDate(item.getProductionDate());

            // 计算过期日期：生产日期 + 保质期(天数)
            LocalDate expirationDate = item.getProductionDate().plusDays(product.getShelfLife());
            inventory.setExpirationDate(expirationDate);
            inventoryMapper.addInventory(inventory);
        }
    }

    // 对入库对象进行校验判断
    private void validateInboundWithItem(InboundWithItemDTO inboundWithItem) {
        // 判断要入库的目标仓库是否存在
        BigInteger warehouseId = inboundWithItem.getWarehouseId();
        if (warehouseMapper.getWarehouseById(warehouseId) == null) {
            throw new BizException(BizExceptionEnum.WAREHOUSE_ID_NOT_SEARCH);
        }

        // 判断是否是采购入库并且供应商id是否为空，若不为空则进行判断，若供应商未搜索到则报错
        if (inboundWithItem.getSupplierId() != null) {
            Supplier supplierById = supplierMapper.getSupplierById(inboundWithItem.getSupplierId());
            if (supplierById == null) {
                throw new BizException(BizExceptionEnum.SUPPLIER_NOT_SEARCH_ID);
            }
            // 如果不是采购入库
            if (!"PURCHASE".equals(inboundWithItem.getType())) {
                throw new BizException(BizExceptionEnum.INBOUND_SUPPLIERID_NEED_PURCHASE);
            }
        }

        // 判断操作员是否为空
        if (inboundWithItem.getOperatorId() == null) {
            throw new BizException(BizExceptionEnum.USER_ID_ERROR);
        }
        User user = userMapper.queryUserById(inboundWithItem.getOperatorId());
        if (user == null) {
            throw new BizException(BizExceptionEnum.USER_NOT_EXIT);
        }

        // 遍历入库的每一个商品
        inboundWithItem.getInboundOrderItems().forEach(item -> {
            // 若商品id未搜索到商品，则报错
            Product productById = productMapper.getProductById(item.getProductId());
            if (productById == null) {
                throw new BizException(BizExceptionEnum.PRODUCT_NOT_EXIT);
            }

            // 设置批次号（如果为空则生成）
            if (item.getBatchNo() == null || item.getBatchNo().isEmpty()) {
                item.setBatchNo(batchNoGenerator.generate(productById.getCode(), item.getProductionDate()));
            }
            // 如果批次号不为空，验证格式是否正确
            else if (!isValidBatchNoFormat(item.getBatchNo(),productById.getCode())) {
                throw new BizException(BizExceptionEnum.INBOUND_BATCHNO_ERROR);
            }
            // 计算入库商品总价
            item.setTotalPrice(item.getQuantity() * item.getUnitPrice());
            // 设置创建时间
            item.setCreateTime(LocalDateTime.now());
        });
    }

    /**
     * 进行批次号判断
     * @param batchNo 批次号
     * @param code 商品编号
     * @return 返回是否符合批次号设计
     */
    private boolean isValidBatchNoFormat(String batchNo, String code) {
        // 正则表达式匹配 SPXXXX-YYYYMMDD-XXX 格式，其中最后部分恰好为3位数字
        // SP + 4位数字 + - + 8位日期 + - + 恰好3位数字
        String pattern = "^SP\\d{4}-\\d{8}-\\d{3}$";

        // 基本参数和格式验证
        if (batchNo == null || code == null || !batchNo.matches(pattern)) {
            return false;
        }

        try {
            // 验证前缀部分是否匹配（提取"SPXXXX"部分）
            String batchPrefix = batchNo.substring(0, 6); // 提取"SP0001"部分

            // 检查是否匹配传入的code参数
            if (!batchPrefix.equals(code)) {
                return false;
            }

            // 验证日期部分是否为有效日期
            String datePart = batchNo.substring(7, 15); // 提取日期部分"20250820"

            // 使用LocalDate验证日期是否真实存在
            LocalDate.parse(datePart, java.time.format.DateTimeFormatter.BASIC_ISO_DATE);

            return true;
        } catch (Exception e) {
            // 捕获任何解析异常，返回false
            return false;
        }
    }


}
