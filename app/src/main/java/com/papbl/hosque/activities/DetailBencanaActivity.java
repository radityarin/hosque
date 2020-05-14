package com.papbl.hosque.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.papbl.hosque.R;
import com.papbl.hosque.model.Hospital;


public class DetailBencanaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_donasiSekarang, btn_back;
    private TextView tv_judulbencana, tv_alamatbencana, tv_deskripsibencana, tv_namaprofil,tv_status_bencana;
    private ImageView iv_fotobencana;
    private Hospital bencana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bencana);

        bencana = getIntent().getParcelableExtra("bencana");

        tv_judulbencana = findViewById(R.id.tv_judulbencana);
        tv_alamatbencana = findViewById(R.id.tv_alamatbencana);
        tv_deskripsibencana = findViewById(R.id.tv_deskripsibencana);
        tv_status_bencana = findViewById(R.id.tv_status_bencana);
        iv_fotobencana = findViewById(R.id.iv_fotobencana);
        tv_namaprofil = findViewById(R.id.tv_namaprofil);

        displayDetailBencana(bencana);
        btn_donasiSekarang = findViewById(R.id.btn_donasiSekarang);
        btn_back = findViewById(R.id.btn_back);
        btn_donasiSekarang.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    private void displayDetailBencana(Hospital bencana) {
        tv_judulbencana.setText(bencana.getNama());
        tv_alamatbencana.setText(bencana.getAlamat());
        tv_deskripsibencana.setText(bencana.getDeskripsi());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        tv_status_bencana.setText(bencana.getTipe());
        Glide.with(this).load(bencana.getUrl_photo()).into(iv_fotobencana);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_donasiSekarang:
                Log.d("cek", "onClick: "+bencana.getUid());
                Intent intent = new Intent(DetailBencanaActivity.this, KategoriDonasiActivity.class);
                intent.putExtra("bencana",bencana.getUid());
                startActivity(intent);
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }

}
