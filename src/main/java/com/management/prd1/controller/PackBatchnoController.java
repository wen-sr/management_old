package com.management.prd1.controller;

import com.management.common.EasyuiTableResponse;
import com.management.prd1.service.IPackBatchnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PackBatchnoController {

    @Autowired
    IPackBatchnoService packBatchnoService;

    @RequestMapping("/packBatchno")
    public EasyuiTableResponse getData(String dd, String id){
        EasyuiTableResponse response = packBatchnoService.getData(dd,id);
        return response;
    }
}
