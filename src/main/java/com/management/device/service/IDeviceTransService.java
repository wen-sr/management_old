package com.management.device.service;

import com.management.common.ServerResponse;
import com.management.device.pojo.DeviceTrans;

public interface IDeviceTransService {
    ServerResponse addTake(DeviceTrans deviceTrans);

    ServerResponse deviceReturn(DeviceTrans deviceTrans);


    ServerResponse query(DeviceTrans deviceTrans);
}
