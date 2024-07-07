package org.example.entity;

public class Inventory {
    private String id;
    private int qty;
    private String location;
    private String bId;

    @Override
    public String toString() {
        return "Inventory{" +
                "id='" + id + '\'' +
                ", qty=" + qty +
                ", location='" + location + '\'' +
                ", bId='" + bId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public Inventory(String id, int qty, String location, String bId) {
        this.id = id;
        this.qty = qty;
        this.location = location;
        this.bId = bId;
    }

    public Inventory() {
    }
}
