package com.management.device.dao;

import com.management.device.pojo.DeviceList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface DeviceListMapper {
    int deleteByPrimaryKey(String deviceId);

    int insert(DeviceList record);

    int insertSelective(DeviceList record);

    DeviceList selectByPrimaryKey(String deviceId);

    int updateByPrimaryKeySelective(DeviceList record);

    int updateByPrimaryKey(DeviceList record);

    List<DeviceList> findAll(DeviceList deviceList);

    DeviceList selectByDeviceIdAndDeviceTypeId(@Param(value = "deviceId") String deviceId, @Param(value = "deviceTypeId") Integer deviceTypeId);

    List<DeviceList> findAllByOrganiation(@Param(value = "ids") Set<Integer> ids,@Param(value = "deviceList") DeviceList list);
}