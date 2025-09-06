package com.xlproject.modules.service.inventory;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.dto.InventoryDto.InventoryWithProductAndWarehouseDTO;
import com.xlproject.modules.dto.InventoryDto.TotalInventoryWithProductAndWarehouse;
import com.xlproject.modules.entity.bean.Inventory;

import java.math.BigInteger;
import java.util.List;

public interface InventoryService {
    Inventory getInventoryById(BigInteger id);
    List<Inventory> getInventoryByProductId(BigInteger id);
    List<Inventory> getInventoryByWarehouseId(BigInteger id);
    PageInfo<InventoryWithProductAndWarehouseDTO> getAllInventoryInfo(int pageNum, int pageSize);
    InventoryWithProductAndWarehouseDTO getInventoryInfoById(BigInteger id);
    PageInfo<InventoryWithProductAndWarehouseDTO> getInventoryInfoByProductId(int pageNum, int pageSize, BigInteger id);
    PageInfo<InventoryWithProductAndWarehouseDTO> getInventoryInfoByProductName(int pageNum, int pageSize, String productName);
    PageInfo<InventoryWithProductAndWarehouseDTO> getInventoryInfoByProductCode(int pageNum, int pageSize, String productCode);
    PageInfo<InventoryWithProductAndWarehouseDTO> getInventoryInfoByWarehouseId(int pageNum, int pageSize, BigInteger warehouseId );
    PageInfo<InventoryWithProductAndWarehouseDTO> getInventoryInfoByCategoryName(int pageNum, int pageSize, String categoryName );
    PageInfo<InventoryWithProductAndWarehouseDTO> getInventoryInfoBySupplierName(int pageNum, int pageSize, String supplierName);
    PageInfo<TotalInventoryWithProductAndWarehouse> getMinStockInfoInventory(int pageNum, int pageSize);
    PageInfo<InventoryWithProductAndWarehouseDTO> getInfoInventoryByDaysLeft(int pageNum, int pageSize, Integer daysLeft);
    PageInfo<TotalInventoryWithProductAndWarehouse> getAllTotalInventory(int pageNum, int pageSize);
    TotalInventoryWithProductAndWarehouse getTotalInventoryByProductId(BigInteger id);
    PageInfo<TotalInventoryWithProductAndWarehouse> getTotalInventoryByProductName(int pageNum, int pageSize, String productName);
    PageInfo<TotalInventoryWithProductAndWarehouse> getTotalInventoryBySupplierName(int pageNum, int pageSize, String supplierName);
    Integer getTotalInventoryCount();


}
