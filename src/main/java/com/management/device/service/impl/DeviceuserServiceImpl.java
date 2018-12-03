package com.management.device.service.impl;

import com.management.common.ServerResponse;
import com.management.device.dao.DeviceUsersMapper;
import com.management.device.pojo.DeviceUsers;
import com.management.device.service.IDeviceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-27  8:47
 */
@Service
@Transactional
public class DeviceuserServiceImpl implements IDeviceUserService {

    @Autowired
    DeviceUsersMapper deviceUsersMapper;

    @Override
    public ServerResponse getDeviceUsers() {
        List<DeviceUsers> deviceUsersList = deviceUsersMapper.selectAll();
        return ServerResponse.createBySuccess(deviceUsersList);
    }
}
