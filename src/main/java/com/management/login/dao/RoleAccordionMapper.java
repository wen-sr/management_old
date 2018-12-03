package com.management.login.dao;

import com.management.login.pojo.RoleAccordionKey;

public interface RoleAccordionMapper {
    int deleteByPrimaryKey(RoleAccordionKey key);

    int insert(RoleAccordionKey record);

    int insertSelective(RoleAccordionKey record);

    int deleteByRoleId(Integer id);
}