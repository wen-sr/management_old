package com.management.common;

import com.management.login.pojo.Login;
import com.management.login.pojo.Role;
import com.management.login.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class BaseCotroller {

    @Autowired
    UserService userService;

    public ServerResponse isLogin(HttpSession session, HttpServletRequest request) {
        Login user = (Login)session.getAttribute(Constant.CURRENT_USER);
        if(user == null ){
            Cookie[] cookies = request.getCookies();
            if(null!=cookies) {
                for (Cookie cookie : cookies) {
                    if("user_id".equals(cookie.getName())){
                        user = userService.getUserInfoById(cookie.getValue());
                        user.setPwd("");
                        break;
                    }
                }
                if(user != null){
                    session.setAttribute(Constant.CURRENT_USER, user);
                    return ServerResponse.createBySuccess(user);
                }else {
                    return ServerResponse.createByErrorMessage("未登录");
                }
            }else{
                return ServerResponse.createByErrorMessage("未登录");
            }
        }
        return ServerResponse.createBySuccess(user);
    }

    public boolean isAllow(Login user, int role){
        List<Integer> roleIdList = userService.getRole(user.getId());
        for(int i : roleIdList) {
            if(i == 1) {
                return true;
            }
        }

        if(role == Constant.Role.ROLE_TECHNOLOGY){
            for(int i : roleIdList) {
                if(i == 24) {
                    return true;
                }
            }
        }
        if(role == Constant.Role.ROLE_DEVICE){
            for(int i : roleIdList) {
                if(i == 21) {
                    return true;
                }
            }
        }

        return false;
    }

}
