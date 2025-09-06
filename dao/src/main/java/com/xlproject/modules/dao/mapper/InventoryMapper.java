package com.xlproject.modules.dao.mapper;

import com.xlproject.modules.dto.InventoryDto.InventoryWithProductAndWarehouseDTO;
import com.xlproject.modules.dto.InventoryDto.TotalInventoryWithProductAndWarehouse;
import com.xlproject.modules.entity.bean.Inventory;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface InventoryMapper {
    Inventory getInventoryById(BigInteger id);
    List<Inventory> getInventoryByProductId(BigInteger id);
    List<Inventory> getInventoryByWarehouseId(BigInteger id);
    int getCountBySPCodeAndProductionDate(String code, LocalDate date);
    int addInventory(Inventory inventory);
    Inventory getInventoryByProductAndBatch(BigInteger productId,BigInteger warehouseID,String batchNo);
    int updateInventory(Inventory inventory);

    List<Inventory> genInventoryByBatchNo(String batchNo);
    List<InventoryWithProductAndWarehouseDTO> getAllInventoryInfo();
    InventoryWithProductAndWarehouseDTO getInventoryInfoById(BigInteger id);
    List<InventoryWithProductAndWarehouseDTO> getInventoryInfoByProductId(BigInteger id);
    List<InventoryWithProductAndWarehouseDTO> getInventoryInfoByProductName(String productName);
    List<InventoryWithProductAndWarehouseDTO> getInventoryInfoByProductCode(String productCode);
    List<InventoryWithProductAndWarehouseDTO> getInventoryInfoByWarehouseId(BigInteger warehouseId);
    List<InventoryWithProductAndWarehouseDTO> getInventoryInfoByCategoryName(String categoryName);
    List<InventoryWithProductAndWarehouseDTO> getInventoryInfoBySupplierName(String supplierName);
    List<TotalInventoryWithProductAndWarehouse> getMinStockInfoInventory();
    List<InventoryWithProductAndWarehouseDTO> getInfoInventoryByDaysLeft(Integer daysLeft);

    List<TotalInventoryWithProductAndWarehouse> getAllTotalInventory();
    TotalInventoryWithProductAndWarehouse getTotalInventoryByProductId(BigInteger id);
    List<TotalInventoryWithProductAndWarehouse> getTotalInventoryByProductName(String productName);
    List<TotalInventoryWithProductAndWarehouse> getTotalInventoryBySupplierName(String supplierName);

    List<Inventory> getInventoryByProductIdAndWarehouseIdOrderByExpiration(BigInteger productId, BigInteger warehouseId);

    Integer getTotalInventoryCount();

}
