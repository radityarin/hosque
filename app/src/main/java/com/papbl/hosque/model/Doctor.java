package com.papbl.hosque.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Doctor implements Parcelable {
    private String nama, email, spesialis;

    public Doctor() {
    }

    public Doctor(String nama, String email, String spesialis) {
        this.nama = nama;
        this.email = email;
        this.spesialis = spesialis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpesialis() {
        return spesialis;
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nama);
        dest.writeString(this.email);
        dest.writeString(this.spesialis);
    }

    protected Doctor(Parcel in) {
        this.nama = in.readString();
        this.email = in.readString();
        this.spesialis = in.readString();
    }

    public static final Parcelable.Creator<Doctor> CREATOR = new Parcelable.Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel source) {
            return new Doctor(source);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };
}
