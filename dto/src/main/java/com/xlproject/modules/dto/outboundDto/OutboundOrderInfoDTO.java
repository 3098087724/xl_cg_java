package com.xlproject.modules.dto.outboundDto;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OutboundOrderInfoDTO {
    private BigInteger id;//出库单ID
    private String orderNo;//出库单编号（CK20050820001）
    private String type;//出库类型（SALE=销售出库，DAMAGE=报损出库，TRANSFER=调拨出库）
    private BigInteger operatorId;//操作员ID
    private String username;
    private String realName;
    private Double totalAmount;//总金额（元）
    private String status;//状态，固定为已审核
    private String remark;//备注信息
    private LocalDateTime createTime;//创建时间
    List<OutboundOrderItemInfoDTO> outboundOrderItems;
}
