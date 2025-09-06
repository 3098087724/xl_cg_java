package com.xlproject.modules.service.product;


import com.github.pagehelper.PageInfo;
import com.xlproject.modules.dto.productDto.ProductWithCategoryAndSupplierDTO;
import com.xlproject.modules.entity.bean.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public interface ProductService {
    PageInfo<Product> getAllProduct(int pageNum, int pageSize);
    Product getProductById(BigInteger id);
    Product getProductByName(String name);
    PageInfo<Product> getProductsLikeName(int pageNum, int pageSize, String name);
    Product getProductByCode(String code);

    PageInfo<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplier(int pageNum,int pageSize);
    PageInfo<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplierLikeName(int pageNum,int pageSize,String name);
    ProductWithCategoryAndSupplierDTO getProductWithCategoryAndSupplierByCode(String code);
    PageInfo<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplierLikeSupplierName(int pageNum,int pageSize,String supplierName);
    PageInfo<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplierLikeCategoryName(int pageNum,int pageSize,String categoryName);

    String getNextCode();
    int addProduct(Product newProduct, MultipartFile file) throws IOException;
    int updProduct(Product product,MultipartFile file);
    int delProductById(BigInteger id);

    PageInfo<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplierByChange(int pageNum, int pageSize, String prodectName, String categoryName, String supplierName);
}
