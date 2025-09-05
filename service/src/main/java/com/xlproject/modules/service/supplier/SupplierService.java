package com.xlproject.modules.service.supplier;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.dto.supplierDto.SupplierWithProductsDTO;
import com.xlproject.modules.entity.bean.Supplier;

import java.math.BigInteger;
import java.util.List;

public interface SupplierService {
    Supplier getSupplierById(BigInteger id);
    PageInfo<Supplier> getAllSupplier(int pageNum, int pageSize);
    Supplier getSupplierByName(String name);
    PageInfo<Supplier> getSuppliersLikeName(int pageNum, int pageSize, String name);
    int addSupplier(Supplier supplier);
    int updSupplier(Supplier supplier);
    int delSupplier(BigInteger id);
    PageInfo<SupplierWithProductsDTO> getSupplierWithProductsLikeName(int pageNum, int pageSize, String name);
}
