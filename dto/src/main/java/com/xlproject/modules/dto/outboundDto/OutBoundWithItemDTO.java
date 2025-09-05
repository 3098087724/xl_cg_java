package com.xlproject.modules.dto.outboundDto;

import com.xlproject.modules.entity.annotation.OutBoundType;
import com.xlproject.modules.entity.bean.OutboundOrderItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class OutBoundWithItemDTO {
    @NotBlank(message = "出库类型不可为空")
    @OutBoundType(message = "出库类型必须为SALE(销售出库)，DAMAGE(报损出库)，TRANSFER(调拨出库)")
    private String type;//出库类型（SALE=销售出库，DAMAGE=报损出库，TRANSFER=调拨出库）
    @NotNull(message = "操作员ID不能为空")
    private BigInteger operatorId;//操作员ID
    private String remark;//备注信息
    @NotEmpty(message = "出库明细不能为空")
    @NotNull(message = "出库明细列表不能为null")
    List<OutboundOrderItem> inboundOrderItems;
}
