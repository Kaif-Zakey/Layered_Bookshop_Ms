package org.example.view.tdm;

public class Order1TM {
    private String oId;
    private String bId;
    private  int qty;
    private  double unitPrice;

    @Override
    public String toString() {
        return "Order1TM{" +
                "oId='" + oId + '\'' +
                ", bId='" + bId + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
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

    public Order1TM(String oId, String bId, int qty, double unitPrice) {
        this.oId = oId;
        this.bId = bId;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public Order1TM() {
    }
}
