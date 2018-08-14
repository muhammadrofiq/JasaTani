package com.example.root.g_track;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.root.g_track.Adapter.SectionsPageAdapter;
import com.example.root.g_track.Fragment.Fragment_tab1;
import com.example.root.g_track.Fragment.Fragment_tab2;
import com.example.root.g_track.Fragment.Fragment_tab3;
import com.example.root.g_track.Fragment.Fragment_tab4;

public class Main_activity extends AppCompatActivity {

    private static final String TAG = "Main activity";
    private SectionsPageAdapter sectionsPageAdapter;
    public static Activity activity;
    public static ProgressDialog nDialog;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    // declare icon
    private int[] tabIcons = {
            R.drawable.ic_home_white,
            R.drawable.ic_assignment_white,
            R.drawable.historywhite,
            R.drawable.ic_person_white
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        activity = this;
        Log.e(TAG, "onCreate: Starting.");
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());




        //declare loading
        Bundle bundle = getIntent().getExtras();
        nDialog = new ProgressDialog(this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Get Data");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);



        //set window color
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));



        viewPager = findViewById(R.id.maincontainer);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.maintabs);


        tabLayout.setupWithViewPager(viewPager);

        //set icon
        setupTabIcons();

        try {
            if (bundle.getString("sumber").equals("edit")){
                TabLayout.Tab tab = tabLayout.getTabAt(3);
                tab.select();
                Log.e("MAIN MASUK", " ");
            }
        } catch (Exception e) {
            Log.e("MAIN ga MASUK", e.getMessage());
        }
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_tab1(), "TAB1");
        adapter.addFragment(new Fragment_tab2(), "TAB2");
        adapter.addFragment(new Fragment_tab3(), "TAB3");
        adapter.addFragment(new Fragment_tab4(), "TAB4");
        viewPager.setAdapter(adapter);
    }

}
