package com.management.login.dao;

import com.management.login.pojo.Accordion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface AccordionMapper {
    int deleteByPrimaryKey(Integer accordionId);

    int insert(Accordion record);

    int insertSelective(Accordion record);

    Accordion selectByPrimaryKey(Integer accordionId);

    int updateByPrimaryKeySelective(Accordion record);

    int updateByPrimaryKey(Accordion record);

    List<Accordion> selectAccordionByRoleID(@Param(value = "roleId") int roleId);

    List<Accordion> selectByFooId(@Param(value = "id") int id,@Param(value = "begin")  int begin,@Param(value = "end")  int end);

    List<Accordion> selectAccordionByRoleIds(@Param(value = "roleIds") List<Integer> roleIds);

    Set<Integer> selectFooIds(String[] ids);

    List<Accordion> selectAllAccordion();
}