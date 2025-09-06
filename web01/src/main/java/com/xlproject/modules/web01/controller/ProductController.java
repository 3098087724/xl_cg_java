package com.xlproject.modules.web01.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.response.R;
import com.xlproject.modules.dto.productDto.ProductWithCategoryAndSupplierDTO;
import com.xlproject.modules.entity.bean.Product;
import com.xlproject.modules.service.product.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;

@RestController
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService){
        this.productService=productService;
    }

    @GetMapping("/products")
    public R<PageInfo<Product>> getAllProcudt(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize){
        return R.OK("获取所有商品信息",productService.getAllProduct(pageNum,pageSize));
    }

    @GetMapping("/product/id/{id}")
    public  R<Product> getProductById(@PathVariable @NotNull BigInteger id){
        System.out.println(id);
        return  R.OK("根据id获取商品信息成功",productService.getProductById(id));
    }

    @GetMapping("/product")
    public  R<Product> getProductById(@RequestParam @NotNull String name){
        return  R.OK("根据商品名获取商品信息成功",productService.getProductByName(name));
    }

    @GetMapping("/products/name")
    public R<PageInfo<Product>> getProductsLikeName(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam String name)
    {
        return R.OK("获取商品信息",productService.getProductsLikeName(pageNum,pageSize,name));
    }

    @GetMapping("/products/code")
    public R<Product> getProductsByCode(@RequestParam String code)
    {
        return R.OK("获取商品信息",productService.getProductByCode(code));
    }

    @GetMapping("/products/info")
    public  R<PageInfo<ProductWithCategoryAndSupplierDTO>> getAllProductWithCategoryAndSupplier(
            @RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "10") int pageSize){
        return  R.OK("获取商品列表详细信息",productService.getAllProductWithCategoryAndSupplier(pageNum,pageSize));
    }
    @GetMapping("/products/info/name")
    public  R<PageInfo<ProductWithCategoryAndSupplierDTO>> getAllProductWithCategoryAndSupplierLikeName(
            @RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "10") int pageSize,@RequestParam(required = false) String name){
        return  R.OK("获取商品列表详细信息",productService.getAllProductWithCategoryAndSupplierLikeName(pageNum,pageSize,name));
    }
    @GetMapping("/product/info/code")
    public  R<ProductWithCategoryAndSupplierDTO> getProductWithCategoryAndSupplierByCode(@RequestParam  String code){
        return  R.OK("获取商品列表详细信息",productService.getProductWithCategoryAndSupplierByCode(code));
    }
    @GetMapping("/products/info/category")
    public  R<PageInfo<ProductWithCategoryAndSupplierDTO>> getAllProductWithCategoryAndSupplierLikeCategoryName(
            @RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "10") int pageSize,@RequestParam(required = false) String name){
        return  R.OK("获取商品列表详细信息",productService.getAllProductWithCategoryAndSupplierLikeCategoryName(pageNum,pageSize,name));
    }
    @GetMapping("/products/info/supplier")
    public  R<PageInfo<ProductWithCategoryAndSupplierDTO>> getAllProductWithCategoryAndSupplierLikeSupplierName(
            @RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "10") int pageSize,@RequestParam(required = false) String name){
        return  R.OK("获取商品列表详细信息",productService.getAllProductWithCategoryAndSupplierLikeSupplierName(pageNum,pageSize,name));
    }

    @GetMapping("/products/info/change")
    public  R<PageInfo<ProductWithCategoryAndSupplierDTO>> getAllProductWithCategoryAndSupplierLikeSupplierName(
            @RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String prodectName,@RequestParam(required = false) String categoryName,@RequestParam(required = false) String supplierName
            ){
        return  R.OK("获取商品列表详细信息",productService.getAllProductWithCategoryAndSupplierByChange(pageNum,pageSize,prodectName,categoryName,supplierName));
    }

    @GetMapping("/product/nextCode")
    public R<String> getNextCode(){
        return R.OK("获取自动生成的商品编号",productService.getNextCode());
    }
    @PostMapping("/product")
    public R<Product> addProduct(@RequestPart("product") @Valid Product product,
                           @RequestPart(value = "productImage", required = false) MultipartFile productImage) throws IOException {
        int i = productService.addProduct(product, productImage);
        if (i > 0) {
            return R.OK("新增商品成功",product);
        }
        return R.ERROR(3000, "新增商品失败，请重试");
    }
    @PutMapping("/product")
    public R<?> updProduct(@RequestPart(value = "product") String productJson,
                           @RequestPart(value = "productImage", required = false) MultipartFile productImage) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(productJson, Product.class);
        int i = productService.updProduct(product, productImage);
        if (i > 0) {
            return R.OK("更新成功");
        }
        return R.ERROR(3000, "更新失败，请重试");
    }

    @DeleteMapping("/product/{id}")
    public  R<?> delProductById(@PathVariable BigInteger id){
        int i = productService.delProductById(id);
        if (i>0){
            return R.OK("删除商品成功");
        }
        return  R.ERROR(3000,"删除商品错误");
    }

}
