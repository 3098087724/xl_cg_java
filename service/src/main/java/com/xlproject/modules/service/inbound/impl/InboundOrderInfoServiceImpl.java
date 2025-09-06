package com.xlproject.modules.service.inbound.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xlproject.modules.dao.mapper.InboundOrderInfoMapper;
import com.xlproject.modules.dto.inboundDto.InboundOrderInfoDTO;
import com.xlproject.modules.service.inbound.InboundOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InboundOrderInfoServiceImpl implements InboundOrderInfoService {
    private InboundOrderInfoMapper inboundOrderInfoMapper;
    @Autowired
    public void setInboundOrderInfoMapper(InboundOrderInfoMapper inboundOrderInfoMapper){this.inboundOrderInfoMapper=inboundOrderInfoMapper;}
    @Override
    public PageInfo<InboundOrderInfoDTO> getAllInboundOrderInfo(int pageNum, int pageSize) {
        try (com.github.pagehelper.Page<InboundOrderInfoDTO> _page = PageHelper.startPage(pageNum, pageSize)) {
            List<InboundOrderInfoDTO> list = inboundOrderInfoMapper.getAllInboundOrderInfo();
            return new PageInfo<>(list);
        }
    }

    @Override
    public Integer getInboundOrderCount() {
        return inboundOrderInfoMapper.getInboundOrderCount();
    }
}
