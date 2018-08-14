package com.example.root.g_track.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.g_track.Detailorder;
import com.example.root.g_track.Main_activity;
import com.example.root.g_track.Model.Openorder_model;
import com.example.root.g_track.Model.Ordermodel;
import com.example.root.g_track.R;

import java.security.PublicKey;
import java.util.List;

public class Adapter_orderhome extends RecyclerView.Adapter<Adapter_orderhome.ViewHolder> {

    private List<Openorder_model> listordermodel;
    private Activity context;
    public Adapter_orderhome(List<Openorder_model> listordermodel, Activity context){
        this.listordermodel = listordermodel;
        this.context = context;
    }



    @Override
    public Adapter_orderhome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.listorder_home,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_orderhome.ViewHolder holder, int position) {

        Openorder_model openorder_model = listordermodel.get(position);

        holder.nama_alat.setText(openorder_model.getNama_alat());
        holder.tanggal_order.setText(openorder_model.getTanggal());

        if(openorder_model.getJenis().equals("Panen")){
            holder.item_order.setCardBackgroundColor(Color.parseColor(context.getResources().getString(R.string.color_banner_panen)));
        }
        else if(openorder_model.getJenis().equals("Tanam")){
            holder.item_order.setCardBackgroundColor(Color.parseColor(context.getResources().getString(R.string.color_banner_tanam)));
        }else if(openorder_model.getJenis().equals("Roda dua") || openorder_model.getJenis().equals("Roda empat")){
            holder.item_order.setCardBackgroundColor(Color.parseColor(context.getResources().getString(R.string.color_banner_truck)));
        }

        holder.id_alat = openorder_model.getId_alat();
        holder.nama = (openorder_model.getNama());
        holder.tanggal = (openorder_model.getTanggal());
        holder.alamat_order = (openorder_model.getAlamats());
        holder.nama_alat.setText(openorder_model.getNama_alat());
        holder.luas = openorder_model.getLuas();
        holder.nomor_telpon = openorder_model.getKontak();
        holder.id_order = openorder_model.getKey();
        holder.kontak = openorder_model.getKontak();
        holder.kedalaman = openorder_model.getKedalaman();
        holder.lokasi = openorder_model.getLokasi();
        holder.air = openorder_model.getAir();
        holder.akses = openorder_model.getAkses();
        holder.hargatot = openorder_model.getHargatotal()+"";
        holder.longitude = openorder_model.getLongitude();
        holder.latitude = openorder_model.getLatitude();
        holder.jenis = openorder_model.getJenis();
        holder.luasstring = openorder_model.getLuas().toString();
        holder.status = openorder_model.getStatus();


    }

    @Override
    public int getItemCount() {
        return listordermodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nama_alat;
        public TextView tanggal_order;
        public CardView item_order;


        public String nomor_telpon, id_order, id_alat, kontak, kedalaman, lokasi, air, akses, hargatot, jenis, luasstring, status
                ,alamat_order, nama, tanggal;
        public Float longitude, latitude, luas;

        public ViewHolder(View itemView) {
            super(itemView);

            Log.e("Masuk", "masuk adapter");
            nama_alat = itemView.findViewById(R.id.orderhome_namaitem);
            tanggal_order = itemView.findViewById(R.id.orderhome_tanggal);
            item_order = itemView.findViewById(R.id.card_list_order_home);

            item_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Main_activity.nDialog.show();
                    Intent intent = new Intent(context, Detailorder.class);
                    intent.putExtra("sumber", "ongoing");
                    intent.putExtra("id_order", id_order);
                    intent.putExtra("nama_alat", nama_alat.getText().toString());
                    intent.putExtra("id_alat", id_alat);
                    intent.putExtra("nama", nama);
                    intent.putExtra("kontak", kontak);
                    intent.putExtra("luas", luasstring);
                    intent.putExtra("tanggal", tanggal);
                    intent.putExtra("latitude", latitude+"");
                    intent.putExtra("longitude", longitude+"");
                    intent.putExtra("alamat", alamat_order);
                    intent.putExtra("kedalaman", kedalaman);
                    intent.putExtra("lokasi", lokasi);
                    intent.putExtra("air", air);
                    intent.putExtra("akses",akses);
                    intent.putExtra("harga_total", hargatot);
                    intent.putExtra("jenis", jenis);
                    intent.putExtra("status" , status);
                    context.startActivity(intent);
                }
            });


        }
    }
}
