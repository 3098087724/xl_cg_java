package com.xlproject.modules.service.outbound;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.dto.outboundDto.OutboundOrderInfoDTO;

import java.util.List;

public interface OutboundInfoService {
    PageInfo<OutboundOrderInfoDTO> getAllOutboundOrderInfo(int pageNum, int pageSize);
}
