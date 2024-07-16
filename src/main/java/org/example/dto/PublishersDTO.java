package org.example.dto;

public class PublishersDTO {
    private String puId;
    private String name;
    private String address;
    private String phoneNumber;
    private String bookId;

    @Override
    public String toString() {
        return "PublishersDTO{" +
                "puId='" + puId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bookId='" + bookId + '\'' +
                '}';
    }

    public String getPuId() {
        return puId;
    }

    public void setPuId(String puId) {
        this.puId = puId;
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

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public PublishersDTO(String puId, String name, String address, String phoneNumber, String bookId) {
        this.puId = puId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.bookId = bookId;
    }

    public PublishersDTO() {
    }
}
