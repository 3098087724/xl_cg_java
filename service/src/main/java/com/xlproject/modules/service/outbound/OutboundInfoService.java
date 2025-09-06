package com.xlproject.modules.service.outbound;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.dto.outboundDto.OutboundOrderInfoDTO;


public interface OutboundInfoService {
    PageInfo<OutboundOrderInfoDTO> getAllOutboundOrderInfo(int pageNum, int pageSize);
    Integer getOutboundOrderCount();
}
