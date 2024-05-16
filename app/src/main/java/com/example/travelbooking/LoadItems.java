package com.example.travelbooking;

import android.content.Context;
import android.database.Cursor;

import com.example.travelbooking.models.AgencyModel;
import com.example.travelbooking.models.ProvinceModel;
import com.example.travelbooking.models.TouristSpotModel;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class LoadItems
{

    private final static String PALAWAN = "Palawan",
            CEBU = "Cebu",
            BOHOL = "Bohol",
            ILOCOS_NORTE = "Ilocos Norte",
            DAVAO = "Davao";

    private List<TouristSpotModel> allList;
    private List<TouristSpotModel> palawanList;
    private List<TouristSpotModel> cebuList;
    private List<TouristSpotModel> boholList;
    private List<TouristSpotModel> ilocosNorteList;
    private List<TouristSpotModel> davaoList;
    private DBHelper dbHelper;
    private Context context;

    public LoadItems(Context context)
    {
        this.context = context;
    }


    public List<TouristSpotModel> getAllList()
    {
        dbHelper = new DBHelper(context);
        allList = new ArrayList<>();

        Cursor cursor = dbHelper.getTouristSpot();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            int img_rid = cursor.getInt(3);
            String province = cursor.getString(4);
            String agency = cursor.getString(5);
            int booking_rid = cursor.getInt(6);

            allList.add(new TouristSpotModel(id, name, desc, img_rid, province, agency, booking_rid));
        }

        dbHelper.close();
        return allList;
    }

    public List<TouristSpotModel> getPalawanList()
    {
        dbHelper = new DBHelper(context);
        palawanList = new ArrayList<>();

        Cursor cursor = dbHelper.getTouristSpot(PALAWAN);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            int img_rid = cursor.getInt(3);
            String province = cursor.getString(4);
            String agency = cursor.getString(5);
            int booking_rid = cursor.getInt(6);

            palawanList.add(new TouristSpotModel(id, name, desc, img_rid, province, agency, booking_rid));
        }

        dbHelper.close();
        return palawanList;
    }


    public List<TouristSpotModel> getCebuList()
    {
        dbHelper = new DBHelper(context);
        cebuList = new ArrayList<>();

        Cursor cursor = dbHelper.getTouristSpot(CEBU);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            int img_rid = cursor.getInt(3);
            String province = cursor.getString(4);
            String agency = cursor.getString(5);
            int booking_rid = cursor.getInt(6);

            cebuList.add(new TouristSpotModel(id, name, desc, img_rid, province, agency, booking_rid));
        }

        dbHelper.close();
        return cebuList;
    }



    public List<TouristSpotModel> getBoholList()
    {
        dbHelper = new DBHelper(context);
        boholList = new ArrayList<>();

        Cursor cursor = dbHelper.getTouristSpot(BOHOL);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            int img_rid = cursor.getInt(3);
            String province = cursor.getString(4);
            String agency = cursor.getString(5);
            int booking_rid = cursor.getInt(6);

            boholList.add(new TouristSpotModel(id, name, desc, img_rid, province, agency, booking_rid));
        }

        dbHelper.close();
        return boholList;
    }


    public List<TouristSpotModel> getIlocosNorteList()
    {
        dbHelper = new DBHelper(context);
        ilocosNorteList = new ArrayList<>();

        Cursor cursor = dbHelper.getTouristSpot(ILOCOS_NORTE);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            int img_rid = cursor.getInt(3);
            String province = cursor.getString(4);
            String agency = cursor.getString(5);
            int booking_rid = cursor.getInt(6);

            ilocosNorteList.add(new TouristSpotModel(id, name, desc, img_rid, province, agency, booking_rid));
        }

        dbHelper.close();
        return ilocosNorteList;
    }

    public List<TouristSpotModel> getDavaoList()
    {
        dbHelper = new DBHelper(context);
        davaoList = new ArrayList<>();

        Cursor cursor = dbHelper.getTouristSpot(DAVAO);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            int img_rid = cursor.getInt(3);
            String province = cursor.getString(4);
            String agency = cursor.getString(5);
            int booking_rid = cursor.getInt(6);


            davaoList.add(new TouristSpotModel(id, name, desc, img_rid, province, agency, booking_rid));
        }

        dbHelper.close();
        return davaoList;
    }


    private List<ProvinceModel> provinceModelList;
    private List<AgencyModel> agencyModelList;



    public List<ProvinceModel> getProvinceModelList()
    {
        provinceModelList = new ArrayList<>();

        provinceModelList.add(new ProvinceModel(R.drawable.palawan_img_2, PALAWAN));
        provinceModelList.add(new ProvinceModel(R.drawable.cebu_img_3, CEBU));
        provinceModelList.add(new ProvinceModel(R.drawable.bohol_img_2, BOHOL));
        provinceModelList.add(new ProvinceModel(R.drawable.ilocos_img_3, ILOCOS_NORTE));
        provinceModelList.add(new ProvinceModel(R.drawable.davao_img_2, DAVAO));


        return provinceModelList;
    }

    public List<AgencyModel> getAgencyModelList()
    {
        agencyModelList = new ArrayList<>();

        agencyModelList.add(new AgencyModel("Traveloka Philippines", "+63 2 246 9057", "16th Floor, Marco Polo Ortigas, Meralco Avenue, Ortigas Center, Pasig City, Metro Manila, Philippines",
                "cs@traveloka.com", new String[]{PALAWAN, DAVAO}));

        agencyModelList.add(new AgencyModel("Cebu Pacific Air (Cebu Pacific Getaways", "+63 2 8702 0888", " 5th Floor, Cebu Pacific Building, Domestic Road, Pasay City, Metro Manila, Philippines",
                "cebgetaways@cebupacificair.com", new String[]{CEBU, BOHOL}));

        agencyModelList.add(new AgencyModel(" Klook Philippines", " +63 2 8231 2728", "18th Floor, Four/Five E-Com Center, Harbor Drive, Mall of Asia Complex, Pasay City, Metro Manila, Philippines",
                "support@klook.com", new String[]{ILOCOS_NORTE}));



        return agencyModelList;
    }







}
