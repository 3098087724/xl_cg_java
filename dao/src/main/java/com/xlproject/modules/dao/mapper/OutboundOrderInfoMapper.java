package com.xlproject.modules.dao.mapper;

import com.xlproject.modules.dto.outboundDto.OutboundOrderInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OutboundOrderInfoMapper {
    List<OutboundOrderInfoDTO> getAllOutboundOrderInfo();
    Integer getOutboundOrderCount();
}
