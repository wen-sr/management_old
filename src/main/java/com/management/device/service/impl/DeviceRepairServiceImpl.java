package com.management.device.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.Constant;
import com.management.common.Constant.DeviceListStatusEnum;
import com.management.common.Constant.DeviceRepairEnum;
import com.management.common.Constant.DeviceTransStatusEnum;
import com.management.common.ServerResponse;
import com.management.device.dao.DeviceListMapper;
import com.management.device.dao.DeviceRepairMapper;
import com.management.device.dao.DeviceTransMapper;
import com.management.device.dao.DeviceTypeMapper;
import com.management.device.pojo.DeviceList;
import com.management.device.pojo.DeviceRepair;
import com.management.device.pojo.DeviceTrans;
import com.management.device.pojo.DeviceType;
import com.management.device.service.IDeviceRepairService;
import com.management.device.vo.RepairVo;
import com.management.login.dao.LoginMapper;
import com.management.login.dao.RoleLoginMapper;
import com.management.login.vo.UserVo;
import com.management.oa.dao.OrganizationMapper;
import com.management.oa.vo.OrganizationVo;
import com.management.util.DateTimeUtil;
import com.management.wechat.dao.UserInfoMapper;
import com.management.wechat.pojo.UserInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.WxMpTemplateMessageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeviceRepairServiceImpl
        implements IDeviceRepairService
{
    @Autowired
    DeviceRepairMapper deviceRepairMapper;
    @Autowired
    DeviceTypeMapper deviceTypeMapper;
    @Autowired
    DeviceListMapper deviceListMapper;
    @Autowired
    WxMpService wxMpService;
    @Autowired
    LoginMapper loginMapper;
    @Autowired
    DeviceTransMapper deviceTransMapper;
    @Autowired
    OrganizationMapper organizationMapper;
    @Autowired
    RoleLoginMapper roleLoginMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    public ServerResponse addRepair(DeviceRepair deviceRepair)
    {
        DeviceList deviceList = this.deviceListMapper.selectByPrimaryKey(deviceRepair.getDeviceId());
        if (deviceList != null)
        {
            deviceList.setStatus(Constant.DeviceListStatusEnum.REPAIRING.getCode().toString());
            this.deviceListMapper.updateByPrimaryKeySelective(deviceList);

            DeviceTrans deviceTrans = this.deviceTransMapper.selectByDeviceId(deviceList.getDeviceId(), Constant.DeviceTransStatusEnum.TAKE.getCode().toString());
            if (deviceTrans != null)
            {
                deviceTrans.setEditwho(deviceRepair.getDeviceUserId());
                deviceTrans.setStatus(Constant.DeviceTransStatusEnum.REPAIR.getCode().toString());
                this.deviceTransMapper.updateByPrimaryKeySelective(deviceTrans);
            }
            DeviceRepair deviceRepair1 = this.deviceRepairMapper.selectByDeviceIdAndStatus(deviceRepair.getDeviceId(), Constant.DeviceRepairEnum.REPAIRING.getCode().toString());
            if (deviceRepair1 != null)
            {
                UserVo userVo = this.loginMapper.selectUserAndOrgnazizationById(deviceRepair1.getDeviceUserId());

                return ServerResponse.createByErrorMessage("您填写的设备编号已由【" + userVo.getOrganizationName() + "】的【" + userVo.getName() + "】报修成功，请核查");
            }
            deviceRepair.setDeviceTypeId(deviceList.getDeviceTypeId());
            DeviceType deviceType = this.deviceTypeMapper.selectByPrimaryKey(deviceList.getDeviceTypeId());
            deviceRepair.setDeviceTypeName(deviceType.getDeviceTypeName());
            deviceRepair.setStatus(Constant.DeviceRepairEnum.REPAIRING.getCode());
            int i = this.deviceRepairMapper.insertSelective(deviceRepair);
            if (i > 0)
            {
                UserVo userVo = this.loginMapper.selectUserAndOrgnazizationById(deviceRepair.getDeviceUserId());
                String first = "【" + userVo.getOrganizationName() + "】的【" + userVo.getName() + "】报修，设备类型：" + deviceRepair.getDeviceTypeName() + "】，设备编号：【" + deviceRepair.getDeviceId() + "】";
                String keyword1 = "设备维修";
                String keyword2 = deviceRepair.getQuestionDescription();

                List<String> roleIds = new ArrayList();
                roleIds.add("1");
                roleIds.add("21");
                roleIds.add("22");

                List<UserInfo> userInfoList = this.userInfoMapper.selectByLoginIds(roleIds);
                if ((userInfoList != null) && (userInfoList.size() > 0)) {
                    for (UserInfo u : userInfoList) {
                        sendMsg(u.getOpenid(), first, keyword1, keyword2);
                    }
                }
                return ServerResponse.createBySuccessMsg("新增报修单成功");
            }
        }
        else
        {
            return ServerResponse.createByErrorMessage("您填写的设备编号不存在，请核实后重新提交");
        }
        return ServerResponse.createByErrorMessage("新增保修单失败，请检查网络链接是否正常");
    }

    private void sendMsg(String openId, String first, String keyword1, String keyword2)
    {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder().toUser(openId).templateId("54MmgysJAjY39jgldJyK-jFjkZZjn79d6pTfPTSgcLg").build();
        templateMessage.getData().add(new WxMpTemplateData("first", first, "#284177"));
        templateMessage.getData().add(new WxMpTemplateData("keyword1", keyword1, "#0044BB"));
        templateMessage.getData().add(new WxMpTemplateData("keyword2", keyword2, "#0044BB"));
        try
        {
            this.wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }
        catch (WxErrorException e)
        {
            e.printStackTrace();
        }
    }

    public ServerResponse<PageInfo> findAll(DeviceRepair deviceRepair, int pageSize, int pageNum, String organizationId)
    {
        Set<Integer> ids = null;
        if (StringUtils.isNotEmpty(organizationId)) {
            ids = getOrganizationId(Integer.valueOf(Integer.parseInt(organizationId)));
        }
        PageHelper.startPage(pageNum, pageSize);

        List<DeviceRepair> deviceRepairList = this.deviceRepairMapper.findAll(deviceRepair, ids);

        List<RepairVo> repairVoList = parseToRepairVo(deviceRepairList);

        PageInfo pageResult = new PageInfo(deviceRepairList);
        pageResult.setList(repairVoList);

        return ServerResponse.createBySuccess(pageResult);
    }

    public ServerResponse editRepair(DeviceRepair deviceRepair)
    {
        DeviceRepair deviceRepair1 = this.deviceRepairMapper.selectByPrimaryKey(deviceRepair.getId());
        if (deviceRepair1.getStatus() == Constant.DeviceRepairEnum.REPAIRED.getCode()) {
            return ServerResponse.createByErrorMessage("该设备已维修完成，不能再修改");
        }
        int i = 0;
        if ((deviceRepair.getStatus() == Constant.DeviceRepairEnum.REPAIRED.getCode()) &&
                (!StringUtils.equals(deviceRepair.getRepairUserId(), deviceRepair1.getDeviceUserId()))) {
            return ServerResponse.createByErrorMessage("维修完成智能由报修人提交");
        }
        DeviceTrans deviceTrans = this.deviceTransMapper.selectByDeviceId(deviceRepair1.getDeviceId(), Constant.DeviceRepairEnum.REPAIRING.getCode().toString());
        if (deviceTrans != null)
        {
            if (deviceRepair.getStatus() == Constant.DeviceRepairEnum.REPAIRED.getCode()) {
                deviceTrans.setStatus(Constant.DeviceTransStatusEnum.TAKE.getCode().toString());
            } else {
                deviceTrans.setStatus(deviceRepair.getStatus().toString());
            }
            deviceTrans.setEditwho(deviceRepair.getRepairUserId());
            i = this.deviceTransMapper.updateByPrimaryKeySelective(deviceTrans);
        }
        DeviceList deviceList = this.deviceListMapper.selectByPrimaryKey(deviceRepair1.getDeviceId());
        if (deviceList == null) {
            return ServerResponse.createByErrorMessage("设备编号不存在，请核查！");
        }
        deviceList.setBk1(deviceRepair.getRepairUserId());
        if (deviceRepair.getStatus() == Constant.DeviceRepairEnum.REPAIRED.getCode()) {
            deviceList.setStatus(Constant.DeviceListStatusEnum.USING.getCode().toString());
        } else {
            deviceList.setStatus(deviceRepair.getStatus().toString());
        }
        i += this.deviceListMapper.updateByPrimaryKeySelective(deviceList);
        if (deviceRepair.getStatus() == Constant.DeviceRepairEnum.REPAIRED.getCode())
        {
            deviceRepair1.setStatus(Constant.DeviceRepairEnum.REPAIRED.getCode());
            if (deviceRepair1.getRepairUserId() == null) {
                deviceRepair1.setRepairUserId(deviceRepair.getRepairUserId());
            }
            if (deviceRepair1.getCause() == null) {
                deviceRepair1.setCause(deviceRepair.getCause());
            }
            if (deviceRepair1.getComponent() == null) {
                deviceRepair1.setComponent(deviceRepair.getComponent());
            }
            if (deviceRepair1.getCost() == null) {
                deviceRepair1.setCost(deviceRepair.getCost());
            }
            if (deviceRepair1.getResult() == null) {
                deviceRepair1.setResult(deviceRepair.getResult());
            }
            i += this.deviceRepairMapper.updateByPrimaryKeySelective(deviceRepair1);
        }
        else
        {
            i += this.deviceRepairMapper.updateByPrimaryKeySelective(deviceRepair);
        }
        if (i > 0) {
            return ServerResponse.createBySuccessMsg("提交成功");
        }
        return ServerResponse.createByErrorMessage("提交失败");
    }

    private List<RepairVo> parseToRepairVo(List<DeviceRepair> deviceRepairList)
    {
        List<RepairVo> repairVoList = new ArrayList();
        RepairVo repairVo = null;
        for (DeviceRepair d : deviceRepairList)
        {
            repairVo = new RepairVo();
            repairVo.setDeviceId(d.getDeviceId());
            repairVo.setAdddate(DateTimeUtil.dateToStr(d.getAdddate()));
            repairVo.setBakup(d.getBakup());
            repairVo.setCause(d.getCause());
            repairVo.setDeviceTypeId(d.getDeviceTypeId());
            repairVo.setDeviceTypeName(d.getDeviceTypeName());
            repairVo.setDeviceUserId(d.getDeviceUserId());

            UserVo userVo = this.loginMapper.selectUserAndOrgnazizationById(d.getDeviceUserId());

            repairVo.setDeviceUserName(userVo.getName());
            repairVo.setOrganizationName(userVo.getOrganizationName());

            repairVo.setEditdate(DateTimeUtil.dateToStr(d.getEditdate()));
            repairVo.setId(d.getId());
            repairVo.setStatus(Constant.DeviceRepairEnum.codeOf(d.getStatus().intValue()).getMsg());
            repairVo.setQuestionDescription(d.getQuestionDescription());
            if (d.getRepairUserId() != null)
            {
                UserVo u = this.loginMapper.selectUserAndOrgnazizationById(d.getRepairUserId());
                repairVo.setRepairUserId(u.getName());
            }
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

    private Set<Integer> getOrganizationId(Integer organizationId)
    {
        Set<Integer> organizationIds = new HashSet();
        organizationIds.add(organizationId);
        getChildrenChildrenIds(organizationIds, organizationId);
        return organizationIds;
    }

    private Set<Integer> getChildrenChildrenIds(Set<Integer> organizationIds, Integer organizationId)
    {
        List<OrganizationVo> organization = this.organizationMapper.selectByParentId(organizationId);
        if ((organization != null) && (organization.size() > 0)) {
            for (OrganizationVo o : organization)
            {
                organizationIds.add(o.getId());
                getChildrenChildrenIds(organizationIds, o.getId());
            }
        }
        return organizationIds;
    }
}
