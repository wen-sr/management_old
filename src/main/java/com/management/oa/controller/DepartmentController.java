package com.management.oa.controller;

import com.management.oa.pojo.Department;
import com.management.oa.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/oa/department")
public class DepartmentController {

    @Autowired
    IDepartmentService departmentService;

    @RequestMapping("/findAll")
    public List<Department> findAll(){
        return departmentService.findAll();
    }
}
