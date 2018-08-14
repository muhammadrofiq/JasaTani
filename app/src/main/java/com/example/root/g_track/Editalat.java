package com.example.root.g_track;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Editalat extends AppCompatActivity {
    private TextView jenis,merek,tipe,tahun;
    private EditText implemen,harga,deskripsi;
    private LinearLayout implemencont;
    private Bundle param;
    private CardView trig;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editalat);
        param = getIntent().getExtras();
        jenis = findViewById(R.id.class_edit);
        merek = findViewById(R.id.merek_edit);
        tipe = findViewById(R.id.tipe_edit);
        tahun = findViewById(R.id.tahun_edit);
        implemen = findViewById(R.id.implemen_edit);
        harga = findViewById(R.id.harga_edit);
        deskripsi = findViewById(R.id.deskripsi_edit);
        implemencont = findViewById(R.id.implemencont);
        trig = findViewById(R.id.edit_simpan);

        firebaseAuth = FirebaseAuth.getInstance();

        jenis.setText(param.getString("jenis"));
        merek.setText(param.getString("merek"));
        tipe.setText(param.getString("tipe"));
        tahun.setText(param.getString("tahun"));
        implemen.setText(param.getString("implemen"));
        harga.setText(param.getString("harga"));
        deskripsi.setText(param.getString("deskripsi"));
        if(param.getString("jenis").equals("Panen") || param.getString("jenis").equals("Tanam")){
            implemencont.setVisibility(View.GONE);
        }

        trig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushdata();
            }
        });
    }
    private void pushdata(){

        if (TextUtils.isEmpty(deskripsi.getText().toString().trim())){
            Toast.makeText(this, "Masukan deskripsi alat", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(harga.getText().toString().trim())){
            Toast.makeText(this, "Masukan harga alat", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(implemen.getText().toString().trim())){
            Toast.makeText(this, "Masukan Implemen alat", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        Map dataalat = new HashMap();
        dataalat.put("Implemen", implemen.getText().toString());
        dataalat.put("Harga", Integer.parseInt(harga.getText().toString()));
        dataalat.put("Deskripsi", deskripsi.getText().toString());
        dataalat.put("ClassAlat", jenis.getText().toString());
        dataalat.put("Merek", merek.getText().toString());
        dataalat.put("Tipe", tipe.getText().toString());
        dataalat.put("Tahun",Integer.parseInt(tahun.getText().toString()));
        dataalat.put("Piduser", user.getUid());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("alat/"+param.getString("key")).setValue(dataalat);
        finish();
    }
}
