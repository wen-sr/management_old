package com.management.device.pojo;

import java.util.Date;

public class DeviceUsers {
    private Integer deviceUserId;

    private String deviceUserName;

    private Date adddate;

    private Date editdate;

    public DeviceUsers(Integer deviceUserId, String deviceUserName, Date adddate, Date editdate) {
        this.deviceUserId = deviceUserId;
        this.deviceUserName = deviceUserName;
        this.adddate = adddate;
        this.editdate = editdate;
    }

    public DeviceUsers() {
        super();
    }

    public Integer getDeviceUserId() {
        return deviceUserId;
    }

    public void setDeviceUserId(Integer deviceUserId) {
        this.deviceUserId = deviceUserId;
    }

    public String getDeviceUserName() {
        return deviceUserName;
    }

    public void setDeviceUserName(String deviceUserName) {
        this.deviceUserName = deviceUserName == null ? null : deviceUserName.trim();
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