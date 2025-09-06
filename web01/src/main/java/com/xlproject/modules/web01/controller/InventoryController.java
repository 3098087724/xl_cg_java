package com.xlproject.modules.web01.controller;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.response.R;
import com.xlproject.modules.dto.InventoryDto.InventoryWithProductAndWarehouseDTO;
import com.xlproject.modules.dto.InventoryDto.TotalInventoryWithProductAndWarehouse;
import com.xlproject.modules.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
public class InventoryController {
    private InventoryService inventoryService;
    @Autowired
    public  void setInventoryService(InventoryService inventoryService){this.inventoryService=inventoryService;}
    @GetMapping("/inventory/info")
    public R<PageInfo<InventoryWithProductAndWarehouseDTO>> getAllInventoryInfo(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize){
        return R.OK("获取库存详细信息",inventoryService.getAllInventoryInfo(pageNum,pageSize));
    }
    @GetMapping("/inventory/info/id/{id}")
    public R<InventoryWithProductAndWarehouseDTO> getInventoryInfoById(@PathVariable BigInteger id){
        return R.OK("根据ID获取库存详细信息",inventoryService.getInventoryInfoById(id));
    }
    @GetMapping("/inventory/info/productid/{id}")
    public R<PageInfo<InventoryWithProductAndWarehouseDTO>> getInventoryInfoByProductId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @PathVariable BigInteger id){
        return R.OK("根据ID获取库存详细信息",inventoryService.getInventoryInfoByProductId(pageNum,pageSize,id));
    }
    @GetMapping("/inventory/info/productName")
    public R<PageInfo<InventoryWithProductAndWarehouseDTO>> getInventoryInfoByProductName(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String productName){
        return R.OK("根据产品名获取库存详细信息",inventoryService.getInventoryInfoByProductName(pageNum,pageSize,productName));
    }
    @GetMapping("/inventory/info/productCode")
    public R<PageInfo<InventoryWithProductAndWarehouseDTO>> getInventoryInfoByProductCode(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String productCode){
        return R.OK("根据产品编号获取库存详细信息",inventoryService.getInventoryInfoByProductCode(pageNum,pageSize,productCode==null ? null:productCode.toUpperCase()));
    }

    @GetMapping("/inventory/info/warehouseId")
    public R<PageInfo<InventoryWithProductAndWarehouseDTO>> getInventoryInfoByWarehouseId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) BigInteger warehouseId){
        return R.OK("根据仓库ID获取库存详细信息",inventoryService.getInventoryInfoByWarehouseId(pageNum,pageSize,warehouseId));
    }
    @GetMapping("/inventory/info/categoryName")
    public R<PageInfo<InventoryWithProductAndWarehouseDTO>> getInventoryInfoByCategoryName(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String categoryName){
        return R.OK("根据商品分类获取库存详细信息",inventoryService.getInventoryInfoByCategoryName(pageNum,pageSize,categoryName));
    }
    @GetMapping("/inventory/info/supplierName")
    public R<PageInfo<InventoryWithProductAndWarehouseDTO>> getInventoryInfoBySupplierName(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String supplierName){
        return R.OK("根据供应商名获取库存详细信息",inventoryService.getInventoryInfoBySupplierName(pageNum,pageSize,supplierName));
    }

    @GetMapping("/inventory/info/minStock")
    public R<PageInfo<TotalInventoryWithProductAndWarehouse>> getMinStockInfoInventory(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize){
        return R.OK("获取库存预警的库存详细信息",inventoryService.getMinStockInfoInventory(pageNum,pageSize));
    }

    @GetMapping("/inventory/info/daysLeft")
    public R<PageInfo<InventoryWithProductAndWarehouseDTO>> getInfoInventoryByDaysLeft(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) Integer daysLeft){
        return R.OK("获取商品保质期小于指定剩余保质期天数的商品库存详细信息",inventoryService.getInfoInventoryByDaysLeft(pageNum,pageSize,daysLeft));
    }


    @GetMapping("/inventory/total")
    public R<PageInfo<TotalInventoryWithProductAndWarehouse>> getTotalInventory(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize){
        return R.OK("获取各商品总库存信息",inventoryService.getAllTotalInventory(pageNum,pageSize));
    }

    @GetMapping("/inventory/total/productId/{id}")
    public R<TotalInventoryWithProductAndWarehouse> getTotalInventoryByProductId(@PathVariable BigInteger id){
        return R.OK("根据商品ID获取商品总库存信息",inventoryService.getTotalInventoryByProductId(id));
    }

    @GetMapping("/inventory/total/productName")
    public R<PageInfo<TotalInventoryWithProductAndWarehouse>> getTotalInventoryByProductName(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String productName){
        return R.OK("商品名获取各商品总库存信息",inventoryService.getTotalInventoryByProductName(pageNum,pageSize,productName));
    }
    @GetMapping("/inventory/total/supplierName")
    public R<PageInfo<TotalInventoryWithProductAndWarehouse>> getTotalInventoryBySupplierName(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String supplierName){
        return R.OK("供应商名获取各商品总库存信息",inventoryService.getTotalInventoryBySupplierName(pageNum,pageSize,supplierName));
    }
    @GetMapping("/inventory/totalCount")
    public R<Integer> getTotalInventoryCount(){
        return R.OK("获取商品总库存数量",inventoryService.getTotalInventoryCount());
    }

}
