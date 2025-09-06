package com.xlproject.modules.web01.controller;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.response.R;
import com.xlproject.modules.dto.inboundDto.InboundOrderInfoDTO;
import com.xlproject.modules.service.inbound.InboundOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InboundOrderInfoController {
    private InboundOrderInfoService inboundOrderInfoService;
    @Autowired
    public void setInboundOrderInfoService(InboundOrderInfoService inboundOrderInfoService){this.inboundOrderInfoService=inboundOrderInfoService;}


    @GetMapping("/inboundOrderInfo")
    public R<PageInfo<InboundOrderInfoDTO>> getInboundOrderInfo(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize){
        return R.OK("获取入库订单详细信息",inboundOrderInfoService.getAllInboundOrderInfo(pageNum,pageSize));
    }

    @GetMapping("/inboundOrderInfo/count")
    public R<Integer> getInboundOrderCount(){
        return R.OK("获取入库订单总数", inboundOrderInfoService.getInboundOrderCount());
    }
}
