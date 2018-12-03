package com.management.device.controller;


import com.management.common.Constant;
import com.management.common.ResponseCode;
import com.management.common.ServerResponse;
import com.management.device.pojo.DeviceType;
import com.management.device.service.IDeviceTypeService;
import com.management.device.vo.DeviceTypeVo;
import com.management.login.pojo.Login;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/deviceType")
public class DeviceTypeController {

    @Autowired
    IDeviceTypeService iDeviceTypeService;

    @RequestMapping("/findAll")
    public List<DeviceTypeVo> findAll(){
        return iDeviceTypeService.findAll();
    }


    @RequestMapping("/addDeviceType")
    public ServerResponse addDeviceType(HttpSession session, DeviceType deviceType){
        Login user = (Login) session.getAttribute(Constant.CURRENT_USER);
        if(user != null){
            deviceType.setAddwho(user.getId());
            return iDeviceTypeService.addDeviceType(deviceType);
        }else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"您尚未登录");
        }

    }

    @RequestMapping("/editDeviceType")
    public ServerResponse editDeviceType(HttpSession session, DeviceType deviceType){
        Login user = (Login) session.getAttribute(Constant.CURRENT_USER);
        if(user != null){
            return iDeviceTypeService.editDeviceType(deviceType);
        }else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"您尚未登录");
        }
    }

    @RequestMapping("/deleteDeviceType")
    public ServerResponse deleteDeviceType(HttpSession session, DeviceType deviceType){
        Login user = (Login) session.getAttribute(Constant.CURRENT_USER);
        if(user != null){
            if(!StringUtils.equals(user.getPostid(),"3")){
                return ServerResponse.createByErrorMessage("对不起，您没有权限删除设备类型");
            }
            return iDeviceTypeService.deleteDeviceType(deviceType);
        }else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"您尚未登录");
        }
    }

}
