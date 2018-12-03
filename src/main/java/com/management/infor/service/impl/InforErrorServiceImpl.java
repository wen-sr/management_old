package com.management.infor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.Constant;
import com.management.common.ResponseCode;
import com.management.common.ServerResponse;
import com.management.device.pojo.DeviceRepair;
import com.management.device.vo.RepairVo;
import com.management.infor.dao.InforErrorMapper;
import com.management.infor.pojo.InforError;
import com.management.infor.service.InforErrorService;
import com.management.infor.vo.InforErrorVo;
import com.management.login.dao.LoginMapper;
import com.management.login.vo.UserVo;
import com.management.oa.dao.OrganizationMapper;
import com.management.oa.vo.OrganizationVo;
import com.management.util.DateTimeUtil;
import com.management.util.FTPUtil;
import com.management.wechat.dao.UserInfoMapper;
import com.management.wechat.pojo.UserInfo;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.Line;
import java.util.*;

@Service
@Transactional
public class InforErrorServiceImpl implements InforErrorService {

    @Autowired
    InforErrorMapper inforErrorMapper;

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    OrganizationMapper organizationMapper;

    @Autowired
    WxMpService wxMpService;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public ServerResponse addInforError(InforError inforError) {
        int i = inforErrorMapper.insertSelective(inforError);

        UserVo userVo = loginMapper.selectUserAndOrgnazizationById(inforError.getAddwho());

        String first = "【" + userVo.getOrganizationName() + "】的【" +userVo.getName() +"】上报系统问题";
        String keyword1 = "系统问题";
        String keyword2 = inforError.getDescription();

        //查询信息主管和员工的工号
        List<String> roleIds = new ArrayList<>();
        roleIds.add("1");
        roleIds.add("24");

        List<UserInfo> userInfoList = userInfoMapper.selectByLoginIds(roleIds);

        if(userInfoList != null && userInfoList.size() > 0){
            for(UserInfo u : userInfoList) {
                this.sendMsg(u.getOpenid(), first, keyword1, keyword2);
            }
        }

        if(i > 0) {
            return ServerResponse.createBySuccessMsg("系统故障上报成功");
        }else{
            return ServerResponse.createByErrorMessage("系统故障上报失败");
        }
    }

    @Override
    public ServerResponse findAll(InforError inforError, Integer pageSize, Integer pageNum, String organizationId) {
        Set<Integer> ids = null;
        if(StringUtils.isNotEmpty(organizationId)){
            ids= this.getOrganizationId(Integer.parseInt(organizationId));
        }

        PageHelper.startPage(pageNum,pageSize);

        List<InforError> inforErrorList = inforErrorMapper.findAll(inforError, ids);

        List<InforErrorVo> inforErrorVoList = this.parseToInforErrorVo(inforErrorList);

        PageInfo pageResult = new PageInfo(inforErrorList);
        pageResult.setList(inforErrorVoList);

        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse edit(InforError inforError) {

        if(StringUtils.equals(inforError.getBk1(), Constant.InforErrorStatusEnum.RESOLVING.getCode().toString())){

        }else if(StringUtils.equals(inforError.getBk1(), Constant.InforErrorStatusEnum.RESOLVED.getCode().toString())){
            InforError i = inforErrorMapper.selectByPrimaryKey(inforError.getId());
            if(StringUtils.equals(i.getAddwho(),inforError.getProcessWho())){
                inforError.setProcessWho(null);
            }else {
                return ServerResponse.createByErrorMessage("必须由上报人来确认处理完成");
            }
        }

        int i = inforErrorMapper.updateByPrimaryKeySelective(inforError);

        if(i > 0) {
            return ServerResponse.createBySuccessMsg("修改成功");
        }else{
            return ServerResponse.createByErrorMessage("修改失败");
        }
    }

    @Override
    public ServerResponse delete(InforError inforError) {
        int i = inforErrorMapper.deleteByPrimaryKey(inforError.getId());
        if(i > 0) {
            return ServerResponse.createBySuccessMsg("删除成功");
        }else{
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

    private List<InforErrorVo> parseToInforErrorVo(List<InforError> inforErrorList) {
        List<InforErrorVo> inforErrorVoList = new ArrayList<>();
        InforErrorVo inforErrorVo = null;
        for(InforError i : inforErrorList){
            inforErrorVo = new InforErrorVo();
            inforErrorVo.setAdddate(DateTimeUtil.dateToStr(i.getAdddate()));
            UserVo u1 = loginMapper.selectUserAndOrgnazizationById(i.getAddwho());
            inforErrorVo.setAddwho(i.getAddwho());
            inforErrorVo.setAddwhoName(u1.getName());
            inforErrorVo.setOrganizationName(u1.getOrganizationName());
            inforErrorVo.setBk2(i.getBk2());
            inforErrorVo.setBk3(i.getBk3());
            inforErrorVo.setBk4(i.getBk4());
            inforErrorVo.setBk5(i.getBk5());
            inforErrorVo.setCause(i.getCause());
            inforErrorVo.setDescription(i.getDescription());
            inforErrorVo.setId(i.getId());
            inforErrorVo.setImageUrl(FTPUtil.getFtpUrl() + i.getImageUrl());
            inforErrorVo.setProcessDate(DateTimeUtil.dateToStr(i.getProcessDate()));
            inforErrorVo.setProcessWho(i.getProcessWho());

            if(i.getProcessWho() != null ){
                UserVo u2 = loginMapper.selectUserAndOrgnazizationById(i.getProcessWho());
                inforErrorVo.setProcessWhoName(u2.getName());
            }

            inforErrorVo.setResult(i.getResult());
            inforErrorVo.setSolution(i.getSolution());
            inforErrorVo.setType(Constant.InforErrorTypeEnum.codeOf(Integer.parseInt(i.getType())).getMsg());
            inforErrorVo.setBk1(Constant.InforErrorStatusEnum.codeOf(Integer.parseInt(i.getBk1())).getMsg());


            inforErrorVoList.add(inforErrorVo);
        }

        return inforErrorVoList;

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


    private void sendMsg(String openId, String first, String keyword1, String keyword2) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId("54MmgysJAjY39jgldJyK-jFjkZZjn79d6pTfPTSgcLg").build();;
        templateMessage.getData().add(new WxMpTemplateData("first", first, "#284177"));
        templateMessage.getData().add(new WxMpTemplateData("keyword1", keyword1, "#0044BB"));
        templateMessage.getData().add(new WxMpTemplateData("keyword2", keyword2, "#0044BB"));
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

}
