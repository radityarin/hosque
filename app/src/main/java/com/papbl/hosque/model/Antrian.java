package com.papbl.hosque.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Antrian implements Parcelable {

    private String nama_rumahsakit, jadwal, nomor, nama_dokter;

    public Antrian() {
    }

    public Antrian(String nama_rumahsakit, String jadwal, String nomor, String nama_dokter) {
        this.nama_rumahsakit = nama_rumahsakit;
        this.jadwal = jadwal;
        this.nomor = nomor;
        this.nama_dokter = nama_dokter;
    }

    public String getNama_rumahsakit() {
        return nama_rumahsakit;
    }

    public void setNama_rumahsakit(String nama_rumahsakit) {
        this.nama_rumahsakit = nama_rumahsakit;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getNama_dokter() {
        return nama_dokter;
    }

    public void setNama_dokter(String nama_dokter) {
        this.nama_dokter = nama_dokter;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nama_rumahsakit);
        dest.writeString(this.jadwal);
        dest.writeString(this.nomor);
        dest.writeString(this.nama_dokter);
    }

    protected Antrian(Parcel in) {
        this.nama_rumahsakit = in.readString();
        this.jadwal = in.readString();
        this.nomor = in.readString();
        this.nama_dokter = in.readString();
    }

    public static final Parcelable.Creator<Antrian> CREATOR = new Parcelable.Creator<Antrian>() {
        @Override
        public Antrian createFromParcel(Parcel source) {
            return new Antrian(source);
        }

        @Override
        public Antrian[] newArray(int size) {
            return new Antrian[size];
        }
    };
}
