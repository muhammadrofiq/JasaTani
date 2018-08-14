package com.example.root.g_track.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.root.g_track.Adapter.Adapter_ongoingorder;
import com.example.root.g_track.Main_activity;
import com.example.root.g_track.Model.Openorder_getmodel2;
import com.example.root.g_track.Model.Openorder_getmodel3;
import com.example.root.g_track.Model.Openorder_model;
import com.example.root.g_track.Model.Ordermodel;
import com.example.root.g_track.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Fragment_tab3 extends Fragment{
    private static final String TAG = "fragment_tab3";
    private RecyclerView recyclerView;
    private Adapter_ongoingorder adapter;
    private Activity activity;


    private List<Openorder_model> lisorder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab3,container,false);

        activity = Main_activity.activity;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        Query query = FirebaseDatabase.getInstance().getReference("endorder/"+user.getUid())
                .orderByChild("unixdate")
                .limitToLast(15);
        query.addValueEventListener(valueEventListener);


        recyclerView = view.findViewById(R.id.container_tab3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        lisorder = new ArrayList<>();
        adapter = new Adapter_ongoingorder(lisorder, activity);
        recyclerView.setAdapter(adapter);


        return view;

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            lisorder.clear();
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Openorder_getmodel3 openorder_getmodel = new Openorder_getmodel3();
                    openorder_getmodel = snapshot.getValue(Openorder_getmodel3.class);

                    Openorder_model ordermodel = new Openorder_model();

                    ordermodel.setAir(openorder_getmodel.air); //
                    ordermodel.setAlamats(openorder_getmodel.alamat);//
                    ordermodel.setHargatotal(openorder_getmodel.hargatotal);
                    ordermodel.setKedalaman(openorder_getmodel.kedalaman);//
                    ordermodel.setKey(snapshot.getKey());
                    ordermodel.setKontak(openorder_getmodel.kontak);//
                    ordermodel.setId_alat(openorder_getmodel.id_alat);//
                    ordermodel.setId_user(openorder_getmodel.id_user);//
                    ordermodel.setTanggal(openorder_getmodel.tanggal);
                    ordermodel.setJenis(openorder_getmodel.jenis);//
                    ordermodel.setLongitude(openorder_getmodel.longitude);
                    ordermodel.setLatitude(openorder_getmodel.latitude);
                    ordermodel.setUnixdate(openorder_getmodel.unixdate);
                    ordermodel.setLuas(openorder_getmodel.luas);
                    ordermodel.setLokasi(openorder_getmodel.lokasi);//
                    ordermodel.setNama(openorder_getmodel.nama);//
                    ordermodel.setNama_alat(openorder_getmodel.nama_alat);//
                    ordermodel.setAkses(openorder_getmodel.akses);
                    ordermodel.setStatus(openorder_getmodel.status);



                    lisorder.add(ordermodel);

                }
                Collections.reverse(lisorder);
                adapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
