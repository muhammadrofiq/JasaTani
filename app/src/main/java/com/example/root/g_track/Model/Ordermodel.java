package com.example.root.g_track.Model;

public class Ordermodel {


    public String id_alat;
    private String id_user;
    private String jenis;
    private String kondisi;
    private String tanggal;

    private String namaalat;

    private Float latitude;
    private Float longitude;
    private Float luas;
    private Float total;

    public Ordermodel() {
        namaalat="";
    }

    public String getId_alat() {
        return id_alat;
    }

    public void setId_alat(String id_alat) {
        this.id_alat = id_alat;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLuas() {
        return luas;
    }

    public void setLuas(Float luas) {
        this.luas = luas;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getNamaalat() {
        return namaalat;
    }

    public void setNamaalat(String namaalat) {
        this.namaalat = namaalat;
    }
}
