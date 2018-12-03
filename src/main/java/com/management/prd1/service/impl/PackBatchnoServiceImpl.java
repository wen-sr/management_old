package com.management.prd1.service.impl;

import com.management.common.EasyuiTableResponse;
import com.management.common.ServerResponse;
import com.management.prd1.service.IPackBatchnoService;
import com.management.prd1.dao.PackBatchnoMapper;
import com.management.prd1.pojo.PackBatchno;
import com.management.util.DataSourceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PackBatchnoServiceImpl implements IPackBatchnoService {

    @Autowired
    PackBatchnoMapper packBatchnoMapper;

    @Override
    public EasyuiTableResponse getData(String dd, String id) {
        DataSourceContextHolder. setDbType(DataSourceContextHolder.SESSION_FACTORY_PRD1);
        List<PackBatchno> packBatchnoList = packBatchnoMapper.getData(dd, id);

        Integer sumCaseQty = 0;
        for (PackBatchno p : packBatchnoList){
            sumCaseQty += p.getCaseqty().intValue();
        }

        EasyuiTableResponse response = new EasyuiTableResponse();
        response.setRows(packBatchnoList);
        response.setCode(0);
        response.setCount(packBatchnoList.size());
        response.setTotal(sumCaseQty);
        return response;
    }
}
