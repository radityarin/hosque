package com.papbl.hosque.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.papbl.hosque.R;
import com.papbl.hosque.fragment.HomeFragment;
import com.papbl.hosque.fragment.NavigasiFragment;
import com.papbl.hosque.fragment.ProfilFragment;
import com.papbl.hosque.fragment.AntrianFragment;
import com.papbl.hosque.fragment.TambahFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainUserActivity extends AppCompatActivity {

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.homebutton:
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.main_frame, homeFragment, "Home Fragment");
                    fragmentTransaction.commit();
                    setTitle("Home");
                    return true;
                case R.id.antrianbutton:
                    AntrianFragment riwayatFragment = new AntrianFragment();
                    fragmentTransaction.replace(R.id.main_frame, riwayatFragment, "Riwayat Fragment");
                    fragmentTransaction.commit();
                    setTitle("Riwayat");
                    return true;
                case R.id.tambahbutton:
                    TambahFragment tambahFragment = new TambahFragment();
                    fragmentTransaction.replace(R.id.main_frame, tambahFragment, "Tambah Fragment");
                    fragmentTransaction.commit();
                    setTitle("Tambah");
                    return true;
                case R.id.navigasibutton:
                    NavigasiFragment navigasiFragment = new NavigasiFragment();
                    fragmentTransaction.replace(R.id.main_frame, navigasiFragment, "Navigasi Fragment");
                    fragmentTransaction.commit();
                    setTitle("Profil");
                    return true;
                case R.id.profilebutton:
                    ProfilFragment profilFragment = new ProfilFragment();
                    fragmentTransaction.replace(R.id.main_frame, profilFragment, "Profil Fragment");
                    fragmentTransaction.commit();
                    setTitle("Profil");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.main_frame, homeFragment, "Home Fragment");
        fragmentTransaction.commit();
        setTitle("Home");
    }

}
