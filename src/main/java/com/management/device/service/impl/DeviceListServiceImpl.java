package com.management.device.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.Constant;
import com.management.common.ServerResponse;
import com.management.device.dao.DeviceListMapper;
import com.management.device.dao.DeviceTransMapper;
import com.management.device.dao.DeviceTypeMapper;
import com.management.device.pojo.DeviceList;
import com.management.device.pojo.DeviceTrans;
import com.management.device.service.IDeviceListService;
import com.management.device.service.IDeviceTransService;
import com.management.device.vo.DeviceListVo;
import com.management.login.dao.LoginMapper;
import com.management.login.pojo.Login;
import com.management.oa.dao.OrganizationMapper;
import com.management.oa.pojo.Organization;
import com.management.oa.vo.OrganizationVo;
import com.management.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class DeviceListServiceImpl implements IDeviceListService {

    @Autowired
    DeviceListMapper deviceListMapper;

    @Autowired
    DeviceTypeMapper deviceTypeMapper;

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    IDeviceTransService deviceTransService;

    @Autowired
    DeviceTransMapper deviceTransMapper;

    @Autowired
    OrganizationMapper organizationMapper;

    @Override
    public ServerResponse findAll(DeviceList deviceList, Integer pageSize, Integer pageNum) {

        Set<Integer> ids = null;
        if(StringUtils.isNotEmpty(deviceList.getBk3())){
            ids= this.getOrganizationId(Integer.parseInt(deviceList.getBk3()));
        }

        List<DeviceList> deviceLists = null;
        PageHelper.startPage(pageNum,pageSize);

        if(StringUtils.isNotEmpty(deviceList.getBk3())){
            deviceLists = deviceListMapper.findAllByOrganiation(ids, deviceList);
        }else {
            deviceLists = deviceListMapper.findAll(deviceList);
        }


        List<DeviceListVo> deviceListVoList = this.parseToDeviceListVo(deviceLists);

        PageInfo pageInfo = new PageInfo(deviceLists);

        pageInfo.setList(deviceListVoList);

        return ServerResponse.createBySuccess(pageInfo);

    }

    @Override
    public ServerResponse addDevice(DeviceList deviceList, String organizationId) {
        DeviceList dd = deviceListMapper.selectByPrimaryKey(deviceList.getDeviceId());

        if(dd != null){
            return ServerResponse.createByErrorMessage("您要添加的设备编号已存在");
        }

        //if(!StringUtils.equals(organizationId, "0" )){
        //    deviceList.setStatus(Constant.DeviceListStatusEnum.USING.getCode().toString());
        //    deviceList.setBk3(organizationId);
        //}else{
        //    deviceList.setStatus(Constant.DeviceListStatusEnum.FREE.getCode().toString());
        //}

        deviceList.setStatus(Constant.DeviceListStatusEnum.FREE.getCode().toString());
        int i =  deviceListMapper.insertSelective(deviceList);

        //设备领用
        if(!StringUtils.equals(organizationId, "0")){
            DeviceTrans deviceTrans = new DeviceTrans();
            deviceTrans.setOrganizationId(Integer.parseInt(organizationId));
            deviceTrans.setDeviceId(deviceList.getDeviceId());
            deviceTrans.setStatus(Constant.DeviceTransStatusEnum.TAKE.getCode().toString());
            deviceTrans.setAddwho(deviceList.getAddwho());
            deviceTrans.setEditwho(deviceList.getAddwho());
            deviceTransService.addTake(deviceTrans);
        }

        if(!StringUtils.equals(organizationId, "0" )){
            deviceList.setStatus(Constant.DeviceListStatusEnum.USING.getCode().toString());
            deviceList.setBk3(organizationId);
            deviceListMapper.updateByPrimaryKeySelective(deviceList);
        }

        if(i > 0){
            return ServerResponse.createBySuccessMsg("设备添加成功");
        }else{
            return ServerResponse.createByErrorMessage("添加设备数据失败，请联系管理员");
        }

    }

    @Override
    public ServerResponse deleteDevice(DeviceList deviceList) {
        DeviceList dd = deviceListMapper.selectByPrimaryKey(deviceList.getDeviceId());
        if(!StringUtils.equals(dd.getStatus(), Constant.DeviceListStatusEnum.FREE.getCode().toString())){
            return ServerResponse.createByErrorMessage("当前设备不是可用状态，不允许删除");
        }

        int i = deviceListMapper.deleteByPrimaryKey(deviceList.getDeviceId());
        if(i > 0) {
            return ServerResponse.createBySuccessMsg("设备删除成功");
        }
        return ServerResponse.createByErrorMessage("设备删除失败");
    }

    @Override
    public ServerResponse editDevice(DeviceList deviceList) {
        int i = deviceListMapper.updateByPrimaryKeySelective(deviceList);
        if(i > 0) {
            return ServerResponse.createBySuccessMsg("设备修改成功");
        }
        return ServerResponse.createByErrorMessage("设备修改失败");
    }

    private List<DeviceListVo> parseToDeviceListVo(List<DeviceList> deviceLists) {
        List<DeviceListVo> deviceListVoList = new ArrayList<>();
        DeviceListVo deviceListVo = null;
        for(DeviceList d : deviceLists) {
            deviceListVo = new DeviceListVo();
            deviceListVo.setAdddate(DateTimeUtil.dateToStr(d.getAdddate()));

            Login user = loginMapper.selectByPrimaryKey(d.getAddwho());

            if(user != null && user.getName() != null){
                deviceListVo.setAddwho(user.getName());
            }
            Login user2 = loginMapper.selectByPrimaryKey(d.getBk1());

            if(user2 != null && user2.getName() != null){
                deviceListVo.setBk1(user2.getName());
            }
//          使用bk3存放organizationId
//            if(StringUtils.equals(d.getStatus(), "1") || StringUtils.equals(d.getStatus(), "2")){
//                DeviceTrans deviceTran = deviceTransMapper.selectByDeviceId(d.getDeviceId(), d.getStatus());
//                if(deviceTran != null) {
//                    deviceListVo.setOrganizationId(deviceTran.getOrganizationId().toString());
//                    Organization organization = organizationMapper.selectByPrimaryKey(deviceTran.getOrganizationId());
//                    deviceListVo.setOrganizationName(organization.getName());
//                }
//            }
            if(d.getBk3() != null) {
                Organization organization = organizationMapper.selectByPrimaryKey(Integer.parseInt(d.getBk3()));
                deviceListVo.setOrganizationName(organization.getName());
            }

            deviceListVo.setBk2(d.getBk2());
//            deviceListVo.setBk3(d.getBk3());
            deviceListVo.setBk4(d.getBk4());
            deviceListVo.setBk5(d.getBk5());
            deviceListVo.setDeviceId(d.getDeviceId());
            deviceListVo.setDeviceTypeId(d.getDeviceTypeId());
            deviceListVo.setDeviceTypeName(deviceTypeMapper.selectByPrimaryKey(d.getDeviceTypeId()).getDeviceTypeName());
            deviceListVo.setEditdate(DateTimeUtil.dateToStr(d.getEditdate()));
            deviceListVo.setStatus(Constant.DeviceListStatusEnum.codeOf(Integer.parseInt(d.getStatus())).getMsg());
            deviceListVoList.add(deviceListVo);
        }

        return deviceListVoList;
    }

    private Set<Integer> getOrganizationId(Integer organizationId) {
        Set<Integer> organizationIds = new HashSet<>();
        organizationIds.add(organizationId);
        this.getChildrenChildrenIds(organizationIds, organizationId);
        return organizationIds;
    }

    private Set<Integer> getChildrenChildrenIds(Set<Integer> organizationIds, Integer organizationId) {
        List<OrganizationVo> organization = organizationMapper.selectByParentId(organizationId);
        if(organization != null && organization.size() > 0){
            for(OrganizationVo o : organization) {
                organizationIds.add(o.getId());
                getChildrenChildrenIds(organizationIds,o.getId());
            }
        }
        return organizationIds;
    }

}
