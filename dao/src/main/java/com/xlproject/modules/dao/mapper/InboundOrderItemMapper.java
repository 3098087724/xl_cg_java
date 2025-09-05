package com.xlproject.modules.dao.mapper;
import com.xlproject.modules.dto.inboundDto.InboundOrderItemInfoDTO;
import com.xlproject.modules.entity.bean.InboundOrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface InboundOrderItemMapper {
    Integer getMaxSerialByProductAndDate(String code, LocalDate date);
    int addInboundOrderItem(InboundOrderItem inboundOrderItem);
    List<InboundOrderItemInfoDTO> getInboundOrderItemInfoByOrderId(BigInteger id);
}
