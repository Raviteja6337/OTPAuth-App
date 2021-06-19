package com.example.otpauth;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.otpauth.ui.main.SectionsPagerAdapter;
import com.example.otpauth.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "ContactsDB";

    private ActivityMainBinding binding;
    public DataBaseHandler dbHandler;
    public static Activity activityCtx;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityCtx = this;
        // Adding JSON to the SQLiteDataBase
        try {
            this.deleteDatabase(DATABASE_NAME);
            addJSONDataToDB();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                Fragment fragmentRem = sectionsPagerAdapter.getItem(tab.getPosition());
//                getSupportFragmentManager().beginTransaction().remove(fragmentRem).commit();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hello ChatBot Message", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void addJSONDataToDB() throws JSONException {
        dbHandler = new DataBaseHandler(this);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("mobno","7989054182");
//        jsonObject.put("contactname","RaviTeja");
//
//        dbHandler.addJson(jsonObject);

        String strJson = loadJSONFromAsset();
        JSONArray jsonArray = new JSONArray(strJson);
        for (int i=0; i<jsonArray.length();i++)
        {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            dbHandler.addJson(jsonObject1);
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("ContactsInfo.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.deleteDatabase(DATABASE_NAME);
    }
}