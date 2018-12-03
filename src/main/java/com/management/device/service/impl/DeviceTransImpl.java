package com.management.device.service.impl;

import com.management.common.Constant;
import com.management.common.ServerResponse;
import com.management.device.dao.DeviceListMapper;
import com.management.device.dao.DeviceTransMapper;
import com.management.device.pojo.DeviceList;
import com.management.device.pojo.DeviceTrans;
import com.management.device.service.IDeviceTransService;
import com.management.device.vo.DeviceTransVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DeviceTransImpl implements IDeviceTransService {


    @Autowired
    DeviceTransMapper deviceTransMapper;

    @Autowired
    DeviceListMapper deviceListMapper;

    @Override
    public ServerResponse addTake(DeviceTrans deviceTrans) {

        DeviceList deviceList = deviceListMapper.selectByPrimaryKey(deviceTrans.getDeviceId());
        if(deviceList == null ){
            return ServerResponse.createByErrorMessage("该设备代码不存在，请核查");
        }
        System.out.println((new Date() ).getTime() - deviceList.getAdddate().getTime());
        if((!StringUtils.equals(deviceList.getStatus(), Constant.DeviceListStatusEnum.FREE.getCode().toString()))){
            return ServerResponse.createByErrorMessage("该设备不是可用状态，无法领用，请核查");
        }


        //判断是否已领用
        DeviceTrans deviceTrans1 = deviceTransMapper.selectByDeviceId(deviceTrans.getDeviceId(), Constant.DeviceTransStatusEnum.TAKE.getCode().toString());
        if(deviceTrans1 != null) {
            return ServerResponse.createByErrorMessage("该设备已被领用，请核查");
        }



        int i = deviceTransMapper.insertSelective(deviceTrans);

        deviceList.setStatus(Constant.DeviceListStatusEnum.USING.getCode().toString());
        deviceList.setBk1(deviceTrans.getAddwho());
        deviceList.setBk3(deviceTrans.getOrganizationId().toString());
        i += deviceListMapper.updateByPrimaryKeySelective(deviceList);

        if(i > 0) {
            return ServerResponse.createBySuccessMsg("设备领用成功");
        }
        return ServerResponse.createByErrorMessage("设备领用失败");
    }

    @Override
    public ServerResponse deviceReturn(DeviceTrans deviceTrans) {

        DeviceList deviceList = deviceListMapper.selectByPrimaryKey(deviceTrans.getDeviceId());
        if(deviceList == null ){
            return ServerResponse.createByErrorMessage("该设备代码不存在，请核查");
        }

        //判断是否已领用
        DeviceTrans deviceTrans1 = deviceTransMapper.selectByDeviceId(deviceTrans.getDeviceId(), Constant.DeviceTransStatusEnum.TAKE.getCode().toString());
        if(deviceTrans1 == null || !StringUtils.equals(deviceTrans1.getStatus(), Constant.DeviceTransStatusEnum.TAKE.getCode().toString())) {
            return ServerResponse.createByErrorMessage("该设备未被领用或不是领用状态，无法归还，请核查");
        }

        deviceTrans.setId(deviceTrans1.getId());
        int i = deviceTransMapper.updateByPrimaryKeySelective(deviceTrans);

        deviceList.setStatus(Constant.DeviceListStatusEnum.FREE.getCode().toString());
        deviceList.setBk1(deviceTrans.getAddwho());
        deviceList.setBk3(null);
        i += deviceListMapper.updateByPrimaryKey(deviceList);

        if(i > 0) {
            return ServerResponse.createBySuccessMsg("设备归还成功");
        }
        return ServerResponse.createByErrorMessage("设备归还失败");
    }

    @Override
    public ServerResponse query(DeviceTrans deviceTrans) {

        DeviceList deviceList = deviceListMapper.selectByPrimaryKey(deviceTrans.getDeviceId());
        if(deviceList == null ){
            return ServerResponse.createByErrorMessage("该设备代码不存在，请核查");
        }

        List<DeviceTransVo> deviceTransVoList = deviceTransMapper.selectAllByDeviceId(deviceTrans.getDeviceId());
        if(deviceTransVoList != null && deviceTransVoList.size() > 0 ){
            DeviceTransVo deviceTransVo = deviceTransVoList.get(0);
            String msg = "您所查询的设备当前状态为：【" + Constant.DeviceTransStatusEnum.codeOf(Integer.parseInt(deviceTransVo.getStatus())).getMsg() + "】, 当前使用部门为：【" + deviceTransVo.getOrganizationName() + "】";
            return ServerResponse.createBySuccessMsg(msg);
        }

        return ServerResponse.createByErrorMessage("未查询到该设备的领用记录");
    }
}
