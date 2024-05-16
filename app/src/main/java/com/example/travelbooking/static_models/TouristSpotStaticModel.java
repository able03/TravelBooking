package com.example.travelbooking.static_models;

public class TouristSpotStaticModel
{
    private static int id;
    private static String name;
    private static String description;
    private static int img_rid;
    private static String province;
    private static String agency;
    private static String hotel1;
    private static String hotel2;

    public TouristSpotStaticModel(int id, String name, String description, int img_rid, String province, String agency, String hotel1, String hotel2) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.img_rid = img_rid;
        this.province = province;
        this.agency = agency;
        this.hotel1 = hotel1;
        this.hotel2 = hotel2;
    }

    public static int getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public static String getDescription() {
        return description;
    }

    public static int getImg_rid() {
        return img_rid;
    }

    public static String getProvince() {
        return province;
    }

    public static String getHotel1() {
        return hotel1;
    }

    public static String getHotel2() {
        return hotel2;
    }

    public static String getAgency() {
        return agency;


    }
}
