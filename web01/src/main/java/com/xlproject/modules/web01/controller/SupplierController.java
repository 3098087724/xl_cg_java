package com.xlproject.modules.web01.controller;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.response.R;
import com.xlproject.modules.dto.supplierDto.SupplierWithProductsDTO;
import com.xlproject.modules.entity.bean.Supplier;
import com.xlproject.modules.service.supplier.SupplierService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
public class SupplierController {
    private SupplierService supplierService;
    @Autowired
    public  void setSupplierService(SupplierService supplierService){this.supplierService=supplierService;}

    @GetMapping("/suppliers")
    public R<PageInfo<Supplier>> getAllSupplier(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize){
        return  R.OK("获取所有供应商信息",supplierService.getAllSupplier(pageNum,pageSize));
    }
    @GetMapping("/supplier/{id}")
    public  R<Supplier>getSupplierById(@PathVariable @NotBlank BigInteger id){
        return R.OK("根据id获取供应商",supplierService.getSupplierById(id));
    }
    @GetMapping("/supplier")
    public R<Supplier> getSupplierByName(@RequestParam @NotBlank String name){
        return R.OK("根据名字获取供应商信息",supplierService.getSupplierByName(name));
    }

    @GetMapping("/suppliers/name")
    public R<PageInfo<Supplier>> getSuppliersLikeName(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String name){
        return R.OK("根据供应商名搜索类似名字的供应商信息",supplierService.getSuppliersLikeName(pageNum,pageSize,name));
    }
    @PostMapping("/supplier")
    public R<?>addSupplier(@RequestBody @Valid Supplier supplier){
        int i = supplierService.addSupplier(supplier);
        if (i>0){
            return  R.OK("新增供应商成功",supplier);
        }
        return  R.ERROR(4000,"新增供应商失败，请重试");
    }

    @PutMapping("/supplier")
    public R<?>updSupplier(@RequestBody Supplier supplier){
        int i = supplierService.updSupplier(supplier);
        if (i>0){
            return R.OK("更改供应商信息成功");
        }
        return  R.ERROR(4000,"更改供应商信息错误，请重试");
    }

    @DeleteMapping("/supplier/{id}")
    public R<?> delSupplier(@PathVariable BigInteger id){
        int i = supplierService.delSupplier(id);
        if (i>0){
            return R.OK("删除供应商成功");
        }
        return R.ERROR(4000,"删除供应商失败，请重试");
    }

    @GetMapping("/supplier/product/info")
    public  R<PageInfo<SupplierWithProductsDTO>> getSupplierWithProductsLikeName
            (@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String name){
        return R.OK("获取供应商和商品信息",supplierService.getSupplierWithProductsLikeName(pageNum,pageSize,name));
    }

}
