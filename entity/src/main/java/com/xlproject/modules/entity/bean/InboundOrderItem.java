package com.xlproject.modules.entity.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InboundOrderItem {
    private BigInteger id;//入库单明细表ID
    private BigInteger orderId;//入库单ID
    @NotNull(message = "商品ID不能为空")
    private BigInteger productId;//商品ID
    private String batchNo;
    @NotNull(message = "入库数量不能为空")
    private Integer quantity;//入库数量
    @NotNull(message = "商品单价不能为空")
    private Double unitPrice;//单价（元）
    private Double totalPrice;//总价
    @NotNull(message = "生产日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate productionDate;//生产日期，用于计算保质期和临期预警
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;//创建时间
}
