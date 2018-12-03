package com.management.xhwl.dao;

import com.management.xhwl.pojo.RecordSku;

public interface RecordSkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordSku record);

    int insertSelective(RecordSku record);

    RecordSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordSku record);

    int updateByPrimaryKey(RecordSku record);
}