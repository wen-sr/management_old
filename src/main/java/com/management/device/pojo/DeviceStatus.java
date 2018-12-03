package com.management.device.pojo;

import java.util.Date;

public class DeviceStatus {
    private String deviceId;

    private Integer deviceTypeId;

    private String deviceTypeName;

    private String deviceUserId;

    private String deviceUserName;

    private Integer status;

    private Date adddate;

    private Date editdate;

    public DeviceStatus toNull(DeviceStatus deviceStatus){
        if(deviceStatus.deviceId == "") {
            deviceStatus.deviceId = null;
        }
        return deviceStatus;
    }

    public DeviceStatus(String deviceId, Integer deviceTypeId, String deviceTypeName, String deviceUserId, String deviceUserName, Integer status, Date adddate, Date editdate) {
        this.deviceId = deviceId;
        this.deviceTypeId = deviceTypeId;
        this.deviceTypeName = deviceTypeName;
        this.deviceUserId = deviceUserId;
        this.deviceUserName = deviceUserName;
        this.status = status;
        this.adddate = adddate;
        this.editdate = editdate;
    }

    public DeviceStatus() {
        super();
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
}