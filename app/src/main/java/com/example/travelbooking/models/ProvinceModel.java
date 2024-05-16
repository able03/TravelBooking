package com.example.travelbooking.models;

public class ProvinceModel
{
    private int img_rid;
    private String name;

    public ProvinceModel(int img_rid, String name)
    {
        this.img_rid = img_rid;
        this.name = name;
    }

    public int getImg_rid()
    {
        return img_rid;
    }

    public String getName()
    {
        return name;
    }
}
