package com.example.root.g_track.Model;

public class Usermodel {
    private String nama,nickname,namausaha,alamat, kontak;
    private Float longs,lats;
    private Integer tahun;

    public String getNamausaha() {
        return namausaha;
    }

    public void setNamausaha(String namausaha) {
        this.namausaha = namausaha;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Float getLats() {
        return lats;
    }

    public void setLats(Float lats) {
        this.lats = lats;
    }

    public Float getLongs() {
        return longs;
    }

    public void setLongs(Float longs) {
        this.longs = longs;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Integer getTahun() {
        return tahun;
    }

    public void setTahun(Integer tahun) {
        this.tahun = tahun;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }
}
