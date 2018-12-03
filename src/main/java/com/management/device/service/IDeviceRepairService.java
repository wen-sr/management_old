package com.management.device.service;

import com.github.pagehelper.PageInfo;
import com.management.common.ServerResponse;
import com.management.device.pojo.DeviceRepair;

public interface IDeviceRepairService {

    ServerResponse addRepair(DeviceRepair deviceRepair);

    ServerResponse<PageInfo> findAll(DeviceRepair deviceRepair, int i, int pageNum, String organizationId);

    ServerResponse editRepair(DeviceRepair deviceRepair);
}
