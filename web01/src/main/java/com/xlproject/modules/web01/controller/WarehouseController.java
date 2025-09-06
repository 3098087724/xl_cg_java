package com.xlproject.modules.web01.controller;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.response.R;
import com.xlproject.modules.dto.warehouseDto.WarehouseWithUserDTO;
import com.xlproject.modules.entity.bean.Warehouse;
import com.xlproject.modules.service.Generator.BizCodeGenerator;
import com.xlproject.modules.service.warehouse.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;

@RestController
public class WarehouseController {
    private WarehouseService warehouseService;
    private BizCodeGenerator bizCodeGenerator;
    @Autowired
    public  void  setWarehouseService(WarehouseService warehouseService){this.warehouseService=warehouseService;}
    @Autowired
    public void setBizCodeGenerator(BizCodeGenerator bizCodeGenerator){this.bizCodeGenerator=bizCodeGenerator;}

    @GetMapping("/warehouses")
    public R<PageInfo<WarehouseWithUserDTO>> getAllWarehouseWithUser(
            @RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "10")int pageSize){
        return R.OK("获取所有仓库信息",warehouseService.getAllWarehouseWithUser(pageNum,pageSize));
    }

    @GetMapping("/warehouse/id")
    public R<Warehouse> getWarehouseById(@RequestParam BigInteger id){
        return R.OK("获取指定id的仓库信息",warehouseService.getWarehouseById(id));
    }

    @GetMapping("/warehouse/code")
    public R<Warehouse> getWarehouseByCode(@RequestParam String code){
        return R.OK("获取指定code编号的仓库信息",warehouseService.getWarehouseByCode(code));
    }

    @GetMapping("/warehouse/codes")
    public R<PageInfo<WarehouseWithUserDTO>> getWarehouseWithUserLikeCode(
            @RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "10")int pageSize,@RequestParam(required = false) String code){
        return R.OK("获取指定code编号的仓库信息",warehouseService.getWarehouseWithUserByCode(pageNum,pageSize,code));
    }

    @GetMapping("/warehouse/name")
    public  R<PageInfo<WarehouseWithUserDTO>>getWarehouseWithUserLikeName(
            @RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "10")int pageSize,@RequestParam(required = false) String name){
        return  R.OK("根据仓库名获取仓库信息",warehouseService.getWarehouseWithUserLikeName(pageNum,pageSize,name));
    }

    @PostMapping("/warehouse")
    public  R<?> addWarehouse(@RequestBody @Valid Warehouse warehouse){
        int i = warehouseService.addWarehouse(warehouse);
        if (i>0){
            return R.OK("新增仓库成功",warehouse);
        }
        return R.ERROR(5000,"新增仓库失败，请重试");
    }

    @PutMapping("/warehouse")
    public  R<?> updWarehouse(@RequestBody Warehouse warehouse) {
        System.out.println(warehouse);
        int i = warehouseService.updWarehouse(warehouse);
        if (i > 0) {
            return R.OK("更新仓库信息成功");
        }
        return R.ERROR(5000, "更新仓库信息错误");
    }

    @DeleteMapping("/warehouse")
    public R<?> delWarehouseById(@RequestParam BigInteger id){
        int i = warehouseService.delWarehouse(id);
        if (i>0){
            return R.OK("删除该仓库成功");
        }
        return  R.ERROR(5000,"删除仓库失败，请重试");
    }
    @GetMapping("/warehouse/nextCode")
    public R<String>getWarehouseNextCode(){
        return R.OK("下次仓库编号",bizCodeGenerator.generate(BizCodeGenerator.BizCode.WAREHOUSE_CODE));
    }
}
