package com.papbl.hosque.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hospital implements Parcelable {

    private String uid, alamat, deskripsi, nama, koordinat, tipe, url_photo;
//    private ArrayList<Doctor> dokter;

    public Hospital() {
    }

    public Hospital(String uid, String alamat, String deskripsi, String nama, String koordinat, String tipe, String url_photo) {
        this.uid = uid;
        this.alamat = alamat;
        this.deskripsi = deskripsi;
        this.nama = nama;
        this.koordinat = koordinat;
        this.tipe = tipe;
        this.url_photo = url_photo;
//        this.dokter = dokter;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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
//
//    public ArrayList<Doctor> getDokter() {
//        return dokter;
//    }
//
//    public void setDokter(ArrayList<Doctor> dokter) {
//        this.dokter = dokter;
//    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.alamat);
        dest.writeString(this.deskripsi);
        dest.writeString(this.nama);
        dest.writeString(this.koordinat);
        dest.writeString(this.tipe);
        dest.writeString(this.url_photo);
//        dest.writeTypedList(this.dokter);
    }

    protected Hospital(Parcel in) {
        this.uid = in.readString();
        this.alamat = in.readString();
        this.deskripsi = in.readString();
        this.nama = in.readString();
        this.koordinat = in.readString();
        this.tipe = in.readString();
        this.url_photo = in.readString();
//        this.dokter = in.createTypedArrayList(Doctor.CREATOR);
    }

    public static final Creator<Hospital> CREATOR = new Creator<Hospital>() {
        @Override
        public Hospital createFromParcel(Parcel source) {
            return new Hospital(source);
        }

        @Override
        public Hospital[] newArray(int size) {
            return new Hospital[size];
        }
    };
}
