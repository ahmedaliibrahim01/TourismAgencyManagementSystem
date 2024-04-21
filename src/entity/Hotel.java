package entity;

import java.util.Arrays;

public class Hotel {
    private int id;
    private String name;
    private String city;
    private String region;
    private String fullAddress;
    private String phone;
    private String email;
    private String star;
    private String[] facilities;
    private String[] pensionTypes;

    public Hotel() {
    }

    public Hotel(int id, String name, String city, String region, String fullAddress, String phone, String email, String star, String[] facilities, String[] pensionTypes) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.region = region;
        this.fullAddress = fullAddress;
        this.phone = phone;
        this.email = email;
        this.star = star;
        this.facilities = facilities;
        this.pensionTypes = pensionTypes;
    }

    public Hotel(String name, String city, String region, String fullAddress, String phone, String email, String star, String[] facilities, String[] pensionTypes) {
        this.name = name;
        this.city = city;
        this.region = region;
        this.fullAddress = fullAddress;
        this.phone = phone;
        this.email = email;
        this.star = star;
        this.facilities = facilities;
        this.pensionTypes = pensionTypes;
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

    public String[] getFacilities() {
        return facilities;
    }

    public void setFacilities(String[] facilities) {
        this.facilities = facilities;
    }

    public String[] getPensionTypes() {
        return pensionTypes;
    }

    public void setPensionTypes(String[] pensionTypes) {
        this.pensionTypes = pensionTypes;
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
                ", facilities=" + Arrays.toString(facilities) +
                ", pensionTypes=" + Arrays.toString(pensionTypes) +
                '}';
    }
}
