package com.management.device.dao;

import com.management.device.pojo.DeviceTrans;
import com.management.device.vo.DeviceTransVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceTransMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceTrans record);

    int insertSelective(DeviceTrans record);

    DeviceTrans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceTrans record);

    int updateByPrimaryKey(DeviceTrans record);

    DeviceTrans selectByDeviceId(@Param(value = "deviceId") String deviceId,@Param(value = "status") String status);

    List<DeviceTransVo> selectAllByDeviceId(String deviceId);
}