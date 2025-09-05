package com.xlproject.modules.dto.inboundDto;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class InboundOrderInfoDTO {
    private BigInteger id;
    private String orderNo;//入库单编号，如（RK20250819001）
    private String type;//入库类型 （入库类型：PURCHASE=采购入库，RETURN=退货入库，TRANSFER=调拨入库）
    private BigInteger warehouseId;//目标仓库ID
    private String warehouseName;
    private BigInteger supplierId;//供应商ID，仅采购入库时有效
    private BigInteger operatorId;//操作员ID
    private String username;//账号
    private String realName;//真实姓名
    private Double totalAmount;//总金额
    private String status;//状态
    private String remark;//备注信息
    private LocalDateTime createTime;
    List<InboundOrderItemInfoDTO> inboundOrderItems;
}
