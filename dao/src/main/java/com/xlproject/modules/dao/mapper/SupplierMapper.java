package com.xlproject.modules.dao.mapper;

import com.xlproject.modules.dto.supplierDto.SupplierWithProductsDTO;
import com.xlproject.modules.entity.bean.Supplier;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface SupplierMapper {
    Supplier getSupplierById(BigInteger id);
    List<Supplier> getAllSupplier();
    Supplier getSupplierByName(String name);
    List<Supplier> getSuppliersLikeName(String name);
    int addSupplier(Supplier supplier);
    int updSupplier(Supplier supplier);
    int delSupplier(BigInteger id);
    List<SupplierWithProductsDTO> getSupplierWithProductsLikeName(String name);
}
