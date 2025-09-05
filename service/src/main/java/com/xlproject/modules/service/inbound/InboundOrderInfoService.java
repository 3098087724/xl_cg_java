package com.xlproject.modules.service.inbound;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.dto.inboundDto.InboundOrderInfoDTO;

import java.util.List;

public interface InboundOrderInfoService {
    PageInfo<InboundOrderInfoDTO> getAllInboundOrderInfo(int pageNum, int pageSize);
}
