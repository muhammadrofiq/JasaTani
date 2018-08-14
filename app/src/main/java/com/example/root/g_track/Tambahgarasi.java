package com.example.root.g_track;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.root.g_track.Model.Alatmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.SnackBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tambahgarasi extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText classalat;
    private EditText merek;
    private EditText tipe;
    private EditText tahun;
    private MaterialEditText implemen;
    private MaterialEditText harga;
    private MaterialEditText deskripsi;
    private Alatmodel alatmodel;
    private CardView trigtambah;
    private ImageView icon_tambahalat;
    private boolean implemenbol;

    private Spinner dropdownclass;
    private Spinner dropdownmerek;
    private Spinner dropdowntipe;
    private Spinner dropdowntahun;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public ArrayAdapter<String> adaptermerekpanen;
    public ArrayAdapter<String> adaptermerekroda2;
    public ArrayAdapter<String> adaptermerekroda4;
    public ArrayAdapter<String> adaptermerektanam;
    public ArrayAdapter<String> adaptermerek;

    public ArrayAdapter<String> adapterpanenyanmar;
    public ArrayAdapter<String> adapterpanenquick;
    public ArrayAdapter<String> adapterpanentanikaya;
    public ArrayAdapter<String> adapterpanenkubota;

    public ArrayAdapter<String> adapterroda2yanmar;
    public ArrayAdapter<String> adapterroda2quick;

    public ArrayAdapter<String> adapterroda4yanmar;
    public ArrayAdapter<String> adapterroda4quick;
    public ArrayAdapter<String> adapterroda4iseki;
    public ArrayAdapter<String> adapterroda4kubota;

    public ArrayAdapter<String> adaptertanamyanmar ;
    public ArrayAdapter<String> adaptertanamtanikaya ;
    public ArrayAdapter<String> adaptertanamiseki ;
    public ArrayAdapter<String> adaptertanamkubota ;

    public List<String> asd;

    public ArrayAdapter<String> adaptertahun;

    public int statusjenis;
    public int statusmerek;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahgarasi);

        implemenbol = false;



        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //get element
        dropdownclass = findViewById(R.id.dropdownclass);
        dropdownmerek = findViewById(R.id.dropdownmerek);
        dropdowntipe = findViewById(R.id.dropdowntipe);
        dropdowntahun = findViewById(R.id.dropdowntahun);
        icon_tambahalat = findViewById(R.id.icon_tambahalat);

        //classalat = findViewById(R.id.classtype);
        //merek = findViewById(R.id.merek);
        //tipe = findViewById(R.id.tipe);
        //tahun = findViewById(R.id.tahun);
        implemen= findViewById(R.id.implemen);
        harga = findViewById(R.id.harga);
        deskripsi = findViewById(R.id.deskripsi);
        trigtambah = findViewById(R.id.addgrsi);

        // pars ke model
        alatmodel = new Alatmodel();
        trigtambah.setOnClickListener(this);

        //create dropdown
        adaptermerek = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.classalat));

        adaptermerekpanen = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.merekpanen));
        adaptermerekroda2 = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.merekrodadua));
        adaptermerekroda4 = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.merekroda4));
        adaptermerektanam = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.merektanam));

        adapterpanenyanmar = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.panenyanmar));
        adapterpanenquick = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.panenquick));
        adapterpanentanikaya = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.panentanikaya));
        adapterpanenkubota = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.panenkubota));

        adapterroda2yanmar = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.roda2yanmar));
        adapterroda2quick = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.roda2quick));

        adapterroda4yanmar = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.roda4yanmar));
        adapterroda4quick = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.roda4quick));
        adapterroda4iseki = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.roda4iseki));
        //asd = new ArrayList<>();
        //asd.add("asd");
        adapterroda4kubota = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.roda4kubota));


        adaptertanamyanmar = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.tanamyanmar));
        adaptertanamtanikaya = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.tanamtanikaya));
        adaptertanamiseki = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.tanamiseki));
        adaptertanamkubota = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.tanamkubota));

        adaptertahun = new ArrayAdapter<String>(Tambahgarasi.this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.tahun));



        adaptermerek.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        adaptermerektanam.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adaptermerekroda4.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adaptermerekroda2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adaptermerekpanen.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        adapterpanenyanmar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterpanenquick.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterpanentanikaya.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterpanenkubota.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        adapterroda2quick.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterroda2yanmar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        adapterroda4yanmar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterroda4quick.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterroda4iseki.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterroda4kubota.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        adaptertanamkubota.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adaptertanamiseki.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adaptertanamtanikaya.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adaptertanamyanmar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        adaptertahun.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        dropdowntahun.setAdapter(adaptertahun);
        dropdownclass.setAdapter(adaptermerek);
        dropdownclass.setOnItemSelectedListener(this);
        dropdownmerek.setOnItemSelectedListener(this);



    }

    @Override
    public void onClick(View v) {
        if(v==trigtambah){
            Pushdata(alatmodel);
        }
    }

    public void Pushdata(Alatmodel alatmodel){
        /*
        if (TextUtils.isEmpty(classalat.getText().toString().trim())){
            Toast.makeText(this, "Masukan Kelas alat", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(merek.getText().toString().trim())){
            Toast.makeText(this, "Masukan Merek alat", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tipe.getText().toString().trim())){
            Toast.makeText(this, "Masukan Tipe alat", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tahun.getText().toString().trim())){
            Toast.makeText(this, "Masukan Tahun", Toast.LENGTH_SHORT).show();
            return;
            TextUtils.isEmpty(implemen.getText().toString().trim())
        }*/
        if (TextUtils.isEmpty(implemen.getText().toString().trim()) && !implemenbol){
            //Toast.makeText(this, "Masukan Implemen alat", Toast.LENGTH_SHORT).show();
            Snackbar.make(findViewById(R.id.dropdownclass), "Masukan Implemen alat", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(harga.getText().toString().trim())){
            //Toast.makeText(this, "Masukan Harga", Toast.LENGTH_SHORT).show();
            Snackbar.make(findViewById(R.id.dropdownclass), "Masukan Harga", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(deskripsi.getText().toString().trim())){
            //Toast.makeText(this, "Masukan deskripsi alat", Toast.LENGTH_SHORT).show();
            Snackbar.make(findViewById(R.id.dropdownclass), "Masukan deskripsi alat", Snackbar.LENGTH_LONG).show();
            return;
        }


        //alatmodel.setClassalat(classalat.getText().toString().trim());
        //alatmodel.setMerek(merek.getText().toString().trim());
        //alatmodel.setTipe(tipe.getText().toString().trim());
        String tipe = dropdowntipe.getSelectedItem().toString();
        alatmodel.setTipe(tipe);
        //alatmodel.setTahun(Integer.parseInt(tahun.getText().toString().trim()));

        alatmodel.setTahun(Integer.parseInt(dropdowntahun.getSelectedItem().toString()));

        alatmodel.setImplemen(implemen.getText().toString().trim());
        alatmodel.setHarga(Integer.parseInt(harga.getText().toString().trim()));
        alatmodel.setDeskripsi(deskripsi.getText().toString().trim());
        FirebaseUser user = firebaseAuth.getCurrentUser();



        Map dataalat = new HashMap();
        dataalat.put("ClassAlat", alatmodel.getClassalat());
        dataalat.put("Merek", alatmodel.getMerek());
        dataalat.put("Tipe", alatmodel.getTipe());
        dataalat.put("Tahun",alatmodel.getTahun());
        if(implemenbol){
            dataalat.put("Implemen", "none");
        }else{
            dataalat.put("Implemen", alatmodel.getImplemen());
        }
        dataalat.put("Harga", alatmodel.getHarga());
        dataalat.put("Deskripsi", alatmodel.getDeskripsi());
        dataalat.put("Piduser", user.getUid());

        databaseReference.child("alat").push().setValue(dataalat);

        Toast.makeText(this, "Berhasil menambah alat", Toast.LENGTH_LONG).show();
        Snackbar.make(findViewById(R.id.dropdownclass), "Berhasil menambah alat", Snackbar.LENGTH_LONG).show();
        Garasi.garasiactivity.finish();
        Intent i = new Intent(Tambahgarasi.this,Garasi.class);
        i.putExtra("sumber", "tambahgarasi");
        startActivity(i);
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String pos = ""+position ;
        switch (parent.getId()){
            case R.id.dropdownclass:

                if (position == 0){
                    icon_tambahalat.setImageResource(R.drawable.harvester);
                    dropdownmerek.setAdapter(adaptermerekpanen);
                    alatmodel.setClassalat("Panen");
                    implemen.setVisibility(View.GONE);
                    implemen.setText("none");
                    implemenbol = true;
                    statusjenis = 1;
                }else if (position == 1){
                    icon_tambahalat.setImageResource(R.drawable.tractor);
                    alatmodel.setClassalat("Roda dua");
                    dropdownmerek.setAdapter(adaptermerekroda2);
                    implemen.setText("");
                    implemen.setVisibility(View.VISIBLE);
                    implemenbol = false;
                    statusjenis = 2;
                }else if (position == 2){
                    icon_tambahalat.setImageResource(R.drawable.tractor);
                    alatmodel.setClassalat("Roda empat");
                    dropdownmerek.setAdapter(adaptermerekroda4);
                    implemen.setText("");
                    implemen.setVisibility(View.VISIBLE);
                    implemenbol = false;
                    statusjenis = 3;
                }else{
                    icon_tambahalat.setImageResource(R.drawable.planter);
                    alatmodel.setClassalat("Tanam");
                    dropdownmerek.setAdapter(adaptermerektanam);
                    implemen.setVisibility(View.GONE);
                    implemen.setText("none");
                    implemenbol = true;
                    statusjenis = 4;
                }
                //Toast.makeText(Tambahgarasi.this, statusjenis+" masuk ke 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dropdownmerek:

                if (position == 0 && statusjenis == 1){
                    dropdowntipe.setAdapter(adapterpanenyanmar);
                    alatmodel.setMerek("Yanmar");
                    statusmerek =1;
                }else if (position == 1 && statusjenis == 1){
                    alatmodel.setMerek("Quick");
                    dropdowntipe.setAdapter(adapterpanenquick);
                    statusmerek =2;
                }else if (position == 2 && statusjenis == 1){
                    alatmodel.setMerek("Tani kaya");
                    dropdowntipe.setAdapter(adapterpanentanikaya);
                    statusmerek =3;
                }else if (position == 3 && statusjenis == 1){
                    alatmodel.setMerek("Kubota");
                    dropdowntipe.setAdapter(adapterpanenkubota);
                    statusmerek =4;
                }

                else if (position == 0 && statusjenis == 2){
                    dropdowntipe.setAdapter(adapterroda2yanmar);
                    alatmodel.setMerek("Yanmar");
                    statusmerek =1;
                }else if (position == 1 && statusjenis == 2){
                    alatmodel.setMerek("Quick");
                    dropdowntipe.setAdapter(adapterroda2quick);
                    statusmerek =2;
                }

                else if (position == 0 && statusjenis == 3) {
                    dropdowntipe.setAdapter(adapterroda4yanmar);
                    alatmodel.setMerek("Yanmar");
                    statusmerek = 1;
                }else if (position == 1 && statusjenis == 3) {
                    dropdowntipe.setAdapter(adapterroda4quick);
                    alatmodel.setMerek("Quick");
                    statusmerek = 2;
                }else if (position == 2 && statusjenis == 3) {
                    dropdowntipe.setAdapter(adapterroda4iseki);
                    alatmodel.setMerek("Iseki");
                    statusmerek = 3;
                }else if (position == 3 && statusjenis == 3) {
                    dropdowntipe.setAdapter(adapterroda4kubota);
                    alatmodel.setMerek("Kubota");
                    statusmerek = 4;
                }

                else if (position == 0 && statusjenis == 4) {
                    dropdowntipe.setAdapter(adaptertanamyanmar);
                    alatmodel.setMerek("Yanmar");
                    statusmerek = 1;
                }else if (position == 1 && statusjenis == 4) {
                    dropdowntipe.setAdapter(adaptertanamtanikaya);
                    alatmodel.setMerek("Tanikaya");
                    statusmerek = 2;
                }else if (position == 2 && statusjenis == 4) {
                    dropdowntipe.setAdapter(adaptertanamiseki);
                    alatmodel.setMerek("Iseki");
                    statusmerek = 3;
                }else if (position == 3 && statusjenis == 4) {
                    dropdowntipe.setAdapter(adaptertanamkubota);
                    alatmodel.setMerek("Kubota");
                    statusmerek = 4;
                }


                Toast.makeText(Tambahgarasi.this, "status jenis: "+statusjenis+" statusm:"+statusmerek, Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
