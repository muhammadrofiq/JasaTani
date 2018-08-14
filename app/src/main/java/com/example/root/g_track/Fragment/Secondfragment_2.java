package com.example.root.g_track.Fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.g_track.Adapter.Adapter_ongoingorder;
import com.example.root.g_track.Main_activity;
import com.example.root.g_track.Model.Openorder_getmodel2;
import com.example.root.g_track.Model.Openorder_model;
import com.example.root.g_track.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Secondfragment_2 extends Fragment{
    private static final String TAG = "Secondfragment_2";


    public FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    public DatabaseReference ref;
    private Activity activity;
    private TextView text;


    private RecyclerView recyclerViewallitem;
    public static List<Openorder_model> listorder_secfrag2;
    private Adapter_ongoingorder adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.secondfragment_2,container,false);

        activity = Main_activity.activity;
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        ref = FirebaseDatabase.getInstance().getReference("ongoingorder");
        Query query = FirebaseDatabase.getInstance().getReference("ongoingorder/"+user.getUid())
                .orderByChild("unixdate")
                .limitToLast(15);
        query.addValueEventListener(valueEventListener);


        recyclerViewallitem = view.findViewById(R.id.list_ongoingorder);
        text = view.findViewById(R.id.text_secfrag2);
        recyclerViewallitem.setHasFixedSize(true);
        recyclerViewallitem.setLayoutManager(new LinearLayoutManager(activity));

        listorder_secfrag2 = new ArrayList<>();
        adapter = new Adapter_ongoingorder(listorder_secfrag2, activity);
        recyclerViewallitem.setAdapter(adapter);


        return view;

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            listorder_secfrag2.clear();
            Log.e("Masuk ","GET DATA firebase fragment 2");
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Openorder_getmodel2 openorder_getmodel = new Openorder_getmodel2();
                    openorder_getmodel = snapshot.getValue(Openorder_getmodel2.class);
                    Log.e("Masuk", openorder_getmodel.id_alat+" "+openorder_getmodel.id_user+" "
                            +openorder_getmodel.luas+" "+openorder_getmodel.nama+" "+openorder_getmodel.tanggal);

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



                    listorder_secfrag2.add(ordermodel);

                }
                text.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
