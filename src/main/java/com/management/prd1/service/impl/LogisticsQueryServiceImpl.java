package com.management.prd1.service.impl;

import com.management.common.ServerResponse;
import com.management.prd1.dao.XsogroupMapper;
import com.management.util.DataSourceContextHolder;
import com.management.prd1.dao.LogisticsQueryMapper;
import com.management.prd1.service.ILogisticsQueryService;
import com.management.prd1.vo.LogisticsQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LogisticsQueryServiceImpl implements ILogisticsQueryService {

    @Autowired
    XsogroupMapper xsogroupMapper;

    public ServerResponse bookShipQuery(LogisticsQueryVo logisticsQueryVo){
        DataSourceContextHolder. setDbType(DataSourceContextHolder.SESSION_FACTORY_PRD1);
        List<LogisticsQueryVo> logisticsQueryVoList = xsogroupMapper.bookShip(logisticsQueryVo);
        return ServerResponse.createBySuccess("", logisticsQueryVoList);
    }
}
