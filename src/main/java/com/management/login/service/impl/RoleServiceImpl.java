package com.management.login.service.impl;

import com.management.common.ServerResponse;
import com.management.login.dao.AccordionMapper;
import com.management.login.dao.RoleAccordionMapper;
import com.management.login.dao.RoleMapper;
import com.management.login.pojo.Authority;
import com.management.login.pojo.AuthorityMid;
import com.management.login.pojo.Role;
import com.management.login.pojo.RoleAccordionKey;
import com.management.login.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleAccordionMapper roleAccordionMapper;

    @Autowired
    AccordionMapper accordionMapper;

    @Override
    public ServerResponse findAll(Role role) {
        List<Role> roleList = roleMapper.findAll(role);
        return ServerResponse.createBySuccess(roleList);
    }

    @Override
    public List<Authority> getAuth(Integer id) {
        List<AuthorityMid> list = roleMapper.selectById(id);
        List<Authority> authorityList = new ArrayList<Authority>();
        for (AuthorityMid a : list) {
            if(a.getFoo_id() == 0){
                Authority au = new Authority();
                au.setId(String.valueOf(a.getAccordion_id()));
                au.setText(a.getAccordion_name());
                au.setState("open");
                List<Authority> children = new ArrayList<Authority>();
                for(AuthorityMid aa : list){
                    if(aa.getFoo_id() == a.getAccordion_id()){
                        Authority auu = new Authority();
                        auu.setId(String.valueOf(aa.getAccordion_id()));
                        auu.setText(aa.getAccordion_name());
                        if(aa.getRole_id() != 0 ){
                            auu.setChecked(true);
                        }else{
                            auu.setChecked(false);
                        }
                        children.add(auu);
                    }else{
                        continue;
                    }
                }
                au.setChildren(children);
                authorityList.add(au);
            }else {
                continue;
            }
        }

        return authorityList;
    }

    @Override
    public ServerResponse addRole(Role role) {
        Role r = roleMapper.selectByName(role.getName());
        if(r != null ){
            return ServerResponse.createByErrorMessage("您要添加的角色名称已存在");
        }
        int maxOrderBy = roleMapper.selectMaxOrderBy();
        role.setOrderBy(maxOrderBy + 1);
        int i = roleMapper.insertSelective(role);
        if(i > 0){
            return ServerResponse.createBySuccessMsg("添加角色成功");
        }
        return ServerResponse.createByErrorMessage("添加角色失败");
    }

    @Override
    public ServerResponse editRole(Role role) {
        int i = roleMapper.updateByPrimaryKeySelective(role);
        if(i > 0){
            return ServerResponse.createBySuccessMsg("修改角色成功");
        }
        return ServerResponse.createByErrorMessage("修改角色失败");
    }

    @Override
    public ServerResponse comfirmAuth(Integer roleId, String[] ids) {
        int i = 0;
        //delete by roleId
        roleAccordionMapper.deleteByRoleId(roleId);
        //find father node
        Set<Integer> set = this.getFooIds(ids);
        //save
        if(set.size() > 0 && set != null ){

            for(String s : ids ){
                set.add(Integer.parseInt(s));
            }

            for (Integer s : set) {
                i += roleAccordionMapper.insert(new RoleAccordionKey(roleId, s));
            }
        }else{
            for(String s : ids) {
                if(s != "") {
                    i += roleAccordionMapper.insert(new RoleAccordionKey(roleId, Integer.parseInt(s)));
                }
                return ServerResponse.createBySuccessMsg("权限设置成功");
            }
        }

        if(i > 0){
            return ServerResponse.createBySuccessMsg("权限设置成功");
        }
        return ServerResponse.createByErrorMessage("权限设置失败");
    }

    @Override
    public ServerResponse deleteRole(Role role) {
        //delete role_accordion
        int i = roleAccordionMapper.deleteByRoleId(role.getId());
        i += roleMapper.deleteByPrimaryKey(role.getId());
        //delete role
        if(i >= 1){
            return ServerResponse.createBySuccessMsg("删除角色成功");
        }
        return ServerResponse.createByErrorMessage("删除角色失败");
    }

    private Set<Integer> getFooIds(String[] ids) {
        return accordionMapper.selectFooIds(ids);
    }

}
