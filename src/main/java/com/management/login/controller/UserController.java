package com.management.login.controller;

import com.google.common.collect.Maps;
import com.management.common.BaseCotroller;
import com.management.common.Constant;
import com.management.common.EasyuiTableResponse;
import com.management.common.ServerResponse;
import com.management.login.dao.LoginMapper;
import com.management.login.pojo.Authority;
import com.management.login.pojo.Login;
import com.management.login.service.UserService;
import com.management.login.vo.Nav;
import com.management.login.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-03  12:54
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseCotroller {

    @Autowired
    UserService userService;

    @Autowired
    LoginMapper loginMapper;

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse login(Login user, HttpSession session, HttpServletResponse  response) throws UnsupportedEncodingException {
        ServerResponse<List> responseData = userService.login(user);
        if(responseData.isSuccess()){
            user = (Login) responseData.getData().get(0);
            session.setAttribute(Constant.CURRENT_USER, responseData.getData().get(0));
            //cookie
            //Cookie cookie = new Cookie("user_name", URLEncoder.encode(user.getName() + "", "utf-8"));
            Cookie cookie = new Cookie(Constant.USERID, user.getId());
            cookie.setMaxAge(1*24*3600*30);
            cookie.setPath("/");
            //把cookie给浏览器
            response.addCookie(cookie);

            List<Map<String,String>> targetCookies = addCookies(user);

            responseData.getData().add(targetCookies);
        }
        return responseData;
    }
    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public ServerResponse getUserInfo(HttpSession session, HttpServletRequest request, HttpServletResponse  response, @RequestParam(value = "loginSystemFlag", defaultValue = "0") String loginSystemFlag) {
        Login user = (Login)session.getAttribute(Constant.CURRENT_USER);
        List<Map<String,String>> targetCookies = null;
        if(user == null ){
            Cookie[] cookies = request.getCookies();
            if(null!=cookies) {
                for (Cookie cookie : cookies) {
                    if(Constant.USERID.equals(cookie.getName()) && cookie.getValue() != ""){
                        user = userService.getUserInfoById(cookie.getValue());
                        user.setPwd("");
                        session.setAttribute(Constant.CURRENT_USER, user);
                        break;
                    }
                }
                if(user == null ){
                    return ServerResponse.createByErrorMessage("未登录");
                }
            }else{
                return ServerResponse.createByErrorMessage("未登录");
            }
        }
        UserVo userVo = loginMapper.selectUserAndOrgnazizationById(user.getId());
        List<Nav> navs = userService.getAccordionByLoginId(user.getId(), loginSystemFlag);
        Map<String, Object> map = Maps.newHashMap();
        map.put("user" , user);
        map.put("menuList" , navs);
        map.put("organizationName", userVo.getOrganizationName());
        targetCookies = addCookies(user);
        map.put("targetCookies", targetCookies);
        map.put("loginSystemFlag", loginSystemFlag);

        return ServerResponse.createBySuccess(map);
    }
//    @RequestMapping(value = "/logout",method = RequestMethod.POST)
//    @ResponseBody
//    public ServerResponse<String> logout(HttpSession session){
//        session.removeAttribute(Constant.CURRENT_USER);
//        return ServerResponse.createBySuccess();
//    }

    @RequestMapping("/findAll")
    @ResponseBody
    public EasyuiTableResponse findAll(@RequestParam(value = "rows", defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                       Login user, HttpSession session){
        ServerResponse response = userService.findAll(user, pageSize, pageNum);

        return response.parseToEasyuiTableResponse(response);
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public ServerResponse addUser(Login user, HttpSession session){
        return userService.addUser(user);
    }

    @RequestMapping(value = "/getRole")
    @ResponseBody
    public List<Authority> getRole(@RequestParam(value = "id") String id, HttpSession session, HttpServletRequest request) {
        if(super.isLogin(session, request).getStatus() == 0){
            return userService.getAllRole(id);
        }else{
            return null;
        }
    }

    @RequestMapping("/confirmRole")
    public ServerResponse confirmRole(@RequestParam(value = "ids") String roleIds,
                                      @RequestParam(value = "id") String id,
                                      HttpSession session, HttpServletRequest request) {
        if(super.isLogin(session, request).getStatus() == 0){
            String[] ids = roleIds.split(",");
            return userService.confirmRole(id,ids);
        }else{
            return ServerResponse.createByErrorMessage("未登录");
        }
    }

    @RequestMapping("/editUser")
    public ServerResponse editUser(Login user, HttpSession session, HttpServletRequest request) {
        if(super.isLogin(session, request).getStatus() == 0){
            return userService.editUser(user);
        }else{
            return ServerResponse.createByErrorMessage("未登录");
        }
    }

    @RequestMapping("/deleteUser")
    public ServerResponse deleteUser(Login user, HttpSession session, HttpServletRequest request) {
        if(super.isLogin(session, request).getStatus() == 0){
            return userService.deleteUser(user);
        }else{
            return ServerResponse.createByErrorMessage("未登录");
        }
    }

    @RequestMapping("/modifyPwd")
    public ServerResponse modifyPwd(@RequestParam(value = "oldPwd") String oldPwd,
                                    @RequestParam(value = "newPwd") String newPwd, HttpSession session, HttpServletRequest request) {
        if(super.isLogin(session, request).getStatus() == 0){
            Login user = (Login) session.getAttribute(Constant.CURRENT_USER);
            return userService.modifyPwd(user.getId(), oldPwd, newPwd);
        }else{
            return ServerResponse.createByErrorMessage("未登录");
        }
    }
    @RequestMapping("/logout")
    public ServerResponse logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        if(super.isLogin(session, request).getStatus() == 0){
            Login user = (Login) session.getAttribute(Constant.CURRENT_USER);
            if(user != null ){
                session.setAttribute(Constant.CURRENT_USER, null);
            }
            Cookie cookies[] = request.getCookies();
            if (cookies != null){
                for (int i = 0; i < cookies.length; i++){
                    if (cookies[i].getName().equals(Constant.USERID)){
//                        cookies[i].setValue("");
                        Cookie cookie = new Cookie(Constant.USERID,"");//这边得用"",不能用null
                        cookie.setPath("/");//设置成跟写入cookies一样的
//                        // cookie.setDomain(".wangwz.com");//设置成跟写入cookies一样的
                        cookies[i].setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        }else{
            return ServerResponse.createByErrorMessage("未登录");
        }
        return ServerResponse.createBySuccess();
    }


    private List<Map<String,String>> addCookies(Login user){
        List<Map<String,String>> targetCookies = new ArrayList<Map<String,String>>();
        // 向其他服务器发送增加cookie
        Map<String,String> targetCookiea = new HashMap<String,String>();
        String url_pallet = "http://141.168.1.108:8081/ncsmwcs/addCookie";
        targetCookiea.put("targetUrl", url_pallet);
        targetCookiea.put("cookieName", Constant.USERID);
        targetCookiea.put("cookieValue", user.getId());
        targetCookies.add(targetCookiea);

//        Map<String,String> targetCookieb = new HashMap<String,String>();
//        String url_jiaocai = "http://141.168.1.188:8080/jxxhwl/addCookie";
//        targetCookieb.put("targetUrl", url_jiaocai);
//        targetCookieb.put("cookieName", Constant.USERID);
//        targetCookieb.put("cookieValue", user.getId());
//        targetCookies.add(targetCookieb);

//        Map<String,String> targetCookiec = new HashMap<String,String>();
//        String url_jiaocai_139 = "http://141.168.1.188:8080/jxxhwl/addCookie";
//        targetCookiec.put("targetUrl", url_jiaocai_139);
//        targetCookiec.put("cookieName", Constant.USERID);
//        targetCookiec.put("cookieValue", user.getId());
//        targetCookies.add(targetCookiec);

        return targetCookies;
    }

}
