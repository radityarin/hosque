package com.papbl.hosque.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String uid, nama, noTelephone, kota, email, fotoIdentitas, fotoProfil;
    private boolean status;

    public User() {
    }

    public User(String uid, String nama, String noTelephone, String kota, String email, String fotoIdentitas, String fotoProfil, boolean status) {
        this.uid = uid;
        this.nama = nama;
        this.noTelephone = noTelephone;
        this.kota = kota;
        this.email = email;
        this.fotoIdentitas = fotoIdentitas;
        this.fotoProfil = fotoProfil;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public String getNama() {
        return nama;
    }

    public String getNoTelephone() {
        return noTelephone;
    }

    public String getKota() {
        return kota;
    }

    public String getEmail() {
        return email;
    }

    public String getFotoIdentitas() {
        return fotoIdentitas;
    }

    public String getFotoProfil() {
        return fotoProfil;
    }

    public boolean isStatus() {
        return status;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNoTelephone(String noTelephone) {
        this.noTelephone = noTelephone;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFotoIdentitas(String fotoIdentitas) {
        this.fotoIdentitas = fotoIdentitas;
    }

    public void setFotoProfil(String fotoProfil) {
        this.fotoProfil = fotoProfil;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.nama);
        dest.writeString(this.noTelephone);
        dest.writeString(this.kota);
        dest.writeString(this.email);
        dest.writeString(this.fotoIdentitas);
        dest.writeString(this.fotoProfil);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
    }

    protected User(Parcel in) {
        this.uid = in.readString();
        this.nama = in.readString();
        this.noTelephone = in.readString();
        this.kota = in.readString();
        this.email = in.readString();
        this.fotoIdentitas = in.readString();
        this.fotoProfil = in.readString();
        this.status = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
