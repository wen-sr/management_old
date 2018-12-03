package com.management.xhwl.controller;

import com.management.common.ServerResponse;
import com.management.xhwl.pojo.RecordSku;
import com.management.xhwl.servivce.IRecordSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/temp")
public class TempController {

    @Autowired
    IRecordSku iRecordSku;


    @RequestMapping("/recodeSku/addInfo")
    @ResponseBody
    public ServerResponse addInfo(RecordSku recordSku) {
        return iRecordSku.addInfo(recordSku);
    }


}
