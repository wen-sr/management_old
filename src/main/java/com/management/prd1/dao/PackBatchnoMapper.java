package com.management.prd1.dao;

import com.management.prd1.pojo.PackBatchno;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PackBatchnoMapper {
    int insert(PackBatchno record);

    int insertSelective(PackBatchno record);

    List<PackBatchno> getData(@Param("dd") String dd, @Param("id")String id);
}