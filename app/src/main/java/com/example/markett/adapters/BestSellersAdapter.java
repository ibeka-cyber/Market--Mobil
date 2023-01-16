package com.example.markett.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.markett.R;
import com.example.markett.models.BestSellersModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BestSellersAdapter extends RecyclerView.Adapter<BestSellersAdapter.ViewHolder> {
    //bu ikisi adaptör create edildiğinde gelmesi lazım
    private ArrayList<BestSellersModel> bestSellers;
    private Context context;

    //o zaman bunların constructorını oluşturalım


    public BestSellersAdapter(Context context,ArrayList<BestSellersModel> bestSellers ) {
        this.bestSellers = bestSellers;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //yeni bir viewHolder oluşturduk. Bunun ınflate edilmesi için içinde bulunduğum frameworkün içerisine best_sellers_itemi koyacak şekilde
        //çektik.
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.best_sellers_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //tıklanabilir olmayı sağlarken position hata veriyor. Tıklama hızına yetişemediği için. biz de böyleçözüyoruz.
        position =holder.getAdapterPosition();

        Glide.with(context).load(bestSellers.get(position).getImg_url()).into(holder.bestImg);
        holder.name.setText(bestSellers.get(position).getName());
        holder.desc.setText(bestSellers.get(position).getDescription());
        holder.rating.setText(bestSellers.get(position).getRating());
        holder.discount.setText(bestSellers.get(position).getDiscount());
        holder.ratingBar.setRating(Float.parseFloat(bestSellers.get(position).getRating()));

        int finalPosition = position;
        //kartların tıklanabilir olması için
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("---------------",bestSellers.get(finalPosition).getName());
                //ürüne tıklandığı anda product fagmentına geçecek.
                /*şimdi tıklanan ürünün datasını götürmemiz lazım. Bunun için Bundle sınıfından nesne kullanacağız.*/
                Bundle bundle =new Bundle();
                bundle.putString("name",bestSellers.get(finalPosition).getName());//burda hazırladığımız datayı navigation'a parametre olarak vermeliyiz.
                Navigation.findNavController(view).navigate(R.id.nav_product,bundle);//, ile verdik.
            }
        });

    }

    @Override
    public int getItemCount() {
            return bestSellers.size();//itemlerin sayısı gelecek dizi uzunluğunda olsun dedik.
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bestImg;
        TextView name,desc,rating,discount;
        RatingBar ratingBar; //bunların binding edilmesi lazım
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            bestImg= itemView.findViewById(R.id.iv_best_card_img);
            name= itemView.findViewById(R.id.tv_best_card_product_name);
            desc= itemView.findViewById(R.id.tv_best_card_product_description);
            rating= itemView.findViewById(R.id.rb_best_card_rating);
            discount=itemView.findViewById(R.id.tv_best_card_discount);
            ratingBar=itemView.findViewById(R.id.rb_best_card);

        }
    }
}
