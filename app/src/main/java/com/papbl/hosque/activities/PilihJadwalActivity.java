package com.papbl.hosque.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.papbl.hosque.R;
import com.papbl.hosque.model.Antrian;
import com.papbl.hosque.model.Doctor;
import com.papbl.hosque.model.Hospital;
import com.papbl.hosque.model.User;

public class PilihJadwalActivity extends AppCompatActivity implements View.OnClickListener {

    private Hospital hospital;
    private Doctor doctor;
    private DatabaseReference createUserRef;
    private Spinner spinner;
    private TextView tv_namarumahsakit, tv_namadokter, tv_spesialis;
    private Button btn_pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_jadwal);

        hospital = getIntent().getParcelableExtra("hospital");
        doctor = getIntent().getParcelableExtra("bencana");

        tv_namadokter = findViewById(R.id.tv_namadokter);
        tv_namarumahsakit = findViewById(R.id.tv_namarumahsakit);
        tv_spesialis = findViewById(R.id.tv_spesialis);
        btn_pesan = findViewById(R.id.btn_pesan);

        btn_pesan.setOnClickListener(this);
        tv_namarumahsakit.setText(hospital.getNama());
        tv_namadokter.setText(doctor.getNama());
        tv_spesialis.setText(doctor.getSpesialis());

        spinner = (Spinner) findViewById(R.id.spinner_jadwal);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_jadwal, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pesan:
                writeOrder();
        }
    }

    private void writeOrder() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();

        createUserRef = FirebaseDatabase.getInstance().getReference().child("queue");
        String mGroupId = createUserRef.push().getKey();
        createUserRef = FirebaseDatabase.getInstance().getReference().child("queue").child(mGroupId);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        Antrian antrian = new Antrian(hospital.getNama(), spinner.getSelectedItem().toString(), "A123", doctor.getNama(), auth.getUid());
        createUserRef.setValue(antrian).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(PilihJadwalActivity.this, MainUserActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(PilihJadwalActivity.this, "Gagal masukin data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
