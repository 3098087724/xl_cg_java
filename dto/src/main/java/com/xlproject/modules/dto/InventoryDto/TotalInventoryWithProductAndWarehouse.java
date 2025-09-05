package com.xlproject.modules.dto.InventoryDto;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class TotalInventoryWithProductAndWarehouse {
    private BigInteger productID;
    private String name;
    private BigInteger totalQuantity;
    private double price;
    private String code;
    private  String unit;
    private  Integer minDaysLeft;
    private LocalDate minProductionDate;
    private String warehouseName;
    private BigInteger supplierId;
    private String supplierName;
}
