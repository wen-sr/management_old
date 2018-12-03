package com.management.login.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.management.common.Constant;
import com.management.common.ServerResponse;
import com.management.login.dao.AccordionMapper;
import com.management.login.dao.LoginMapper;
import com.management.login.dao.RoleLoginMapper;
import com.management.login.dao.RoleMapper;
import com.management.login.pojo.*;
import com.management.login.service.UserService;
import com.management.login.vo.Menu;
import com.management.login.vo.Nav;
import com.management.login.vo.UserVo;
import com.management.oa.dao.OrganizationMapper;
import com.management.oa.pojo.Organization;
import com.management.oa.vo.OrganizationVo;
import com.management.util.MD5Util;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-03  12:58
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    RoleLoginMapper roleLoginMapper;

    @Autowired
    AccordionMapper accordionMapper;

    @Autowired
    OrganizationMapper organizationMapper;

    @Autowired
    RoleMapper roleMapper;


    @Override
    public ServerResponse<List> login(Login user) {
        Login login = loginMapper.selectByIdAndPassword(user.getId(),MD5Util.MD5EncodeUtf8(user.getPwd()));
        if(login != null){
            login.setPwd(null);
            List<Object> data = new ArrayList<>();
            data.add(login);
            return ServerResponse.createBySuccess("登录成功", data);
        }
        return ServerResponse.createByErrorMessage("用户名或密码错误");
    }

    @Override
    public List<Integer> getRole(String id) {
        return roleLoginMapper.selectByLoginId(id);
    }

    @Override
    public List<Menu> getAccordionByRoleId(int roleId, String loginSystemFlag){
        List<Accordion> accordionList = accordionMapper.selectAccordionByRoleID(roleId);
        List<Menu> menus = getMenu(accordionList, loginSystemFlag);
        return  menus;
    }

    /**
     * 根据工号得到对应权限的菜单列表
     * @param id
     * @param loginSystemFlag
     * @return
     */
    @Override
    public List<Nav> getAccordionByLoginId(String id, String loginSystemFlag) {
        List<Integer> roleIds = getRole(id);
        //如果是管理员将list置为null
        for(Integer i : roleIds ){
            if(i == 1 ){
                roleIds = null;
            }
        }
        List<Accordion> parentAccordions = getFooAccordion(roleIds);
        List<Menu> menus = getMenu(parentAccordions, loginSystemFlag);

        //删除menus中无子元素的元素
        Iterator<Menu> iterator = menus.iterator();
        while(iterator.hasNext()){
            Menu menu = iterator.next();
            if(menu.getMenuList().size() == 0)
                iterator.remove();
        }

        List<Nav> navs = parseToNavList(menus);


        return navs;
    }

    @Override
    public Login getUserInfoById(String id) {
        return loginMapper.selectByPrimaryKey(id);
    }

    @Override
    public ServerResponse findAll(Login user, Integer pageSize, Integer pageNum) {
        Set<Integer> ids = null;
        if(user.getOrganizationId() != null){
            ids= this.getOrganizationId(user.getOrganizationId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Login> userList = null;

        if(user != null ){
            if(user.getId() == ""){
                user.setId(null);
            }
            if(user.getName() == ""){
                user.setName(null);
            }
        }
        if(user.getId() != null || user.getName() != null || user.getOrganizationId() == null){
            if(user.getName() != null) {
                user.setName("%" + user.getName() +"%");
            }
            userList = loginMapper.findAll(user);
        }else {
            userList = loginMapper.findByOrganizationIds(ids);
        }

        List<UserVo> userVoList = this.parseToUserVoList(userList);

        PageInfo pageInfo = new PageInfo(userList);

        pageInfo.setList(userVoList);

        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse addUser(Login user) {
//        D8F80B67499E434EA61ADAF6E6219BF2 D8F80B67499E434EA61ADAF6E6219BF2
        user.setBk2(MD5Util.MD5EncodeUtf8(Constant.DEFAULTPASSWORD));
        user.setPwd(Constant.DEFAULTPASSWORD);
        user.setStatus(String.valueOf(Constant.UserStatusEnum.ACTIVE.getCode()));
        int i = loginMapper.insertSelective(user);
        if(i > 0){
            return ServerResponse.createBySuccessMsg("添加用户成功");
        }
        return ServerResponse.createByErrorMessage("添加用户失败");
    }

    @Override
    public List<Authority> getAllRole(String s) {
        List<Integer> ids = getRole(s);
        List<Role> roleList = roleMapper.findAll(null);
        List<Authority> authorityList = new ArrayList<>();
        Authority a = null;
        for(Role r : roleList) {
            a = new Authority();
            a.setId(r.getId().toString());
            a.setText(r.getName());
            for(Integer i : ids ){
                if(StringUtils.equals(String.valueOf(i), a.getId())){
                    a.setChecked(true);
                    break;
                }
            }
            authorityList.add(a);
        }

        return authorityList;
    }

    @Override
    public ServerResponse confirmRole(String id, String[] ids) {
        int i = 0;
        i = roleLoginMapper.deleteByLoginId(id);
        if(ids != null &&ids.length > 0){
            for(String s : ids ){
                i += roleLoginMapper.insertSelective(new RoleLoginKey(Integer.parseInt(s), id));
            }
        }
        if(i >= 0){
            return ServerResponse.createBySuccessMsg("角色设置成功");
        }
        return ServerResponse.createByErrorMessage("角色设置失败");
    }

    @Override
    public ServerResponse editUser(Login user) {
        int i = loginMapper.updateByPrimaryKeySelective(user);
        if(i >= 0){
            return ServerResponse.createBySuccessMsg("用户修改成功");
        }
        return ServerResponse.createByErrorMessage("用户修改失败");
    }

    @Override
    public ServerResponse deleteUser(Login user) {
        int i = loginMapper.deleteByPrimaryKey(user.getId());
        if(i >= 0){
            return ServerResponse.createBySuccessMsg("用户删除成功");
        }
        return ServerResponse.createByErrorMessage("用户删除失败");
    }

    @Override
    public ServerResponse modifyPwd(String id, String oldPwd, String newPwd) {
        Login user = loginMapper.selectByPrimaryKey(id);
        if(!StringUtils.equals(MD5Util.MD5EncodeUtf8(oldPwd), user.getBk2())){
            return ServerResponse.createByErrorMessage("旧密码错误，修改密码失败");
        }

        user.setPwd(newPwd);
        user.setBk2(MD5Util.MD5EncodeUtf8(newPwd));
        int i = 0;
        i = loginMapper.updateByPrimaryKeySelective(user);
        if(i >= 0){
            return ServerResponse.createBySuccessMsg("密码修改成功");
        }
        return ServerResponse.createByErrorMessage("密码修改失败");
    }

    private Set<Integer> getOrganizationId(Integer organizationId) {
        Set<Integer> organizationIds = new HashSet<>();
        organizationIds.add(organizationId);
        this.getChildrenChildrenIds(organizationIds, organizationId);
        return organizationIds;
    }

    private Set<Integer> getChildrenChildrenIds(Set<Integer> organizationIds, Integer organizationId) {
        List<OrganizationVo> organization = organizationMapper.selectByParentId(organizationId);
        if(organization != null && organization.size() > 0){
            for(OrganizationVo o : organization) {
                organizationIds.add(o.getId());
                getChildrenChildrenIds(organizationIds,o.getId());
            }
        }
        return organizationIds;
    }

    private List<UserVo> parseToUserVoList(List<Login> userList) {
        List<UserVo> userVoList = new ArrayList<>();
        UserVo userVo = null;
        for (Login l : userList) {
            userVo = new UserVo();

            userVo.setBk1(Constant.UserSexEnum.codeOf(Integer.parseInt(l.getBk1())).getMsg());

            userVo.setBk2(l.getBk2());
            userVo.setBk3(l.getBk3());
            userVo.setBk4(l.getBk4());
            userVo.setBk5(l.getBk5());
            userVo.setId(l.getId());
            userVo.setIdOld(l.getIdOld());
            userVo.setName(l.getName());
            userVo.setOrganizationId(l.getOrganizationId());

            Organization organization = organizationMapper.selectByPrimaryKey(l.getOrganizationId());
            if(organization != null) {
                userVo.setOrganizationName(organization.getName());
            }
            userVo.setPost(l.getPost());
            userVo.setPostid(l.getPostid());
            userVo.setPwd("");
            userVo.setPy(l.getPy());
            userVo.setPyname(l.getPyname());

            userVo.setStatus(Constant.UserStatusEnum.codeOf(Integer.parseInt(l.getStatus())).getMsg());
            userVoList.add(userVo);
        }
        return userVoList;
    }

    private List<Nav> parseToNavList(List<Menu> menuList) {
        List<Nav> navs = new ArrayList<Nav>();
        for (Menu m1 : menuList){
            Nav nav = new Nav();
            nav.setId(m1.getMenuId());
            nav.setTitle(m1.getMenuName());
            nav.setIcon(m1.getMenuIcon());
            nav.setSpread(false);

            List<Nav> children = new ArrayList<>();
            for(Menu m2 : m1.getMenuList()){
                Nav child = new Nav();
                child.setId(m2.getMenuId());
                child.setTitle(m2.getMenuName());
                child.setIcon(m2.getMenuIcon());
                child.setHref(m2.getMenuUrl());
                children.add(child);
            }

            nav.setChildren(children);
            navs.add(nav);
        }

        return navs;

    }

    private List<Accordion> getFooAccordion(List<Integer> roleIds){
        List<Accordion> parentAccordions = null;
        if(roleIds == null) {
            parentAccordions = accordionMapper.selectAllAccordion();
        }else{
            parentAccordions = accordionMapper.selectAccordionByRoleIds(roleIds);
        }
        return parentAccordions;
    }

    private List<Menu> getMenu(List<Accordion> accordionList, String loginSystemFlag) {
        List<Menu> menus = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(accordionList)){
            for(Accordion a : accordionList){
                Menu menu = new Menu();
                menu.setMenuId(String.valueOf(a.getAccordionId()));
                menu.setMenuName(a.getAccordionName());
                menu.setMenuIcon(a.getAccordionIcon());
                List<Menu> menuChild = Lists.newArrayList();
                List<Accordion> l = getMenuChild(a.getAccordionId(),loginSystemFlag);
                for(Accordion aa : l ){
                    Menu m = new Menu();
                    m.setMenuIcon(aa.getAccordionIcon());
                    m.setMenuName(aa.getAccordionName());
                    m.setMenuId(String.valueOf(aa.getAccordionId()));
                    m.setMenuUrl(aa.getAccordionUrl());
                    menuChild.add(m);
                }
                menu.setMenuList(menuChild);
                menus.add(menu);
            }
        }
        return menus;
    }

    private List<Accordion> getMenuChild(int id , String loginSystemFlag){
        List<Accordion> menuChild = null;
        switch (loginSystemFlag){
            case Constant.LoginSystemFlag.DEFAULT:
                menuChild = accordionMapper.selectByFooId(id,1, 999999999);
                break;
            case Constant.LoginSystemFlag.XIN_HUA:
                menuChild = accordionMapper.selectByFooId(id,50, 98);
                break;
            case Constant.LoginSystemFlag.WCS:
                menuChild = accordionMapper.selectByFooId(id,300, 399);
                break;
            case Constant.LoginSystemFlag.PALLECT:
                menuChild = accordionMapper.selectByFooId(id,200, 299);
                break;
            case Constant.LoginSystemFlag.TMS:
                menuChild = accordionMapper.selectByFooId(id,400, 499);
                break;
            case Constant.LoginSystemFlag.REPORT:
                menuChild = accordionMapper.selectByFooId(id,500, 599);
                break;
            case Constant.LoginSystemFlag.TECH:
                menuChild = accordionMapper.selectByFooId(id,99, 199);
                break;
            case Constant.LoginSystemFlag.JIAO_CAI:
                menuChild = accordionMapper.selectByFooId(id,1, 49);
                break;
            default:
                menuChild = accordionMapper.selectByFooId(id,1, 999999999);
                break;
        }
        return menuChild;
    }


}
