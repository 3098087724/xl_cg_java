package com.xlproject.modules.dao.mapper;

import com.xlproject.modules.dto.outboundDto.OutboundOrderItemInfoDTO;
import com.xlproject.modules.entity.bean.OutboundOrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface OutboundOrderItemMapper {
    int addOutboundOrderItem(OutboundOrderItem outboundOrderItem);

    List<OutboundOrderItemInfoDTO> getOutboundItemsInfoByOrderId(BigInteger id);
}
