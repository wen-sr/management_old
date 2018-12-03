package com.management.device.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.Constant;
import com.management.common.ServerResponse;
import com.management.device.dao.*;
import com.management.device.pojo.*;
import com.management.device.service.DeviceStatusService;
import com.management.device.vo.DeviceVo;
import com.management.device.vo.RepairVo;
import com.management.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-20  14:09
 */
@Service
@Transactional
public class DeviceStatusServiceImpl implements DeviceStatusService {

    @Autowired
    DeviceStatusMapper deviceStatusMapper;

    @Autowired
    DeviceListMapper deviceListMapper;

    @Autowired
    DeviceTypeMapper deviceTypeMapper;

    @Autowired
    DeviceUsersMapper deviceUsersMapper;

    @Autowired
    DeviceRepairMapper deviceRepairMapper;
    @Override
    public ServerResponse takeReturn(String deviceId, Integer deviceTypeId, Integer status, Integer userId) {
        DeviceStatus deviceStatus = deviceStatusMapper.selectByPrimaryKey(deviceId);
        DeviceList deviceList = deviceListMapper.selectByDeviceIdAndDeviceTypeId(deviceId, deviceTypeId);
        if(deviceList == null){
            return ServerResponse.createByErrorMessage("系统中还没有对应的信息，请先添加该设备的信息");
        }
        if(status == Constant.DeviceStatusEnum.TAKE.getCode()){
            if(userId == -1){
                return ServerResponse.createByErrorMessage("你还没有选择领用者");
            }
        }
        if(status == Constant.DeviceStatusEnum.QUERY.getCode()){
            if(deviceStatus != null){
                if(deviceStatus.getStatus() != null){
                    StringBuffer sb = new StringBuffer("该设备当前的状态是:【").append(Constant.DeviceStatusEnum.codeOf(deviceStatus.getStatus()).getMsg()).append("】");
                    if(deviceStatus.getStatus() == Constant.DeviceStatusEnum.TAKE.getCode()){
                        sb.append(",领用者为【"+ deviceStatus.getDeviceUserName() +"】");
                    }
                    return ServerResponse.createBySuccessMsg(sb.toString());
                }
            }else {
                return ServerResponse.createBySuccessMsg("该设备可用");
            }
        }

        if(deviceStatus == null ){
            if(status == Constant.DeviceStatusEnum.RETURN.getCode()){
                return ServerResponse.createByErrorMessage("【归还失败】该设备处于可用状态无需归还");
            }
            DeviceType deviceType = deviceTypeMapper.selectByPrimaryKey(deviceTypeId);
            if(status == Constant.DeviceStatusEnum.FIX.getCode()){
                DeviceRepair deviceRepair = new DeviceRepair();
                deviceRepair.setDeviceId(deviceList.getDeviceId());
                deviceRepair.setDeviceTypeId(deviceList.getDeviceTypeId());
                deviceRepair.setDeviceTypeName(deviceType.getDeviceTypeName());
                deviceRepair.setStatus(Constant.DeviceStatusEnum.REPAIRING.getCode());
                deviceRepairMapper.insertSelective(deviceRepair);
            }

            deviceStatus = new DeviceStatus();
            deviceStatus.setDeviceId(deviceList.getDeviceId());
            deviceStatus.setDeviceTypeId(deviceList.getDeviceTypeId());
            deviceStatus.setStatus(status);
            deviceStatus.setDeviceUserId(String.valueOf(userId));
            deviceStatus.setDeviceTypeName(deviceType.getDeviceTypeName());
            if(status == Constant.DeviceStatusEnum.TAKE.getCode()){
                DeviceUsers deviceUsers = deviceUsersMapper.selectByPrimaryKey(userId);
                deviceStatus.setDeviceUserName(deviceUsers.getDeviceUserName());
            }

            deviceStatusMapper.insertSelective(deviceStatus);
            return returnSuccessByStatus(status);
        }else {

            if(status == Constant.DeviceStatusEnum.RETURN.getCode()){
                if(deviceStatus.getStatus() != Constant.DeviceStatusEnum.TAKE.getCode())
                return ServerResponse.createByErrorMessage("【归还失败】该设备不是处于 【领用】 状态无法归还");
            }

            if(status == Constant.DeviceStatusEnum.FIX.getCode()){
                DeviceRepair deviceRepair = new DeviceRepair();
                deviceRepair.setDeviceId(deviceStatus.getDeviceId());
                deviceRepair.setStatus(Constant.DeviceStatusEnum.REPAIRING.getCode());
                deviceRepair.setDeviceTypeId(deviceStatus.getDeviceTypeId());
                deviceRepair.setDeviceTypeName(deviceStatus.getDeviceTypeName());
                deviceRepair.setDeviceUserId(deviceStatus.getDeviceUserId());
                deviceRepair.setDeviceUserName(deviceStatus.getDeviceUserName());
                deviceRepairMapper.insertSelective(deviceRepair);
            }

            if(status == Constant.DeviceStatusEnum.TAKE.getCode()){
                if(deviceStatus.getStatus() == Constant.DeviceStatusEnum.TAKE.getCode()){
                    return ServerResponse.createByErrorMessage("【领用失败】该设备已被其他人领用");
                }
                if(deviceStatus.getStatus() == Constant.DeviceStatusEnum.FIX.getCode()){
                    return ServerResponse.createByErrorMessage("【领用失败】该设备处于维修状态暂不可用");
                }
            }

            deviceStatus.setStatus(status);
            deviceStatus.setDeviceUserId(String.valueOf(-1));
            deviceStatus.setDeviceUserName("");
            if(status == Constant.DeviceStatusEnum.TAKE.getCode()){
                DeviceUsers deviceUsers = deviceUsersMapper.selectByPrimaryKey(userId);
                deviceStatus.setDeviceUserId(String.valueOf(userId));
                deviceStatus.setDeviceUserName(deviceUsers.getDeviceUserName());
            }

            int i = deviceStatusMapper.updateByPrimaryKeySelective(deviceStatus);
            if(i > 0) {
                return returnSuccessByStatus(status);
            }else{
                return ServerResponse.createByErrorMessage("【领用失败】修改设备状态失败");
            }
        }
    }

