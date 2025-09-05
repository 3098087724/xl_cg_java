package com.xlproject.modules.dto.InventoryDto;

import com.xlproject.modules.dto.productDto.ProductWithCategoryAndSupplierDTO;
import com.xlproject.modules.entity.bean.Warehouse;
import lombok.Data;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InventoryWithProductAndWarehouseDTO {
    private BigInteger id;//库存记录ID
    private BigInteger productId;//商品ID
    private BigInteger warehouseId;//仓库ID
    private Integer quantity;//当前库存数量
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//最后更新时间
    private String batchNo;//批次号
    private LocalDate productionDate;//生成日期
    private LocalDate expirationDate;//过期日期
    private Integer daysLeft;
    private ProductWithCategoryAndSupplierDTO product;
    private Warehouse warehouse;
}
