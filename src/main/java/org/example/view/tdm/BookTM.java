package org.example.view.tdm;

public class BookTM {
    private String id;
    private double UnitPrice;
    private String Description;
    private int qtyOnHand;

    public BookTM() {
    }

    public BookTM(String id, double unitPrice, String description, int qtyOnHand) {
        this.id = id;
        UnitPrice = unitPrice;
        Description = description;
        this.qtyOnHand = qtyOnHand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "BookTM{" +
                "id='" + id + '\'' +
                ", UnitPrice=" + UnitPrice +
                ", Description='" + Description + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }

}
