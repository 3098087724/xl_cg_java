package com.xlproject.modules.service.inbound;


import com.xlproject.modules.dto.inboundDto.InboundWithItemDTO;

public interface InboundService {
    boolean inbound(InboundWithItemDTO inboundWithItem);
}
