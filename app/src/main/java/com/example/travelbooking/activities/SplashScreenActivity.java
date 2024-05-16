package com.example.travelbooking.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelbooking.DBHelper;
import com.example.travelbooking.R;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView iv_plane, iv_suitcase, iv_tire, iv_word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initValues();
        DBHelper dbHelper = new DBHelper(this);

        Cursor cursor = dbHelper.getTouristSpot();
        if(cursor.getCount() == 0)
        {
            dbHelper.embedTouristSpot();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_plane.setAnimation(AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.move_up));
                iv_plane.setVisibility(View.VISIBLE);


            }
        }, 1000);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_suitcase.setAnimation(AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.enter_left));
                iv_suitcase.setVisibility(View.VISIBLE);
            }
        }, 2000);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_tire.setAnimation(AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.enter_left));
                iv_tire.setVisibility(View.VISIBLE);
            }
        }, 2000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_word.setAnimation(AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.enter_left));
                iv_word.setVisibility(View.VISIBLE);
            }
        }, 2000);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_tire.setAnimation(AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.exit_right));
                iv_tire.setVisibility(View.VISIBLE);
            }
        }, 3000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_tire.setVisibility(View.INVISIBLE);
            }
        }, 4000);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_plane.setAnimation(AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.exit_up));
                iv_plane.setVisibility(View.VISIBLE);
            }
        }, 3000);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                iv_plane.setVisibility(View.INVISIBLE);
            }
        }, 4000);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_word.setAnimation(AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.exit_right));
                iv_word.setVisibility(View.VISIBLE);
            }
        }, 4000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_suitcase.setAnimation(AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.exit_right));
                iv_suitcase.setVisibility(View.VISIBLE);
            }
        }, 4000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_suitcase.setVisibility(View.INVISIBLE);
                iv_word.setVisibility(View.INVISIBLE);
            }
        }, 4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, 4200);
    }



    private void initValues()
    {
        iv_plane = findViewById(R.id.iv_plane);
        iv_suitcase = findViewById(R.id.iv_suitcase);
        iv_tire = findViewById(R.id.iv_tire);
        iv_word = findViewById(R.id.iv_word);
    }
}