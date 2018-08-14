package com.example.root.g_track;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.g_track.Adapter.Adapteralat;
import com.example.root.g_track.Model.Alatgetmodel;
import com.example.root.g_track.Model.Alatmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Garasi extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    private TextView Username;
    //private CardView Tombollogout;
    private FloatingActionButton addgarasibtn;
    private TextView tidakadaalat;

    private RecyclerView recyclerViewallitem;
    private Adapteralat adapter;

    private List<Alatmodel> listalat;
    public Context context;
    public String toast;

    public FirebaseDatabase database;
    public DatabaseReference ref;
    public static Activity garasiactivity;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garasi);
        //get user
        garasiactivity = this;
        context = this;
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        toast = user.getUid();
        //get element

        tidakadaalat = findViewById(R.id.text_tidakadaalat);
        addgarasibtn = findViewById(R.id.addbutton);
        Bundle extras = getIntent().getExtras();
        String test = "";
        test = extras.getString("sumber","");
        if(test.equals("order")){
            addgarasibtn.setVisibility(View.GONE);
        }
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        Username = header.findViewById(R.id.email_user_nav);

        //set element
        Username.setText(user.getEmail());
        addgarasibtn.setOnClickListener(this);

        //set list item
        recyclerViewallitem = findViewById(R.id.listalat);
        recyclerViewallitem.setHasFixedSize(true);
        recyclerViewallitem.setLayoutManager(new LinearLayoutManager(this));

        listalat = new ArrayList<>();
        adapter = new Adapteralat(listalat, this);
        recyclerViewallitem.setAdapter(adapter);
        /*
        Query query= FirebaseDatabase.getInstance().getReference("user")
                .orderByChild("Piduser")
                .equalTo(user.getUid());

        query.addListenerForSingleValueEvent(valueEventListener);
        */
        database = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("alat");
        Query query = FirebaseDatabase.getInstance().getReference("alat")
                .orderByChild("Piduser")
                .equalTo(user.getUid());
        query.addValueEventListener(valueEventListener);

        /*ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("ada1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Alatgetmodel getalat = new Alatgetmodel();
                    getalat = ds.getValue(Alatgetmodel.class);
                    Toast.makeText(context,"masuk sini", Toast.LENGTH_SHORT).show();
                    Log.e("ada", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            listalat.clear();
            Log.e("Masuk","masuk awal");
            if(dataSnapshot.exists()){
                tidakadaalat.setVisibility(View.GONE);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Alatgetmodel alatgetmodel = new Alatgetmodel();
                    alatgetmodel = snapshot.getValue(Alatgetmodel.class);
                    Alatmodel alatmodel = new Alatmodel();
                    alatmodel.setKey(snapshot.getKey());
                    alatmodel.setMerek(alatgetmodel.Merek);
                    alatmodel.setTipe(alatgetmodel.Tipe);
                    alatmodel.setDeskripsi(alatgetmodel.Deskripsi);
                    alatmodel.setHarga(alatgetmodel.Harga);
                    alatmodel.setImplemen(alatgetmodel.Implemen);
                    alatmodel.setTahun(alatgetmodel.Tahun);
                    alatmodel.setClassalat(alatgetmodel.ClassAlat);
                    alatmodel.setPiduser(alatgetmodel.Piduser);
                    listalat.add(alatmodel);
                    Log.e("Masuk", alatmodel.getMerek());
                    //Toast.makeText(context, alatgetmodel.ClassAlat, Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onClick(View v) {

        if(v== addgarasibtn){
            Intent i = new Intent(Garasi.this,Tambahgarasi.class);
            startActivity(i);
            //finish();
        }/*
        if(v==Username){
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_logout:
                //Toast.makeText(context,"Logout", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                Intent i = new Intent(Garasi.this,Login.class);
                startActivity(i);
                finish();
                break;
            case R.id.nav_home:
                Intent intent = new Intent(Garasi.this,Main_activity.class);
                startActivity(intent);
                break;
            case R.id.nav_garasi:
                Intent intents = new Intent(Garasi.this,Profileinput.class);
                startActivity(intents);
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}