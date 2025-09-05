package com.xlproject.modules.entity.bean;

import lombok.Data;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class OutboundOrderItem {
    private BigInteger id;//出库单明细表ID
    //@NotNull(message = "出库单ID不能为空")
    private BigInteger orderId;//出库单ID
    //@NotNull(message = "商品ID不能为空")
    private BigInteger productId;//商品ID
    //@NotNull(message = "源仓库ID不能为空")
    private BigInteger warehouseId;//源仓库ID
    private BigInteger targetWarehouseId;//目标仓库ID，仅调拨出库时使用
    //@NotBlank(message = "批次号不能为空")
    private String batchNo;//批次号
    //@NotNull(message = "出库数量不能为空")
    private Integer quantity;//出库数量
    //@NotNull(message = "出库商品单价不能为空")
    private Double unitPrice;//单价（元）
    //@NotNull(message = "出库商品总价不能为空")
    private Double totalPrice;//总价
    private LocalDateTime createTime;//创建时间
}
