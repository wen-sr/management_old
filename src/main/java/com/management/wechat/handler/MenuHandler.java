package com.management.wechat.handler;

import com.alibaba.fastjson.JSON;
import com.management.wechat.builder.AbstractBuilder;
import com.management.wechat.builder.ImageBuilder;
import com.management.wechat.builder.TextBuilder;
import com.management.wechat.dao.UserInfoMapper;
import com.management.wechat.dto.WxMenuKey;
import com.management.wechat.pojo.UserInfo;
import com.management.wechat.service.WeixinService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class MenuHandler extends AbstractHandler {

  @Autowired
  UserInfoMapper userInfoMapper;

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
      Map<String, Object> context, WxMpService wxMpService,
      WxSessionManager sessionManager) {
    WeixinService weixinService = (WeixinService) wxMpService;

    String key = wxMessage.getEventKey();

    String content = "";

    if(StringUtils.equals(key, "jobNumberBinding")){

      UserInfo userInfo = userInfoMapper.selectByOpenId(wxMessage.getFromUser());

      if(userInfo.getLoginId() != null && "".equals(userInfo.getLoginId())){
        content = "您好,您已经绑定工号【" + userInfo.getLoginId() + "】,无需再次绑定";
      }else{
        content = "您好，本公司员工才能绑定工号，请按照以下格式输入并发送给我即可完成绑定：工号@姓名，如LH8888888@王小二";
      }

      return WxMpXmlOutMessage.TEXT()
              .content(content)
              .fromUser(wxMessage.getToUser())
              .toUser(wxMessage.getFromUser())
              .build();

    }

    WxMenuKey menuKey = null;
    try {
      menuKey = JSON.parseObject(key, WxMenuKey.class);
    } catch (Exception e) {
      return WxMpXmlOutMessage.TEXT().content(key)
          .fromUser(wxMessage.getToUser())
          .toUser(wxMessage.getFromUser()).build();
    }

    AbstractBuilder builder = null;
    switch (menuKey.getType()) {
    case WxConsts.XmlMsgType.TEXT:
      builder = new TextBuilder();
      break;
    case WxConsts.XmlMsgType.IMAGE:
      builder = new ImageBuilder();
      break;
    case WxConsts.XmlMsgType.VOICE:
      break;
    case WxConsts.XmlMsgType.VIDEO:
      break;
    case WxConsts.XmlMsgType.NEWS:
      break;
    default:
      break;
    }

    if (builder != null) {
      try {
        return builder.build(menuKey.getContent(), wxMessage, weixinService);
      } catch (Exception e) {
        this.logger.error(e.getMessage(), e);
      }
    }

    return null;

  }

}
