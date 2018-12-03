package com.management.device.dao;

import com.management.device.pojo.DeviceRepair;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface DeviceRepairMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceRepair record);

    int insertSelective(DeviceRepair record);

    DeviceRepair selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceRepair record);

    int updateByPrimaryKey(DeviceRepair record);

    List<DeviceRepair> findAll(@Param(value = "deviceRepair") DeviceRepair deviceRepair, @Param(value = "ids") Set<Integer> ids);

    DeviceRepair selectByDeviceIdAndStatus(@Param(value = "deviceId") String deviceId, @Param(value = "status") String status);
}