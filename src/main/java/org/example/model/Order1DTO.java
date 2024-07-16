package org.example.model;

public class Order1DTO {
    private String orId;
    private String boId;
    private  int qty;
    private  double unitPrice;

    @Override
    public String toString() {
        return "Order1DTO{" +
                "orId='" + orId + '\'' +
                ", boId='" + boId + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public String getOrId() {
        return orId;
    }

    public void setOrId(String orId) {
        this.orId = orId;
    }

    public String getBoId() {
        return boId;
    }

    public void setBoId(String boId) {
        this.boId = boId;
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

    public Order1DTO() {
    }

    public Order1DTO(String orId, String boId, int qty, double unitPrice) {
        this.orId = orId;
        this.boId = boId;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }
}
