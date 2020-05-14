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
import com.papbl.hosque.model.Antrian;

import java.util.ArrayList;

public class AdapterAntrian extends RecyclerView.Adapter<AdapterAntrian.ViewHolder> {

    private final Context context;
    private final ArrayList<Antrian> list_bencana = new ArrayList<>();

    public AdapterAntrian(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Antrian> items) {
        list_bencana.clear();
        list_bencana.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_antrian, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), DetailBencanaActivity.class);
                intent.putExtra("bencana", (Parcelable) list_bencana.get(holder.getAdapterPosition()));
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
        private final TextView tv_namadokter;
        private final TextView tv_jadwal;
        private final TextView tv_namarumahsakit;
        private final TextView tv_nomor;

        ViewHolder(View itemView) {
            super(itemView);
            tv_namadokter = itemView.findViewById(R.id.tv_namadokter);
            tv_jadwal = itemView.findViewById(R.id.tv_jadwal);
            tv_namarumahsakit = itemView.findViewById(R.id.tv_rumahsakit);
            tv_nomor = itemView.findViewById(R.id.tv_nomor);
        }

        void bind(Antrian bencana) {
            tv_namarumahsakit.setText(bencana.getNama_rumahsakit());
            tv_nomor.setText(bencana.getNomor());
            tv_jadwal.setText(bencana.getJadwal());
            tv_namadokter.setText(bencana.getNama_dokter());
        }
    }
}