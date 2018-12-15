package com.management.prd1.controller;

import com.management.common.EasyuiTableResponse;
import com.management.prd1.dto.TaskViewDto;
import com.management.prd1.service.ITaskViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskViewController {

    @Autowired
    ITaskViewService taskViewService;

    @RequestMapping(value = "/TaskQuery" , method = RequestMethod.POST)
    public EasyuiTableResponse getData(TaskViewDto taskView){
        EasyuiTableResponse response = taskViewService.getData(taskView);
        return response;
    }
}
