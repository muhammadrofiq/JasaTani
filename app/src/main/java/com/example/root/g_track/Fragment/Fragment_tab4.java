package com.example.root.g_track.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.g_track.Garasi;
import com.example.root.g_track.Login;
import com.example.root.g_track.Main_activity;
import com.example.root.g_track.Model.Usergetmodel;
import com.example.root.g_track.Model.Usermodel;
import com.example.root.g_track.Profileinput;
import com.example.root.g_track.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.Button;

public class Fragment_tab4 extends Fragment{
    private static final String TAG = "fragment_tab4";
    private Usermodel usermodel;
    private TextView nama, nickname, namausaha,tahun, alamat, kontak;
    private Float longs,lats;
    private MapView mMapView;
    private CardView edit;
    private Button logout;
    private GoogleMap googleMap;
    private Marker marker;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private LatLng pos;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab4,container,false);

        mMapView = (MapView) view.findViewById(R.id.map_profile);
        mMapView.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                mMap.getUiSettings().setScrollGesturesEnabled(false);

                // For dropping a marker at a point on the Map
                //LatLng sydney = new LatLng(0, 0);

                // For zooming automatically to the location of the marker
            }
        });

        usermodel = new Usermodel();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("user")
                .orderByKey()
                .equalTo(user.getUid());
        query.addValueEventListener(valueEventListener);

        nama = view.findViewById(R.id.profile_namalengkap);
        nickname = view.findViewById(R.id.profile_nickname);
        namausaha = view.findViewById(R.id.profile_namausaha);
        tahun = view.findViewById(R.id.profile_tahunterbentuk);
        alamat = view.findViewById(R.id.profile_alamat);
        edit = view.findViewById(R.id.profile_edit);
        kontak = view.findViewById(R.id.profile_kontak);
        logout = view.findViewById(R.id.pprofile_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent i = new Intent(Main_activity.activity,Login.class);
                startActivity(i);
                Main_activity.activity.finish();
            }
        });





        return view;

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    if (snapshot.getKey().equals(user.getUid())){

                        Usergetmodel usergetmodel = new Usergetmodel();
                        usergetmodel = snapshot.getValue(Usergetmodel.class);
                        usermodel.setNama(usergetmodel.namalengkap);
                        usermodel.setTahun(usergetmodel.tahun);
                        usermodel.setLongs(usergetmodel.longitude);
                        usermodel.setLats(usergetmodel.latitude);
                        usermodel.setNamausaha(usergetmodel.namausaha);
                        usermodel.setNickname(usergetmodel.nickname);
                        usermodel.setAlamat(usergetmodel.alamat);
                        usermodel.setKontak(usergetmodel.kontak);



                    }
                }

            }

            pos = new LatLng(usermodel.getLats(),usermodel.getLongs());
            if(marker!= null){
                marker.setPosition(pos);

                CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }else{
                googleMap.addMarker(new MarkerOptions().position(pos).title("Lokasi saya"));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
            nama.setText(usermodel.getNama());
            nickname.setText(usermodel.getNickname());
            namausaha.setText(usermodel.getNamausaha());
            tahun.setText(usermodel.getTahun()+"");
            alamat.setText(usermodel.getAlamat());
            kontak.setText(usermodel.getKontak());

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Main_activity.activity, Profileinput.class);
                    i.putExtra("sumber","edit");
                    i.putExtra("nama", usermodel.getNama());
                    i.putExtra("nickname", usermodel.getNickname());
                    i.putExtra("namausaha",usermodel.getNamausaha());
                    i.putExtra("tahun", usermodel.getTahun()+"");
                    i.putExtra("alamat", usermodel.getAlamat());
                    i.putExtra("longs", usermodel.getLongs()+"");
                    i.putExtra("lats", usermodel.getLats()+"");
                    i.putExtra("kontak", usermodel.getKontak());
                    Log.e("FRAG 4 lempar", usermodel.getLats()+" "+ usermodel.getLongs());

                    startActivity(i);
                }
            });

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
