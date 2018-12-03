package com.management.device.controller;

import com.management.common.*;
import com.management.device.pojo.DeviceList;
import com.management.device.pojo.DeviceRepair;
import com.management.device.pojo.DeviceType;
import com.management.device.service.IDeviceListService;
import com.management.login.pojo.Login;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/deviceList")
public class DeviceListController extends BaseCotroller{

    @Autowired
    IDeviceListService deviceListService;

    @RequestMapping("findAll")
    public EasyuiTableResponse findAll(@RequestParam(value = "rows", defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                       DeviceList deviceList,HttpSession session){

        ServerResponse response = deviceListService.findAll(deviceList, pageSize, pageNum);

        return response.parseToEasyuiTableResponse(response);
    }

    @RequestMapping("/addDevice")
    public ServerResponse addDevice(HttpSession session, DeviceList deviceList, @RequestParam(value = "organizationId", defaultValue = "0") String organizationId ){
        Login user = (Login) session.getAttribute(Constant.CURRENT_USER);
        if(user != null){
            if(!super.isAllow(user, Constant.Role.ROLE_TECHNOLOGY)){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.NO_AUTHORITY.getCode(),"对不起，您没有权限操作");
            }

            deviceList.setAddwho(user.getId());
            deviceList.setBk1(user.getId());
            //if(!StringUtils.equals(organizationId, "0" )){
            //    deviceList.setStatus(Constant.DeviceListStatusEnum.USING.getCode().toString());
            //    deviceList.setBk3(organizationId);
            //}else{
            //    deviceList.setStatus(Constant.DeviceListStatusEnum.FREE.getCode().toString());
            //}
            return deviceListService.addDevice(deviceList, organizationId);
        }else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"您尚未登录");
        }
    }


    @RequestMapping("/editDevice")
    public ServerResponse editDeviceType(HttpSession session, DeviceList deviceList){
        Login user = (Login) session.getAttribute(Constant.CURRENT_USER);

        if(user != null){
            if(!super.isAllow(user, Constant.Role.ROLE_TECHNOLOGY)){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.NO_AUTHORITY.getCode(),"对不起，您没有权限操作");
            }
            return deviceListService.editDevice(deviceList);
        }else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"您尚未登录");
        }
    }

    @RequestMapping("/deleteDevice")
    public ServerResponse deleteDevice(HttpSession session, DeviceList deviceList){
        Login user = (Login) session.getAttribute(Constant.CURRENT_USER);
        if(user != null){
            if(!super.isAllow(user, Constant.Role.ROLE_TECHNOLOGY)){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.NO_AUTHORITY.getCode(),"对不起，您没有权限操作");
            }
            return deviceListService.deleteDevice(deviceList);
        }else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"您尚未登录");
        }
    }
}
