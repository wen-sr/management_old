package com.management.wechat.handler;

import com.management.wechat.builder.TextBuilder;
import com.management.wechat.dao.UserInfoMapper;
import com.management.wechat.pojo.UserInfo;
import com.management.wechat.service.IUserInfoService;
import com.management.wechat.service.WeixinService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class SubscribeHandler extends AbstractHandler {



  @Autowired
  UserInfoMapper userInfoMapper;

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
      WxSessionManager sessionManager) throws WxErrorException {

    this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

    WeixinService weixinService = (WeixinService) wxMpService;

    // 获取微信用户基本信息
    WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);

    if (userWxInfo != null) {
      // TODO 可以添加关注用户到本地

      UserInfo userInfo = userInfoMapper.selectByOpenId(userWxInfo.getOpenId());

      if(userInfo == null) {
        userInfo = new UserInfo();
        userInfo.setCity(userWxInfo.getCity());
        userInfo.setCountry(userWxInfo.getCountry());
        userInfo.setGroupid(userWxInfo.getGroupId().toString());
        userInfo.setHeadimgurl(userWxInfo.getHeadImgUrl());
        userInfo.setLanguage(userWxInfo.getLanguage());
        userInfo.setNickname(userWxInfo.getNickname());
        userInfo.setOpenid(userWxInfo.getOpenId());
        userInfo.setProvince(userWxInfo.getProvince());
        userInfo.setRemark(userWxInfo.getRemark());
        userInfo.setSex(userWxInfo.getSexId());
        userInfo.setSubscribe(1);
        userInfo.setSubscribetime(userWxInfo.getSubscribeTime());
        userInfoMapper.insertSelective(userInfo);

      }else if(userInfo.getSubscribe() == 0){
        userInfo.setSubscribe(1);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
      }

    }

    WxMpXmlOutMessage responseResult = null;
    try {
      responseResult = handleSpecial(wxMessage);
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }

    if (responseResult != null) {
      return responseResult;
    }

    try {
      return new TextBuilder().build("感谢关注", wxMessage, weixinService);
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }

    return null;
  }

  /**
   * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
   */
  protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception {
    //TODO
    return null;
  }

}
