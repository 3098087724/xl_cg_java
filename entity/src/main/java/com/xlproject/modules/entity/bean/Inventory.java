package com.xlproject.modules.entity.bean;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Inventory {
    private BigInteger id;//库存记录ID
    @NotNull(message = "商品ID不能为空")
    private BigInteger productId;//商品ID
    @NotNull(message = "仓库ID不能为空")
    private BigInteger warehouseId;//仓库ID
    @NotNull(message = "库存数量不能为空")
    private Integer quantity;//当前库存数量
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//最后更新时间
    @NotBlank(message = "批次号不能为空")
    private String batchNo;//批次号
    @NotNull(message = "生产日期不能为空")
    private LocalDate productionDate;//生成日期
    @NotNull(message = "过期日期不能为空")
    private LocalDate expirationDate;//过期日期
}
