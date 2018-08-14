package com.example.root.g_track.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.g_track.Adapter.Adapter_orderhome;
import com.example.root.g_track.Garasi;
import com.example.root.g_track.Login;
import com.example.root.g_track.Main_activity;
import com.example.root.g_track.Model.Alatgetmodel;
import com.example.root.g_track.Model.Alatmodel;
import com.example.root.g_track.Model.Openorder_getmodel;
import com.example.root.g_track.Model.Openorder_getmodel2;
import com.example.root.g_track.Model.Openorder_model;
import com.example.root.g_track.Model.Ordermodel;
import com.example.root.g_track.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Fragment_tab1 extends Fragment{
    private static final String TAG = "fragment_tab1";
    private CardView Garasi;
    private CardView Jadwal;


    private RecyclerView recyclerViewaorderhome;
    public static List<Openorder_model> listorder;
    private Adapter_orderhome adapter_orderhome;

    public FirebaseDatabase database;
    public DatabaseReference ref;

    private TextView text_disini_order;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab1,container,false);
        Garasi = view.findViewById(R.id.trig_garasi);
        Jadwal = view.findViewById(R.id.tab1_jadwal);
        text_disini_order = view.findViewById(R.id.text_tab1);

        //declare firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //get database

        /*
        //KOTRETAN
        long unixTimenow = System.currentTimeMillis() ;

        String testanggal = "17/7/2018";
        Date simple = new Date();
        SimpleDateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
        Long startunix = null;
        try {
            simple = temp.parse(testanggal);
            startunix = simple.getTime() ;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Date test = new java.util.Date(unixTime+(7*24*60*60*1000));

        Long endunix = startunix + 7 *60*60*24;
        Date awal = new java.util.Date(timestamp*1000);
        Date akhir = new java.util.Date(timestamp*1000 + (7*24*60*60*1000));
        Log.e("Tanggal compareisona", test+"");
        */


        /*
        //untuk test ngambil date
        String date = "29/7/2018";
        String dates = "31/7/2018";
        Date date1 = new Date();
        Date date2 = new Date();
        try {
            date1=new SimpleDateFormat("dd/MM/yyyy").parse(date);
            date2=new SimpleDateFormat("dd/MM/yyyy").parse(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Log.e("WAKTU", String.valueOf(date1) +" "+ String.valueOf(date2));
        Log.e("WAKTU", date1 +" "+ date2);
        */
        Calendar cal = Calendar.getInstance();
        Date datenow = new Date();
        try {
            datenow=new SimpleDateFormat("dd/MM/yyyy").parse(cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Long unixnow = datenow.getTime();
        Long unixnowplus7 = unixnow +(7*24*60*60*1000);
        Date test = new java.util.Date(unixnowplus7);

        database = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("open_order");
        Query query = FirebaseDatabase.getInstance().getReference("ongoingorder/"+user.getUid())
                .orderByChild("unixdate").limitToFirst(15).startAt(unixnow).endAt(unixnowplus7);
        query.addValueEventListener(valueEventListener);
        Log.e("Masuk TAB 1", unixnow+"  "+unixnowplus7+"  "+ test);


        // setclicker
        Garasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), com.example.root.g_track.Garasi.class);
                i.putExtra("sumber","garasi");
                startActivity(i);
                //Main_activity.activity.finish();
            }
        });

        Jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // ini ke tambah order
                Intent i = new Intent(getContext(), com.example.root.g_track.Tambahorder.class);
                startActivity(i);
                //

            }
        });


        //get element
        recyclerViewaorderhome = view.findViewById(R.id.listorderhome);
        recyclerViewaorderhome.setHasFixedSize(true);
        recyclerViewaorderhome.setLayoutManager(new LinearLayoutManager(view.getContext()));

        listorder = new ArrayList<>();
        adapter_orderhome = new Adapter_orderhome(listorder, getActivity());
        recyclerViewaorderhome.setAdapter(adapter_orderhome);


        return view;

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            listorder.clear();

            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Openorder_getmodel2 openorder_getmodel = new Openorder_getmodel2();
                    openorder_getmodel = snapshot.getValue(Openorder_getmodel2.class);
                    Log.e("Masuk loop tab1", openorder_getmodel.id_alat+" "+openorder_getmodel.id_user+" "
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

                    listorder.add(ordermodel);

                }
                text_disini_order.setVisibility(View.GONE);
                adapter_orderhome.notifyDataSetChanged();
            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
