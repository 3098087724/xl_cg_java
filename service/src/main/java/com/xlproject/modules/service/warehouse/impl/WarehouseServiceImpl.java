package com.xlproject.modules.service.warehouse.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.exception.BizException;
import com.xlproject.modules.common.exception.BizExceptionEnum;
import com.xlproject.modules.dao.mapper.InventoryMapper;
import com.xlproject.modules.dao.mapper.UserMapper;
import com.xlproject.modules.dao.mapper.WarehouseMapper;
import com.xlproject.modules.dto.warehouseDto.WarehouseWithUserDTO;
import com.xlproject.modules.entity.bean.Inventory;
import com.xlproject.modules.entity.bean.User;
import com.xlproject.modules.entity.bean.Warehouse;
import com.xlproject.modules.service.warehouse.WarehouseService;
import com.xlproject.modules.service.Generator.BizCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    //<editor-fold desc="资源">
    private WarehouseMapper warehouseMapper;
    private BizCodeGenerator bizCodeGenerator;
    private InventoryMapper inventoryMapper;
    private UserMapper userMapper;
    @Autowired
    public  void setWareHouseMapper(WarehouseMapper warehouseMapper){this.warehouseMapper=warehouseMapper;}
    @Autowired
    public void  setBizCodeGenerator(BizCodeGenerator bizCodeGenerator){this.bizCodeGenerator=bizCodeGenerator;}
    @Autowired
    public  void setUserMapper(UserMapper userMapper){this.userMapper=userMapper;}
    @Autowired
    public  void setInventoryMapper(InventoryMapper inventoryMapper){this.inventoryMapper=inventoryMapper;}
    //</editor-fold>

    //<editor-fold desc="获取所有仓库信息">

//    @Override
//    public List<Warehouse> getAllWareHouse() {
//            return  warehouseMapper.getAllWarehouse();
//    }
    //</editor-fold>


    /**
     * 获取指定id的仓库信息
     * @param id 参数：id值
     * @return 返回搜索到的仓库信息
     */
    @Override
    public Warehouse getWarehouseById(BigInteger id) {
        Warehouse warehouseById = warehouseMapper.getWarehouseById(id);
        if (warehouseById==null){
            throw new BizException(BizExceptionEnum.WAREHOUSE_ID_NOT_SEARCH);
        }
        return warehouseById;
    }

    /**
     * 获取指定code编号的仓库信息
     * @param code 参数code编号
     * @return 返回搜索到的仓库信息
     */
    @Override
    public Warehouse getWarehouseByCode(String code) {
        if (code!=null){
            code=code.toUpperCase();
        }
        Warehouse warehouseByCode = warehouseMapper.getWarehouseByCode(code);
        if (warehouseByCode==null){
            throw  new BizException(BizExceptionEnum.WAREHOUSE_CODE_NOT_SEARCH);
        }
        return warehouseByCode;
    }

    //<editor-fold desc="根据仓库名搜索仓库信息">

