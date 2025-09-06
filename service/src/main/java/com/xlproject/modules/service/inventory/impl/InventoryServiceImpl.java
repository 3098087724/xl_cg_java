package com.xlproject.modules.service.inventory.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.exception.BizException;
import com.xlproject.modules.common.exception.BizExceptionEnum;
import com.xlproject.modules.dao.mapper.InventoryMapper;
import com.xlproject.modules.dao.mapper.ProductMapper;
import com.xlproject.modules.dao.mapper.WarehouseMapper;
import com.xlproject.modules.dto.InventoryDto.InventoryWithProductAndWarehouseDTO;
import com.xlproject.modules.dto.InventoryDto.TotalInventoryWithProductAndWarehouse;
import com.xlproject.modules.entity.bean.Inventory;
import com.xlproject.modules.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.List;
@Service
public class InventoryServiceImpl implements InventoryService {
    private InventoryMapper inventoryMapper;
    private ProductMapper productMapper;
    private WarehouseMapper warehouseMapper;
    @Autowired
    public void setInventoryMapper(InventoryMapper inventoryMapper){this.inventoryMapper=inventoryMapper;}
    @Autowired
    public  void  setProductMapper(ProductMapper productMapper){this.productMapper=productMapper;}
    @Autowired
    public  void setWarehouseMapper(WarehouseMapper warehouseMapper){this.warehouseMapper=warehouseMapper;}

    /**
     * 根据id获取库存信息
     * @param id 参数id值
     * @return 返回获取的
     */
    @Override
    public Inventory getInventoryById(BigInteger id) {
        if (id==null){
            throw new BizException(BizExceptionEnum.INVENTORY_ID_ERROR);
        }
        Inventory inventoryById = inventoryMapper.getInventoryById(id);
        if (inventoryById==null){
            throw new BizException(BizExceptionEnum.INVENTORY_ID_NOT_SEARCH);
        }
        return inventoryById;
    }

    @Override
    public List<Inventory> getInventoryByProductId(BigInteger id) {
        if (id==null){
            throw  new BizException(BizExceptionEnum.PRODUCT_ID_ERROR);
        }
        if (productMapper.getProductById(id)==null){
            throw  new BizException(BizExceptionEnum.PRODUCT_NOT_EXIT);
        }
        List<Inventory> inventoryByProductId = inventoryMapper.getInventoryByProductId(id);
        if (inventoryByProductId.isEmpty()){
            throw  new BizException(BizExceptionEnum.INVENTORY_PRODUCTID_NOT_SEARCH);
        }
        return inventoryByProductId;
    }

    @Override
    public List<Inventory> getInventoryByWarehouseId(BigInteger id) {
        if (id==null){
            throw  new BizException(BizExceptionEnum.WAREHOUSE_ID_ERROR);
        }
        if (warehouseMapper.getWarehouseById(id)==null){
            throw  new BizException(BizExceptionEnum.WAREHOUSE_ID_NOT_SEARCH);
        }
        List<Inventory> inventoryByWarehouseId = inventoryMapper.getInventoryByWarehouseId(id);
        if (inventoryByWarehouseId.isEmpty()){
            throw new BizException(BizExceptionEnum.INVENTORY_WAREHOUSEID_NOT_SEARCH);
        }
        return inventoryMapper.getInventoryByWarehouseId(id);
    }

    @Override
    public PageInfo<InventoryWithProductAndWarehouseDTO> getAllInventoryInfo(int pageNum, int pageSize) {
        try (com.github.pagehelper.Page<InventoryWithProductAndWarehouseDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<InventoryWithProductAndWarehouseDTO> list = inventoryMapper.getAllInventoryInfo();
            return new PageInfo<>(list);
        }
    }

    @Override
    public InventoryWithProductAndWarehouseDTO getInventoryInfoById(BigInteger id) {
        if (id==null){
            throw  new BizException(BizExceptionEnum.INVENTORY_ID_ERROR);
        }
        InventoryWithProductAndWarehouseDTO inventoryInfoById = inventoryMapper.getInventoryInfoById(id);
        if (inventoryInfoById==null){
            throw new BizException(BizExceptionEnum.INVENTORY_ID_NOT_SEARCH);
        }
        return inventoryInfoById;
    }

    @Override
    public PageInfo<InventoryWithProductAndWarehouseDTO> getInventoryInfoByProductId(int pageNum, int pageSize, BigInteger id) {
        if (id==null){
            throw  new BizException(BizExceptionEnum.PRODUCT_ID_ERROR);
        }
        if (productMapper.getProductById(id)==null) {
            throw new BizException(BizExceptionEnum.PRODUCT_NOT_EXIT);
        }
        try (com.github.pagehelper.Page<InventoryWithProductAndWarehouseDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<InventoryWithProductAndWarehouseDTO> inventoryInfoByProductId = inventoryMapper.getInventoryInfoByProductId(id);
            if (inventoryInfoByProductId.isEmpty()){
                throw new BizException(BizExceptionEnum.INVENTORY_PRODUCTID_NOT_SEARCH);
            }
            return new PageInfo<>(inventoryInfoByProductId);
        }
    }

    @Override
    public PageInfo<InventoryWithProductAndWarehouseDTO> getInventoryInfoByProductName(int pageNum, int pageSize, String productName) {
        try (com.github.pagehelper.Page<InventoryWithProductAndWarehouseDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<InventoryWithProductAndWarehouseDTO> list = inventoryMapper.getInventoryInfoByProductName(productName);
            return new PageInfo<>(list);
        }
    }

