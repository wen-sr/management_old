package com.management.infor.controller;

import com.management.common.*;
import com.management.infor.pojo.InforError;
import com.management.infor.service.InforErrorService;
import com.management.login.pojo.Login;
import com.management.util.FileUploadUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/infor")
public class InfoErrorController extends BaseCotroller {

    @Autowired
    FileUploadUtil fileUploadUtil;

    @Autowired
    WxMpService wxMpService;

    @Autowired
    InforErrorService inforErrorService;

    /**
     * 系统报修wx
     * @param session
     * @param mediaId
     * @param request
     * @return
     */
    @RequestMapping("/addInforError")
    @ResponseBody
    public ServerResponse repair(HttpSession session,
                                 @RequestParam(value = "mediaId ",required = false) String mediaId,
                                 InforError inforError,
                                 HttpServletRequest request){

        ServerResponse response = isLogin(session, request);

        if(response.getStatus() != 0){
            return response;
        }


        Login user = (Login) response.getData();
        String path = request.getSession().getServletContext().getRealPath("upload");
        System.out.println(path);

        try {
            File file = wxMpService.getMaterialService().mediaDownload(mediaId);
            if(file != null) {
                // 读入 文件
                FileInputStream in_file = new FileInputStream(file);
                String fileName = file.getName();

                // 转 MultipartFile
                MultipartFile multiFile = new MockMultipartFile(file.getName(), in_file);

                String targetFileName = fileUploadUtil.upload(multiFile,path,"saveName");

                if(targetFileName != null){

                    inforError.setImageUrl(targetFileName);
                    inforError.setAddwho(user.getId());
                    inforError.setBk1("0");

                    return inforErrorService.addInforError(inforError);

                }else {
                    return ServerResponse.createByErrorMessage("文件上传失败，稍后重试");
                }
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 系统报修PC
     * @param session
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/addInforErrorPC")
    public String repairPC(HttpSession session,
                           @RequestParam(value = "file",required = false) MultipartFile file,
                           InforError inforError,
                           HttpServletRequest request,
                           HttpServletResponse res) throws IOException {

            ServerResponse response = isLogin(session, request);

        if(response.getStatus() != 0){
            return  "redirect:/page/error/10.html";
        }

        Login user = (Login) response.getData();
        String path = request.getSession().getServletContext().getRealPath("upload");
        System.out.println(path);

        String targetFileName = fileUploadUtil.upload(file,path,"saveName");

        if(targetFileName != null){

            inforError.setImageUrl(targetFileName);
            inforError.setAddwho(user.getId());
            //status
            inforError.setBk1("0");

            inforErrorService.addInforError(inforError);
            return "redirect:/page/infor/inforErrorManage.html";
        }else {
            //return ServerResponse.createByErrorMessage("文件上传失败，稍后重试");
            return  "redirect:/page/error/500.html";

        }
    }




    @RequestMapping("/edit")
    @ResponseBody
    public ServerResponse edit(HttpSession session,
                           InforError inforError,
                           HttpServletRequest request,
                           HttpServletResponse res) throws IOException {

        ServerResponse response = isLogin(session, request);

        if(response.getStatus() != 0){
            return  ServerResponse.createByErrorMessage("未登录");
        }

        Login user = (Login) response.getData();

        if(user != null) {
            //修改需要管理员和信息主管权限
            if(StringUtils.equals(inforError.getBk1(), Constant.InforErrorStatusEnum.RESOLVING.getCode().toString())){
                if(!isAllow(user, Constant.Role.ROLE_TECHNOLOGY)){
                    return ServerResponse.createByErrorCodeMessage(ResponseCode.NO_AUTHORITY.getCode(),"对不起，您没有权限操作");
                }
            }else if(StringUtils.equals(inforError.getBk1(), Constant.InforErrorStatusEnum.RESOLVED.getCode().toString())){

            }
            inforError.setProcessWho(user.getId());
            return inforErrorService.edit(inforError);
        }

        return ServerResponse.createByErrorMessage("获取信息失败，请重试");

    }


    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(HttpSession session,
                           InforError inforError,
                           HttpServletRequest request,
                           HttpServletResponse res) throws IOException {

        ServerResponse response = isLogin(session, request);

        if(response.getStatus() != 0){
            return  ServerResponse.createByErrorMessage("未登录");
        }

        Login user = (Login) response.getData();

        if(user != null) {
            if(!isAllow(user, Constant.Role.ROLE_ADMIN)){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.NO_AUTHORITY.getCode(),"对不起，您没有权限操作");
            }
            return inforErrorService.delete(inforError);
        }

        return ServerResponse.createByErrorMessage("获取信息失败，请重试");

    }




    @RequestMapping("/findAll")
    @ResponseBody
    public EasyuiTableResponse findAll(@RequestParam(value = "rows", defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                       InforError inforError,
                                       @RequestParam(value = "organizationId", defaultValue = "0")String organizationId,
                                       HttpSession session){
        if(inforError.getType() == ""){
            inforError.setType(null);
        }
        if(inforError.getBk1() == ""){
            inforError.setBk1(null);
        }
        ServerResponse response = inforErrorService.findAll(inforError, pageSize, pageNum, organizationId);


        return response.parseToEasyuiTableResponse(response);
    }


}