//    @Override
//    public List<Warehouse> getWarehouseLikeName(String name) {
//        return warehouseMapper.getWarehouseLikeName(name);
//    }
    //</editor-fold>

    /**
     * 新增仓库
     * @param warehouse 仓库信息
     * @return 返回受影响行数
     */
    @Override
    public int addWarehouse(Warehouse warehouse) {
        // 检查ID是否已存在
        validateWarehouseIdNotExists(warehouse.getId());

        // 处理仓库编号
        handleWarehouseCode(warehouse);

        // 检查仓库名称和位置是否已存在
        validateWarehouseNameAndLocationNotExists(warehouse);

        // 检查负责人ID是否存在
        validateManagerIdExists(warehouse.getManagerId());

        // 设置创建时间
        warehouse.setCreateTime(LocalDateTime.now());

        return warehouseMapper.addWarehouse(warehouse);
    }

    @Override
    public int updWarehouse(Warehouse warehouse) {
        // 验证ID不能为空
        if (warehouse.getId() == null) {
            throw new BizException(BizExceptionEnum.WAREHOUSE_ID_ERROR);
        }

        // 验证仓库是否存在
        Warehouse existingWarehouse = getWarehouseById(warehouse.getId());

        // 检查仓库名称和位置是否与其他仓库冲突
        validateWarehouseNameAndLocationForUpdate(warehouse, existingWarehouse);

        // 检查负责人ID是否存在
        validateManagerIdExists(warehouse.getManagerId());

        // 验证状态值
        validateWarehouseStatus(warehouse.getStatus());

        // 执行更新操作
        return warehouseMapper.updWarehouse(warehouse);
    }

    // 私有辅助方法,判断仓库id是否已存在
    private void validateWarehouseIdNotExists(BigInteger id) {
        if (id != null) {
            Warehouse warehouseById = warehouseMapper.getWarehouseById(id);
            if (warehouseById != null) {
                throw new BizException(BizExceptionEnum.WAREHOUSE_ID_EXIT);
            }
        }
    }

    //处理仓库编号，看是否存在
    private void handleWarehouseCode(Warehouse warehouse) {
        String code = warehouse.getCode();
        if (code != null) {
            // 转换为大写并检查是否已存在
            String upperCaseCode = code.toUpperCase();
            warehouse.setCode(upperCaseCode);
            Warehouse existingWarehouse = warehouseMapper.getWarehouseByCode(upperCaseCode);
            if (existingWarehouse != null) {
                throw new BizException(BizExceptionEnum.WAREHOUSE_CODE_EXIT);
            }
        } else {
            // 自动生成仓库编号
            warehouse.setCode(bizCodeGenerator.generate(BizCodeGenerator.BizCode.WAREHOUSE_CODE));
        }
    }


    // 检查仓库名称和位置是否已存在
    private void validateWarehouseNameAndLocationNotExists(Warehouse warehouse) {
        if (warehouse.getName() != null && warehouse.getLocation() != null) {
            Warehouse warehouseByNameAndLocation = warehouseMapper.getWarehouseByNameAndLocation(
                    warehouse.getName(), warehouse.getLocation());
            if (warehouseByNameAndLocation != null) {
                throw new BizException(BizExceptionEnum.WAREHOUSE_EXIT);
            }
        }
    }
    //检查仓库名称和位置是否与其他仓库冲突
    private void validateWarehouseNameAndLocationForUpdate(Warehouse warehouse, Warehouse existingWarehouse) {
        String name = warehouse.getName();
        String location = warehouse.getLocation();

        // 如果名称或位置有变更，则检查是否与其他仓库冲突
        if (name != null || location != null) {
            String checkName = name != null ? name : existingWarehouse.getName();
            String checkLocation = location != null ? location : existingWarehouse.getLocation();

            Warehouse warehouseByNameAndLocation = warehouseMapper.getWarehouseByNameAndLocation(
                    checkName, checkLocation);
            if (warehouseByNameAndLocation != null &&
                    !warehouseByNameAndLocation.getId().equals(warehouse.getId())) {
                throw new BizException(BizExceptionEnum.WAREHOUSE_EXIT);
            }
        }
    }


    //检查负责人ID是否存在
    private void validateManagerIdExists(BigInteger managerId) {
        if (managerId != null) {
            User user = userMapper.queryUserById(managerId);
            if (user == null) {
                throw new BizException(BizExceptionEnum.WAREHOUSE_MANAGER_ID_NOT_SEARCH);
            }
        }
    }

    //验证状态值
    private void validateWarehouseStatus(Integer status) {
        if (status != null && status != 0 && status != 1) {
            throw new BizException(BizExceptionEnum.WAREHOUSE_STATUS_ERROR);
        }
    }

    @Override
    public int delWarehouse(BigInteger id) {
        getWarehouseById(id);
        List<Inventory> inventoryByWarehouseId = inventoryMapper.getInventoryByWarehouseId(id);
        if (!inventoryByWarehouseId.isEmpty()){
            throw  new BizException(BizExceptionEnum.WAREHOUSE_PRODUCT_EXIT);
        }
        return warehouseMapper.delWarehouseById(id);
    }

    @Override
    public PageInfo<WarehouseWithUserDTO> getAllWarehouseWithUser(int pageNum, int pageSize) {
        try (com.github.pagehelper.Page<WarehouseWithUserDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<WarehouseWithUserDTO> allWarehouseWithUser = warehouseMapper.getAllWarehouseWithUser();
            return new PageInfo<>(allWarehouseWithUser);
        }
    }

    @Override
    public PageInfo<WarehouseWithUserDTO> getWarehouseWithUserLikeName(int pageNum, int pageSize, String name) {
        try(com.github.pagehelper.Page<WarehouseWithUserDTO>_page=PageHelper.startPage(pageNum,pageSize)) {

            List<WarehouseWithUserDTO> warehouseWithUserLikeName = warehouseMapper.getWarehouseWithUserLikeName(name);
            return new PageInfo<>(warehouseWithUserLikeName);
        }

    }

    @Override
    public PageInfo<WarehouseWithUserDTO> getWarehouseWithUserByCode(int pageNum, int pageSize, String code) {
        try(com.github.pagehelper.Page<WarehouseWithUserDTO>_page=PageHelper.startPage(pageNum,pageSize)) {
            if (code!=null) {
                code=code.toUpperCase();
            }
            List<WarehouseWithUserDTO> warehouseWithUserLikeCode = warehouseMapper.getWarehouseWithUserLikeCode(code);
            return new PageInfo<>(warehouseWithUserLikeCode);
        }
    }
}
