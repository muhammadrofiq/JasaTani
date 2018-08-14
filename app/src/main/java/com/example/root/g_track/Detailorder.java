package com.example.root.g_track;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.FragmentActivity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.g_track.Fragment.Fragment_tab1;
import com.example.root.g_track.Fragment.Secondfragment_1;
import com.example.root.g_track.Fragment.Secondfragment_2;
import com.example.root.g_track.Model.Openorder_model;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Detailorder extends FragmentActivity implements OnMapReadyCallback {

    private LinearLayout terimatolak, banner;
    private static GoogleMap mMap;
    private Bundle extras;
    private TextView nama_alat,id_alat, nama, kontak, luas, tangaal, alamat, kedalaman,lokasi,air, akses, harga;
    private CardView selesai, terima, tolak;
    private FirebaseUser user;

    private ImageView call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailorder);

        extras = getIntent().getExtras();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //dissmis dialog in home
        Main_activity.nDialog.dismiss();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //getlayout
        call = findViewById(R.id.detail_call);
        banner = findViewById(R.id.detail_banner);
        terimatolak = findViewById(R.id.detail_container_terimatolak);
        nama_alat = findViewById(R.id.detail_nama_alat);
        id_alat = findViewById(R.id.detail_alat_id);
        nama = findViewById(R.id.detail_nama_pengorder);
        kontak = findViewById(R.id.detail_no_hp_order);
        luas = findViewById(R.id.detail_luas_lahan);
        tangaal = findViewById(R.id.detail_datepicker_trig2);
        alamat = findViewById(R.id.detail_order_lokasi);
        kedalaman = findViewById(R.id.detail_kedalaman);
        lokasi = findViewById(R.id.detail_lokasi_kondisi);
        air = findViewById(R.id.detail_air);
        akses  = findViewById(R.id.detail_akses);
        harga = findViewById(R.id.detail_order_harga);
        selesai = findViewById(R.id.selesai_order_trig);
        terima = findViewById(R.id.terima_order_trig);
        tolak = findViewById(R.id.tolak_order_trig);



        //set visibility button berdasarkan sumber
        if( extras.getString("status")==null || (extras.getString("status")!=null && extras.getString("status").equals("selesai"))){
            terimatolak.setVisibility(View.GONE);
        }else{
            terima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                createDialogListenerterima(Detailorder.this);
                }
            });
            tolak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogListenertolak(Detailorder.this);
                }
            });


            //call.setImageResource(R.drawable.ic_cancel);
        }
        if(extras.getString("status") != null || extras.getString("status", "gaada").equals("selesai")){
            selesai.setVisibility(View.GONE);
        }

        nama_alat.setText(extras.getString("nama_alat"));
        id_alat.setText(extras.getString("id_alat"));
        nama.setText(extras.getString("nama"));
        kontak.setText(extras.getString("kontak"));
        luas.setText(extras.getString("luas"));
        tangaal.setText(extras.getString("tanggal"));
        alamat.setText(extras.getString("alamat"));
        kedalaman.setText(extras.getString("kedalaman"));
        lokasi.setText(extras.getString("lokasi"));
        air.setText(extras.getString("air"));
        akses.setText(extras.getString("akses"));
        harga.setText(extras.getString("harga_total"));


        Date date1 = new Date();
        try {
            date1=new SimpleDateFormat("dd/MM/yyyy").parse(extras.getString("tanggal"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Date finalDate = date1;
        tangaal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, "Order atas nama: "+extras.getString("nama"));
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        finalDate.getTime());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        (finalDate.getTime()+(1*24*60*60*1000)));
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, extras.getString("latitude")+" , "+extras.getString("longitude"));
                intent.putExtra(CalendarContract.Events.ALL_DAY, true);// periodicity
                intent.putExtra(CalendarContract.Events.DESCRIPTION,extras.getString("alamat")+"");
                startActivity(intent);
            }
        });

        //Log.e("DELETE", "ongoingorder/"+user.getUid()+"/"+extras.getString("id_order"));

        call.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + extras.getString("kontak")));
                startActivity(intent);
            }
        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogListener(Detailorder.this);
            }
        });

        if(extras.getString("jenis").equals("Panen")){
            banner.setBackgroundColor(Color.parseColor(getResources().getString(R.string.color_banner_panen)));
        } else if(extras.getString("jenis").equals("Roda dua") || extras.getString("jenis").equals("Roda empat")){
            banner.setBackgroundColor(Color.parseColor(getResources().getString(R.string.color_banner_truck)));
        } else if(extras.getString("jenis").equals("Tanam")){
            banner.setBackgroundColor(Color.parseColor(getResources().getString(R.string.color_banner_tanam)));
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.getUiSettings().setScrollGesturesEnabled(false);


            //Log.e("MASUK", extras.getString("latitude" , "kosong ")+" "+extras.getString("longitude"));
            // Add a marker in Sydney and move the camera
            LatLng lokasi_order = new LatLng(Float.parseFloat(extras.getString("latitude")), Float.parseFloat(extras.getString("longitude")));
            mMap.addMarker(new MarkerOptions().position(lokasi_order).title("Lokasi order"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasi_order,10));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+extras.getString("latitude")+","+extras.getString("longitude")+"");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);


            }
        });


    }

    public void createDialogListener(final Context contexts){
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:


                        Date currentTime = Calendar.getInstance().getTime();

                        Map dataorder = new HashMap();
                        dataorder.put("id_user",user.getUid());
                        dataorder.put("id_alat",extras.getString("id_alat"));
                        dataorder.put("nama_alat",extras.getString("nama_alat"));
                        dataorder.put("jenis",extras.getString("jenis"));
                        dataorder.put("latitude", Float.parseFloat(extras.getString("latitude")));
                        dataorder.put("longitude", Float.parseFloat(extras.getString("longitude")));
                        dataorder.put("alamat", alamat.getText().toString());
                        dataorder.put("luas",Float.valueOf(extras.getString("luas")));
                        dataorder.put("tanggal",extras.getString("tanggal"));
                        dataorder.put("hargatotal",Integer.parseInt(extras.getString("harga_total")));
                        dataorder.put("nama",nama.getText().toString());
                        dataorder.put("kontak",kontak.getText().toString().trim());
                        dataorder.put("kedalaman",kedalaman.getText().toString());
                        dataorder.put("lokasi",lokasi.getText().toString());
                        dataorder.put("air",air.getText().toString());
                        dataorder.put("akses", akses.getText().toString());
                        dataorder.put("unixdate", currentTime.getTime());
                        dataorder.put("status", "selesai");

                        FirebaseDatabase.getInstance().getReference("ongoingorder/"+user.getUid()+"/"+extras.getString("id_order")).removeValue();
                        FirebaseDatabase.getInstance().getReference().child("endorder/"+user.getUid()).push().setValue(dataorder);

                        // kalo order sisa satu dan di delete
                        if (Secondfragment_2.listorder_secfrag2.size() == 1){
                            Main_activity.activity.finish();
                            Intent i = new Intent(Detailorder.this, Main_activity.class);
                            startActivity(i);

                        }
                        finish();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //Toast.makeText(contexts, "masuk no", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin menolak pesanan?").setPositiveButton("Ya", clickListener)
                .setNegativeButton("Tidak", clickListener).show();

    }

    public void createDialogListenerterima(final Context contexts){
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:


                        Date currentTime = Calendar.getInstance().getTime();

                        Map dataorder = new HashMap();
                        dataorder.put("id_user",user.getUid());
                        dataorder.put("id_alat",extras.getString("id_alat"));
                        dataorder.put("nama_alat",extras.getString("nama_alat"));
                        dataorder.put("jenis",extras.getString("jenis"));
                        dataorder.put("latitude", Float.parseFloat(extras.getString("latitude")));
                        dataorder.put("longitude", Float.parseFloat(extras.getString("longitude")));
                        dataorder.put("alamat", alamat.getText().toString());
                        dataorder.put("luas",Float.valueOf(extras.getString("luas")));
                        dataorder.put("tanggal",extras.getString("tanggal"));
                        dataorder.put("hargatotal",Integer.parseInt(extras.getString("harga_total")));
                        dataorder.put("nama",nama.getText().toString());
                        dataorder.put("kontak",kontak.getText().toString().trim());
                        dataorder.put("kedalaman",kedalaman.getText().toString());
                        dataorder.put("lokasi",lokasi.getText().toString());
                        dataorder.put("air",air.getText().toString());
                        dataorder.put("akses", akses.getText().toString());
                        dataorder.put("unixdate", currentTime.getTime());

                        FirebaseDatabase.getInstance().getReference("neworder/"+user.getUid()+"/"+extras.getString("id_order")).removeValue();
                        FirebaseDatabase.getInstance().getReference().child("ongoingorder/"+user.getUid()).push().setValue(dataorder);

                        // kalo order sisa satu dan di delete
                        if (Secondfragment_2.listorder_secfrag2.size() == 1 || Fragment_tab1.listorder.size() == 1){
                            Main_activity.activity.finish();
                            Intent i = new Intent(Detailorder.this, Main_activity.class);
                            startActivity(i);

                        }
                        finish();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //Toast.makeText(contexts, "masuk no", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah anda yakin?").setPositiveButton("Yes", clickListener)
                .setNegativeButton("No", clickListener).show();

    }

    public void createDialogListenertolak(final Context contexts) {
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:


                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        FirebaseDatabase.getInstance().getReference("neworder/" + user.getUid() + "/" + extras.getString("key")).removeValue();
                        Log.e("key", extras.getString("key"));
                        // kalo order sisa satu dan di delete
                        if (Secondfragment_1.listorder_frstfrag2.size() == 1) {
                            Main_activity.activity.finish();
                            Intent i = new Intent(Detailorder.this, Main_activity.class);
                            startActivity(i);

                        }
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //Toast.makeText(contexts, "masuk no", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah anda yakin?").setPositiveButton("Yes", clickListener)
                .setNegativeButton("No", clickListener).show();

    }
}
