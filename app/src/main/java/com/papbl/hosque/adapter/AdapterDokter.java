package com.papbl.hosque.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.papbl.hosque.R;
import com.papbl.hosque.activities.DetailBencanaActivity;
import com.papbl.hosque.model.Doctor;

import java.util.ArrayList;

public class AdapterDokter extends RecyclerView.Adapter<AdapterDokter.ViewHolder> {

    private final Context context;
    private final ArrayList<Doctor> list_bencana = new ArrayList<>();

    public AdapterDokter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Doctor> items) {
        list_bencana.clear();
        list_bencana.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_dokter, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), DetailBencanaActivity.class);
                intent.putExtra("bencana", (Parcelable)list_bencana.get(holder.getAdapterPosition()));
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bind(list_bencana.get(position));
    }

    @Override
    public int getItemCount() {
        return list_bencana.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_name;
        private final TextView tv_spesialis;

        ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_spesialis = itemView.findViewById(R.id.tv_spesialis);
        }

        void bind(Doctor bencana){
            tv_name.setText(bencana.getNama());
            tv_spesialis.setText(bencana.getSpesialis());
//            tv_judulbencana.setText(bencana.getJudul());
//            tv_deskripsi.setText(bencana.getDeskripsi());
//            DatabaseReference getNameUser = FirebaseDatabase.getInstance().getReference().child("Users").child(bencana.getIdUser());
//            getNameUser.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String namaUser = dataSnapshot.child("nama").getValue().toString();
//                    tv_namaUser.setText(namaUser);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//            RequestOptions requestOptions = new RequestOptions();
//            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));
//            Glide.with(itemView.getContext())
//                    .load(bencana.getFotoBencana())
//                    .apply(requestOptions)
//                    .into(iv_fotobencana);
        }
    }
}