package com.management.oa.pojo;

public class Performance {
    private String dd;

    private String oaid;

    private String depart;

    private String name;

    private String postbonus;

    private String yearsbonus;

    private String overtimebonus;

    private String performancebonus;

    private String othersaddbonus;

    private String totaladdbonus;

    private String socialsecurity;

    private String medicare;

    private String unemploymentinsurance;

    private String providentfund;

    private String membershipfee;

    private String hydropower;

    private String error;

    private String absence;

    private String othersminusbonus;

    private String tax;

    private String totalminusbonus;

    private String realbonus;

    public Performance(String dd, String oaid, String depart, String name, String postbonus, String yearsbonus, String overtimebonus, String performancebonus, String othersaddbonus, String totaladdbonus, String socialsecurity, String medicare, String unemploymentinsurance, String providentfund, String membershipfee, String hydropower, String error, String absence, String othersminusbonus, String tax, String totalminusbonus, String realbonus) {
        this.dd = dd;
        this.oaid = oaid;
        this.depart = depart;
        this.name = name;
        this.postbonus = postbonus;
        this.yearsbonus = yearsbonus;
        this.overtimebonus = overtimebonus;
        this.performancebonus = performancebonus;
        this.othersaddbonus = othersaddbonus;
        this.totaladdbonus = totaladdbonus;
        this.socialsecurity = socialsecurity;
        this.medicare = medicare;
        this.unemploymentinsurance = unemploymentinsurance;
        this.providentfund = providentfund;
        this.membershipfee = membershipfee;
        this.hydropower = hydropower;
        this.error = error;
        this.absence = absence;
        this.othersminusbonus = othersminusbonus;
        this.tax = tax;
        this.totalminusbonus = totalminusbonus;
        this.realbonus = realbonus;
    }

    public Performance(String dd, String oaid, String name, String postbonus) {
        this.dd = dd;
        this.oaid = oaid;
        this.name = name;
        this.postbonus = postbonus;
    }

    public Performance() {
        super();
    }



    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd == null ? null : dd.trim();
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid == null ? null : oaid.trim();
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart == null ? null : depart.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPostbonus() {
        return postbonus;
    }

    public void setPostbonus(String postbonus) {
        this.postbonus = postbonus == null ? null : postbonus.trim();
    }

    public String getYearsbonus() {
        return yearsbonus;
    }

    public void setYearsbonus(String yearsbonus) {
        this.yearsbonus = yearsbonus == null ? null : yearsbonus.trim();
    }

    public String getOvertimebonus() {
        return overtimebonus;
    }

    public void setOvertimebonus(String overtimebonus) {
        this.overtimebonus = overtimebonus == null ? null : overtimebonus.trim();
    }

    public String getPerformancebonus() {
        return performancebonus;
    }

    public void setPerformancebonus(String performancebonus) {
        this.performancebonus = performancebonus == null ? null : performancebonus.trim();
    }

    public String getOthersaddbonus() {
        return othersaddbonus;
    }

    public void setOthersaddbonus(String othersaddbonus) {
        this.othersaddbonus = othersaddbonus == null ? null : othersaddbonus.trim();
    }

    public String getTotaladdbonus() {
        return totaladdbonus;
    }

    public void setTotaladdbonus(String totaladdbonus) {
        this.totaladdbonus = totaladdbonus == null ? null : totaladdbonus.trim();
    }

    public String getSocialsecurity() {
        return socialsecurity;
    }

    public void setSocialsecurity(String socialsecurity) {
        this.socialsecurity = socialsecurity == null ? null : socialsecurity.trim();
    }

    public String getMedicare() {
        return medicare;
    }

    public void setMedicare(String medicare) {
        this.medicare = medicare == null ? null : medicare.trim();
    }

    public String getUnemploymentinsurance() {
        return unemploymentinsurance;
    }

    public void setUnemploymentinsurance(String unemploymentinsurance) {
        this.unemploymentinsurance = unemploymentinsurance == null ? null : unemploymentinsurance.trim();
    }

    public String getProvidentfund() {
        return providentfund;
    }

    public void setProvidentfund(String providentfund) {
        this.providentfund = providentfund == null ? null : providentfund.trim();
    }

    public String getMembershipfee() {
        return membershipfee;
    }

    public void setMembershipfee(String membershipfee) {
        this.membershipfee = membershipfee == null ? null : membershipfee.trim();
    }

    public String getHydropower() {
        return hydropower;
    }

    public void setHydropower(String hydropower) {
        this.hydropower = hydropower == null ? null : hydropower.trim();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error == null ? null : error.trim();
    }

    public String getAbsence() {
        return absence;
    }

    public void setAbsence(String absence) {
        this.absence = absence == null ? null : absence.trim();
    }

    public String getOthersminusbonus() {
        return othersminusbonus;
    }

    public void setOthersminusbonus(String othersminusbonus) {
        this.othersminusbonus = othersminusbonus == null ? null : othersminusbonus.trim();
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax == null ? null : tax.trim();
    }

    public String getTotalminusbonus() {
        return totalminusbonus;
    }

    public void setTotalminusbonus(String totalminusbonus) {
        this.totalminusbonus = totalminusbonus == null ? null : totalminusbonus.trim();
    }

    public String getRealbonus() {
        return realbonus;
    }

    public void setRealbonus(String realbonus) {
        this.realbonus = realbonus == null ? null : realbonus.trim();
    }
}