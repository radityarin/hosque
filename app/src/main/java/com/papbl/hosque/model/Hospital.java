package com.papbl.hosque.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Hospital implements Parcelable {

    private String alamat, deskripsi, nama, koordinat, tipe, url_photo;
    private ArrayList<String> doctor, main_queue;

    public Hospital() {
    }

    public Hospital(String alamat, String deskripsi, String nama, String koordinat, String tipe, String url_photo, ArrayList<String> doctor, ArrayList<String> main_queue) {
        this.alamat = alamat;
        this.deskripsi = deskripsi;
        this.nama = nama;
        this.koordinat = koordinat;
        this.tipe = tipe;
        this.url_photo = url_photo;
        this.doctor = doctor;
        this.main_queue = main_queue;
    }

    protected Hospital(Parcel in) {
        alamat = in.readString();
        deskripsi = in.readString();
        nama = in.readString();
        koordinat = in.readString();
        tipe = in.readString();
        url_photo = in.readString();
        doctor = in.createStringArrayList();
        main_queue = in.createStringArrayList();
    }

    public static final Creator<Hospital> CREATOR = new Creator<Hospital>() {
        @Override
        public Hospital createFromParcel(Parcel in) {
            return new Hospital(in);
        }

        @Override
        public Hospital[] newArray(int size) {
            return new Hospital[size];
        }
    };

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKoordinat() {
        return koordinat;
    }

    public void setKoordinat(String koordinat) {
        this.koordinat = koordinat;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }

    public ArrayList<String> getDoctor() {
        return doctor;
    }

    public void setDoctor(ArrayList<String> doctor) {
        this.doctor = doctor;
    }

    public ArrayList<String> getMain_queue() {
        return main_queue;
    }

    public void setMain_queue(ArrayList<String> main_queue) {
        this.main_queue = main_queue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(alamat);
        dest.writeString(deskripsi);
        dest.writeString(nama);
        dest.writeString(koordinat);
        dest.writeString(tipe);
        dest.writeString(url_photo);
        dest.writeStringList(doctor);
        dest.writeStringList(main_queue);
    }
}
