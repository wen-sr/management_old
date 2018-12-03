package com.management.wechat.dao;

import com.management.wechat.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo selectByOpenId(String openId);

    List<UserInfo> selectByLoginIds(@Param(value = "roleIds") List<String> roleIds);

    UserInfo selectUserByName(String name);

    UserInfo selectByLoginId(String login_id);
}