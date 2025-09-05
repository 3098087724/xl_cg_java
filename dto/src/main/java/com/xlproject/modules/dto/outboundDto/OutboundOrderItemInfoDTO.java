package com.xlproject.modules.dto.outboundDto;

import com.xlproject.modules.dto.productDto.ProductWithCategoryAndSupplierDTO;
import lombok.Data;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class OutboundOrderItemInfoDTO {
    private BigInteger id;//出库单明细表ID
    private BigInteger orderId;//出库单ID
    private BigInteger productId;//商品ID
    private BigInteger warehouseId;//源仓库ID
    private String warehouseName;//仓库名
    private BigInteger targetWarehouseId;//目标仓库ID，仅调拨出库时使用
    private String targetWarehouseName;
    private String batchNo;//批次号
    private Integer quantity;//出库数量
    private Double unitPrice;//单价（元）
    private Double totalPrice;//总价
    private LocalDateTime createTime;//创建时间
    private ProductWithCategoryAndSupplierDTO product;
}
