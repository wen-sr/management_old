package com.management.device.pojo;

import java.util.Date;

public class DeviceList
{
    private String deviceId;
    private Integer deviceTypeId;
    private Date adddate;
    private Date editdate;
    private String addwho;
    private String status;
    private String bk1;
    private String bk2;
    private String bk3;
    private String bk4;
    private String bk5;

    public DeviceList(String deviceId, Integer deviceTypeId, Date adddate, Date editdate, String addwho, String status, String bk1, String bk2, String bk3, String bk4, String bk5)
    {
        this.deviceId = deviceId;
        this.deviceTypeId = deviceTypeId;
        this.adddate = adddate;
        this.editdate = editdate;
        this.addwho = addwho;
        this.status = status;
        this.bk1 = bk1;
        this.bk2 = bk2;
        this.bk3 = bk3;
        this.bk4 = bk4;
        this.bk5 = bk5;
    }

    public DeviceList() {}

    public String getDeviceId()
    {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = (deviceId == null ? null : deviceId.trim());
    }

    public Integer getDeviceTypeId()
    {
        return this.deviceTypeId;
    }

    public void setDeviceTypeId(Integer deviceTypeId)
    {
        this.deviceTypeId = deviceTypeId;
    }

    public Date getAdddate()
    {
        return this.adddate;
    }

    public void setAdddate(Date adddate)
    {
        this.adddate = adddate;
    }

    public Date getEditdate()
    {
        return this.editdate;
    }

    public void setEditdate(Date editdate)
    {
        this.editdate = editdate;
    }

    public String getAddwho()
    {
        return this.addwho;
    }

    public void setAddwho(String addwho)
    {
        this.addwho = (addwho == null ? null : addwho.trim());
    }

    public String getStatus()
    {
        return this.status;
    }

    public void setStatus(String status)
    {
        this.status = (status == null ? null : status.trim());
    }

    public String getBk1()
    {
        return this.bk1;
    }

    public void setBk1(String bk1)
    {
        this.bk1 = (bk1 == null ? null : bk1.trim());
    }

    public String getBk2()
    {
        return this.bk2;
    }

    public void setBk2(String bk2)
    {
        this.bk2 = (bk2 == null ? null : bk2.trim());
    }

    public String getBk3()
    {
        return this.bk3;
    }

    public void setBk3(String bk3)
    {
        this.bk3 = (bk3 == null ? null : bk3.trim());
    }

    public String getBk4()
    {
        return this.bk4;
    }

    public void setBk4(String bk4)
    {
        this.bk4 = (bk4 == null ? null : bk4.trim());
    }

    public String getBk5()
    {
        return this.bk5;
    }

    public void setBk5(String bk5)
    {
        this.bk5 = (bk5 == null ? null : bk5.trim());
    }
}
