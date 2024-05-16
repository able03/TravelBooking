package com.example.travelbooking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.travelbooking.models.TouristSpotModel;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    private final static String PALAWAN = "Palawan",
            CEBU = "Cebu",
            BOHOL = "Bohol",
            ILOCOS_NORTE = "Ilocos Norte",
            DAVAO = "Davao";


    private final static String TRAVELOKA = "Traveloka Philippines",
            CEBU_PACIFIC = "Cebu Pacific Air (Cebu Pacific Getaways)",
            KLOOK = "Klook Philippines";

    Context context;
    String sql;


    public DBHelper(@Nullable Context context) {
        super(context, "TravelBookingDatabase", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "CREATE TABLE IF NOT EXISTS customer(customer_id INTEGER PRIMARY KEY AUTOINCREMENT, first_name VARCHAR, last_name VARCHAR, birthdate VARCHAR, address VARCHAR, contact VARCHAR, email VARCHAR, pin VARCHAR, color_rid INTEGER)";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS tourist_spots(tourist_spot_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, description VARCHAR, img_rid INTEGER, province VARCHAR, agency VARCHAR, img_booking_rid INTEGER)";
        db.execSQL(sql);

    }

    public void CreateAccount(String fn, String ln, String bdate, String address, String contact, String email, String pin, int color_rid){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("first_name", fn);
        cv.put("last_name",ln);
        cv.put("birthdate", bdate);
        cv.put("address", address);
        cv.put("contact", contact);
        cv.put("email", email);
        cv.put("pin", pin);
        cv.put("color_rid", color_rid);
        db.insert("customer", null, cv);

    }

    public Cursor FindAccount(String pin){
        SQLiteDatabase db = this.getWritableDatabase();
        sql = "SELECT * FROM customer WHERE pin = '"+pin+"'";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }


    public String FindPinWithEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        sql = "SELECT * FROM customer WHERE email = '"+email+"'";
        Cursor cFindAccount = db.rawQuery(sql, null);
        cFindAccount.moveToFirst();
        if(cFindAccount.getCount() > 0){
            String pinFound = cFindAccount.getString(7);
            return pinFound;
        }

        return null;


    }

    public boolean addTouristSpot(String name, String description, int img_rid, String province, String agency, int img_booking_rid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("img_rid", img_rid);
        values.put("province", province);
        values.put("agency", agency);
        values.put("img_booking_rid", img_booking_rid);

        long rowAffected = db.insert("tourist_spots", null, values);

        if(rowAffected != -1)
        {
            return true;
        }
       return false;

    }

    public void embedTouristSpot()
    {
        // PALAWAM
       addTouristSpot("Puerto Princesa Underground River", "One of the New 7 Wonders of Nature, featuring a navigable underground river.",
                R.drawable.puerto_img, PALAWAN, TRAVELOKA, R.drawable.puerto);

        addTouristSpot("El Nido", "Known for its stunning limestone cliffs, crystal-clear waters, and island-hopping tours.",
                R.drawable.elnido_img, PALAWAN, TRAVELOKA, R.drawable.elnido);

        addTouristSpot("Coron", "Famous for its World War II shipwrecks, pristine lakes, and vibrant coral reefs.",
                R.drawable.coron_img, PALAWAN, TRAVELOKA, R.drawable.coron);


        // CEBU
        addTouristSpot("Magellan''s Cross", "A historical landmark marking the arrival of Ferdinand Magellan in 1521.",
                R.drawable.cebu_img_magellan, CEBU, CEBU_PACIFIC, R.drawable.magellan);

        addTouristSpot("Kawasan Falls", "A beautiful multi-tiered waterfall known for canyoneering adventures.",
                R.drawable.cebu_img_kawasan, CEBU, CEBU_PACIFIC, R.drawable.kawasan);

        addTouristSpot("Basilica Minore del Santo Niño", "The oldest Roman Catholic church in the country, housing the image of the Santo Niño.",
                R.drawable.cebu_img_basilica, CEBU, CEBU_PACIFIC, R.drawable.basilica);


        // BOHOL
        addTouristSpot("Chocolate Hills", "Unique geological formations consisting of at least 1,260 hills that turn brown in the dry season.",
                R.drawable.bohol_img_hills, BOHOL, CEBU_PACIFIC, R.drawable.choco);

        addTouristSpot("Tarsier Sanctuary", "Home to the world's smallest primate, the Philippine tarsier.",
                R.drawable.bohol_img_tarsier, BOHOL, CEBU_PACIFIC, R.drawable.tarsier);

        addTouristSpot("Loboc River Cruise", "A scenic river cruise offering buffet dining and cultural performances.",
                R.drawable.bohol_img_loboc, BOHOL, CEBU_PACIFIC, R.drawable.loboc);

        // ILOCOS NORTE
        addTouristSpot("Paoay Church", "A UNESCO World Heritage Site known for its distinct architecture.",
                R.drawable.ilocos_img_paoay, ILOCOS_NORTE, KLOOK, R.drawable.paoay);

        addTouristSpot("Bangui Windmills", ": A wind farm featuring giant wind turbines along the coast.",
                R.drawable.ilocos_img_bangui, ILOCOS_NORTE, KLOOK, R.drawable.bangui);

        addTouristSpot("Pagudpud", "Known for its pristine beaches, including Saud Beach and Blue Lagoon.",
                R.drawable.ilocos_img_pagudpud, ILOCOS_NORTE, KLOOK, R.drawable.pagudpud);


        // DAVAO
        addTouristSpot("Philippine Eagle Center", ": A conservation center dedicated to the endangered Philippine eagle.",
                R.drawable.davao_img_eagle, DAVAO, TRAVELOKA,R.drawable.eagle);

        addTouristSpot("Eden Nature Park", ": A mountain resort offering gardens, hiking trails, and adventure activities.",
                R.drawable.davao_img_eden, DAVAO, TRAVELOKA, R.drawable.eden);

        addTouristSpot("Samal Island", ": Known for its beautiful beaches, diving spots, and the Monfort Bat Sanctuary.",
                R.drawable.davao_img_samal, DAVAO, TRAVELOKA, R.drawable.samal);


    }


    public Cursor getTouristSpot()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tourist_spots", null);
    }

    public Cursor getTouristSpot(String province)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tourist_spots WHERE province = '"+province+"'", null);
    }

    public List<TouristSpotModel> getAllTouristSpotList()
    {
        List<TouristSpotModel> tempList = new ArrayList<>();

        Cursor cursor = getTouristSpot();

        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            int img_rid = cursor.getInt(3);
            String province = cursor.getString(4);
            String agency = cursor.getString(5);
            int booking_rid = cursor.getInt(6);

            tempList.add(new TouristSpotModel(id, name, desc, img_rid, province, agency, booking_rid));
        }

        return tempList;
    }




    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

