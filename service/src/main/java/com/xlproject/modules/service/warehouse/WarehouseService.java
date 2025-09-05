package com.xlproject.modules.service.warehouse;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.dto.warehouseDto.WarehouseWithUserDTO;
import com.xlproject.modules.entity.bean.Warehouse;

import java.math.BigInteger;

public interface WarehouseService {
    //List<Warehouse> getAllWareHouse();
    Warehouse getWarehouseById(BigInteger id);
    Warehouse getWarehouseByCode(String code);
    //List<Warehouse> getWarehouseLikeName(String name);
    int addWarehouse(Warehouse warehouse);
    int updWarehouse(Warehouse warehouse);
    int delWarehouse(BigInteger id);
    PageInfo<WarehouseWithUserDTO> getAllWarehouseWithUser(int pageNum, int pageSize);
    PageInfo<WarehouseWithUserDTO> getWarehouseWithUserLikeName(int pageNum,int pageSize,String name);
    PageInfo<WarehouseWithUserDTO> getWarehouseWithUserByCode(int pageNum,int pageSize,String code);
}
