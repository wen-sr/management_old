package com.management.login.pojo;

public class RoleAccordionKey {
    private Integer roleId;

    private Integer accordionId;

    public RoleAccordionKey(Integer roleId, Integer accordionId) {
        this.roleId = roleId;
        this.accordionId = accordionId;
    }

    public RoleAccordionKey() {
        super();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAccordionId() {
        return accordionId;
    }

    public void setAccordionId(Integer accordionId) {
        this.accordionId = accordionId;
    }
}