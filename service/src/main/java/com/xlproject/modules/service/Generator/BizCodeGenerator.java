package com.xlproject.modules.service.Generator;

import com.xlproject.modules.dao.mapper.ProductMapper;
import com.xlproject.modules.dao.mapper.WarehouseMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BizCodeGenerator {

    private ProductMapper productMapper;
    private WarehouseMapper wareHouseMapper;

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Autowired
    public void setWareHouseMapper(WarehouseMapper wareHouseMapper) {
        this.wareHouseMapper = wareHouseMapper;
    }

    @Getter
    public enum BizCode {
        PRODUCT_CODE("SP", "商品编号"),
        WAREHOUSE_CODE("WH", "仓库编号");

        private final String prefix;
        private final String desc;

        BizCode(String prefix, String desc) {
            this.prefix = prefix;
            this.desc = desc;
        }

    }

    public synchronized String generate(BizCode bizCode) {
        if (bizCode == null) {
            throw new IllegalArgumentException("业务编码类型不能为空");
        }

        Integer maxCode = getMaxCode(bizCode);
        int nextSeq = (maxCode != null) ? maxCode + 1 : 1;

        return switch (bizCode) {
            case PRODUCT_CODE -> bizCode.getPrefix() + String.format("%04d", nextSeq);
            case WAREHOUSE_CODE -> bizCode.getPrefix() + String.format("%03d", nextSeq);
        };
    }

    public Integer getMaxCode(BizCode bizCode) {
        return switch (bizCode) {
            case PRODUCT_CODE -> productMapper.getMaxCode();
            case WAREHOUSE_CODE -> wareHouseMapper.getMaxCode();
        };
    }




}
