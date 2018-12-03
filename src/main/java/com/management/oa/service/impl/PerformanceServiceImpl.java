package com.management.oa.service.impl;

import com.management.common.ServerResponse;
import com.management.login.dao.LoginMapper;
import com.management.login.pojo.Login;
import com.management.oa.dao.PerformanceMapper;
import com.management.oa.pojo.Performance;
import com.management.oa.service.IPerformanceService;
import com.management.util.MD5Util;
import com.management.wechat.dao.UserInfoMapper;
import com.management.wechat.pojo.UserInfo;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class PerformanceServiceImpl implements IPerformanceService {

    @Autowired
    PerformanceMapper performanceMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    WxMpService wxMpService;

    @Autowired
    LoginMapper loginMapper;


    @Override
    public ServerResponse saveRecords(List<List<Object>> listob) throws Exception {
        Performance p = new Performance();
        BASE64Encoder encoder = new BASE64Encoder();
        Performance pp = performanceMapper.selectInfo(new Performance(
                String.valueOf(listob.get(0).get(0)),
                encoder.encode(String.valueOf(listob.get(0).get(1)).getBytes()),
                encoder.encode(String.valueOf(listob.get(0).get(3)).getBytes()),
                encoder.encode(String.valueOf(listob.get(0).get(4)).getBytes())));

        if(pp != null){
            return ServerResponse.createByErrorMessage("您已上传，不能重复上传");
        }

        int ii = 0;
        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            p.setDd(String.valueOf(lo.get(0)));
            p.setOaid(encoder.encode(String.valueOf(lo.get(2)).getBytes()));
            p.setDepart(encoder.encode(String.valueOf(lo.get(2)).getBytes()));
            p.setName(encoder.encode(String.valueOf(lo.get(3)).getBytes()));
            p.setPostbonus(encoder.encode(String.valueOf(lo.get(4)).getBytes()));
            p.setYearsbonus(encoder.encode(String.valueOf(lo.get(5)).getBytes()));
            p.setOvertimebonus(encoder.encode(String.valueOf(lo.get(6)).getBytes()));
            p.setPerformancebonus(encoder.encode(String.valueOf(lo.get(7)).getBytes()));
            p.setOthersaddbonus(encoder.encode(String.valueOf(lo.get(8)).getBytes()));
            p.setTotaladdbonus(encoder.encode(String.valueOf(lo.get(9)).getBytes()));
            p.setSocialsecurity(encoder.encode(String.valueOf(lo.get(10)).getBytes()));
            p.setMedicare(encoder.encode(String.valueOf(lo.get(11)).getBytes()));
            p.setUnemploymentinsurance(encoder.encode(String.valueOf(lo.get(12)).getBytes()));
            p.setProvidentfund(encoder.encode(String.valueOf(lo.get(13)).getBytes()));
            p.setMembershipfee(encoder.encode(String.valueOf(lo.get(14)).getBytes()));
            p.setHydropower(encoder.encode(String.valueOf(lo.get(15)).getBytes()));
            p.setError(encoder.encode(String.valueOf(lo.get(16)).getBytes()));
            p.setAbsence(encoder.encode(String.valueOf(lo.get(17)).getBytes()));
            p.setOthersminusbonus(encoder.encode(String.valueOf(lo.get(18)).getBytes()));
            p.setTax(encoder.encode(String.valueOf(lo.get(19)).getBytes()));
            p.setTotalminusbonus(encoder.encode(String.valueOf(lo.get(20)).getBytes()));
            p.setRealbonus(encoder.encode(String.valueOf(lo.get(21)).getBytes()));

            ii  += performanceMapper.insert(p);

            //发送微信消息
            sendWechatMessage(p);
        }

        if(ii > 0 ){

            return ServerResponse.createBySuccessMsg("数据上传成功");
        }

        return ServerResponse.createByErrorMessage("数据上传失败，请联系管理员");
    }

    @Override
    public ServerResponse query(String begin, String end, String pwd, String id) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        BASE64Decoder decoder = new BASE64Decoder();

        //验证工号与口令
        Login u = loginMapper.selectUserByIdAndPwd(id, pwd);
        if(u == null){
            u = loginMapper.selectUserByIdAndPwd(id, MD5Util.MD5EncodeUtf8(pwd));
        }

        if(u != null){
            List<Performance> performanceList = performanceMapper.query(begin, end, encoder.encode(u.getName().getBytes()));
            for(Performance p : performanceList){
                p.setName(new String(decoder.decodeBuffer(p.getName())));
            }
            return ServerResponse.createBySuccess(performanceList);
        }
        return ServerResponse.createByErrorMessage("没有查询到您的信息");
    }

    @Override
    public ServerResponse modifyPwd(String oldPwd, String newPwd, String rePwd, String id) {
        Login user = loginMapper.selectUserByIdAndPwd(id, oldPwd);
        if(user == null) {
            user = loginMapper.selectUserByIdAndPwd(id, MD5Util.MD5EncodeUtf8(oldPwd));
            if(user == null) {
                return ServerResponse.createByErrorMessage("您输入的原口令不正确");
            }
        }
        user.setBk3(MD5Util.MD5EncodeUtf8(newPwd));
        loginMapper.updateByPrimaryKeySelective(user);

        return ServerResponse.createBySuccessMsg("口令修改完成");
    }

    private void sendWechatMessage(Performance p) throws Exception {
        UserInfo userInfo = null;
        BASE64Decoder decoder = new BASE64Decoder();
        userInfo = userInfoMapper.selectUserByName(new String(decoder.decodeBuffer(p.getName())));

        if(userInfo != null && userInfo.getOpenid() != null ) {
            try {
                String info = "岗位工资:" + new String(decoder.decodeBuffer(p.getPostbonus())) + ";" + 
                        "年功工资:" + new String(decoder.decodeBuffer(p.getYearsbonus())) + ";" + 
                        "加班:" + new String(decoder.decodeBuffer(p.getOvertimebonus())) + ";" +
                        "绩效:" + new String(decoder.decodeBuffer(p.getPerformancebonus())) + ";" +
                        "其他（增项）:" + new String(decoder.decodeBuffer(p.getOthersaddbonus())) + ";" + 
                        "应发合计:" + new String(decoder.decodeBuffer(p.getTotaladdbonus())) + ";" + 
                        "保险:" + new String(decoder.decodeBuffer(p.getSocialsecurity())) + ";" + 
                        "医疗险:" + new String(decoder.decodeBuffer(p.getMedicare())) + ";" + 
                        "失业险:" + new String(decoder.decodeBuffer(p.getUnemploymentinsurance())) + ";" + 
                        "公积金:" + new String(decoder.decodeBuffer(p.getProvidentfund())) + ";" + 
                        "会费:" + new String(decoder.decodeBuffer(p.getMembershipfee())) + ";" + 
                        "水电费:" + new String(decoder.decodeBuffer(p.getHydropower())) + ";" + 
                        "差错:" + new String(decoder.decodeBuffer(p.getError())) + ";" + 
                        "缺勤:" + new String(decoder.decodeBuffer(p.getAbsence())) + ";" + 
                        "其他（减项）:" + new String(decoder.decodeBuffer(p.getOthersminusbonus())) + ";" + 
                        "扣税:" + new String(decoder.decodeBuffer(p.getTax())) + ";" + 
                        "扣款合计:" + new String(decoder.decodeBuffer(p.getTotalminusbonus())) + ";" + 
                        "实发合计:" + new String(decoder.decodeBuffer(p.getRealbonus()));

                String first = "您的工资条已下发，具体到账时间以银行到账时间为准";
                String keyword1 = p.getDd();
                String keyword3 = "江西蓝海物流科技有限公司";

                this.sendMsg(userInfo.getOpenid(), first, keyword1, info, keyword3);
            } catch (Exception e) {
            }
        }

    }

    private void sendMsg(String openId, String first, String keyword1, String keyword2, String keyword3) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId("092HZD99REA_V0-LvrKr6y93LpH2w5Qgu9agJzmUltk")
                .url("http://www.jxlh56.com/management/oa/wechat/auth?returnUrl=/page/lhwl/performanceWeChat.html")
                .build();
        templateMessage.getData().add(new WxMpTemplateData("first", first, "#284177"));
        templateMessage.getData().add(new WxMpTemplateData("keyword1", keyword1, "#0044BB"));
        templateMessage.getData().add(new WxMpTemplateData("keyword2", keyword2, "#0044BB"));
        templateMessage.getData().add(new WxMpTemplateData("keyword3", keyword3, "#0044BB"));
        templateMessage.getData().add(new WxMpTemplateData("remark", "最终解释权归属江西蓝海物流人力资源部所有", "#AAAAAA"));
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }


}


