package com.management.oa.controller;

import com.management.common.ServerResponse;
import com.management.oa.pojo.Organization;
import com.management.oa.service.IOrganizationService;
import com.management.oa.vo.OrganizationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    IOrganizationService organizationService;

    @RequestMapping("/findTree")
    public List<OrganizationVo> findTree(@RequestParam(value = "id", defaultValue = "0")Integer id, @RequestParam(value = "flag", defaultValue = "0")Integer flag){

        List<OrganizationVo> organizationVoList = organizationService.findTree(id, flag);

        return organizationVoList;
    }

    @RequestMapping("/add")
    public ServerResponse add(Organization organization) {
        return organizationService.add(organization);
    }

    @RequestMapping("/edit")
    public ServerResponse edit(Organization organization) {
        return organizationService.edit(organization);
    }

    @RequestMapping("/delete")
    public ServerResponse delete(Organization organization) {
        return organizationService.delete(organization);
    }

}
