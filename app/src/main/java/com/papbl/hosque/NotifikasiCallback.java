package com.papbl.hosque;

public interface NotifikasiCallback {
    void onSuccess(int newUser);
    void onError(boolean failure);
}
