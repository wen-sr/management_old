package com.management.wechat.service.impl;

import com.management.wechat.dao.UserInfoMapper;
import com.management.wechat.pojo.UserInfo;
import com.management.wechat.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserInfoServiceImpl implements IUserInfoService {


    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfo findByOpenId(String openId) {
        return userInfoMapper.selectByOpenId(openId);
    }
}
