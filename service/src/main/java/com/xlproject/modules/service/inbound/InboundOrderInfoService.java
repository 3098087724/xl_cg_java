package com.xlproject.modules.service.inbound;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.dto.inboundDto.InboundOrderInfoDTO;


public interface InboundOrderInfoService {
    PageInfo<InboundOrderInfoDTO> getAllInboundOrderInfo(int pageNum, int pageSize);
    Integer getInboundOrderCount();
}
