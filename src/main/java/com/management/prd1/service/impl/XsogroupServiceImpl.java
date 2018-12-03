package com.management.prd1.service.impl;

import com.management.common.ServerResponse;
import com.management.prd1.dao.XsogroupMapper;
import com.management.prd1.dto.ReceiptDto;
import com.management.prd1.pojo.Xsogroup;
import com.management.prd1.service.XsogroupService;
import com.management.util.DataSourceContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class XsogroupServiceImpl implements XsogroupService{

    @Autowired
    XsogroupMapper xsogroupMapper;

    @Override
    public ServerResponse getReceiptAndDelivery(String begin, String end) {


        List<Xsogroup> xsogroups = getReceipt(begin,end);
        return null;
    }

    @Override
    public ServerResponse getYield(String begin, String end) {
        StringBuilder sb = null;
        if(StringUtils.isEmpty(begin) && StringUtils.isEmpty(end)){
            List<ReceiptDto> receiptDtos = getReceiptToday();
            sb = new StringBuilder("新华物流今日产量：");
            if(!CollectionUtils.isEmpty(receiptDtos)){
                int yb = 0;
                int dzz = 0;
                int gp = 0;
                int njsw = 0;
                int brxx = 0 ;
                int ybt = 0;
                int dzzt = 0;
                int qt = 0;
                int sumqty = 0;
                for(ReceiptDto s : receiptDtos ){
                    sumqty += s.getQty();
                    if("01".equals(s.getType())){
                        yb += s.getQty();
                    }else if("02".equals(s.getType())){
                        dzz += s.getQty();
                    }else if("03".equals(s.getType())){
                        gp += s.getQty();
                    }else if("04".equals(s.getType())){
                        njsw += s.getQty();
                    }else if("05".equals(s.getType())){
                        brxx += s.getQty();
                    }else if("06".equals(s.getType())){
                        ybt += s.getQty();
                    }else if("07".equals(s.getType())){
                        dzzt += s.getQty();
                    }else if("08".equals(s.getType())){
                        qt += s.getQty();
                    }
                }
                sb.append("总收货：").append(sumqty).append("件；");
            }
        }
        return ServerResponse.createBySuccessMsg(sb.toString());
    }

    private List<ReceiptDto> getReceiptToday() {
        DataSourceContextHolder. setDbType(DataSourceContextHolder.SESSION_FACTORY_PRD1);
        return xsogroupMapper.getReceiptToday();
    }

    private List<Xsogroup> getReceipt(String begin, String end) {
        DataSourceContextHolder. setDbType(DataSourceContextHolder.SESSION_FACTORY_PRD1);
        List<Xsogroup> xsogroups = null;
        if(StringUtils.isEmpty(begin) && StringUtils.isEmpty(end)){
//            xsogroups = xsogroupMapper.getReceiptToday();
        }else{
            xsogroups = xsogroupMapper.getReceipt(begin, end);
        }

        return xsogroups;

    }

    private int getDelivery(String begin, String end) {
        return 0;
    }


}
