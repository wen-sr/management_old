package com.management.login.pojo;

public class RoleLoginKey {
    private Integer roleId;

    private String loginId;

    public RoleLoginKey(Integer roleId, String loginId) {
        this.roleId = roleId;
        this.loginId = loginId;
    }

    public RoleLoginKey() {
        super();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId == null ? null : loginId.trim();
    }
}