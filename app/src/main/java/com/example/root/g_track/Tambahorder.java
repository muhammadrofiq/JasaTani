package com.example.root.g_track;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.Calendar;
import java.util.regex.Pattern;


//

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//




public class Tambahorder extends FragmentActivity implements OnMapReadyCallback{
    private ImageView datepicktrig1;
    private TextView datepicktrgig2;
    private String tanggal;
    private static final int DIALOG_ID = 0;
    private int dptanggal,dpbulan,dptahun,totalharga;


    private static Marker marker;
    public static TextView alamat;
    public static LatLng cam;
    public static int Harga;
    public static EditText luaslahan;
    public static LinearLayout trigalat;
    public static TextView namaalat;
    public static TextView idalat;
    public static String jenis;
    public static boolean status_alat = false;
    public static boolean isStatus_maps = false;

    private static GoogleMap mMap;

    private Spinner kedalaman;
    private Spinner lokasi;
    private Spinner air;
    private Spinner akses;
    private TextView hargaorder;
    private CardView buat;
    private EditText nama_pengorder, kontak;
    private Float luasend;

    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahorder);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        datepicktrgig2 = findViewById(R.id.tambahorder_datepicker_trig2);
        datepicktrig1 = findViewById(R.id.tambahorder_datepicker_trig1);
        alamat = findViewById(R.id.order_lokasi);
        luaslahan = findViewById(R.id.luas_lahan);
        trigalat = findViewById(R.id.order_trigalatmain);
        namaalat = findViewById(R.id.order_nama_alat);
        idalat = findViewById(R.id.order_alat_id);
        kedalaman = findViewById(R.id.spinner_kedalaman);
        lokasi = findViewById(R.id.spinner_lokasi);
        air = findViewById(R.id.spinner_air);
        akses = findViewById(R.id.spinner_akses);
        hargaorder = findViewById(R.id.order_harga);
        buat = findViewById(R.id.buat_order_trig);
        nama_pengorder =findViewById(R.id.nama_pengorder);
        kontak = findViewById(R.id.no_hp_order);

        buat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushdata();
            }
        });

        String[] skedalaman = new String[]{"Dalam", "Dangkal"};
        String[] slokasi = new String[]{"Pinggir", "Tengah"};
        String[] sair = new String[]{"Ada", "Tidak"};
        String[] sakses = new String[]{"Jalan", "roda dua", "roda empat"};

        ArrayAdapter<String> aKedalaman = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, skedalaman);
        ArrayAdapter<String> aLokasi = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, slokasi);
        ArrayAdapter<String> aAir = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sair);
        ArrayAdapter<String> aAkses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sakses);

        kedalaman.setAdapter(aKedalaman);
        lokasi.setAdapter(aLokasi);
        air.setAdapter(aAir);
        akses.setAdapter(aAkses);
        //trigalat.setBackgroundColor(Color.parseColor("#2b2b2b"));
        //luaslahan.setFocusable(false);
        //luaslahan.setInputType(InputType.TYPE_CLASS_PHONE);

        luaslahan.setTag(luaslahan.getKeyListener());
        luaslahan.setKeyListener(null);
        luaslahan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Float nilai = s.replace()
                String a = s.toString();
                if(a.contains(",") ){
                    String[] b = a.split(Pattern.quote(","));
                    if(b.length >=2){
                        Float d = Float.valueOf(b[0]+"."+b[1]);
                        luasend = d;
                        totalharga = Math.round(d*Harga);
                        hargaorder.setText(totalharga+"");
                    }
                }else if (s.toString().length() >=1 ){
                    luasend = Float.valueOf(s.toString());
                    totalharga = Math.round(Float.valueOf(s.toString())*Harga);
                    hargaorder.setText(totalharga+"");
                }
            }
        });

        Calendar rightNow = Calendar.getInstance();
        dptanggal = rightNow.get(Calendar.DAY_OF_MONTH);
        dpbulan = rightNow.get(Calendar.MONTH);
        dptahun = rightNow.get(Calendar.YEAR);

        datepicktrig1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
        datepicktrgig2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
        trigalat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tambahorder.this, Garasi.class);
                i.putExtra("sumber","order");
                startActivity(i);
            }
        });

    }


    public void pushdata(){
        if (!status_alat){
            Snackbar.make(findViewById(R.id.luas_lahan), "Silahkan pilih alat", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(this, "Silahkan pilih alat", Toast.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(nama_pengorder.getText().toString().trim())){
            Snackbar.make(findViewById(R.id.luas_lahan), "Masukan nama", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(this, "Masukan nama", Toast.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(kontak.getText().toString().trim())){
            Snackbar.make(findViewById(R.id.luas_lahan), "Masukan Kontak", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(this, "Masukan Kontak", Toast.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(luaslahan.getText().toString().trim())){
            Snackbar.make(findViewById(R.id.luas_lahan), "Masukan luas lahan", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(this, "Masukan luas lahan", Toast.LENGTH_SHORT).show();
            return;
        }if (datepicktrgig2.getText().toString().trim().equals("Tanggal")){
            Snackbar.make(findViewById(R.id.luas_lahan), "Pilih tanggal", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(this, "Pilih tanggal", Toast.LENGTH_SHORT).show();
            return;
        }if (!isStatus_maps){
            Snackbar.make(findViewById(R.id.luas_lahan), "Pilih lokasi", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(this, "Silahkan pilih lokasi", Toast.LENGTH_SHORT).show();
            return;
        }
        Date dates = new Date();

        try {
            dates =new SimpleDateFormat("dd/MM/yyyy").parse(datepicktrgig2.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long unixdate = dates.getTime() ;

        Map dataorder = new HashMap();
        dataorder.put("id_user",user.getUid());
        dataorder.put("id_alat",idalat.getText().toString());
        dataorder.put("nama_alat",namaalat.getText().toString());
        dataorder.put("jenis",jenis);
        dataorder.put("latitude", cam.latitude);
        dataorder.put("longitude", cam.longitude);
        dataorder.put("alamat", alamat.getText().toString());
        dataorder.put("luas",luasend);
        dataorder.put("tanggal",datepicktrgig2.getText().toString());
        dataorder.put("hargatotal",totalharga);
        dataorder.put("nama",nama_pengorder.getText().toString());
        dataorder.put("kontak",kontak.getText().toString().trim());
        dataorder.put("kedalaman",kedalaman.getSelectedItem().toString());
        dataorder.put("lokasi",lokasi.getSelectedItem().toString());
        dataorder.put("air",air.getSelectedItem().toString());
        dataorder.put("akses", akses.getSelectedItem().toString());
        dataorder.put("unixdate",unixdate);
        //dataorder.put("status", 2);

        databaseReference.child("ongoingorder/"+user.getUid()).push().setValue(dataorder);
        Toast.makeText(this, "Berhasil menambah order", Toast.LENGTH_LONG).show();
        //Intent i = new Intent(Tambahorder.this,Main_activity.class);
        //startActivity(i);
        finish();



    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID){
            return new DatePickerDialog(this,datepickerlistener,dptahun,dpbulan,dptanggal);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datepickerlistener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dptahun = year;
            dpbulan = month+1;
            dptanggal = dayOfMonth;
            tanggal = dptanggal+"/"+dpbulan+"/"+dptahun;
            datepicktrgig2.setText(tanggal);
        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-6.580168, 106.767662);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                //Log.e("asdasd", point.latitude+" "+point.longitude);
                Intent i = new Intent(Tambahorder.this, Ordermapspick.class);
                startActivity(i);


            }
        });
    }

    public static void updatecam() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cam, 10));
        if (marker == null) {

            marker = mMap.addMarker(new MarkerOptions().position(cam));
        } else {
            marker.setPosition(cam);
        }
    }


}
