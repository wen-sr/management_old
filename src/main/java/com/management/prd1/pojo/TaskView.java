package com.management.prd1.pojo;

import java.util.Date;

public class TaskView {
    private String tasktype;

    private String contNo;

    private Date createDate;

    private String zt;

    private String retcode;

    private Date rettime;

    private String sendcode;

    private Date sendtime;

    public TaskView(String tasktype, String contNo, Date createDate, String zt, String retcode, Date rettime, String sendcode, Date sendtime) {
        this.tasktype = tasktype;
        this.contNo = contNo;
        this.createDate = createDate;
        this.zt = zt;
        this.retcode = retcode;
        this.rettime = rettime;
        this.sendcode = sendcode;
        this.sendtime = sendtime;
    }

    public TaskView() {
        super();
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype == null ? null : tasktype.trim();
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo == null ? null : contNo.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt == null ? null : zt.trim();
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode == null ? null : retcode.trim();
    }

    public Date getRettime() {
        return rettime;
    }

    public void setRettime(Date rettime) {
        this.rettime = rettime;
    }

    public String getSendcode() {
        return sendcode;
    }

    public void setSendcode(String sendcode) {
        this.sendcode = sendcode == null ? null : sendcode.trim();
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }
}