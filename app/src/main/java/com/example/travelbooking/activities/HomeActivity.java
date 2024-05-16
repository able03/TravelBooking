package com.example.travelbooking.activities;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelbooking.R;
import com.example.travelbooking.fragments.BookingsHistoryFragment;
import com.example.travelbooking.fragments.HomeFragment;
import com.example.travelbooking.fragments.MyBookingsFragment;
import com.example.travelbooking.fragments.ProfileFragment;
import com.example.travelbooking.static_models.AccountStaticModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {


    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private TextView tv_name, tv_profile;
    private String fname, lname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        initValues();
        setNavDrawer();
        setFragment(new HomeFragment());
        toolbar.setTitle("Home");
        setProfile();
    }


    private void initValues()
    {

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.home);
        toolbar = findViewById(R.id.toolbar);



    }


    private void setNavDrawer()
    {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if(id == R.id.home_menu)
                {
                    setFragment(new HomeFragment());
                    toolbar.setTitle("Home");
                }
                else if(id == R.id.my_bookings_menu)
                {
                    setFragment(new MyBookingsFragment());
                    toolbar.setTitle("My Bookings");
                }
                else if(id == R.id.history_menu)
                {
                    setFragment(new BookingsHistoryFragment());
                    toolbar.setTitle("Booking History");
                }
                else if(id == R.id.logout_menu)
                {
                    finish();
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


                drawerLayout.close();
                return true;
            }
        });
    }


    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_homepage, fragment);
        fragmentTransaction.commit();
    }




    private void setProfile()
    {
        View view = navigationView.getHeaderView(0);

        tv_profile = view.findViewById(R.id.tv_profile);
        tv_name = view.findViewById(R.id.tv_profile_name);

        String fname = Character.toString(AccountStaticModel.getFname().charAt(0)).toUpperCase();
        String lname = Character.toString(AccountStaticModel.getLname().charAt(0)).toUpperCase();

        String name = fname+lname;

        int color = AccountStaticModel.getColor_rid();
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(300);
        drawable.setColor(color);
        Log.d("Debugging", AccountStaticModel.getColor_rid()+": color rid");
        tv_profile.setBackground(drawable);

        tv_name.setText(AccountStaticModel.getFname() + " " + AccountStaticModel.getLname());
        tv_profile.setText(name);



    }



}