package com.management.login.controller;

import com.management.common.BaseCotroller;
import com.management.common.ServerResponse;
import com.management.login.pojo.Authority;
import com.management.login.pojo.Role;
import com.management.login.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseCotroller{

    @Autowired
    IRoleService roleService;

    @RequestMapping("/findAll")
    public List<Role> findAll(Role role, HttpSession session){
        ServerResponse response = roleService.findAll(role);

        return (List<Role>) response.getData();
    }

    @RequestMapping("/getAuth")
    public List<Authority> getAuth(Role role){
         return roleService.getAuth(role.getId());
    }

    @RequestMapping("/addRole")
    public ServerResponse addRole(Role role, HttpSession session, HttpServletRequest request) {
        if(super.isLogin(session, request).getStatus() == 0){
            return roleService.addRole(role);
        }else{
            return ServerResponse.createByErrorMessage("未登录");
        }
    }


    @RequestMapping("/editRole")
    public ServerResponse editRole(Role role, HttpSession session, HttpServletRequest request) {
        if(super.isLogin(session, request).getStatus() == 0){
            return roleService.editRole(role);
        }else{
            return ServerResponse.createByErrorMessage("未登录");
        }
    }

    @RequestMapping("/deleteRole")
    public ServerResponse deleteRole(Role role, HttpSession session, HttpServletRequest request) {
        if(super.isLogin(session, request).getStatus() == 0){
            return roleService.deleteRole(role);
        }else{
            return ServerResponse.createByErrorMessage("未登录");
        }
    }

    @RequestMapping("/confirmAuth")
    public ServerResponse confirmAuth(@RequestParam(value = "ids") String accordionIds,
                                      @RequestParam(value = "id") Integer roleId,
                                      HttpSession session, HttpServletRequest request) {
        if(super.isLogin(session, request).getStatus() == 0){
            String[] ids = accordionIds.split(",");
            return roleService.comfirmAuth(roleId,ids);
        }else{
            return ServerResponse.createByErrorMessage("未登录");
        }
    }





}
