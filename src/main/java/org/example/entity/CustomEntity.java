package org.example.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomEntity {
    private String id;
    private String name;
    private String address;

    //item
    private String code;
    private String description;
    private int qtyOnHand;
    private double unitPrice;

    //Order
    private String oid;
    private LocalDate date;
    private String customerID;

    //OrderDetails
    private String itemCode;
    private int qty;

    @Override
    public String toString() {
        return "CustomEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                ", oid='" + oid + '\'' +
                ", date=" + date +
                ", customerID='" + customerID + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
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

    public CustomEntity(String id, String name, String address, String code, String description, int qtyOnHand, double unitPrice, String oid, LocalDate date, String customerID, String itemCode, int qty) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.code = code;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.oid = oid;
        this.date = date;
        this.customerID = customerID;
        this.itemCode = itemCode;
        this.qty = qty;
    }
    public CustomEntity(String oid, LocalDate date, String customerID, String itemCode, int qty, double unitPrice) {
        this.unitPrice = unitPrice;
        this.oid = oid;
        this.date = date;
        this.customerID = customerID;
        this.itemCode = itemCode;
        this.qty = qty;
    }
    public CustomEntity() {

    }
}
