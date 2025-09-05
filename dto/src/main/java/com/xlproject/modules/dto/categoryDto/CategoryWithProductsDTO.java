package com.xlproject.modules.dto.categoryDto;

import com.xlproject.modules.entity.bean.Product;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CategoryWithProductsDTO {  // 使用大写 DTO 后缀
    private BigInteger id;//商品分类ID
    private String name;//分类名称
    private BigInteger parentId;//父分类ID（可选）
    private Integer status;//状态 1=启用，0=停用
    private LocalDateTime createTime;//创建时间
    private List<Product> products;//同商品分类下的所有商品
}
