package com.management.login.dao;

import com.management.login.pojo.AuthorityMid;
import com.management.login.pojo.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> findAll(Role role);

    List<AuthorityMid> selectById(Integer id);

    Role selectByName(String name);

    int selectMaxOrderBy();

}