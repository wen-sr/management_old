package com.management.wechat.handler;

import com.management.login.dao.LoginMapper;
import com.management.login.vo.UserVo;
import com.management.util.ApacheHttpUtils;
import com.management.wechat.builder.TextBuilder;
import com.management.wechat.dao.UserInfoMapper;
import com.management.wechat.pojo.UserInfo;
import com.management.wechat.service.WeixinService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    LoginMapper loginMapper;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
      Map<String, Object> context, WxMpService wxMpService,
            WxSessionManager sessionManager)    {

        WeixinService weixinService = (WeixinService) wxMpService;
        //组装回复消息
        String content = "";
        if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
            //可以选择将消息保存到本地

            if(wxMessage.getContent().contains("@")){
                content = "绑定工号中。。。";
                String[] strArray = wxMessage.getContent().split("@");
                if(strArray.length != 2 || "".equals(strArray[0]) || "".equals(strArray[1]) || "LH".equals(strArray[0].subSequence(0, 1))) {
                    content = "您绑定工号的格式不对";
                }else{
                    UserInfo userInfo = userInfoMapper.selectByOpenId(wxMessage.getFromUser());
                    if(userInfo != null){
                        if(userInfo.getLoginId() != null && "".equals(userInfo.getLoginId())){
                            content = "您好,您已经绑定工号【" + userInfo.getLoginId() + "】,无需再次绑定";
                        }else{
                            userInfo = userInfoMapper.selectByLoginId(strArray[0].toUpperCase());
                            if(userInfo != null){
                                content = "您好,您的工号已经绑定了一个微信,无法再次绑定";
                            }
                            //验证工号和姓名是否匹配
                            UserVo userVo = loginMapper.selectUserAndOrgnazizationById(strArray[0].toUpperCase());
                            if(userVo == null || !StringUtils.equals(userVo.getName(), strArray[1])){
                                String con = "";
                                if(userVo == null){
                                    con = "您所输入的工号不存在";
                                }else{
                                    con = "您所输入的工号与姓名不符";
                                }
                                WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                                        .toUser(wxMessage.getFromUser())
                                        .templateId("TikJ2xrlkEXAzx-hBmZZbjfV9HHeOaaPy3vq4jdRpCI").build();
                                templateMessage.getData().add(new WxMpTemplateData("first", "您好，绑定工号失败！", "#284177"));
                                templateMessage.getData().add(new WxMpTemplateData("keyword1", wxMessage.getContent(), "#0044BB"));
                                templateMessage.getData().add(new WxMpTemplateData("keyword2", con, "#0044BB"));
                                templateMessage.getData().add(new WxMpTemplateData("remark", "江西蓝海物流技术部", "#AAAAAA"));
                                try {
                                    wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
                                    return null;
                                } catch (WxErrorException e) {
                                    e.printStackTrace();
                                }
                            }
                            userInfo.setLoginId(strArray[0].toUpperCase());
                            int i = userInfoMapper.updateByPrimaryKeySelective(userInfo);
                            if(i > 0) {
                                WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                                        .toUser(wxMessage.getFromUser())
                                        .templateId("DYzLbq8MOWCimVg5MG2-qtmdm5RX7MqF2gAvwE9sIuQ").build();
                                templateMessage.getData().add(new WxMpTemplateData("first", "恭喜您，工号和微信账号绑定成功！", "#284177"));
                                templateMessage.getData().add(new WxMpTemplateData("keyword1", userInfo.getNickname(), "#0044BB"));
                                templateMessage.getData().add(new WxMpTemplateData("keyword2", userVo.getName(), "#0044BB"));
                                templateMessage.getData().add(new WxMpTemplateData("keyword3", "江西蓝海物流" + userVo.getOrganizationName(), "#0044BB"));
                                templateMessage.getData().add(new WxMpTemplateData("remark", "江西蓝海物流技术部", "#AAAAAA"));
                                try {
                                    wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
                                    return null;
                                } catch (WxErrorException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }else{
                        content = "您好,绑定工号出错,请您重新关注,再重新绑定工号";
                    }
                }

            }

        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        if (StringUtils.startsWithAny(wxMessage.getContent(), "发送产量", "新华物流产量")) {
            ApacheHttpUtils.sendHttpPost("http://141.168.1.139:8080/jxxhwl/wx/wXFunction_sendChanLiang.action");
        }
        if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
            && weixinService.hasKefuOnline()) {
            return WxMpXmlOutMessage
                .TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser()).build();
        }




        return new TextBuilder().build(content, wxMessage, weixinService);

    }

}
