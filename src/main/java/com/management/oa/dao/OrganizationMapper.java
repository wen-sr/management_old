package com.management.oa.dao;

import com.management.oa.pojo.Organization;
import com.management.oa.vo.OrganizationVo;

import java.util.List;

public interface OrganizationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Organization record);

    int insertSelective(Organization record);

    Organization selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);

    List<OrganizationVo> selectByParentId(Integer id);

    Organization selectByName(String name);

    String selectMaxOrderBy(Integer fooId);
}