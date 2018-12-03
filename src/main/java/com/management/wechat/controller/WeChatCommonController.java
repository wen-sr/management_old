package com.management.wechat.controller;

import com.management.common.Constant;
import com.management.common.ServerResponse;
import com.management.login.pojo.Login;
import com.management.wechat.service.IWeChatCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wechat/common")
public class WeChatCommonController {

    @Autowired
    IWeChatCommonService weChatCommonService;

    @RequestMapping("/register")
    @ResponseBody
    public ServerResponse register(Login login, HttpSession session){
        ServerResponse response = weChatCommonService.register(login);
        session.setAttribute(Constant.CURRENT_USER, response.getData());
        return response;
    }

    @RequestMapping("/login")
    @ResponseBody
    public ServerResponse login(Login login, HttpSession session, HttpServletResponse res){
        ServerResponse response = weChatCommonService.login(login);

        if(response.isSuccess()){
            login = (Login) response.getData();
            session.setAttribute(Constant.CURRENT_USER, response.getData());
            //cookie
            //Cookie cookie = new Cookie("user_name", URLEncoder.encode(user.getName() + "", "utf-8"));
            Cookie cookie = new Cookie(Constant.USERID, login.getId());
            cookie.setMaxAge(3600*3);
            cookie.setPath("/");
            //把cookie给浏览器
            res.addCookie(cookie);
        }

        return response;
    }

    @RequestMapping("/customerQuery")
    public String customerQuery(HttpSession session, HttpServletRequest request){
        Login user = (Login)session.getAttribute(Constant.CURRENT_USER);
        if(user == null ){
            Cookie[] cookies = request.getCookies();
            if(null!=cookies) {
                for (Cookie cookie : cookies) {
                    if(Constant.USERID.equals(cookie.getName()) && cookie.getValue() != ""){
                        user = weChatCommonService.getUserInfoByIdLike(cookie.getValue());
                        user.setPwd("");
                        session.setAttribute(Constant.CURRENT_USER, user);
                        break;
                    }
                }
                if(user == null ){
                    return "/page/wechat/login.html";
                }
            }else{
                return "/page/wechat/customerQuery.html";
            }
        }
        return "/page/wechat/customerQuery.html";
    }
}
