package com.example.travelbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelbooking.R;
import com.example.travelbooking.models.ProvinceModel;

import java.util.List;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ViewHolder>
{

    Context context;
    List<ProvinceModel> modelList;

    public ProvinceAdapter(Context context)
    {
        this.context = context;
    }

    public void setModelList(List<ProvinceModel> modelList)
    {
        this.modelList = modelList;
    }

    public void setFilteredList(List<ProvinceModel> modelList)
    {
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_province_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        int pos = position;

        int img_rid = modelList.get(pos).getImg_rid();
        String name = modelList.get(pos).getName();

        holder.iv.setImageResource(img_rid);
        holder.tv_name.setText(name);
    }

    @Override
    public int getItemCount()
    {
        return modelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv;
        TextView tv_name;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_province_rv);
            tv_name = itemView.findViewById(R.id.tv_province_name_rv);
        }
    }

}
