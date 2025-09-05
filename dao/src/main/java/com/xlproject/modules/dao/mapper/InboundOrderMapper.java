package com.xlproject.modules.dao.mapper;

import com.xlproject.modules.entity.bean.InboundOrder;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;

@Mapper
public interface InboundOrderMapper {
    String getMaxOrderNoByDate();
    int AddInboundOrder(InboundOrder inboundOrder);
}
