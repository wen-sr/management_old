package com.management.prd1.dao;

import com.management.prd1.vo.LogisticsQueryVo;

import java.util.List;

public interface LogisticsQueryMapper {
    /**
     * @param id
     * @return
     */
    String selectStorerById(String id);
    //List<LogisticsQueryVo> bookShip(LogisticsQueryVo logisticsQueryVo);
}
