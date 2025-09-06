package com.xlproject.modules.service.outbound.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xlproject.modules.dao.mapper.OutboundOrderInfoMapper;
import com.xlproject.modules.dto.outboundDto.OutboundOrderInfoDTO;
import com.xlproject.modules.service.outbound.OutboundInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutboundInfoServiceImpl implements OutboundInfoService {
    private OutboundOrderInfoMapper outboundOrderInfoMapper;
    @Autowired
    public void setOrderInfoMapper(OutboundOrderInfoMapper outboundOrderInfoMapper){this.outboundOrderInfoMapper=outboundOrderInfoMapper;}

    @Override
    public PageInfo<OutboundOrderInfoDTO> getAllOutboundOrderInfo(int pageNum, int pageSize) {
        try (com.github.pagehelper.Page<OutboundOrderInfoDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<OutboundOrderInfoDTO> list = outboundOrderInfoMapper.getAllOutboundOrderInfo();
            return new PageInfo<>(list);
        }
    }

    @Override
    public Integer getOutboundOrderCount() {
        return outboundOrderInfoMapper.getOutboundOrderCount();
    }
}
