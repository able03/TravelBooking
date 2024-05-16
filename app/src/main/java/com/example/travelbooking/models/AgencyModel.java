package com.example.travelbooking.models;

public class AgencyModel
{
    private String name;
    private String contact;
    private String address;
    private String email;
    private String[] provinces;

    public AgencyModel(String name, String contact, String address, String email, String[] provinces)
    {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.email = email;
        this.provinces = provinces;
    }

    public String getName()
    {
        return name;
    }

    public String getContact()
    {
        return contact;
    }

    public String getAddress()
    {
        return address;
    }

    public String getEmail()
    {
        return email;
    }

    public String[] getProvinces()
    {
        return provinces;
    }
}
