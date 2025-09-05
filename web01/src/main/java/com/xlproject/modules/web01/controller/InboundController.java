package com.xlproject.modules.web01.controller;


import com.xlproject.modules.common.response.R;
import com.xlproject.modules.dto.inboundDto.InboundWithItemDTO;
import com.xlproject.modules.service.inbound.InboundService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InboundController {
    InboundService inboundService;
    @Autowired
    public  void setInboundService(InboundService inboundService){this.inboundService=inboundService;}

    @PostMapping("/inbound")
    public R<?> inboundProduct(@RequestBody @Valid InboundWithItemDTO inboundWithItem){
        if (inboundService.inbound(inboundWithItem)){
            return R.OK("入库成功");
        }
        return R.ERROR(7000,"入库失败，请重试");
    }
}