    @Override
    public PageInfo<InventoryWithProductAndWarehouseDTO> getInventoryInfoByProductCode(int pageNum, int pageSize, String productCode) {
        try (com.github.pagehelper.Page<InventoryWithProductAndWarehouseDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<InventoryWithProductAndWarehouseDTO> inventoryInfoByProductCode = inventoryMapper.getInventoryInfoByProductCode(productCode);
            if (inventoryInfoByProductCode.isEmpty()){
                throw new BizException(BizExceptionEnum.INVENTORY_PRODUCTCODE_NOT_SEARCH);
            }
            return new PageInfo<>(inventoryInfoByProductCode);
        }
    }

    @Override
    public PageInfo<InventoryWithProductAndWarehouseDTO> getInventoryInfoByWarehouseId(int pageNum, int pageSize, BigInteger warehouseId) {
        if (warehouseId==null||warehouseMapper.getWarehouseById(warehouseId)==null){
            throw new BizException(BizExceptionEnum.WAREHOUSE_ID_ERROR);
        }
        try (com.github.pagehelper.Page<InventoryWithProductAndWarehouseDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<InventoryWithProductAndWarehouseDTO> list = inventoryMapper.getInventoryInfoByWarehouseId(warehouseId);
            return new PageInfo<>(list);
        }
    }

    @Override
    public PageInfo<InventoryWithProductAndWarehouseDTO> getInventoryInfoByCategoryName(int pageNum, int pageSize, String categoryName) {
        try (com.github.pagehelper.Page<InventoryWithProductAndWarehouseDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<InventoryWithProductAndWarehouseDTO> list = inventoryMapper.getInventoryInfoByCategoryName(categoryName);
            return new PageInfo<>(list);
        }
    }

    @Override
    public PageInfo<InventoryWithProductAndWarehouseDTO> getInventoryInfoBySupplierName(int pageNum, int pageSize, String supplierName) {
        try (com.github.pagehelper.Page<InventoryWithProductAndWarehouseDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<InventoryWithProductAndWarehouseDTO> inventoryInfoBySupplierName = inventoryMapper.getInventoryInfoBySupplierName(supplierName);
            if (inventoryInfoBySupplierName.isEmpty()){
                throw  new BizException(BizExceptionEnum.INVENTORY_SUPPLIERNAME_NOT_SEARCH);
            }
            return new PageInfo<>(inventoryInfoBySupplierName);
        }
    }

    @Override
    public PageInfo<TotalInventoryWithProductAndWarehouse> getMinStockInfoInventory(int pageNum, int pageSize) {
        try (com.github.pagehelper.Page<InventoryWithProductAndWarehouseDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<TotalInventoryWithProductAndWarehouse> list = inventoryMapper.getMinStockInfoInventory();
            return new PageInfo<>(list);
        }
    }

    @Override
    public PageInfo<InventoryWithProductAndWarehouseDTO> getInfoInventoryByDaysLeft(int pageNum, int pageSize, Integer daysLeft) {
        try (com.github.pagehelper.Page<InventoryWithProductAndWarehouseDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<InventoryWithProductAndWarehouseDTO> list = inventoryMapper.getInfoInventoryByDaysLeft(daysLeft);
            return new PageInfo<>(list);
        }
    }

    @Override
    public PageInfo<TotalInventoryWithProductAndWarehouse> getAllTotalInventory(int pageNum, int pageSize) {
        try (com.github.pagehelper.Page<TotalInventoryWithProductAndWarehouse> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<TotalInventoryWithProductAndWarehouse> list = inventoryMapper.getAllTotalInventory();
            return new PageInfo<>(list);
        }
    }

    @Override
    public TotalInventoryWithProductAndWarehouse getTotalInventoryByProductId(BigInteger id) {
        if (id==null){
            throw  new BizException(BizExceptionEnum.PRODUCT_ID_ERROR);
        }
        if (productMapper.getProductById(id)==null){
            throw  new BizException(BizExceptionEnum.PRODUCT_NOT_EXIT);
        }
        TotalInventoryWithProductAndWarehouse totalInventoryByProductId = inventoryMapper.getTotalInventoryByProductId(id);
        if (totalInventoryByProductId==null){
            throw  new BizException(BizExceptionEnum.INVENTORY_PRODUCTID_NOT_SEARCH);
        }
        return totalInventoryByProductId;
    }

    @Override
    public PageInfo<TotalInventoryWithProductAndWarehouse> getTotalInventoryByProductName(int pageNum, int pageSize, String productName) {
        try (com.github.pagehelper.Page<TotalInventoryWithProductAndWarehouse> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<TotalInventoryWithProductAndWarehouse> list = inventoryMapper.getTotalInventoryByProductName(productName);
            return new PageInfo<>(list);
        }
    }

    @Override
    public PageInfo<TotalInventoryWithProductAndWarehouse> getTotalInventoryBySupplierName(int pageNum, int pageSize, String supplierName) {
        try (com.github.pagehelper.Page<TotalInventoryWithProductAndWarehouse> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<TotalInventoryWithProductAndWarehouse> list = inventoryMapper.getTotalInventoryBySupplierName(supplierName);
            return new PageInfo<>(list);
        }
    }
    @Override
    public Integer getTotalInventoryCount() {
        return inventoryMapper.getTotalInventoryCount();
    }
    
}
