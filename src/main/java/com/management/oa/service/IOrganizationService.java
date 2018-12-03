package com.management.oa.service;

import com.management.common.ServerResponse;
import com.management.oa.pojo.Organization;
import com.management.oa.vo.OrganizationVo;

import java.util.List;

public interface IOrganizationService {
    List<OrganizationVo> findTree(Integer id, Integer flag);

    ServerResponse add(Organization organization);

    ServerResponse edit(Organization organization);

    ServerResponse delete(Organization organization);
}
