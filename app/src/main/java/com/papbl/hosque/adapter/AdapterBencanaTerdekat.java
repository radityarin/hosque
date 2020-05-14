package com.papbl.hosque.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.papbl.hosque.R;
import com.papbl.hosque.activities.DetailBencanaActivity;
import com.papbl.hosque.model.Bencana;
import com.papbl.hosque.model.Hospital;

import java.util.ArrayList;

public class AdapterBencanaTerdekat extends RecyclerView.Adapter<AdapterBencanaTerdekat.ViewHolder> {

    private final Context context;
    private final ArrayList<Hospital> list_bencana = new ArrayList<>();

    public AdapterBencanaTerdekat(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Hospital> items) {
        list_bencana.clear();
        list_bencana.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_bencana, parent, false);
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
        private final ImageView iv_fotobencana;
        private final TextView tv_judulbencana;
        private final TextView tv_kategori;

        ViewHolder(View itemView) {
            super(itemView);
            iv_fotobencana = itemView.findViewById(R.id.iv_fotobencana);
            tv_judulbencana = itemView.findViewById(R.id.tv_judulbencana);
            tv_kategori = itemView.findViewById(R.id.tv_kategori);
        }

        void bind(Hospital bencana){
            tv_judulbencana.setText(bencana.getNama());
            tv_kategori.setText(bencana.getTipe());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));
            Glide.with(itemView.getContext())
                    .load(bencana.getUrl_photo())
                    .apply(requestOptions)
                    .into(iv_fotobencana);
        }
    }
}