package com.papbl.hosque.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.papbl.hosque.R;
import com.papbl.hosque.adapter.AdapterDokter;
import com.papbl.hosque.model.Doctor;
import com.papbl.hosque.model.Hospital;

import java.util.ArrayList;

public class ListDokterActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Hospital bencana;
    private RecyclerView rv_dokter;
    public static final String EXTRA_BENCANA = "bencana";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dokter);

        bencana = getIntent().getParcelableExtra(EXTRA_BENCANA);

        rv_dokter = findViewById(R.id.rv_dokter);
        toolbar = findViewById(R.id.toolbar_donasi);
        toolbar.setTitle("List Dokter");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getBencanaTerdekat(bencana);
    }

    private void getBencanaTerdekat(final Hospital bencana) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("cek", "getBencanaTerdekat: "+bencana);
        DatabaseReference databaseReference = firebaseDatabase.getReference("hospital").child(bencana.getUid()).child("dokter");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Doctor> bencanaterdekat = new ArrayList<>();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Doctor bencana = dt.getValue(Doctor.class);
                    bencanaterdekat.add(bencana);
                }
                rv_dokter.setVisibility(View.VISIBLE);
                AdapterDokter adapterBencanaTerdekat = new AdapterDokter(getApplicationContext(),bencana);
                adapterBencanaTerdekat.setData(bencanaterdekat);
                rv_dokter.setAdapter(adapterBencanaTerdekat);
                rv_dokter.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
//            case R.id.ll_donasipakaian:
//                Intent intent = new Intent(KategoriDonasiActivity.this, DonasiPakaianActivity.class);
//                intent.putExtra(EXTRA_BENCANA,bencana);
//                startActivity(intent);
//                break;
//            case R.id.ll_donasiuang:
//                Intent intentUang = new Intent(KategoriDonasiActivity.this, DonasiUangActivity.class);
//                intentUang.putExtra(EXTRA_BENCANA,bencana);
//                startActivity(intentUang);
//                break;
        }
    }
}
