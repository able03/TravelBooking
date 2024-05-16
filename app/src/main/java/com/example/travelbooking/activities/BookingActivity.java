package com.example.travelbooking.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelbooking.R;
import com.example.travelbooking.static_models.TouristSpotStaticModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class BookingActivity extends AppCompatActivity {

    Chip hotel1, hotel2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initValues();
        setHotel();
    }
    private void initValues()
    {
        hotel1 = findViewById(R.id.hotel1);
        hotel2 = findViewById(R.id.hotel2);
    }

    private void setHotel()
    {
        hotel1.setText(TouristSpotStaticModel.getHotel1());
        hotel2.setText(TouristSpotStaticModel.getHotel2());
    }

}