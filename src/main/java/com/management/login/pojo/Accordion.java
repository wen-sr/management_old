package com.management.login.pojo;

public class Accordion {
    private Integer accordionId;

    private String accordionName;

    private String accordionIcon;

    private String accordionUrl;

    private Integer accordionFlag;

    private Integer accordionGroup;

    private Integer orderBy;

    private Integer functionId;

    private Integer fooId;

    public Accordion(Integer accordionId, String accordionName, String accordionIcon, String accordionUrl, Integer accordionFlag, Integer accordionGroup, Integer orderBy, Integer functionId, Integer fooId) {
        this.accordionId = accordionId;
        this.accordionName = accordionName;
        this.accordionIcon = accordionIcon;
        this.accordionUrl = accordionUrl;
        this.accordionFlag = accordionFlag;
        this.accordionGroup = accordionGroup;
        this.orderBy = orderBy;
        this.functionId = functionId;
        this.fooId = fooId;
    }

    public Accordion() {
        super();
    }

    public Integer getAccordionId() {
        return accordionId;
    }

    public void setAccordionId(Integer accordionId) {
        this.accordionId = accordionId;
    }

    public String getAccordionName() {
        return accordionName;
    }

    public void setAccordionName(String accordionName) {
        this.accordionName = accordionName == null ? null : accordionName.trim();
    }

    public String getAccordionIcon() {
        return accordionIcon;
    }

    public void setAccordionIcon(String accordionIcon) {
        this.accordionIcon = accordionIcon == null ? null : accordionIcon.trim();
    }

    public String getAccordionUrl() {
        return accordionUrl;
    }

    public void setAccordionUrl(String accordionUrl) {
        this.accordionUrl = accordionUrl == null ? null : accordionUrl.trim();
    }

    public Integer getAccordionFlag() {
        return accordionFlag;
    }

    public void setAccordionFlag(Integer accordionFlag) {
        this.accordionFlag = accordionFlag;
    }

    public Integer getAccordionGroup() {
        return accordionGroup;
    }

    public void setAccordionGroup(Integer accordionGroup) {
        this.accordionGroup = accordionGroup;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getFooId() {
        return fooId;
    }

    public void setFooId(Integer fooId) {
        this.fooId = fooId;
    }
}