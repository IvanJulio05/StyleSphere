package com.example.tiendaropa.recycleView;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.tiendaropa.Models.Product;
import com.example.tiendaropa.R;

import java.nio.channels.ClosedByInterruptException;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {

    Context context;
    List<Product> products;
    private View.OnClickListener listener;


    public MyAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        //return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
        view.setOnClickListener(this);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(products.get(position).getName());
        holder.priceView.setText("$"+products.get(position).getPrice());
        holder.description.setText(products.get(position).getDescription());

        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(products.get(position).getUrl_img()).apply(requestOptions).into(holder.imageView);
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

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }
}
