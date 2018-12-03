package com.management.prd1.controller;


import com.management.common.ServerResponse;
import com.management.prd1.pojo.Xsogroup;
import com.management.prd1.service.XsogroupService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/prd1")
public class XsogroupController {

    @Autowired
    private XsogroupService xsogroupService;

    @Autowired
    WxMpService wxMpService;

    @RequestMapping("/getReceiptAndDelivery")
    @ResponseBody
    public ServerResponse getReceiptAndDelivery(String begin, String end){
        ServerResponse response = xsogroupService.getReceiptAndDelivery(begin,end);
        return null;
    }

    @RequestMapping("/getYield")
    @ResponseBody
    public ServerResponse getYield(String begin, String end){
        ServerResponse response = xsogroupService.getYield(begin,end);
//        WxMpKefuMessage message = WxMpKefuMessage
//                .TEXT()
//                .toUser("oPOAgvx1Utuu0Mg25QTPs5yqDUyw")
//                .content("测试客服消息")
//                .build();

        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("oPOAgvx1Utuu0Mg25QTPs5yqDUyw")
                .templateId("j3uSiEeYMGG1UrMmU_AZlIJ_xwdTChiCGkKCpbTr2sk").build();
        templateMessage.setUrl("http://www.baidu.com");
        templateMessage.getData().add(new WxMpTemplateData("first", "测试模版消息", "#FF3333"));
        templateMessage.getData().add(new WxMpTemplateData("keyword1", "新华物流", "#0044BB"));
        templateMessage.getData().add(new WxMpTemplateData("keyword2", new Date().toString(), "#0044BB"));
        templateMessage.getData().add(new WxMpTemplateData("remark", "很荣幸为您服务，如有任何疑问请联系技术人员","#AAAAAA"));

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
//            wxMpService.getKefuService().sendKefuMessage(message);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return response;
    }
}
