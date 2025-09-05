package com.xlproject.modules.dao.mapper;

import com.xlproject.modules.dto.inboundDto.InboundOrderInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InboundOrderInfoMapper {
    List<InboundOrderInfoDTO> getAllInboundOrderInfo();
}
