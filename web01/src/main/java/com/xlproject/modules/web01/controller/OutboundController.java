package com.xlproject.modules.web01.controller;

import com.xlproject.modules.common.response.R;
import com.xlproject.modules.dto.outboundDto.OutBoundWithItemDTO;
import com.xlproject.modules.service.outbound.OutboundService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OutboundController {
    private OutboundService outboundService;
    @Autowired
    public  void setOutboundService(OutboundService outboundService){this.outboundService=outboundService;}
    @PostMapping("/outbound")
    public R<?> outbound(@RequestBody @Valid OutBoundWithItemDTO request){
            return R.OK("出库",outboundService.outBound(request));
    }
}
