package com.xlproject.modules.service.supplier.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.exception.BizException;
import com.xlproject.modules.common.exception.BizExceptionEnum;
import com.xlproject.modules.dao.mapper.ProductMapper;
import com.xlproject.modules.dao.mapper.SupplierMapper;
import com.xlproject.modules.dto.supplierDto.SupplierWithProductsDTO;
import com.xlproject.modules.entity.bean.Product;
import com.xlproject.modules.entity.bean.Supplier;
import com.xlproject.modules.service.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupplierImpl implements SupplierService {
    private SupplierMapper supplierMapper;
    @Autowired
    public  void setSupplierMapper(SupplierMapper supplierMapper){this.supplierMapper=supplierMapper;}
    private ProductMapper productMapper;
    @Autowired
    private  void setProductMapper(ProductMapper productMapper){this.productMapper=productMapper;}

    /**
     * 根据id获取供应商信息
     * @param id 参数id值
     * @return 返回获取的供应商信息
     */
    @Override
    public Supplier getSupplierById(BigInteger id) {
        Supplier supplierById = supplierMapper.getSupplierById(id);
        if (supplierById==null){
            throw  new BizException(BizExceptionEnum.SUPPLIER_NOT_SEARCH_ID);
        }
        return supplierById;
    }

    /**
     * 获取所有供应商信息
     * @return 返回所有供应商信息
     */
    @Override
    public PageInfo<Supplier> getAllSupplier(int pageNum, int pageSize) {
        try (com.github.pagehelper.Page<Supplier> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<Supplier> list = supplierMapper.getAllSupplier();
            return new PageInfo<>(list);
        }
    }

    /**
     * 根据名字搜索供应商信息
     * @param name 参数：供应商名
     * @return 返回供应商信息
     */

    @Override
    public Supplier getSupplierByName(String name) {
        if (name.isBlank()){
            throw  new BizException(BizExceptionEnum.SUPPLIER_NAME_ERROR);
        }
        Supplier supplierByName = supplierMapper.getSupplierByName(name);
        if (supplierByName==null){
            throw  new BizException(BizExceptionEnum.SUPPLIER_NOT_SEARCH_NAME);
        }
        return supplierByName;
    }

    /**
     * 根据供应商名模糊搜索供应商信息
     * @param name 参数供应商名
     * @return 返回符合条件的所有供应商信息
     */
    @Override
    public PageInfo<Supplier> getSuppliersLikeName(int pageNum, int pageSize, String name) {
        try (com.github.pagehelper.Page<Supplier> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<Supplier> list = supplierMapper.getSuppliersLikeName(name);
            return new PageInfo<>(list);
        }
    }

    /**
     * 新增供应商信息
     * @param supplier 供应商信息
     * @return 返回受影响行数
     */
    @Override
    public int addSupplier(Supplier supplier) {
        supplier.setCreateTime(LocalDateTime.now());
        return  supplierMapper.addSupplier(supplier);
    }

    /**
     * 更改供应商信息
     * @param supplier 供应商信息
     * @return 返回受影响行数
     */
    @Override
    public int updSupplier(Supplier supplier) {
        if (supplier==null){
            throw  new BizException(BizExceptionEnum.SUPPLIER_ADD_ERROR);
        }
        if (supplier.getId()==null){
            throw  new BizException(BizExceptionEnum.SUPPLIER_UPD_ID_ERROR);
        }
        Supplier supplierById = supplierMapper.getSupplierById(supplier.getId());
        if (supplierById==null){
            throw  new BizException(BizExceptionEnum.SUPPLIER_NOT_SEARCH_ID);
        }
        if (supplier.getStatus()!=0&&supplier.getStatus()!=1){
            throw new BizException(BizExceptionEnum.SUPPLIER_UPD_STATUS_ERROR);
        }
        return supplierMapper.updSupplier(supplier);
    }

    /**
     * 根据供应商id删除供应商
     * @param id 供应商id
     * @return 返回受影响行数
     */
    @Override
    public int delSupplier(BigInteger id) {
        Supplier supplier = supplierMapper.getSupplierById(id);
        if (supplier == null) {
            throw new BizException(BizExceptionEnum.SUPPLIER_NOT_SEARCH_ID);
        }

        List<Product> productBySupplierId = productMapper.getProductBySupplierId(id);
        if (!productBySupplierId.isEmpty()) {
            throw new BizException(BizExceptionEnum.SUPPLIER_HAS_PRODUCTS);
        }
        // 执行删除逻辑
        return supplierMapper.delSupplier(id);
    }

    /**
     * 供应商名模糊搜索供应商供应的商品
     * @param name 参数供应商名
     * @return 返回供应商提供的商品
     */
    @Override
    public PageInfo<SupplierWithProductsDTO> getSupplierWithProductsLikeName(int pageNum, int pageSize, String name) {
        try (com.github.pagehelper.Page<SupplierWithProductsDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<SupplierWithProductsDTO> list = supplierMapper.getSupplierWithProductsLikeName(name);
            return new PageInfo<>(list);
        }
    }
}
