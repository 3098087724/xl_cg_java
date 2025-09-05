package com.xlproject.modules.dao.mapper;

import com.xlproject.modules.dto.warehouseDto.WarehouseWithUserDTO;
import com.xlproject.modules.entity.bean.Warehouse;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface WarehouseMapper {
    Integer getMaxCode();
    List<Warehouse> getAllWarehouse();
    Warehouse getWarehouseById(BigInteger id);
    Warehouse getWarehouseByCode(String code);
    List<Warehouse> getWarehouseLikeCode(String code);
    List<Warehouse> getWarehouseLikeName(String name);
    int addWarehouse(Warehouse warehouse);
    Warehouse getWarehouseByNameAndLocation(String name,String location);
    int updWarehouse(Warehouse warehouse);
    int delWarehouseById(BigInteger id);

    List<WarehouseWithUserDTO> getAllWarehouseWithUser();
    List<WarehouseWithUserDTO> getWarehouseWithUserLikeCode(String code);
    List<WarehouseWithUserDTO> getWarehouseWithUserLikeName(String name);
}
