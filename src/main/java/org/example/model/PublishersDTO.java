package org.example.model;

public class PublishersDTO {
    private String pId;
    private String name;
    private String address;
    private String phoneNumber;
    private String bId;

    @Override
    public String toString() {
        return "PublishersDTO{" +
                "pId='" + pId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bId='" + bId + '\'' +
                '}';
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public PublishersDTO(String pId, String name, String address, String phoneNumber, String bId) {
        this.pId = pId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.bId = bId;
    }

    public PublishersDTO() {
    }
}
