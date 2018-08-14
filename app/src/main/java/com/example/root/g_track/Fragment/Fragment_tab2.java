package com.example.root.g_track.Fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.g_track.Adapter.SectionsPageAdapter;
import com.example.root.g_track.Adapter.SectionsPageAdapter2;
import com.example.root.g_track.Main_activity;
import com.example.root.g_track.R;

public class Fragment_tab2 extends Fragment{
    private static final String TAG = "fragment_tab2";
    private Activity activity;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab2,container,false);
        activity = Main_activity.activity;

        viewPager = view.findViewById(R.id.maincontainersecondtabs);
        setupViewPager(viewPager);

        tabLayout = view.findViewById(R.id.secondtabs);


        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabTextColors(Color.WHITE,Color.WHITE);
        tabLayout.getTabAt(0).setText("Pesanan baru");
        tabLayout.getTabAt(1).setText("Pesanan aktif");

        return view;

    }

    public void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter2 adapter = new SectionsPageAdapter2(getChildFragmentManager());
        adapter.addFragment(new Secondfragment_1(), "TAB1");
        adapter.addFragment(new Secondfragment_2(), "TAB2");
        viewPager.setAdapter(adapter);
    }
}
