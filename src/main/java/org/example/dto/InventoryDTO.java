package org.example.dto;

public class InventoryDTO {
    private String id;
    private int qty;
    private String location;
    private String BoId;

    @Override
    public String toString() {
        return "InventoryDTO{" +
                "id='" + id + '\'' +
                ", qty=" + qty +
                ", location='" + location + '\'' +
                ", BoId='" + BoId + '\'' +
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

    public String getBoId() {
        return BoId;
    }

    public void setBoId(String boId) {
        BoId = boId;
    }

    public InventoryDTO() {
    }

    public InventoryDTO(String id, int qty, String location, String boId) {
        this.id = id;
        this.qty = qty;
        this.location = location;
        BoId = boId;
    }
}
