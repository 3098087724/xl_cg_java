package com.xlproject.modules.service.product.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.exception.BizException;
import com.xlproject.modules.common.exception.BizExceptionEnum;
import com.xlproject.modules.dao.mapper.ProductMapper;
import com.xlproject.modules.dto.productDto.ProductWithCategoryAndSupplierDTO;
import com.xlproject.modules.entity.bean.Inventory;
import com.xlproject.modules.entity.bean.Product;
import com.xlproject.modules.service.category.CategoryService;
import com.xlproject.modules.service.inventory.InventoryService;
import com.xlproject.modules.service.product.ProductService;
import com.xlproject.modules.service.supplier.SupplierService;
import com.xlproject.modules.service.Generator.BizCodeGenerator;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductMapper productMapper;
    private BizCodeGenerator bizCodeGenerator;
    private CategoryService categoryService;
    private SupplierService supplierService;
    private InventoryService inventoryService;
    @Value("${web.upload-path}") // 从配置文件中注入路径
    private String uploadPath;

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Autowired
    public void setBizCodeGenerator(BizCodeGenerator bizCodeGenerator) {
        this.bizCodeGenerator = bizCodeGenerator;
    }

    @Autowired
    public void setCategoryMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * 获取所有商品信息
     *
     * @return 商品列表
     */
    @Override
    public PageInfo<Product> getAllProduct(int pageNum, int pageSize) {
        try (com.github.pagehelper.Page<Product> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<Product> list = productMapper.getAllProduct();
            return new PageInfo<>(list);
        }
    }

    /**
     * 根据id值获取商品信息
     *
     * @param id 商品ID
     * @return 返回获取的商品信息
     */
    @Override
    public Product getProductById(BigInteger id) {
        if (id == null) {
            throw new BizException(BizExceptionEnum.PRODUCT_ID_ERROR);
        }
        Product productById = productMapper.getProductById(id);
        if (productById == null) {
            throw new BizException(BizExceptionEnum.PRODUCT_ID_NOT_EXIT);
        }
        return productById;
    }

    /**
     * 根据商品名获取商品信息
     *
     * @param name 商品名称
     * @return 返回获取的商品信息
     */
    @Override
    public Product getProductByName(String name) {
        // 统一使用StringUtils.isBlank进行空值检查
        if (StringUtils.isBlank(name)) {
            throw new BizException(BizExceptionEnum.PRODUCT_NAME_ERROR);
        }
        Product productByName = productMapper.getProductByName(name);
        if (productByName == null) {
            throw new BizException(BizExceptionEnum.PRODUCT_NAME_NOT_EXIT);
        }
        return productByName;
    }

    /**
     * 商品名模糊查询商品
     *
     * @param name 要查的商品名
     * @return 返回查询到的商品列表
     */
    @Override
    public PageInfo<Product> getProductsLikeName(int pageNum, int pageSize, String name) {
        // 建议添加空值检查
        if (StringUtils.isBlank(name)) {
            throw new BizException(BizExceptionEnum.PRODUCT_NAME_ERROR);
        }
        try (com.github.pagehelper.Page<Product> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<Product> list = productMapper.getProductsLikeName(name);
            return new PageInfo<>(list);
        }
    }

    /**
     * 根据商品编号搜索商品信息
     *
     * @param code 商品编号
     * @return 返回商品信息
     */
    @Override
    public Product getProductByCode(String code) {
        if (StringUtils.isBlank(code) || !code.toUpperCase().startsWith("SP")) {
            throw new BizException(BizExceptionEnum.PRODUCT_CODE_ERROR);
        }
        // 先转换再查询，保持一致性
        String upperCode = code.toUpperCase();
        Product productByCode = productMapper.getProductByCode(upperCode);
        if (productByCode == null) {
            throw new BizException(BizExceptionEnum.PRODUCT_CODE_NOT_EXIT);
        }
        return productByCode;
    }

    /**
     * 获取商品表，商品类型表，商品供应商表的联合查询
     *
     * @return 返回商品详细信息列表
     */
    @Override
    public PageInfo<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplier(int pageNum, int pageSize) {
        try (com.github.pagehelper.Page<ProductWithCategoryAndSupplierDTO> page = PageHelper.startPage(pageNum,
                pageSize)) {
            List<ProductWithCategoryAndSupplierDTO> result = productMapper.getAllProductWithCategoryAndSupplier();
            return new PageInfo<>(result);
        }

    }

    /**
     * 根据商品名搜索商品详细信息（包含商品分类名以及商品供应商名）
     *
     * @param name 搜索商品名
     * @return 返回商品详细信息列表
     */
    @Override
    public PageInfo<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplierLikeName(int pageNum,
                                                                                                    int pageSize,
                                                                                                    String name) {
        try (
                com.github.pagehelper.Page<ProductWithCategoryAndSupplierDTO> _page = PageHelper.startPage(pageNum,
                        pageSize)
        ) {
            List<ProductWithCategoryAndSupplierDTO> result =
                    productMapper.getAllProductWithCategoryAndSupplierLikeName(name);
            // 检查列表是否为空而不是null
            if (result.isEmpty()) {
                throw new BizException(BizExceptionEnum.PRODUCT_NAME_NOT_EXIT);
            }
            return new PageInfo<>(result);
        }

    }

    /**
     * 根据商品编号搜索商品详细信息
     *
     * @param code 商品编号
     * @return 返回获取的商品详细信息
     */
    @Override
    public ProductWithCategoryAndSupplierDTO getProductWithCategoryAndSupplierByCode(String code) {
        if (StringUtils.isBlank(code) || !code.toUpperCase().startsWith("SP")) {
            throw new BizException(BizExceptionEnum.PRODUCT_CODE_ERROR);
        }
        String upperCode = code.toUpperCase();
        ProductWithCategoryAndSupplierDTO result = productMapper.getProductWithCategoryAndSupplierByCode(upperCode);
        if (result == null) {
            throw new BizException(BizExceptionEnum.PRODUCT_CODE_NOT_EXIT);
        }
        return result;
    }

    /**
     * 根据供应商名获取商品详细信息
     *
     * @param supplierName 供应商名
     * @return 返回获得的商品详细信息
     */
    @Override
    public PageInfo<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplierLikeSupplierName(int pageNum, int pageSize, String supplierName) {
        try (com.github.pagehelper.Page<ProductWithCategoryAndSupplierDTO> _page = PageHelper.startPage(pageNum,
                pageSize)) {
            List<ProductWithCategoryAndSupplierDTO> result =
                    productMapper.getAllProductWithCategoryAndSupplierLikeSupplierName(supplierName);
            // 检查列表是否为空而不是null
            if (result.isEmpty()) {
                throw new BizException(BizExceptionEnum.PRODUCT_SUPPLIER_NOT_SEARCH);
            }
            return new PageInfo<>(result);
        }
    }

    /**
     * 根据商品分类名获取商品详细信息
     *
     * @param categoryName 商品分类名
     * @return 返回获得的商品详细信息
     */

    @Override
    public PageInfo<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplierLikeCategoryName(int pageNum, int pageSize, String categoryName) {
        try (com.github.pagehelper.Page<ProductWithCategoryAndSupplierDTO> _page = PageHelper.startPage(pageNum,
                pageSize)) {
            List<ProductWithCategoryAndSupplierDTO> result =
                    productMapper.getAllProductWithCategoryAndSupplierLikeCategoryName(categoryName);
            // 检查列表是否为空而不是null
            if (result.isEmpty()) {
                throw new BizException(BizExceptionEnum.PRODUCT_CATEGORY_NOT_SEARCH);
            }
            return new PageInfo<>(result);
        }
    }

    /**
     * 获取商品下一次的商品编号
     *
     * @return 返回自动生成的商品编号
     */
    @Override
    public String getNextCode() {
        return bizCodeGenerator.generate(BizCodeGenerator.BizCode.PRODUCT_CODE);
    }

    /**
     * 新增商品
     *
     * @param newProduct 要新增的商品信息
     * @param file       要上传的图片
     * @return 返回受影响行数
     */
    @Override
    public int addProduct(Product newProduct, MultipartFile file) {
        // 验证关联的分类ID是否存在
        if (newProduct.getCategoryId() != null) {
            categoryService.getCategoryById(newProduct.getCategoryId());
        }
        // 验证关联的供应商ID是否存在
        if (newProduct.getSupplierId() != null) {
            supplierService.getSupplierById(newProduct.getSupplierId());
        }

        // 如果未提供商品编号，则自动生成
        if (newProduct.getCode() == null) {
            newProduct.setCode(bizCodeGenerator.generate(BizCodeGenerator.BizCode.PRODUCT_CODE));
        } else {
            newProduct.setCode(newProduct.getCode().toUpperCase());
            String code = newProduct.getCode();
            if (!code.startsWith("SP") || code.length() != 6) {
                throw new BizException(BizExceptionEnum.PRODUCT_CODE_ERROR);
            }
        }
        if (newProduct.getStatus() == null) {
            newProduct.setStatus(1);
        }

        newProduct.setImageUrl(handleImageUpload(file, newProduct.getCode()));

        // 设置创建时间
        newProduct.setCreateTime(LocalDateTime.now());

        // 插入商品信息
        return productMapper.addProduct(newProduct);
    }

    /**
     * 检查文件内容类型是否为图片
     *
     * @param contentType 文件内容类型
     * @return 是否为图片类型
     */
    private boolean isImageFile(String contentType) {
        return contentType != null && (
                contentType.startsWith("image/") ||
                        contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/gif") ||
                        contentType.equals("image/bmp") ||
                        contentType.equals("image/webp")
        );
    }

    /**
     * 检查文件扩展名是否为图片格式
     *
     * @param filename 文件名
     * @return 是否为图片扩展名
     */
    private boolean isImageExtension(String filename) {
        if (filename == null) return false;

        String lowerFilename = filename.toLowerCase();
        return lowerFilename.endsWith(".jpg") ||
                lowerFilename.endsWith(".jpeg") ||
                lowerFilename.endsWith(".png") ||
                lowerFilename.endsWith(".gif") ||
                lowerFilename.endsWith(".bmp") ||
                lowerFilename.endsWith(".webp");
    }

    /**
     * 修改商品信息
     *
     * @param product 商品信息
     * @param file    商品新图片
     * @return 返回受影响行数
     */
    @Override
    public int updProduct(Product product, MultipartFile file) {
        // 获取原商品信息
        Product originalProduct = getProductById(product.getId());

        // 验证商品是否存在
        if (originalProduct == null) {
            throw new BizException(BizExceptionEnum.PRODUCT_NOT_EXIT);
        }

        // 验证商品名是否被占用（如果商品名有更改且不为空）
        if (product.getName() != null && !product.getName().isEmpty() &&
                !product.getName().equals(originalProduct.getName())) {
            Product productByName = productMapper.getProductByName(product.getName());
            if (productByName != null && !productByName.getId().equals(product.getId())) {
                throw new BizException(BizExceptionEnum.PRODUCT_NAME_ALREADY_EXIT);
            }
        }

        // 验证商品分类是否存在（如果分类ID有更改且不为null）
        if (product.getCategoryId() != null && !product.getCategoryId().equals(originalProduct.getCategoryId())) {
            categoryService.getCategoryById(product.getCategoryId());
        }

        // 验证价格（如果价格有传入）
        if (product.getPrice() != null) {
            if (product.getPrice() < 0) {
                throw new BizException(BizExceptionEnum.PRODUCT_PRICE_ERROR);
            }
            // 如果价格为0，保持原价格不变（这个逻辑可以根据业务需求调整）
            if (product.getPrice() == 0.0) {
                product.setPrice(originalProduct.getPrice());
            }
        }

        // 验证最小库存（如果最小库存有传入）
        if (product.getMinStock() != null) {
            if (product.getMinStock() < 10) {
                throw new BizException(BizExceptionEnum.PRODUCT_MIN_STOCK_ERROR);
            }
        }

        // 验证单位（如果单位有传入）
        if (product.getUnit() != null) {
            if (product.getUnit().isEmpty()) {
                throw new BizException(BizExceptionEnum.PRODUCT_UNIT_ERROR);
            }
        }

        // 验证供应商是否存在（如果供应商ID有更改且不为null）
        if (product.getSupplierId() != null && !product.getSupplierId().equals(originalProduct.getSupplierId())) {
            supplierService.getSupplierById(product.getSupplierId());
        }

        // 验证启用状态（如果状态有传入）
        if (product.getStatus() != null) {
            if (product.getStatus() != 1 && product.getStatus() != 0) {
                throw new BizException(BizExceptionEnum.PRODUCT_STATUS_ERROR);
            }
        }

        // 处理文件上传
        if (file != null && !file.isEmpty()) {
            // 判断新文件与原文件是否不同
            if (isDifferentFile(originalProduct.getImageUrl(), file)) {
                String imageUrl = handleImageUpload(file, originalProduct.getCode());
                product.setImageUrl(imageUrl);
            } else {
                // 文件相同，保留原图片URL
                product.setImageUrl(originalProduct.getImageUrl());
            }
        } else {
            // 没有新文件上传，保留原图片URL
            product.setImageUrl(originalProduct.getImageUrl());
        }

        // 执行更新操作并返回影响行数
        return productMapper.updProduct(product);
    }


    /**
     * 判断新文件与原文件是否不同
     *
     * @param originalImageUrl 原图片URL
     * @param newFile          新上传的文件
     * @return 是否不同
     */
    private boolean isDifferentFile(String originalImageUrl, MultipartFile newFile) {
        // 如果原商品没有图片，而新文件存在，则认为不同
        if (StringUtils.isBlank(originalImageUrl)) {
            return newFile != null && !newFile.isEmpty();
        }

        // 如果新文件为空，则认为相同
        if (newFile == null || newFile.isEmpty()) {
            return false;
        }

        // 从原图片URL中提取文件名
        String originalFileName = extractFileNameFromUrl(originalImageUrl);
        String newFileName = newFile.getOriginalFilename();

        // 比较文件名是否不同
        return !Objects.equals(newFileName, originalFileName);
    }

    /**
     * 从图片URL中提取文件名
     *
     * @param imageUrl 图片URL
     * @return 文件名
     */
    private String extractFileNameFromUrl(String imageUrl) {
        if (StringUtils.isBlank(imageUrl)) {
            return null;
        }

        // 从 /upload/SP00001_123456789.jpg 中提取文件名
        int lastSlashIndex = imageUrl.lastIndexOf('/');
        if (lastSlashIndex >= 0 && lastSlashIndex < imageUrl.length() - 1) {
            return imageUrl.substring(lastSlashIndex + 1);
        }
        return imageUrl;
    }

    /**
     * 处理商品图片上传
     *
     * @param file        上传的文件
     * @param productCode 商品编号
     * @return 图片访问URL
     */
    private String handleImageUpload(MultipartFile file, String productCode) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            // 验证文件类型，确保是图片
            String contentType = file.getContentType();
            if (!isImageFile(contentType)) {
                throw new BizException(BizExceptionEnum.FILE_TYPE_ERROR);
            }

            // 验证文件扩展名
            String originalFilename = file.getOriginalFilename();
            if (!isImageExtension(originalFilename)) {
                throw new BizException(BizExceptionEnum.FILE_TYPE_ERROR);
            }

            // 规范化上传路径
            String normalizedUploadPath = Paths.get(uploadPath).normalize().toString();

            // 确保路径以分隔符结尾
            if (!normalizedUploadPath.endsWith(File.separator)) {
                normalizedUploadPath += File.separator;
            }

            // 生成安全的文件名
            String fileExtension = "";
            if (originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String safeFileName = productCode + "_" + System.currentTimeMillis() + fileExtension;

            // 规范化完整文件路径
            Path filePath = Paths.get(normalizedUploadPath, safeFileName).normalize();

            // 安全检查：确保文件路径在指定目录内
            Path rootPath = Paths.get(normalizedUploadPath).toAbsolutePath().normalize();
            if (!filePath.toAbsolutePath().normalize().startsWith(rootPath)) {
                throw new BizException(BizExceptionEnum.FILE_UPLOAD_ERROR);
            }

            // 保存文件
            File destFile = filePath.toFile();
            file.transferTo(destFile);

            // 返回图片URL路径（相对于Web访问路径）
            return "/images/" + safeFileName;

        } catch (Exception e) {
            throw new BizException(BizExceptionEnum.FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public int delProductById(BigInteger id) {
        getProductById(id);
        List<Inventory> inventoryByProductId = inventoryService.getInventoryByProductId(id);
        if (!inventoryByProductId.isEmpty()) {
            throw new BizException(BizExceptionEnum.PRODUCT_EXIT_INVENTORY);
        }
        return productMapper.delProductById(id);
    }

    @Override
    public PageInfo<ProductWithCategoryAndSupplierDTO> getAllProductWithCategoryAndSupplierByChange(int pageNum, int pageSize, String prodectName, String categoryName, String supplierName) {
        try (com.github.pagehelper.Page<ProductWithCategoryAndSupplierDTO> _page = PageHelper.startPage(pageNum,
                pageSize)){
            List<ProductWithCategoryAndSupplierDTO> result = productMapper.getAllProductWithCategoryAndSupplierByChange(prodectName, categoryName, supplierName);
            return new PageInfo<>(result);
        }
    }
}
