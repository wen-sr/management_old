package com.management.wechat.handler;

import com.management.wechat.dao.UserInfoMapper;
import com.management.wechat.pojo.UserInfo;
import com.management.wechat.service.IUserInfoService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {

    @Autowired
    UserInfoMapper userInfoMapper;



    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService wxMpService,
            WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        this.logger.info("取消关注用户 OPENID: " + openId);
        // TODO 可以更新本地数据库为取消关注状态

        UserInfo userInfo = userInfoMapper.selectByOpenId(openId);
        if(userInfo != null) {
            userInfo.setSubscribe(0);
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
        }

        return null;
    }

}
