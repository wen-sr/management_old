package com.management.util;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by geely
 */
@Component
public class FileUploadUtil {

    private Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);


    public String upload(MultipartFile file, String path, String flag){
        //String fileName = file.getOriginalFilename();
        String fileName = file.getOriginalFilename();
        if("".equals(fileName)){
            fileName = file.getName();
        }
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        //TODO
        if("saveName".equals(flag)){
            System.out.println("******************************************************************************");
        }
        File targetFile = new File(path,uploadFileName);
        System.out.println();

        try {
            file.transferTo(targetFile);
            //文件已经上传成功了
//            logger.error("文件上传");

            boolean b = FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            if(!b){
                return null;
            }

            //已经上传到ftp服务器上
            targetFile.delete();
        } catch (Exception e) {
            logger.error("上传文件异常",e);
            return null;
        }
        //A:abc.jpg
        //B:abc.jpg
        return targetFile.getName();
    }

}
