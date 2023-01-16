package com.example.markett.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.markett.R;
import com.example.markett.models.CategoryHome;

import java.util.ArrayList;
//burada viewholdeı tanımayacak. Onu oluşturmamız gerekiyor. Sağ tık create diyoruz.
public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.ViewHolder>{
    ArrayList<CategoryHome> categoryHomeArraylist;
    Context context;

    public CategoryHomeAdapter(Context context,ArrayList<CategoryHome> categoryHomeArraylist) {
        this.categoryHomeArraylist = categoryHomeArraylist;
        this.context = context;
    }


    @NonNull
    @Override
    public CategoryHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHomeAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(categoryHomeArraylist.get(position).getImg_url()).into(holder.imageView);
        holder.category.setText(categoryHomeArraylist.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoryHomeArraylist.size();
    }
//lambaya tıklayarak ve super constructorlarını oluşturucaz.

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.hc_image);
            category =itemView.findViewById(R.id.hc_categori_name);
        }
    } // adaptörümüz hazır. şimdi kategori bilgilerini çekelim ve sayfaya yansıtalım
}
