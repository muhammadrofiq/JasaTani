package com.example.root.g_track.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.g_track.Detailalat;
import com.example.root.g_track.Model.Alatmodel;
import com.example.root.g_track.R;
import com.example.root.g_track.Tambahorder;

import java.util.List;

public class Adapteralat extends RecyclerView.Adapter<Adapteralat.ViewHolder> {

    public Adapteralat(List<Alatmodel> listaalatmodel, Activity context) {
        this.listaalatmodel = listaalatmodel;
        this.context = context;
    }

    private List<Alatmodel> listaalatmodel;
    private Activity context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_alat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alatmodel alatmodel = listaalatmodel.get(position);

        holder.jenis = alatmodel.getClassalat();
        holder.Harga = alatmodel.getHarga();
        holder.classalat =alatmodel.getClassalat();
        if (alatmodel.getImplemen().equals("none")){
            holder.nama= alatmodel.getMerek()+" "+alatmodel.getTipe();
            holder.listnamaalat.setText(alatmodel.getMerek()+" "+alatmodel.getTipe());
        }else{
            holder.nama= alatmodel.getMerek()+" "+alatmodel.getTipe()+" "+ alatmodel.getImplemen();

            holder.listnamaalat.setText(alatmodel.getMerek()+" "+alatmodel.getTipe()+" "+ alatmodel.getImplemen());
        }
        //holder.listtahunalat.setText(Integer.toString(alatmodel.getTahun()));
        holder.listtahunalat.setText(alatmodel.getKey());
        if(alatmodel.getClassalat().equals("Panen")){
            holder.listgambaralat.setImageResource(R.drawable.harvester);
        } else if(alatmodel.getClassalat().equals("Roda dua") || alatmodel.getClassalat().equals("Roda empat")){
            holder.listgambaralat.setImageResource(R.drawable.tractor);
        } else if(alatmodel.getClassalat().equals("Tanam")){
            holder.listgambaralat.setImageResource(R.drawable.planter);
        }

        holder.id_alat = alatmodel.getKey();
        holder.alatmodels = alatmodel;
    }

    @Override
    public int getItemCount() {
        return listaalatmodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public int Harga;
        public TextView listnamaalat;
        public TextView listtahunalat;
        public ImageView listgambaralat;
        public String id_alat;
        public String nama, jenis;
        public String classalat;
        public Alatmodel alatmodels = new Alatmodel();

        public ViewHolder(View itemView) {
            super(itemView);
            listnamaalat = itemView.findViewById(R.id.listnamaalat);
            listtahunalat = itemView.findViewById(R.id.listtahunalat);
            listgambaralat = itemView.findViewById(R.id.icon_alat);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = context.getIntent().getExtras();
                    String test = extras.getString("sumber");
                    if(test.equals("order")){
                        if(classalat.equals("Panen")){
                            if(test.equals("order")){Tambahorder.trigalat.setBackgroundColor(Color.parseColor(context.getResources().getString(R.string.color_banner_panen)));}
                        } else if(classalat.equals("Roda dua") || classalat.equals("Roda empat")){
                            if(test.equals("order")){Tambahorder.trigalat.setBackgroundColor(Color.parseColor(context.getResources().getString(R.string.color_banner_truck)));}
                        } else if(classalat.equals("Tanam")){
                            if(test.equals("order")){Tambahorder.trigalat.setBackgroundColor(Color.parseColor(context.getResources().getString(R.string.color_banner_tanam)));}
                        }
                        Tambahorder.status_alat = true;
                        Tambahorder.jenis = jenis;
                        Tambahorder.Harga = Harga;
                        Tambahorder.luaslahan.setKeyListener((KeyListener) Tambahorder.luaslahan.getTag());
                        Tambahorder.luaslahan.setInputType(InputType.TYPE_CLASS_PHONE);
                        Tambahorder.idalat.setText(id_alat);
                        Tambahorder.namaalat.setText(nama);
                        context.finish();
                    }else {
                        Intent intent = new Intent(context, Detailalat.class);
                        intent.putExtra("ID_ALAT", id_alat);
                        context.startActivity(intent);
                        //context.finish();
                    }
                }
            });

        }
    }
}
