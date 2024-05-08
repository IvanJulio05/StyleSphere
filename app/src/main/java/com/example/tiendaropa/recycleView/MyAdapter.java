package com.example.tiendaropa.recycleView;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tiendaropa.Models.Product;
import com.example.tiendaropa.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

    Context context;
    List<Product> products;

    public MyAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(products.get(position).getName());
        holder.priceView.setText(products.get(position).getPrice());
        Glide.with(context).load(products.get(position).getUrl_img()).into(holder.imageView);
        if(products.get(position).isFreeShipping()){
            holder.shipmentView.setText("Envio Gratis");
        }
        else{
            holder.shipmentView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
