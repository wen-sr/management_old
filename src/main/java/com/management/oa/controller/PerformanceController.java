package com.management.oa.controller;


import com.management.common.Constant;
import com.management.common.ServerResponse;
import com.management.login.pojo.Login;
import com.management.oa.service.IPerformanceService;
import com.management.util.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;


@Controller
@RequestMapping("/performance")
public class PerformanceController {

    @Autowired
    IPerformanceService performanceService;

    private final static String excel2003L = ".xls";    //2003- 版本的excel
    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel

    /**
     * 描述：通过传统方式form表单提交方式导入excel文件
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/uploadPerformanceExcel", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ServerResponse uploadExcel(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        System.out.println("通过传统方式form表单提交方式导入excel文件！");

        InputStream in = null;
        List<List<Object>> listob = null;
        MultipartFile file = multipartRequest.getFile("upfile");
        if (file.isEmpty()) {
            throw new Exception("文件不存在！");
        }
        in = file.getInputStream();
        //
        //HSSFWorkbook workbook = new HSSFWorkbook();

        ImportExcelUtil iu = new ImportExcelUtil();

        listob = iu.getBankListByExcel(in, file.getOriginalFilename());


        in.close();

        ServerResponse response = performanceService.saveRecords(listob);



        return response;
    }


    /**
     * 描述：通过 jquery.form.js 插件提供的ajax方式上传文件
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/ajaxUploadPerformanceExcel", method = {RequestMethod.GET, RequestMethod.POST})
    public ServerResponse ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        System.out.println("通过 jquery.form.js 提供的ajax方式上传文件！");

        InputStream in = null;
        List<List<Object>> listob = null;
        MultipartFile file = multipartRequest.getFile("upfile");
        if (file.isEmpty()) {
            throw new Exception("文件不存在！");
        }

        in = file.getInputStream();
        listob = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());

        in.close();

        return performanceService.saveRecords(listob);
    }

    @ResponseBody
    @RequestMapping("/query")
    public ServerResponse query(@RequestParam(value = "begin")String begin,
                                @RequestParam(value = "end")String end,
                                @RequestParam(value = "pwd")String pwd,
                                HttpSession session) throws IOException {
        Login user = (Login) session.getAttribute(Constant.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorMessage("您未登录或未绑定工号，无法查询");
        }

        ServerResponse response = performanceService.query(begin, end, pwd, user.getId());
        session.setAttribute(Constant.PERFORMANCE, response.getData());
        return response;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public ServerResponse getData(HttpSession session){
        if(session.getAttribute(Constant.PERFORMANCE) != null) {
            return ServerResponse.createBySuccess(session.getAttribute(Constant.PERFORMANCE));
        }
        return ServerResponse.createByErrorMessage("没有查询到您的信息");
    }

    @ResponseBody
    @RequestMapping("/modifyPwd")
    public ServerResponse modifyPwd(@RequestParam(value = "oldPwd")String oldPwd,
                                    @RequestParam(value = "newPwd")String newPwd,
                                    @RequestParam(value = "rePwd")String rePwd,
                                    HttpSession session){
        Login user = (Login) session.getAttribute(Constant.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorMessage("您未登录或未绑定工号，无法查询");
        }
        return performanceService.modifyPwd(oldPwd, newPwd, rePwd, user.getId());
    }


}
