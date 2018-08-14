package com.example.root.g_track.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.g_track.Detailorder;
import com.example.root.g_track.Fragment.Secondfragment_1;
import com.example.root.g_track.Fragment.Secondfragment_2;
import com.example.root.g_track.Main_activity;
import com.example.root.g_track.Model.Openorder_model;
import com.example.root.g_track.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_ongoingorder extends RecyclerView.Adapter<Adapter_ongoingorder.Viewholder> {

    private List<Openorder_model> listorder;
    private Activity activity;

    public Adapter_ongoingorder(List<Openorder_model> listordermodel, Activity activity) {
        this.activity = activity;
        this.listorder = listordermodel;
    }


    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_recycleview_ongorder, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        final Openorder_model openorder_model = listorder.get(position);

        if (openorder_model.getJenis().equals("Panen")) {
            holder.banner.setCardBackgroundColor(Color.parseColor(activity.getResources().getString(R.string.color_banner_panen)));
        } else if (openorder_model.getJenis().equals("Roda dua") || openorder_model.getJenis().equals("Roda empat")) {
            holder.banner.setCardBackgroundColor(Color.parseColor(activity.getResources().getString(R.string.color_banner_truck)));
        } else if (openorder_model.getJenis().equals("Tanam")) {
            holder.banner.setCardBackgroundColor(Color.parseColor(activity.getResources().getString(R.string.color_banner_tanam)));
        }

        holder.id_alat = openorder_model.getId_alat();
        holder.nama.setText(openorder_model.getNama());
        holder.tanggal.setText("  " + openorder_model.getTanggal());
        holder.alamat_order.setText(openorder_model.getAlamats());
        holder.nama_alat.setText(openorder_model.getNama_alat());
        //Log.e("CEKLUAS",openorder_model.getLuas()+"");
        holder.luas.setText(String.valueOf(openorder_model.getLuas()) + "   Ha");
        holder.nomor_telpon = openorder_model.getKontak();
        holder.id_order = openorder_model.getKey();
        holder.kontak = openorder_model.getKontak();
        holder.kedalaman = openorder_model.getKedalaman();
        holder.lokasi = openorder_model.getLokasi();
        holder.air = openorder_model.getAir();
        holder.akses = openorder_model.getAkses();
        holder.hargatot = openorder_model.getHargatotal() + "";
        holder.longitude = openorder_model.getLongitude();
        holder.latitude = openorder_model.getLatitude();
        holder.jenis = openorder_model.getJenis();
        holder.luasstring = openorder_model.getLuas().toString();
        holder.status = openorder_model.getStatus();
        holder.key = openorder_model.getKey();
        if (openorder_model.getStatus() != null) {
            holder.order_header.setText("Selesai");
            if (openorder_model.getStatus().equals("new")) {
                holder.icon_call.setImageResource(R.drawable.ic_cancel);
                holder.order_header.setText("Baru");
                holder.icon_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createDialogListener(activity, openorder_model);
                    }
                });
                Drawable draw = activity.getResources().getDrawable(R.drawable.redalert);
                holder.tanggal.setCompoundDrawablesWithIntrinsicBounds(draw, null, null, null);
            }else{
                holder.icon_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + openorder_model.getKontak()));
                        activity.startActivity(intent);
                    }
                });
            }
        }else {
            holder.icon_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + openorder_model.getKontak()));
                    activity.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return listorder.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public TextView tanggal, alamat_order, nama, nama_alat, luas, order_header;
        public ImageView icon_call;
        public CardView banner;
        public String nomor_telpon, id_order, id_alat, kontak, kedalaman, lokasi, air, akses, hargatot, jenis, luasstring, status,key;
        public Float longitude, latitude;
        public CardView item_trigger;


        public Viewholder(View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.itemonorder_tanggal);
            alamat_order = itemView.findViewById(R.id.itemonorder_alamat);
            nama = itemView.findViewById(R.id.itemonorder_nama);
            nama_alat = itemView.findViewById(R.id.itemonorder_namaalat);
            luas = itemView.findViewById(R.id.itemonorder_luaslahan);
            icon_call = itemView.findViewById(R.id.itemonorder_call);
            banner = itemView.findViewById(R.id.itemonorder_banner);
            item_trigger = itemView.findViewById(R.id.item_ongoing);
            order_header = itemView.findViewById(R.id.list_order_item_headername);

            item_trigger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Main_activity.nDialog.show();
                    Intent intent = new Intent(activity, Detailorder.class);

                    intent.putExtra("sumber", "ongoing");

                    intent.putExtra("id_order", id_order);
                    intent.putExtra("nama_alat", nama_alat.getText().toString());
                    intent.putExtra("id_alat", id_alat);
                    intent.putExtra("nama", nama.getText().toString());
                    intent.putExtra("kontak", kontak);
                    intent.putExtra("luas", luasstring);
                    intent.putExtra("tanggal", tanggal.getText().toString());
                    intent.putExtra("latitude", latitude + "");
                    intent.putExtra("longitude", longitude + "");
                    intent.putExtra("alamat", alamat_order.getText().toString());
                    intent.putExtra("kedalaman", kedalaman);
                    intent.putExtra("lokasi", lokasi);
                    intent.putExtra("air", air);
                    intent.putExtra("akses", akses);
                    intent.putExtra("harga_total", hargatot);
                    intent.putExtra("jenis", jenis);
                    intent.putExtra("status", status);
                    intent.putExtra("key",key);
                    activity.startActivity(intent);
                }
            });
        }
    }

    public void createDialogListener(final Context contexts, final Openorder_model openorder_model) {
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:


                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        FirebaseDatabase.getInstance().getReference("neworder/" + user.getUid() + "/" + openorder_model.getKey()).removeValue();

                        // kalo order sisa satu dan di delete
                        if (Secondfragment_1.listorder_frstfrag2.size() == 1) {
                            Main_activity.activity.finish();
                            Intent i = new Intent(activity, Main_activity.class);
                            activity.startActivity(i);
                        }

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //Toast.makeText(contexts, "masuk no", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder.setMessage("Yakin menolak pesanan?").setPositiveButton("Ya", clickListener)
                .setNegativeButton("Tidak", clickListener).show();
        Button btnPositive = dialog.getButton(Dialog.BUTTON_POSITIVE);
        Button btnNegative = dialog.getButton(Dialog.BUTTON_NEGATIVE);
        btnPositive.setTextSize(16);
        btnNegative.setTextSize(16);
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTextSize(16);


    }

}


