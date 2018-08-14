package com.example.root.g_track;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.g_track.Model.Alatgetmodel;
import com.example.root.g_track.Model.Alatmodel;
import com.example.root.g_track.Model.Usermodel;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.root.g_track.R.id.blocking;
import static com.example.root.g_track.R.id.login;
import static com.example.root.g_track.R.id.map;
import static com.example.root.g_track.R.id.profileinput_alamat;

public class Profileinput extends FragmentActivity implements OnMapReadyCallback {


    private FirebaseAuth firebaseAuth;
    public DatabaseReference ref;

    private static GoogleMap mMap;
    private static Marker marker =  null;
    private Fragment map;
    private Context context;
    public static TextView alamat;
    public static LatLng cam;
    private CardView selesai;
    private FirebaseUser userfirebase;

    private EditText namalengkap;
    private MaterialEditText nickname;
    private MaterialEditText namausaha;
    private MaterialEditText tahun , kontak;
    private Bundle param;
    private Intent intent;


    private String sumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileinput);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        context = this;
        param = getIntent().getExtras();
        try {
            sumber = param.getString("sumber");
            intent = new Intent(this, Main_activity.class);
            intent.putExtra("sumber", "edit");
        }catch (Exception e){

        }

        firebaseAuth = FirebaseAuth.getInstance();
        userfirebase = firebaseAuth.getCurrentUser();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_pi);
        mapFragment.getMapAsync(this);
        // get element
        namalengkap = findViewById(R.id.profileinput_namalengkap);
        nickname = findViewById(R.id.profileinput_nickname);
        namausaha = findViewById(R.id.profileinput_namausaha);
        tahun = findViewById(R.id.profileinput_tahunterbentuk);
        kontak = findViewById(R.id.profileinput_kontak);

        alamat = findViewById(R.id.profileinput_alamat);
        selesai = findViewById(R.id.profileinput_selesai);

        if (param.getString("sumber","a").equals("edit")){
            namalengkap.setText(param.getString("nama"));
            nickname.setText(param.getString("nickname"));
            namausaha.setText(param.getString("namausaha"));
            tahun.setText(param.getString("tahun"));
            alamat.setText(param.getString("alamat"));
            kontak.setText(param.getString("kontak", "00000"));
        }

        //set element
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Click masuk", Toast.LENGTH_SHORT).show();

                pushdata();
            }
        });


    }

    public void pushdata() {
        Usermodel user = new Usermodel();
        String namaLengkap = namalengkap.getText().toString();
        String nickName = nickname.getText().toString();
        String namaUsaha = namausaha.getText().toString();
        String kontaks = kontak.getText().toString();

        if (TextUtils.isEmpty(namaLengkap)) {
            Toast.makeText(this, "Periksa kembali Nama Lengkap", Toast.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(kontaks)) {
            Toast.makeText(this, "Periksa kembali Kontak", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(nickName)) {
            Toast.makeText(this, "Periksa kembali Nick Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(namaUsaha)) {
            Toast.makeText(this, "Periksa kembali Nama usaha", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tahun.getText().toString())) {
            Toast.makeText(this, "Periksa kembali Tahun terbentuk", Toast.LENGTH_SHORT).show();
            return;
        }
        if (marker == null) {
            Toast.makeText(this, "Periksa kembali Alamat", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer tahuns = Integer.parseInt(tahun.getText().toString());

        user.setAlamat(alamat.getText().toString());
        user.setNama(namaLengkap);
        user.setNickname(nickName);
        user.setNamausaha(namaUsaha);
        user.setTahun(tahuns);
        user.setKontak(kontaks);
        user.setLats(Float.parseFloat(cam.latitude + ""));
        user.setLongs(Float.parseFloat(cam.longitude + ""));

        Map datauser = new HashMap();
        datauser.put("namalengkap", user.getNama());
        datauser.put("nickname", user.getNickname());
        datauser.put("namausaha", user.getNamausaha());
        datauser.put("tahun", user.getTahun());
        datauser.put("alamat", user.getAlamat());
        datauser.put("longitude", user.getLongs());
        datauser.put("latitude", user.getLats());
        datauser.put("kontak",user.getKontak());

        FirebaseDatabase.getInstance().getReference().child("user/" + userfirebase.getUid()).setValue(datauser);
        Toast.makeText(this, "Berhasil menyimpan data", Toast.LENGTH_LONG).show();

        if (param.getString("sumber").equals("register")) {
            Intent i = new Intent(this, Main_activity.class);
            startActivity(i);
        }

        if (sumber.equals("edit")){
            Main_activity.activity.finish();
            Log.e("profile sumber edit", " ");
            startActivity(intent);
        }
        finish();

    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()) {
                Log.e("Masuk", "USER ADA!!!!!!!! OUT");
            } else {
                Log.e("Masuk", "USER GA ADA!!! ");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    public static void updatecam() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cam, 10));
        if (marker == null) {

            Log.e("masuk g ada marker a", "ngeng");
            marker = mMap.addMarker(new MarkerOptions().position(cam));
        } else {
            Log.e("masuk ada marker a", "ngeng");
            marker.setPosition(cam);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-6.580168, 106.767662);

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
        if (param.getString("sumber","a").equals("edit")){
            Log.e("TEST PARAM", param.getString("nama")+" "+param.getString("lats")+" "+param.getString("longs"));
            LatLng positions = new LatLng(Float.valueOf(param.getString("lats")), Float.valueOf(param.getString("longs")));
            cam = positions;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positions, 10));
            if (marker == null) {

                Log.e("masuk g ada marker b", "ngeng");
                marker = mMap.addMarker(new MarkerOptions().position(positions));
            } else {

                Log.e("masuk ada marker b", "ngeng");
                marker.setPosition(positions);
            }
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                //Log.e("asdasd", point.latitude+" "+point.longitude);
                Intent i = new Intent(Profileinput.this, Profilemapspick.class);
                if (param.getString("sumber","a").equals("edit")){
                    i.putExtra("sumber", "edit");
                }
                startActivity(i);


            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
