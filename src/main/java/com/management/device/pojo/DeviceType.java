package com.management.device.pojo;

import java.util.Date;

public class DeviceType {
    private Integer deviceTypeId;

    private String deviceTypeName;

    private Date adddate;

    private String addwho;

    private String bk1;

    private String bk2;

    private String bk3;

    private String bk4;

    private String bk5;

    public DeviceType(Integer deviceTypeId, String deviceTypeName, Date adddate, String addwho, String bk1, String bk2, String bk3, String bk4, String bk5) {
        this.deviceTypeId = deviceTypeId;
        this.deviceTypeName = deviceTypeName;
        this.adddate = adddate;
        this.addwho = addwho;
        this.bk1 = bk1;
        this.bk2 = bk2;
        this.bk3 = bk3;
        this.bk4 = bk4;
        this.bk5 = bk5;
    }

    public DeviceType() {
        super();
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

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public String getAddwho() {
        return addwho;
    }

    public void setAddwho(String addwho) {
        this.addwho = addwho == null ? null : addwho.trim();
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
}