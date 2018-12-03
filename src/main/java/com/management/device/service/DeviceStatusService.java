package com.management.device.service;

import com.github.pagehelper.PageInfo;
import com.management.common.ServerResponse;
import com.management.device.pojo.DeviceRepair;
import com.management.device.pojo.DeviceStatus;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-20  14:08
 */
public interface DeviceStatusService {
    ServerResponse takeReturn(String deviceId, Integer deviceTypeId, Integer status, Integer userId);

    ServerResponse findAll(DeviceStatus deviceStatus, int pageSize, int currentPage);

    ServerResponse<PageInfo> findRepair(DeviceRepair deviceRepair, int pageSize, int pageNum);

    ServerResponse updateRepair(DeviceRepair deviceRepair);
}
