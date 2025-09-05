package com.xlproject.modules.service.Generator;

import com.xlproject.modules.dao.mapper.InboundOrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class BatchNoGenerator {
    InboundOrderItemMapper inboundOrderItemMapper;
    @Autowired
    public  void setInboundOrderItemMapper(InboundOrderItemMapper inboundOrderItemMapper){this.inboundOrderItemMapper=inboundOrderItemMapper;}
    public String generate(String code, LocalDate productionDate){
        Integer maxSerial = inboundOrderItemMapper.getMaxSerialByProductAndDate(code, productionDate);
        int nextSerial = (maxSerial == null) ? 1 : maxSerial + 1;
        String serial = String.format("%03d", nextSerial);
        return code+"-"+productionDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"-"+serial;
    }
}
