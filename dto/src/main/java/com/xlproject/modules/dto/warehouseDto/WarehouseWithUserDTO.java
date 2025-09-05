package com.xlproject.modules.dto.warehouseDto;

import lombok.Data;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class WarehouseWithUserDTO {
    private BigInteger id;//仓库Id
    private String code;//仓库编号，如（WH001）
    private String name;//仓库名称
    private String location;//仓库位置
    private BigInteger managerId;//负责人ID
    private String username;
    private String realName;
    private int capacity;//仓库容量
    private Integer status;//状态 1=启用，0=停用
    private LocalDateTime createTime;//创建时间
}
