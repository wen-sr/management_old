package com.management.device.service;

import com.management.common.ServerResponse;
import com.management.device.pojo.DeviceType;
import com.management.device.vo.DeviceTypeVo;

import java.util.List;

public interface IDeviceTypeService {
    List<DeviceTypeVo> findAll();

    ServerResponse addDeviceType(DeviceType deviceType);

    ServerResponse editDeviceType(DeviceType deviceType);

    ServerResponse deleteDeviceType(DeviceType deviceType);
}
