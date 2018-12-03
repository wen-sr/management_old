package com.management.prd1.dao;

import com.management.prd1.dto.ReceiptDto;
import com.management.prd1.dto.RecordSkuDto;
import com.management.prd1.pojo.Xsogroup;
import com.management.prd1.vo.LogisticsQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XsogroupMapper {
    int deleteByPrimaryKey(String xsogroupkey);

    int insert(Xsogroup record);

    int insertSelective(Xsogroup record);

    Xsogroup selectByPrimaryKey(String xsogroupkey);

    int updateByPrimaryKeySelective(Xsogroup record);

    int updateByPrimaryKey(Xsogroup record);

    List<ReceiptDto> getReceiptToday();

    List<Xsogroup> getReceipt(@Param(value = "begin") String begin, @Param(value = "end") String end);

    List<RecordSkuDto> selectByBarcode(String barcode);

    List<LogisticsQueryVo> bookShip(LogisticsQueryVo logisticsQueryVo);

    String selectStorerById(String id);
}