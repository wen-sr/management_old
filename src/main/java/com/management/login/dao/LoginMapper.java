package com.management.login.dao;

import com.management.login.pojo.Login;
import com.management.login.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface LoginMapper {
    int deleteByPrimaryKey(String id);

    int insert(Login record);

    int insertSelective(Login record);

    Login selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Login record);

    int updateByPrimaryKey(Login record);

//    Login selectByIdAndPassword(@Param(value = "id") String id, @Param(value = "pwd") String pwd);

    List<Login> findAll(Login user);

    List<Login> findByOrganizationIds(Set<Integer> ids);

    UserVo selectUserAndOrgnazizationById(String id);

    Login selectUserByName(@Param(value = "name") String name);

    Login selectUserByIdAndPwd(@Param(value = "id") String id, @Param(value = "pwd") String pwd);

    List<Login> selectUserByIdAndPwdLike(@Param(value = "id") String id, @Param(value = "pwd") String pwd);
}