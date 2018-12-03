package com.management.device.controller;

import com.management.common.BaseCotroller;
import com.management.common.Constant;
import com.management.common.ResponseCode;
import com.management.common.ServerResponse;
import com.management.device.pojo.DeviceTrans;
import com.management.device.service.IDeviceTransService;
import com.management.login.pojo.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/deviceTrans")
public class DeviceTransController extends BaseCotroller{

    @Autowired
    IDeviceTransService deviceTransService;

    @RequestMapping(value = "/addTake",method = RequestMethod.POST)
    public ServerResponse addTake(DeviceTrans deviceTrans, HttpSession session){
        Login user = (Login) session.getAttribute(Constant.CURRENT_USER);
        if(user != null){
            deviceTrans.setAddwho(user.getId());
            deviceTrans.setEditwho(user.getId());
            deviceTrans.setStatus(Constant.DeviceTransStatusEnum.TAKE.getCode().toString());
            return deviceTransService.addTake(deviceTrans);
        }else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"您尚未登录");
        }
    }

    @RequestMapping(value = "/deviceReturn",method = RequestMethod.POST)
    public ServerResponse deviceReturn(DeviceTrans deviceTrans, HttpSession session){
        Login user = (Login) session.getAttribute(Constant.CURRENT_USER);
        if(user != null){
            deviceTrans.setEditwho(user.getId());
            deviceTrans.setStatus(Constant.DeviceTransStatusEnum.RETURN.getCode().toString());
            deviceTrans.setOrganizationId(null);
            return deviceTransService.deviceReturn(deviceTrans);
        }else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"您尚未登录");
        }
    }

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public ServerResponse query(DeviceTrans deviceTrans, HttpSession session){
        Login user = (Login) session.getAttribute(Constant.CURRENT_USER);
        if(user != null){
            return deviceTransService.query(deviceTrans);
        }else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"您尚未登录");
        }
    }




}
