package com.management.prd1.dto;

import java.math.BigDecimal;
import java.util.Date;

public class RecordSkuDto {
    private String barcode;

    private String descr;

    private BigDecimal price;


    public RecordSkuDto() {
        super();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr == null ? null : descr.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}