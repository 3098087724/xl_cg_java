package com.xlproject.modules.dao.mapper;

import com.xlproject.modules.dto.productDto.ProductWithCategoryAndSupplierDTO;
import com.xlproject.modules.entity.bean.Product;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> getAllProduct();
    Product getProductById(BigInteger id);
    Product getProductByName(String name);
    List<Product> getProductsLikeName(String name);
    Product getProductByCode(String code);
    List<Product> getProductBySupplierId(BigInteger id);
    List<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplier();
    List<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplierLikeName(String name);
    List<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplierLikeSupplierName(String supplierName);
    List<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplierLikeCategoryName(String categoryName);
    ProductWithCategoryAndSupplierDTO getProductWithCategoryAndSupplierByCode(String code);
    Integer getMaxCode();
    int addProduct(Product newProduct);
    int updProduct(Product product);
    int delProductById(BigInteger id);
    ProductWithCategoryAndSupplierDTO getProductWithCategoryAndSupplierById(BigInteger id);

    List<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplierByChange(String productName, String categoryName, String supplierName);
}
