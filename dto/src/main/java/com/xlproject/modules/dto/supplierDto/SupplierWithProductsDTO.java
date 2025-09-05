package com.xlproject.modules.dto.supplierDto;

import com.xlproject.modules.entity.bean.Product;
import lombok.Data;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class SupplierWithProductsDTO {
    private BigInteger id;//供应商Id
    private  String name;//供应商名称
    private  String contactPerson;//联系人姓名
    private  String phone;//联系电话
    private  String address;//地址
    private  Integer status;//状态 1=合作中，0=已终止
    private LocalDateTime createTime;//创建时间
    private Product product;
}
