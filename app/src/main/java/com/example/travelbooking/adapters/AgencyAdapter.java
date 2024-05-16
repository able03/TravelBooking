package com.example.travelbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelbooking.R;
import com.example.travelbooking.models.AgencyModel;

import java.util.List;

public class AgencyAdapter extends RecyclerView.Adapter<AgencyAdapter.ViewHolder>
{

    Context context;
    List<AgencyModel> modelList;

    public AgencyAdapter(Context context)
    {
        this.context = context;
    }

    public void setModelList(List<AgencyModel> modelList)
    {
        this.modelList = modelList;
    }


    public void setFilteredList(List<AgencyModel> modelList)
    {
        this.modelList = modelList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view = LayoutInflater.from(context).inflate(R.layout.rv_agency_layout, parent, false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        int pos = position;
        holder.tv_name.setText(modelList.get(pos).getName());
        holder.tv_contact.setText(modelList.get(pos).getContact());
        holder.tv_address.setText(modelList.get(pos).getAddress());
        holder.tv_email.setText(modelList.get(pos).getEmail());

        String[] provinces = modelList.get(pos).getProvinces();
        StringBuilder sb = new StringBuilder();
        for(String province : provinces)
        {
            sb.append(province+"/n");
        }

        holder.tv_provinces.setText(sb.toString());
    }

    @Override
    public int getItemCount()
    {
        return modelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_name, tv_contact, tv_address, tv_email, tv_provinces;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_agency_name);
            tv_contact = itemView.findViewById(R.id.tv_agency_contact);
            tv_address = itemView.findViewById(R.id.tv_agency_address);
            tv_email = itemView.findViewById(R.id.tv_agency_email);
            tv_provinces = itemView.findViewById(R.id.tv_agency_provinces);
        }
    }

}
