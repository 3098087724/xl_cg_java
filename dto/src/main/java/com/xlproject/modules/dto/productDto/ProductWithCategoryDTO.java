package com.xlproject.modules.dto.productDto;

import com.xlproject.modules.entity.bean.Category;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class ProductWithCategoryDTO {
    private BigInteger id;//商品ID
    private String code;//商品编号（SP001）
    private  String name;//商品名称
    private  BigInteger categoryId;//所属分类ID,关联category表
    private  String unit;//计量单位（如：件，箱，千克，包)
    private  double price;//单价，单位：元
    private  BigInteger supplierId;//供应商ID
    private  int shelfLife;//保质期，单位（天）
    private  Integer status;//状态 1=正常销售，0=已停用
    private  String imageUrl;//商品图片地址
    private LocalDateTime createTime;//创建时间
    private Category category;
}
