package com.example.markett.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.markett.R;
import com.example.markett.models.CartModel;

import java.util.ArrayList;

public class CartModelAdapter extends RecyclerView.Adapter<CartModelAdapter.ViewHolder> {
    ArrayList<CartModel> carts=new ArrayList<>();
    Context context=null;

    public CartModelAdapter(Context context,ArrayList<CartModel> carts) {
        this.carts = carts;
        this.context = context;
    }

    @NonNull
    @Override
    public CartModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartModelAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartModelAdapter.ViewHolder holder, int position) {
        position =holder.getAdapterPosition();
        holder.name.setText(carts.get(position).getName());
        holder.price.setText(carts.get(position).getPrice());
        holder.date.setText(carts.get(position).getDate());
        holder.time.setText(carts.get(position).getTime());
        holder.quantity.setText(carts.get(position).getQuantity());
        holder.amount.setText(carts.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,date,time,quantity,amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cd_name);
            price =itemView.findViewById(R.id.cd_price);
            date =itemView.findViewById(R.id.cd_date);
            time=itemView.findViewById(R.id.cd_time);
            quantity = itemView.findViewById(R.id.cd_quantity);
            amount = itemView.findViewById(R.id.cd_amount);


        }
    }
}
