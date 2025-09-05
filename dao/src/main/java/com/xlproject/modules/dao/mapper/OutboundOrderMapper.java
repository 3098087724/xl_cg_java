package com.xlproject.modules.dao.mapper;

import com.xlproject.modules.entity.bean.OutboundOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutboundOrderMapper {
    String getMaxOrderNoByDate();
    int addOutboundOrder(OutboundOrder outboundOrder);
}
