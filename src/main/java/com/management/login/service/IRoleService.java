package com.management.login.service;

import com.management.common.ServerResponse;
import com.management.login.pojo.Authority;
import com.management.login.pojo.Role;

import java.util.List;

public interface IRoleService {
    ServerResponse findAll(Role role);

    List<Authority> getAuth(Integer id);

    ServerResponse addRole(Role role);

    ServerResponse editRole(Role role);

    ServerResponse comfirmAuth(Integer roleId, String[] ids);

    ServerResponse deleteRole(Role role);
}
