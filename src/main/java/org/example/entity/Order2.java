package org.example.entity;

import java.util.Date;

public class Order2 {
    private String Oid;
    private String cid;
    private Date date;

    public Order2() {
    }

    @Override
    public String toString() {
        return "Order2{" +
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

    public Order2(String oid, String cid, Date date) {
        Oid = oid;
        this.cid = cid;
        this.date = date;
    }
}
