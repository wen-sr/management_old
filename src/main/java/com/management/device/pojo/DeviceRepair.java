package com.management.device.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class DeviceRepair {
    private Long id;

    private String deviceId;

    private Integer deviceTypeId;

    private String deviceTypeName;

    private String deviceUserId;

    private String deviceUserName;

    private Integer status;

    private String cause;

    private String bakup;

    private Date adddate;

    private Date editdate;

    private String questionDescription;

    private String repairUserId;

    private String component;

    private BigDecimal cost;

    private String result;

    private String bk1;

    private String bk2;

    private String bk3;

    private String bk4;

    private String bk5;

    public DeviceRepair(Long id, String deviceId, Integer deviceTypeId, String deviceTypeName, String deviceUserId, String deviceUserName, Integer status, String cause, String bakup, Date adddate, Date editdate, String questionDescription, String repairUserId, String component, BigDecimal cost, String result, String bk1, String bk2, String bk3, String bk4, String bk5) {
        this.id = id;
        this.deviceId = deviceId;
        this.deviceTypeId = deviceTypeId;
        this.deviceTypeName = deviceTypeName;
        this.deviceUserId = deviceUserId;
        this.deviceUserName = deviceUserName;
        this.status = status;
        this.cause = cause;
        this.bakup = bakup;
        this.adddate = adddate;
        this.editdate = editdate;
        this.questionDescription = questionDescription;
        this.repairUserId = repairUserId;
        this.component = component;
        this.cost = cost;
        this.result = result;
        this.bk1 = bk1;
        this.bk2 = bk2;
        this.bk3 = bk3;
        this.bk4 = bk4;
        this.bk5 = bk5;
    }

    public DeviceRepair() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public Integer getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Integer deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName == null ? null : deviceTypeName.trim();
    }

    public String getDeviceUserId() {
        return deviceUserId;
    }

    public void setDeviceUserId(String deviceUserId) {
        this.deviceUserId = deviceUserId;
    }

    public String getDeviceUserName() {
        return deviceUserName;
    }

    public void setDeviceUserName(String deviceUserName) {
        this.deviceUserName = deviceUserName == null ? null : deviceUserName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    public String getBakup() {
        return bakup;
    }

    public void setBakup(String bakup) {
        this.bakup = bakup == null ? null : bakup.trim();
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public Date getEditdate() {
        return editdate;
    }

    public void setEditdate(Date editdate) {
        this.editdate = editdate;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription == null ? null : questionDescription.trim();
    }

    public String getRepairUserId() {
        return repairUserId;
    }

    public void setRepairUserId(String repairUserId) {
        this.repairUserId = repairUserId == null ? null : repairUserId.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getBk1() {
        return bk1;
    }

    public void setBk1(String bk1) {
        this.bk1 = bk1 == null ? null : bk1.trim();
    }

    public String getBk2() {
        return bk2;
    }

    public void setBk2(String bk2) {
        this.bk2 = bk2 == null ? null : bk2.trim();
    }

    public String getBk3() {
        return bk3;
    }

    public void setBk3(String bk3) {
        this.bk3 = bk3 == null ? null : bk3.trim();
    }

    public String getBk4() {
        return bk4;
    }

    public void setBk4(String bk4) {
        this.bk4 = bk4 == null ? null : bk4.trim();
    }

    public String getBk5() {
        return bk5;
    }

    public void setBk5(String bk5) {
        this.bk5 = bk5 == null ? null : bk5.trim();
    }

    public DeviceRepair toNull(DeviceRepair deviceRepair) {
        if(String.valueOf(deviceRepair.getDeviceId()) == ""){
            deviceRepair.setDeviceId(null);
        }
        return deviceRepair;
    }
}