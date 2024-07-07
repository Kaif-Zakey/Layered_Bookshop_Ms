package org.example.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetailDTO implements Serializable {
    private String oid;
    private String itemCode;
    private int qty;
    private double unitPrice;

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "oid='" + oid + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderDetailDTO(String oid, String itemCode, int qty, double unitPrice) {
        this.oid = oid;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public OrderDetailDTO() {
    }
}
