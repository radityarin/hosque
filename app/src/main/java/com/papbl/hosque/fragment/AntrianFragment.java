package com.papbl.hosque.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.papbl.hosque.R;
import com.papbl.hosque.adapter.AdapterAntrian;
import com.papbl.hosque.model.Antrian;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AntrianFragment extends Fragment {

    private RecyclerView rv_antrian;
    private FirebaseAuth auth;

    public AntrianFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_antrian, container, false);
        rv_antrian = view.findViewById(R.id.rv_antrian);
        auth = FirebaseAuth.getInstance();

        getBencanaTerdekat();

        return view;
    }

    private void getBencanaTerdekat() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("users").child("patient").child(auth.getUid()).child("antrian");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Antrian> bencanaterdekat = new ArrayList<>();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Antrian bencana = dt.getValue(Antrian.class);
                    bencanaterdekat.add(bencana);
                }
                AdapterAntrian adapterBencanaTerdekat = new AdapterAntrian(getContext());
                adapterBencanaTerdekat.setData(bencanaterdekat);
                rv_antrian.setAdapter(adapterBencanaTerdekat);
                rv_antrian.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
