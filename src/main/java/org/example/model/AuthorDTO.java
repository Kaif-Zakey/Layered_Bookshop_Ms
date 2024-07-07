package org.example.model;

import java.io.Serializable;

public class AuthorDTO implements Serializable {
    private String id;
    private String name;
    private String country;

    public AuthorDTO() {
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AuthorDTO(String id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
