package com.management.oa.service.impl;

import com.management.common.ServerResponse;
import com.management.oa.dao.OrganizationMapper;
import com.management.oa.pojo.Organization;
import com.management.oa.service.IOrganizationService;
import com.management.oa.vo.OrganizationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrganizationServiceImpl implements IOrganizationService{

    @Autowired
    OrganizationMapper organizationMapper;

    @Override
    public List<OrganizationVo> findTree(Integer id, Integer flag) {

        Organization organization = organizationMapper.selectByPrimaryKey(id);
        List<OrganizationVo> organizationVoList = new ArrayList<>();
        OrganizationVo organizationVo = null;
        if(organization != null){
            organizationVo = this.parseToOrganizationVo(organization);
        }else{
            organizationVo = new OrganizationVo();
            organizationVo.setId(0);
            organizationVo.setName("所有组织");
            organizationVo.setText("所有组织");
        }
        organizationVo.setState("closed");
        List<OrganizationVo> organizationVoChildrenList = this.findChild(organizationVo);
        organizationVo.setChildren(organizationVoChildrenList);
        organizationVoList.add(organizationVo);

        if(flag == 0){
            this.setState(organizationVoList);
        }
        organizationVoList.get(0).setState("open");
        return organizationVoList;
    }

    @Override
    public ServerResponse add(Organization organization) {
        Organization o = organizationMapper.selectByName(organization.getName());
        if(o != null ){
            return ServerResponse.createByErrorMessage("您要添加的部门已存在");
        }
        organization.setFlag("1");
        organization.setGroupId(organization.getFooId());
        String maxOrderBy = organizationMapper.selectMaxOrderBy(organization.getFooId());
        organization.setOrderBy(Integer.parseInt(maxOrderBy==null ?"0":maxOrderBy)+1);
        int i = organizationMapper.insertSelective(organization);
        if(i > 0){
            return ServerResponse.createBySuccessMsg("添加部门成功");
        }
        return ServerResponse.createByErrorMessage("添加部门失败");
    }

    @Override
    public ServerResponse edit(Organization organization) {
        Organization o = organizationMapper.selectByName(organization.getName());
        if(o != null ){
            return ServerResponse.createByErrorMessage("您要添加的部门已存在");
        }
        int i = organizationMapper.updateByPrimaryKeySelective(organization);
        if(i > 0){
            return ServerResponse.createBySuccessMsg("修改部门成功");
        }
        return ServerResponse.createByErrorMessage("修改部门失败");
    }

    @Override
    public ServerResponse delete(Organization organization) {
        int i = organizationMapper.deleteByPrimaryKey(organization.getId());
        if(i > 0){
            return ServerResponse.createBySuccessMsg("删除部门成功");
        }
        return ServerResponse.createByErrorMessage("删除部门失败");
    }


    public List<OrganizationVo> findChild(OrganizationVo o){

        List<OrganizationVo> organizationVoChildrenList = organizationMapper.selectByParentId(o.getId());
        o.setChildren(organizationVoChildrenList);
        List<OrganizationVo> organizationVoChildrenList2 = null;
        for(OrganizationVo or : organizationVoChildrenList){
            organizationVoChildrenList2 = new ArrayList<>();
            organizationVoChildrenList2 = findChild(or);
            or.setChildren(organizationVoChildrenList2);
        }

        return organizationVoChildrenList;
    }



    private OrganizationVo parseToOrganizationVo(Organization o) {
        OrganizationVo or = new OrganizationVo();
        or.setId(o.getId());
        or.setFlag(o.getFlag());
        or.setFooId(o.getFooId());
        or.setGroupId(o.getGroupId());
        or.setIcon(o.getIcon());
        or.setName(o.getName());
        or.setOrderBy(o.getOrderBy());
        or.setText(o.getName());
        return or;
    }

     private List<OrganizationVo> setState(List<OrganizationVo> organizationVoList){

        for(OrganizationVo o : organizationVoList){
            if(o.getChildren() != null && o.getChildren().size() > 0 ){
                o.setState("closed");
                this.setState(o.getChildren());
            }
        }

        return organizationVoList;
    }

}
