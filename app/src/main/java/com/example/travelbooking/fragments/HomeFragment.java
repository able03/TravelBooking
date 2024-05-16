package com.example.travelbooking.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.travelbooking.DBHelper;
import com.example.travelbooking.FixedSpeedScroller;
import com.example.travelbooking.LoadItems;
import com.example.travelbooking.R;
import com.example.travelbooking.adapters.AgencyAdapter;
import com.example.travelbooking.adapters.ProvinceAdapter;
import com.example.travelbooking.adapters.TouristSpotAdapter;
import com.example.travelbooking.adapters.ViewPagerAdapter;
import com.example.travelbooking.models.AgencyModel;
import com.example.travelbooking.models.ProvinceModel;
import com.example.travelbooking.models.TouristSpotModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {


    private final static String
            ALL = "All",
            PALAWAN = "Palawan",
            CEBU = "Cebu",
            BOHOL = "Bohol",
            ILOCOS_NORTE = "Ilocos Norte",
            DAVAO = "Davao";

    int[] images = {R.drawable.palawan_img_1, R.drawable.cebu_img_1, R.drawable.bohol_img_1};

    ViewPager viewPager;
    ViewPagerAdapter adapter;
    RecyclerView recyclerView;
    TouristSpotAdapter touristSpotAdapter;
    List<TouristSpotModel> modelList;
    List<TouristSpotModel> touristSpotModelList;
    List<TouristSpotModel> touristSpotModelList_SUB;
    DBHelper dbHelper;
    LoadItems items;
    ChipGroup chipGroup, cg_search_by;
    SearchView searchView;
    AlertDialog.Builder builder_spot;
    AlertDialog alert_spot;
    View view_spot;
    Spinner spinner;
    String search_by_selected;

    String selection;
    String search_by[] = {"Province", "Agency", "Tourist Spot"};


    List<ProvinceModel> provinceList;
    List<ProvinceModel> provinceList_SUB;
    ProvinceAdapter provinceAdapter;



    List<AgencyModel> agencyModelList;
    List<AgencyModel> agencyModelList_SUB;
    AgencyAdapter agencyAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initValues();
        setViewPager();

        setData();
        populateRV();

        setSearch_by();

        setSearch();

        setChip();
    }



    private void initValues()
    {
        viewPager = getView().findViewById(R.id.view_pager);
        dbHelper = new DBHelper(getContext());
        recyclerView = getView().findViewById(R.id.rv_main);
        items = new LoadItems(getContext());
        chipGroup = getView().findViewById(R.id.cg_main);
        modelList = dbHelper.getAllTouristSpotList();
        searchView = getView().findViewById(R.id.sv_search_view);
        searchView.clearFocus();
        touristSpotModelList = items.getAllList();


        cg_search_by = getView().findViewById(R.id.cg_seacrh_by);

        provinceList = items.getProvinceModelList();
        agencyModelList = items.getAgencyModelList();
    }



    private void setSearch_by()
    {
        cg_search_by.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener()
        {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup chipGroup, @NonNull List<Integer> list)
            {
                for(Integer i : list)
                {
                    Chip chip = getView().findViewById(i);
                    selection = chip.getText().toString();
                }
            }
        });
    }


    private void setSearchView(String newText)
    {
        switch (selection)
        {
            case "Province":
                provinceList_SUB = new ArrayList<>();
                for(ProvinceModel province : provinceList)
                {
                    if(province.getName().toLowerCase().contains(newText.toLowerCase()))
                    {
                        provinceList_SUB.add(province);
                    }
                }

                provinceAdapter = new ProvinceAdapter(getContext());
                provinceAdapter.setFilteredList(provinceList_SUB);

                recyclerView.setAdapter(provinceAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                break;

            case "Tourist Spot":
                touristSpotModelList_SUB = new ArrayList<>();

                for(TouristSpotModel model : touristSpotModelList)
                {
                    if(model.getName().toLowerCase().contains(newText.toLowerCase()))
                    {
                        touristSpotModelList_SUB.add(model);
                    }
                }

                if(!touristSpotModelList_SUB.isEmpty())
                {
                    touristSpotAdapter.setFilteredList(touristSpotModelList_SUB);
                }



                break;

            case "Agency":

                agencyModelList_SUB = new ArrayList<>();
                for(AgencyModel agency : agencyModelList)
                {
                    if(agency.getName().toLowerCase().contains(newText.toLowerCase()))
                    {
                        agencyModelList_SUB.add(agency);
                    }
                }

                agencyAdapter = new AgencyAdapter(getContext());
                agencyAdapter.setFilteredList(agencyModelList_SUB);

                recyclerView.setAdapter(agencyAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                break;

            default:
                break;
        }
    }




    private void setSearch()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                setSearchView(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });
    }


    private void setChip()
    {
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup chipGroup, @NonNull List<Integer> list) {

                for(Integer i : list)
                {
                    Chip chip = getView().findViewById(i);
                    String chipTxt = chip.getText().toString();

                    switch (chipTxt)
                    {
                        case ALL:
                            modelList = items.getAllList();
                            setData();
                            populateRV();
                            break;

                        case PALAWAN:
                            modelList = items.getPalawanList();
                            setData();
                            populateRV();
                            break;

                        case CEBU:
                            modelList = items.getCebuList();
                            setData();
                            populateRV();
                            break;

                        case BOHOL:
                            modelList = items.getBoholList();
                            setData();
                            populateRV();
                            break;

                        case ILOCOS_NORTE:
                            modelList = items.getIlocosNorteList();
                            setData();
                            populateRV();
                            break;

                        case DAVAO:
                            modelList = items.getDavaoList();
                            setData();
                            populateRV();
                            break;

                        default:
                            break;

                    }
                }
            }
        });
    }

    private void setData()
    {
        touristSpotAdapter = new TouristSpotAdapter(getContext());
        touristSpotAdapter.setModelList(modelList);
    }

    private void populateRV()
    {
        recyclerView.setAdapter(touristSpotAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }


    private void setViewPager()
    {
        adapter = new ViewPagerAdapter(images, getContext());
        viewPager.setAdapter(adapter);


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable(){
                    @Override
                    public void run() {
                        viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%images.length);
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 3000, 7000);

        try {
            AccelerateInterpolator sInterpolator = new AccelerateInterpolator();
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), sInterpolator);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}