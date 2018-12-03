package com.management.device.dao;

import com.management.device.pojo.DeviceUsers;

import java.util.List;

public interface DeviceUsersMapper {
    int deleteByPrimaryKey(Integer deviceUserId);

    int insert(DeviceUsers record);

    int insertSelective(DeviceUsers record);

    DeviceUsers selectByPrimaryKey(Integer deviceUserId);

    int updateByPrimaryKeySelective(DeviceUsers record);

    int updateByPrimaryKey(DeviceUsers record);

    List<DeviceUsers> selectAll();
}