    @Override
    public ServerResponse findAll(DeviceStatus deviceStatus, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);

        List<DeviceStatus> deviceStatusList = deviceStatusMapper.findAll(deviceStatus);

        List<DeviceVo> deviceVoList = parseToDeviceVo(deviceStatusList);

        PageInfo pageResult = new PageInfo(deviceStatusList);
        pageResult.setList(deviceVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<PageInfo> findRepair(DeviceRepair deviceRepair, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);

        List<DeviceRepair> deviceRepairList = deviceRepairMapper.findAll(deviceRepair, null);

        List<RepairVo> repairVoList = parseToRepairVo(deviceRepairList);

        PageInfo pageResult = new PageInfo(deviceRepairList);
        pageResult.setList(repairVoList);
        
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse updateRepair(DeviceRepair deviceRepair) {
        int i = deviceRepairMapper.updateByPrimaryKeySelective(deviceRepair);
        if(i > 0){
            if(deviceRepair.getStatus() == Constant.DeviceStatusEnum.REPAIRED.getCode()) {
                DeviceStatus deviceStatus = new DeviceStatus();
                deviceStatus.setDeviceId(deviceRepair.getDeviceId());
                deviceStatus.setStatus(Constant.DeviceStatusEnum.RETURN.getCode());
                deviceStatusMapper.updateByPrimaryKeySelective(deviceStatus);
            }
            return ServerResponse.createBySuccessMsg("数据更新成功");
        }
        return ServerResponse.createByErrorMessage("数据更新失败，请联系管理员");
    }

    private List<RepairVo> parseToRepairVo(List<DeviceRepair> deviceRepairList) {
        List<RepairVo> repairVoList = new ArrayList<>();
        RepairVo repairVo = null;
        for(DeviceRepair d : deviceRepairList){
            repairVo = new RepairVo();
            repairVo.setDeviceId(d.getDeviceId());
            repairVo.setAdddate(DateTimeUtil.dateToStr(d.getAdddate()));
            repairVo.setBakup(d.getBakup());
            repairVo.setCause(d.getCause());
            repairVo.setDeviceTypeId(d.getDeviceTypeId());
            repairVo.setDeviceTypeName(d.getDeviceTypeName());
            repairVo.setDeviceUserId(d.getDeviceUserId());
            repairVo.setDeviceUserName(d.getDeviceUserName());
            repairVo.setEditdate(DateTimeUtil.dateToStr(d.getEditdate()));
            repairVo.setId(d.getId());
            repairVo.setStatus(Constant.DeviceStatusEnum.codeOf(d.getStatus()).getMsg());
            repairVo.setQuestionDescription(d.getQuestionDescription());
            repairVo.setRepairUserId(d.getRepairUserId());
            repairVo.setComponent(d.getComponent());
            repairVo.setCost(d.getCost());
            repairVo.setResult(d.getResult());
            repairVo.setBk1(d.getBk1());
            repairVo.setBk2(d.getBk2());
            repairVo.setBk3(d.getBk3());
            repairVo.setBk4(d.getBk4());
            repairVo.setBk5(d.getBk5());
            repairVoList.add(repairVo);
        }
        return repairVoList;
    }


    private List<DeviceVo> parseToDeviceVo(List<DeviceStatus> deviceStatusList) {
        List<DeviceVo> deviceVoList = new ArrayList<>();
        DeviceVo deviceVo = null;
        for(DeviceStatus deviceStatus : deviceStatusList ){
            deviceVo = new DeviceVo();
            deviceVo.setAdddate(DateTimeUtil.dateToStr(deviceStatus.getAdddate()));
            deviceVo.setDeviceId(deviceStatus.getDeviceId());
            deviceVo.setDeviceTypeId(deviceStatus.getDeviceTypeId());
            deviceVo.setDeviceTypeName(deviceStatus.getDeviceTypeName());
            deviceVo.setDeviceUserName(deviceStatus.getDeviceUserName());
            deviceVo.setEditdate(DateTimeUtil.dateToStr(deviceStatus.getEditdate()));
            deviceVo.setDeviceUserId(deviceStatus.getDeviceUserId());
            deviceVo.setStatus(Constant.DeviceStatusEnum.codeOf(deviceStatus.getStatus()).getMsg());
            deviceVoList.add(deviceVo);
        }

        return deviceVoList;
    }
    
    


    private ServerResponse returnSuccessByStatus(Integer status) {
        if(status == Constant.DeviceStatusEnum.TAKE.getCode() ){
            return ServerResponse.createBySuccessMsg("成功将该设备修改为【" + Constant.DeviceStatusEnum.TAKE.getMsg() + "】状态");
        }else if(status == Constant.DeviceStatusEnum.RETURN.getCode()){
            return ServerResponse.createBySuccessMsg("成功将该设备修改为【" + Constant.DeviceStatusEnum.RETURN.getMsg() + "】状态");
        }else if(status == Constant.DeviceStatusEnum.FIX.getCode()){
            return ServerResponse.createBySuccessMsg("成功将该设备修改为【" + Constant.DeviceStatusEnum.FIX.getMsg() + "】状态");
        }
        return ServerResponse.createByErrorMessage("系统故障，联系管理员");
    }

}
