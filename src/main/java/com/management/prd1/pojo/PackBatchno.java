package com.management.prd1.pojo;

import java.math.BigDecimal;

public class PackBatchno {
    private String dd;

    private String batchno;

    private BigDecimal caseqty;

    private String packUser;

    private String usrName;

    public PackBatchno(String dd, String batchno, BigDecimal caseqty, String packUser, String usrName) {
        this.dd = dd;
        this.batchno = batchno;
        this.caseqty = caseqty;
        this.packUser = packUser;
        this.usrName = usrName;
    }

    public PackBatchno() {
        super();
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd == null ? null : dd.trim();
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno == null ? null : batchno.trim();
    }

    public BigDecimal getCaseqty() {
        return caseqty;
    }

    public void setCaseqty(BigDecimal caseqty) {
        this.caseqty = caseqty;
    }

    public String getPackUser() {
        return packUser;
    }

    public void setPackUser(String packUser) {
        this.packUser = packUser == null ? null : packUser.trim();
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName == null ? null : usrName.trim();
    }
}