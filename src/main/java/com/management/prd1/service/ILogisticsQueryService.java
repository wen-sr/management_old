package com.management.prd1.service;

import com.management.common.ServerResponse;
import com.management.prd1.vo.LogisticsQueryVo;

public interface ILogisticsQueryService {
    ServerResponse bookShipQuery(LogisticsQueryVo logisticsQueryVo);
}
