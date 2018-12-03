package com.management.login.dao;

import com.management.login.pojo.RoleLoginKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleLoginMapper {
    int deleteByPrimaryKey(RoleLoginKey key);

    int insert(RoleLoginKey record);

    int insertSelective(RoleLoginKey record);

    List<Integer> selectByLoginId(@Param(value = "id") String id);

    int deleteByLoginId(String id);

}