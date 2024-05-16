package com.example.travelbooking.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelbooking.R;
import com.example.travelbooking.activities.BookingActivity;
import com.example.travelbooking.models.TouristSpotModel;
import com.example.travelbooking.static_models.TouristSpotStaticModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TouristSpotAdapter extends RecyclerView.Adapter<TouristSpotAdapter.MyViewHolder>
{

    private final static String PALAWAN = "Palawan",
            CEBU = "Cebu",
            BOHOL = "Bohol",
            ILOCOS_NORTE = "Ilocos Norte",
            DAVAO = "Davao";

    Context context;
    List<TouristSpotModel> modelList;
    AlertDialog.Builder builder_spot;
    AlertDialog alert_spot;
    View view_spot;
    int id;
    int img_rid;
    String province;
    ImageView iv_close, iv_spot;
    MaterialButton btn_book;
    static String hotel1, hotel2;

    public TouristSpotAdapter(Context context) {
        this.context = context;
    }

    public void setModelList(List<TouristSpotModel> modelList)
    {

        this.modelList = modelList;
    }

    public void setFilteredList(List<TouristSpotModel> modelList)
    {

        this.modelList = modelList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.rv_cube_layout, parent, false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int pos = position;


        TouristSpotModel touristSpot = modelList.get(pos);
        setHotel(touristSpot);

        id = modelList.get(pos).getId();
        String name = modelList.get(pos).getName();
        String desc = modelList.get(pos).getDescription();
        img_rid = modelList.get(pos).getImg_rid();
        province = modelList.get(pos).getProvince();
        String agency = modelList.get(pos).getAgency();

        holder.tv_id.setText(String.valueOf(id));
        holder.tv_name.setText(name);
//        holder.tv_desc.setText(desc);
        holder.iv.setImageResource(img_rid);
        holder.tv_province.setText(province);
        holder.tv_agency.setText(agency);


        holder.cv.setOnClickListener(click -> {
            Log.d("Debugging", "running : cardOnClick()");
            new TouristSpotStaticModel(id, name, desc, img_rid, province, agency, hotel1, hotel2);
            showTouristSpotDialog(touristSpot);
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        CardView cv;
        TextView tv_id, tv_name, tv_desc, tv_province, tv_book, tv_agency;
        ImageView iv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_spot_id);
            tv_name = itemView.findViewById(R.id.tv_spot_name);
            tv_desc = itemView.findViewById(R.id.tv_spot_description);
            tv_province = itemView.findViewById(R.id.tv_spot_province);
            tv_book = itemView.findViewById(R.id.tv_spot_book);
            tv_agency = itemView.findViewById(R.id.tv_spot_agency);

            cv = itemView.findViewById(R.id.cv_rv);

            iv = itemView.findViewById(R.id.iv_spot_img);
        }
    }

    private void showTouristSpotDialog(TouristSpotModel touristSpot)
    {
        Log.d("Debugging", "running : showTouristSpotDialog()");
        view_spot = LayoutInflater.from(context).inflate(R.layout.tourist_spot_dialog_layout, null);

        int imgRID = touristSpot.getImg_booking_rid();

        iv_close = view_spot.findViewById(R.id.iv_close);
        iv_spot = view_spot.findViewById(R.id.iv_spot);
        btn_book = view_spot.findViewById(R.id.btn_book_dialog);

        iv_spot.setImageResource(imgRID);

        iv_close.setOnClickListener(close -> {
            alert_spot.dismiss();
        });

        btn_book.setOnClickListener(book -> {
            Intent intent = new Intent(context, BookingActivity.class);
            context.startActivity(intent);
        });

        builder_spot = new AlertDialog.Builder(context).setView(view_spot);

        alert_spot = builder_spot.create();

        alert_spot.show();
    }

    private void setHotel(TouristSpotModel touristSpotModel)
    {
        String province = touristSpotModel.getProvince();
        switch(province)
        {
            case CEBU:
                hotel1 = "Bai Hotel Cebu";
                hotel2 = "Hop Inn Hotel Cebu City";
                break;
            case BOHOL:
                hotel1 = "Amorita Resort";
                hotel2 = "Henann Resort Alona Beach";
                break;
            case PALAWAN:
                hotel1 = "Two Seasons Coron Island Resort & Spa";
                hotel2 = "El Nido Resorts Lagen Island";
                break;
            case ILOCOS_NORTE:
                hotel1 = "Fort Ilocandia Resort Hotel";
                hotel2 = "Plaza del Norte Hotel and Convention Center";
                break;
            case DAVAO:
                hotel1 = "Marco Polo Davao";
                hotel2 = "Waterfront Insular Hotel Davao";
                break;
            default:
                break;

        }
    }




}
