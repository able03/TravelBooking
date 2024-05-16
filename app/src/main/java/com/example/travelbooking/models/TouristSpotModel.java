package com.example.travelbooking.models;

public class TouristSpotModel
{
    private int id;
    private String name;
    private String description;
    private int img_rid;
    private String province;
    private String agency;
    private int img_booking_rid;

    public TouristSpotModel(int id, String name, String description, int img_rid, String province, String agency, int img_booking_rid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.img_rid = img_rid;
        this.province = province;
        this.agency = agency;
        this.img_booking_rid = img_booking_rid;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImg_rid() {
        return img_rid;
    }

    public String getProvince() {
        return province;
    }


    public String getAgency() {
        return agency;
    }

    public int getImg_booking_rid() {
        return img_booking_rid;
    }
}
