package com.management.prd1.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Xsogroup {
    private String xsogroupkey;

    private Date arriveDate;

    private String type;

    private String fromStation;

    private String fromVendor;

    private String carrierNo;

    private String shipNo;

    private String flag;

    private BigDecimal expectQty;

    private BigDecimal receiveQty;

    private String errorFlag;

    private BigDecimal errorQty;

    private String transport;

    private String shipType;

    private String shipUser;

    private Date shipDate;

    private String receiveUser;

    private Date receiveDate;

    private String sendUser;

    private Date sendDate;

    private String note;

    private String groupkey;

    private String status;

    private Date adddate;

    private String addwho;

    private Date editdate;

    private String editwho;

    private Date workgroupDate;

    public Xsogroup(String xsogroupkey, Date arriveDate, String type, String fromStation, String fromVendor, String carrierNo, String shipNo, String flag, BigDecimal expectQty, BigDecimal receiveQty, String errorFlag, BigDecimal errorQty, String transport, String shipType, String shipUser, Date shipDate, String receiveUser, Date receiveDate, String sendUser, Date sendDate, String note, String groupkey, String status, Date adddate, String addwho, Date editdate, String editwho, Date workgroupDate) {
        this.xsogroupkey = xsogroupkey;
        this.arriveDate = arriveDate;
        this.type = type;
        this.fromStation = fromStation;
        this.fromVendor = fromVendor;
        this.carrierNo = carrierNo;
        this.shipNo = shipNo;
        this.flag = flag;
        this.expectQty = expectQty;
        this.receiveQty = receiveQty;
        this.errorFlag = errorFlag;
        this.errorQty = errorQty;
        this.transport = transport;
        this.shipType = shipType;
        this.shipUser = shipUser;
        this.shipDate = shipDate;
        this.receiveUser = receiveUser;
        this.receiveDate = receiveDate;
        this.sendUser = sendUser;
        this.sendDate = sendDate;
        this.note = note;
        this.groupkey = groupkey;
        this.status = status;
        this.adddate = adddate;
        this.addwho = addwho;
        this.editdate = editdate;
        this.editwho = editwho;
        this.workgroupDate = workgroupDate;
    }

    public Xsogroup() {
        super();
    }

    public String getXsogroupkey() {
        return xsogroupkey;
    }

    public void setXsogroupkey(String xsogroupkey) {
        this.xsogroupkey = xsogroupkey == null ? null : xsogroupkey.trim();
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation == null ? null : fromStation.trim();
    }

    public String getFromVendor() {
        return fromVendor;
    }

    public void setFromVendor(String fromVendor) {
        this.fromVendor = fromVendor == null ? null : fromVendor.trim();
    }

    public String getCarrierNo() {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo) {
        this.carrierNo = carrierNo == null ? null : carrierNo.trim();
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo == null ? null : shipNo.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public BigDecimal getExpectQty() {
        return expectQty;
    }

    public void setExpectQty(BigDecimal expectQty) {
        this.expectQty = expectQty;
    }

    public BigDecimal getReceiveQty() {
        return receiveQty;
    }

    public void setReceiveQty(BigDecimal receiveQty) {
        this.receiveQty = receiveQty;
    }

    public String getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(String errorFlag) {
        this.errorFlag = errorFlag == null ? null : errorFlag.trim();
    }

    public BigDecimal getErrorQty() {
        return errorQty;
    }

    public void setErrorQty(BigDecimal errorQty) {
        this.errorQty = errorQty;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport == null ? null : transport.trim();
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType == null ? null : shipType.trim();
    }

    public String getShipUser() {
        return shipUser;
    }

    public void setShipUser(String shipUser) {
        this.shipUser = shipUser == null ? null : shipUser.trim();
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser == null ? null : receiveUser.trim();
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser == null ? null : sendUser.trim();
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getGroupkey() {
        return groupkey;
    }

    public void setGroupkey(String groupkey) {
        this.groupkey = groupkey == null ? null : groupkey.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public Date getEditdate() {
        return editdate;
    }

    public void setEditdate(Date editdate) {
        this.editdate = editdate;
    }

    public String getEditwho() {
        return editwho;
    }

    public void setEditwho(String editwho) {
        this.editwho = editwho == null ? null : editwho.trim();
    }

    public Date getWorkgroupDate() {
        return workgroupDate;
    }

    public void setWorkgroupDate(Date workgroupDate) {
        this.workgroupDate = workgroupDate;
    }
}