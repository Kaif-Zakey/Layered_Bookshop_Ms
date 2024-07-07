package org.example.model;

import java.io.Serializable;

public class InventoryDTO implements Serializable {
    private String id;
    private int qty;
    private String location;
    private String bId;

    public InventoryDTO() {
    }

    @Override
    public String toString() {
        return "InventoryDTO{" +
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

    public InventoryDTO(String id, int qty, String location, String bId) {
        this.id = id;
        this.qty = qty;
        this.location = location;
        this.bId = bId;
    }
}
