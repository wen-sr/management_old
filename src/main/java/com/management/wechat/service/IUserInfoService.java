package com.management.wechat.service;

import com.management.wechat.pojo.UserInfo;

public interface IUserInfoService {
    UserInfo findByOpenId(String openId);

}
