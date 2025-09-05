package com.xlproject.modules.web01.controller;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.response.R;
import com.xlproject.modules.dto.outboundDto.OutboundOrderInfoDTO;
import com.xlproject.modules.service.outbound.OutboundInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OutboundOrderInfoController {
    private OutboundInfoService outboundInfoService;
    @Autowired
    public void setOutboundInfoService(OutboundInfoService outboundInfoService){this.outboundInfoService=outboundInfoService;}

    @GetMapping("/outboundInfo")
    public R<PageInfo<OutboundOrderInfoDTO>>getAllOutboundOrderInfo(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize){
        return R.OK("获取所有出库单详细信息",outboundInfoService.getAllOutboundOrderInfo(pageNum,pageSize));
    }
}
