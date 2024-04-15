package entity;

import java.util.ArrayList;

public class Hotel {
    private int id;
    private String name;
    private String city;
    private String region;
    private String fullAddress;
    private String phone;
    private String email;
    private String star;
    private ArrayList<String> facilities;

    public Hotel() {
    }

    public Hotel(int id, String name, String city, String region, String fullAddress, String phone, String email, String star, ArrayList<String> facilities) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.region = region;
        this.fullAddress = fullAddress;
        this.phone = phone;
        this.email = email;
        this.star = star;
        this.facilities = facilities;
    }

    public Hotel(String name, String city, String region, String fullAddress, String phone, String email, String star, ArrayList<String> facilities) {
        this.name = name;
        this.city = city;
        this.region = region;
        this.fullAddress = fullAddress;
        this.phone = phone;
        this.email = email;
        this.star = star;
        this.facilities = facilities;
    }

    public Hotel(String name, String city, String region, String fullAddress, String phone, String email, String star) {
        this.name = name;
        this.city = city;
        this.region = region;
        this.fullAddress = fullAddress;
        this.phone = phone;
        this.email = email;
        this.star = star;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public ArrayList<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(ArrayList<String> facilities) {
        this.facilities = facilities;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", star='" + star + '\'' +
                ", facilities=" + facilities +
                '}';
    }
}
