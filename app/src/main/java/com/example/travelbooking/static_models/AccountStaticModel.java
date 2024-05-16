package com.example.travelbooking.static_models;

public class AccountStaticModel
{
    private static int id;
    private static String fname;
    private static String lname;
    private static String bdate;
    private static String address;
    private static String contact;
    private static String email;
    private static int color_rid;

    public AccountStaticModel(int id, String fname, String lname, String bdate, String address, String contact, String email, int color_rid) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.bdate = bdate;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.color_rid = color_rid;
    }

    public static int getId() {
        return id;
    }

    public static String getFname() {
        return fname;
    }

    public static String getLname() {
        return lname;
    }

    public static String getBdate() {
        return bdate;
    }

    public static String getAddress() {
        return address;
    }

    public static String getContact() {
        return contact;
    }

    public static String getEmail() {
        return email;
    }


    public static int getColor_rid() {
        return color_rid;
    }
}
