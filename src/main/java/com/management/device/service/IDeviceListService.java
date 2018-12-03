package com.management.device.service;

import com.management.common.ServerResponse;
import com.management.device.pojo.DeviceList;


public interface IDeviceListService {


    ServerResponse findAll(DeviceList deviceList, Integer pageSize, Integer pageNum);

    ServerResponse addDevice(DeviceList deviceList, String organizationId);

    ServerResponse deleteDevice(DeviceList deviceList);

    ServerResponse editDevice(DeviceList deviceList);
}
