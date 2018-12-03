package com.management.oa.controller;

import com.management.common.ServerResponse;
import com.management.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/oa/repair")
public class RepairController {

    @Autowired
    FileUploadUtil fileUploadUtil;

    /**
     * 系统报修
     * @param session
     * @param files
     * @param request
     * @return
     */
    @RequestMapping("needRepair")
    public ServerResponse repair(HttpSession session, @RequestParam(value = "file",required = false) MultipartFile[] files, HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload");
        System.out.println(path);
        StringBuilder targetFileName = new StringBuilder("");
        for(int i=0; i< files.length;i++){
            targetFileName = targetFileName.append(fileUploadUtil.upload(files[i],path,"saveName")).append(",");

            System.out.println(targetFileName);
        }
        return null;
    }



}
