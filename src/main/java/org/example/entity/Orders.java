package org.example.entity;

import java.time.LocalDate;


public class Orders {
    private String oid;

    private String Cid;

    private LocalDate date;

    @Override
    public String toString() {
        return "Orders{" +
                "oid='" + oid + '\'' +
                ", Cid='" + Cid + '\'' +
                ", date=" + date +
                '}';
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }



    public Orders(String oid, String cid, LocalDate date) {
        this.oid = oid;
        Cid = cid;
        this.date = date;
    }
}
