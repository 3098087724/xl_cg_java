package com.xlproject.modules.service.Generator;

import com.xlproject.modules.dao.mapper.InboundOrderMapper;
import com.xlproject.modules.dao.mapper.OutboundOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 业务编号生成器
 * 生成格式：前缀 + 日期(YYYYMMDD) + 3位流水号
 */
@Component
public class BizNoGenerator {
    private OutboundOrderMapper outboundMapper;
    @Autowired
    public  void setOutboundMapper(OutboundOrderMapper outboundMapper){this.outboundMapper=outboundMapper;}

    private InboundOrderMapper inboundMapper;
    @Autowired
    public  void setInboundOrderMapper(InboundOrderMapper inboundMapper){this.inboundMapper=inboundMapper;}

    /**
     * 生成业务编号
     * @param type 业务类型 (RK:入库, CK:出库)
     * @return 生成的业务编号
     */
    public synchronized String generate(String type) {
        LocalDate today = LocalDate.now();
        String dateStr = today.format(DateTimeFormatter.BASIC_ISO_DATE);

        // 每次都从数据库获取最大编号
        String maxNo = getMaxOrderNo(type);
        int nextSeq = extractNextSequence(maxNo);

        return String.format("%s%s%03d", type, dateStr, nextSeq);
    }

    /**
     * 从最大编号中提取下一个序列号
     */
    private int extractNextSequence(String maxNo) {
        if (maxNo != null && !maxNo.isEmpty()) {
            try {
                // 数据库返回的是纯数字序列，直接解析
                int sequence = Integer.parseInt(maxNo);
                return sequence + 1;
            } catch (NumberFormatException e) {
                // 解析失败，从1开始
            }
        }
        return 1;
    }

    /**
     * 根据类型查询最大编号
     */
    private String getMaxOrderNo(String type) {
        return switch (type) {
            case "RK" -> inboundMapper.getMaxOrderNoByDate();
            case "CK" -> outboundMapper.getMaxOrderNoByDate();
            default -> throw new IllegalArgumentException("不支持的单据类型: " + type);
        };
    }
}
