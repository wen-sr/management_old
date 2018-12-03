package com.management.device.controller;

import com.github.pagehelper.PageInfo;
import com.management.common.*;
import com.management.common.Constant.DeviceRepairEnum;
import com.management.device.pojo.DeviceRepair;
import com.management.device.service.IDeviceRepairService;
import com.management.login.pojo.Login;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/repair"})
public class DeviceRepairController
        extends BaseCotroller
{
    @Autowired
    IDeviceRepairService deviceRepairService;

    @RequestMapping({"/addRepair"})
    public ServerResponse addRepair(DeviceRepair deviceRepair, HttpSession session)
    {
        Login user = (Login)session.getAttribute("currentUser");
        if (user != null)
        {
            deviceRepair.setDeviceId(deviceRepair.getDeviceId().toUpperCase());
            deviceRepair.setDeviceUserId(user.getId());
            return this.deviceRepairService.addRepair(deviceRepair);
        }
        return ServerResponse.createByErrorMessage("����������");
    }

    @RequestMapping({"/editRepair"})
    public ServerResponse editRepair(DeviceRepair deviceRepair, HttpSession session)
    {
        Login user = (Login)session.getAttribute("currentUser");
        if (user != null)
        {
            if ((deviceRepair.getStatus() == Constant.DeviceRepairEnum.SCRAPPED.getCode()) &&
                    (!isAllow(user, 3))) {
                return ServerResponse.createByErrorCodeMessage(ResponseCode.NO_AUTHORITY.getCode(), "����������������������");
            }
            deviceRepair.setRepairUserId(user.getId());
            return this.deviceRepairService.editRepair(deviceRepair);
        }
        return ServerResponse.createByErrorMessage("����������");
    }

    @RequestMapping({"/findAll"})
    public EasyuiTableResponse findB(@RequestParam(value="rows", defaultValue="10") String pageSize, @RequestParam(value="page", defaultValue="1") int pageNum, @RequestParam(value="organizationId", defaultValue="0") String organizationId, DeviceRepair deviceRepair, HttpSession session, HttpServletRequest request)
    {
        deviceRepair = deviceRepair.toNull(deviceRepair);
        ServerResponse<PageInfo> response = this.deviceRepairService.findAll(deviceRepair, Integer.parseInt(pageSize), pageNum, organizationId);
        EasyuiTableResponse easyuiTableResponse = response.parseToEasyuiTableResponse(response);
        return easyuiTableResponse;
    }
}
