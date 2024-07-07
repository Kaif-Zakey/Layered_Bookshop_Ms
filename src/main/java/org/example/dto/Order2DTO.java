package org.example.dto;

import java.util.Date;

public class Order2DTO {
    private String Oid;
    private String cid;
    private Date date;

    @Override
    public String toString() {
        return "Order2DTO{" +
                "Oid='" + Oid + '\'' +
                ", cid='" + cid + '\'' +
                ", date=" + date +
                '}';
    }

    public String getOid() {
        return Oid;
    }

    public void setOid(String oid) {
        Oid = oid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Order2DTO(String oid, String cid, Date date) {
        Oid = oid;
        this.cid = cid;
        this.date = date;
    }

    public Order2DTO() {
    }
}
