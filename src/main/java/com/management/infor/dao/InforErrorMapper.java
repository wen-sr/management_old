package com.management.infor.dao;

import com.management.infor.pojo.InforError;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface InforErrorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InforError record);

    int insertSelective(InforError record);

    InforError selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InforError record);

    int updateByPrimaryKey(InforError record);

    List<InforError> findAll(@Param(value = "inforError") InforError inforError, @Param(value = "ids") Set<Integer> ids);
}