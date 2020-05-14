package com.papbl.hosque.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.papbl.hosque.AlarmReceiver;
import com.papbl.hosque.R;
import com.papbl.hosque.fragment.BencanaVerifFragment;
import com.papbl.hosque.fragment.DataDiriVerifFragment;
import com.papbl.hosque.fragment.ProfilFragment;
import com.papbl.hosque.fragment.ProgresBencanaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuAdminActivity extends AppCompatActivity {

    private AlarmReceiver alarmReceiver;
    private AlarmReceiver alarmReceiverBencana;

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.homebutton:
                    DataDiriVerifFragment dataDiriVerifFragment = new DataDiriVerifFragment();
                    fragmentTransaction.replace(R.id.main_frame_admin, dataDiriVerifFragment, "Data Diri Verif Fragment");
                    fragmentTransaction.commit();
                    setTitle("Data diri");
                    return true;
                case R.id.riwayatbutton:
                    BencanaVerifFragment bencanaVerifFragment = new BencanaVerifFragment();
                    fragmentTransaction.replace(R.id.main_frame_admin, bencanaVerifFragment, "Bencana Verif Fragment");
                    fragmentTransaction.commit();
                    setTitle("Bencana");
                    return true;
                case R.id.progresbutton:
                    ProgresBencanaFragment progres_bencana = new ProgresBencanaFragment();
                    fragmentTransaction.replace(R.id.main_frame_admin, progres_bencana, "Progres Bencana Fragment");
                    fragmentTransaction.commit();
                    setTitle("Progres Bencana");
                    return true;
                case R.id.profilebutton:
                    ProfilFragment profilFragment = new ProfilFragment();
                    fragmentTransaction.replace(R.id.main_frame_admin, profilFragment, "Profil Fragment");
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
        setContentView(R.layout.activity_menu_admin);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_admin);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DataDiriVerifFragment dataDiriVerifFragment = new DataDiriVerifFragment();
        fragmentTransaction.replace(R.id.main_frame_admin, dataDiriVerifFragment, "Data Diri Verif Fragment");
        fragmentTransaction.commit();
        setTitle("Data diri");

        alarmReceiver = new AlarmReceiver();
        alarmReceiverBencana = new AlarmReceiver();
        alarmReceiver.setNewUsersAndBencanaAlarm(this, AlarmReceiver.TYPE_NEWUSER);
        alarmReceiverBencana.setNewUsersAndBencanaAlarm(this,AlarmReceiver.TYPE_NEWBENCANA);

    }
}
