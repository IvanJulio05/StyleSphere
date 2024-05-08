package com.example.tiendaropa.recycleView;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendaropa.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView,priceView,shipmentView;//envio

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        nameView = itemView.findViewById(R.id.nombre);
        priceView =itemView.findViewById(R.id.precio);
        shipmentView = itemView.findViewById(R.id.envio);
    }
}
