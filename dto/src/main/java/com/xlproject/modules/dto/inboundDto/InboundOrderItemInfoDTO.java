package com.xlproject.modules.dto.inboundDto;


import com.xlproject.modules.dto.productDto.ProductWithCategoryAndSupplierDTO;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InboundOrderItemInfoDTO {
    private BigInteger id;//入库单明细表ID
    private BigInteger orderId;//入库单ID
    private String batchNo;//批次号
    private BigInteger productId;//商品ID
    private Integer quantity;//入库数量
    private Double unitPrice;//单价（元）
    private Double totalPrice;//总价
    private LocalDate productionDate;//生产日期，用于计算保质期和临期预警
    private LocalDateTime createTime;//创建时间
    private ProductWithCategoryAndSupplierDTO product;

}
