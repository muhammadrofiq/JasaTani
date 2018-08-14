package com.example.root.g_track;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.g_track.Model.Alatgetmodel;
import com.example.root.g_track.Model.Alatmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Detailalat extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    public Alatmodel alatmodel;

    //declare view
    public TextView detail_class;
    public TextView detail_merek;
    public TextView detail_tipe;
    public TextView detail_tahun;
    public TextView detail_implemen;
    public TextView detail_harga;
    public TextView detail_deskripsi;
    public ImageView detail_imageview;
    public CardView Tombol_hapus;
    public CardView Tombol_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_detailalat);
        //TextView idalat = findViewById(R.id.detail_id_alat);
        //idalat.setText(getIntent().getStringExtra("ID_ALAT"));
        Log.e("TAG",getIntent().getStringExtra("ID_ALAT") );

        //create dialog

        //get elemen
        alatmodel = new Alatmodel();
        detail_class = findViewById(R.id.class_detail);
        detail_merek = findViewById(R.id.merek_detail);
        detail_tipe = findViewById(R.id.tipe_detail);
        detail_tahun = findViewById(R.id.tahun_detail);
        detail_implemen = findViewById(R.id.implemen_detail);
        detail_harga = findViewById(R.id.harga_detail);
        detail_deskripsi = findViewById(R.id.deskripsi_detail);
        detail_imageview = findViewById(R.id.icon_tambahalat_detail);
        Tombol_hapus = findViewById(R.id.hapus_alat);
        Tombol_edit = findViewById(R.id.edit_alat);

        //get data
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("alat");
        Query query = ref.orderByKey().equalTo(getIntent().getStringExtra("ID_ALAT"));
        alatmodel.setKey(getIntent().getStringExtra("ID_ALAT"));
        query.addListenerForSingleValueEvent(valueEventListener);

        //set elemen
        Tombol_hapus.setOnClickListener(this);
        Tombol_edit.setOnClickListener(this);



    }

    public void setelemen(){
        detail_class.setText(alatmodel.getClassalat());
        detail_deskripsi.setText(alatmodel.getDeskripsi());
        detail_harga.setText(alatmodel.getHarga()+"");
        detail_implemen.setText(alatmodel.getImplemen());
        detail_tahun.setText(alatmodel.getTahun()+"");
        detail_tipe.setText(alatmodel.getTipe());
        detail_merek.setText(alatmodel.getMerek());

        if(alatmodel.getClassalat().equals("Panen")){
            detail_imageview.setImageResource(R.drawable.harvester);
        } else if(alatmodel.getClassalat().equals("Roda dua") || alatmodel.getClassalat().equals("Roda empat")){
            detail_imageview.setImageResource(R.drawable.tractor);
        } else if(alatmodel.getClassalat().equals("Tanam")){
            detail_imageview.setImageResource(R.drawable.planter);
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Alatgetmodel alatgetmodel = new Alatgetmodel();
                    alatgetmodel = snapshot.getValue(Alatgetmodel.class);
                    Log.e("TAGTAG",alatgetmodel.Merek);
                    alatmodel.setMerek(alatgetmodel.Merek);
                    alatmodel.setTipe(alatgetmodel.Tipe);
                    alatmodel.setDeskripsi(alatgetmodel.Deskripsi);
                    alatmodel.setHarga(alatgetmodel.Harga);
                    alatmodel.setImplemen(alatgetmodel.Implemen);
                    alatmodel.setTahun(alatgetmodel.Tahun);
                    alatmodel.setClassalat(alatgetmodel.ClassAlat);
                    alatmodel.setPiduser(alatgetmodel.Piduser);
                    Toast.makeText(context, alatgetmodel.Merek+" "+alatgetmodel.Tipe, Toast.LENGTH_SHORT).show();
                }
                setelemen();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hapus_alat:
                Toast.makeText(context, "masuk hapus", Toast.LENGTH_SHORT).show();
                // jika hapus di klik, muncul dialog
                createDialogListener(context);
                break;

            case R.id.edit_alat:
                //Toast.makeText(context, "masuk edit", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, Editalat.class);
                i.putExtra("jenis", detail_class.getText().toString());
                i.putExtra("merek", detail_merek.getText().toString());
                i.putExtra("tipe", detail_tipe.getText().toString());
                i.putExtra("tahun", detail_tahun.getText().toString());
                i.putExtra("implemen",detail_implemen.getText().toString());
                i.putExtra("harga",detail_harga.getText().toString());
                i.putExtra("deskripsi",detail_deskripsi.getText().toString());
                i.putExtra("key", alatmodel.getKey());
                startActivity(i);
                finish();
                break;

        }
    }

    public void createDialogListener(final Context contexts){
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(contexts, "alat/"+alatmodel.getKey(), Toast.LENGTH_SHORT).show();
                        Garasi.garasiactivity.finish();
                        FirebaseDatabase.getInstance().getReference("alat/"+alatmodel.getKey()).removeValue();
                        Intent i = new Intent(Detailalat.this, Garasi.class);
                        i.putExtra("sumber","garasi");
                        startActivity(i);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(contexts, "masuk no", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", clickListener)
                .setNegativeButton("No", clickListener).show();

    }
}




