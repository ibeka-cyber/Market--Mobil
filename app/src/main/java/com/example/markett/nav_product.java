package com.example.markett;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.markett.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_product#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_product extends Fragment {
    String name;
    TextView prod_name;
    TextView prod_rating;
    TextView prod_discount;
    TextView prod_description;
    TextView prod_price;
    RatingBar rb_ratingbar;
    ImageView img;
    Button add,rem,addToCart;
    EditText quantity;
    int productQuantity=1;
    float amount=0.0f;

    FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseAuth auth =FirebaseAuth.getInstance();

    public nav_product() {

    }


    public static nav_product newInstance(String param1, String param2) {
        nav_product fragment = new nav_product();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name=getArguments().getString("name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nav_product, container, false);
    }

    //bu fonksiyon bize gelen viewi verecek biz de onun içerisinden almamız gerekenleri alacağız.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prod_name=view.findViewById(R.id.pr_name);
        prod_description=view.findViewById(R.id.pr_description);
        prod_price=view.findViewById(R.id.pr_price);
        prod_discount=view.findViewById(R.id.pr_discount);
        prod_rating=view.findViewById(R.id.pr_rating);
        rb_ratingbar=view.findViewById(R.id.pr_ratingBar);
        img = view.findViewById(R.id.pr_image);

        add=view.findViewById(R.id.btn_q_add);
        rem=view.findViewById(R.id.btn_q_rem);
        addToCart=view.findViewById(R.id.btn_addtocart);
        quantity=view.findViewById(R.id.et_quantity);
        quantity.setText("1");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productQuantity++;
                quantity.setText(String.valueOf(productQuantity));
            }
        });
        rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productQuantity--;
                quantity.setText(String.valueOf(productQuantity));
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productAddToCart();
            }
        });
        getProductFromFireStore();
        //databaseden her şeyi çekmeyeceğimiz için Where koyarak kısıtladık.


    }
    private void getProductFromFireStore() {

        db.collection("bestsellers").whereEqualTo("name",name).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                            ProductModel product=doc.toObject(ProductModel.class);
                            Glide.with(getActivity()).load(product.getImg_url()).into(img);
                            prod_name.setText(product.getName());
                            prod_price.setText(product.getPrice());
                            prod_discount.setText(product.getDiscount());
                            prod_rating.setText(product.getRating());
                            prod_description.setText(product.getDescription());
                            rb_ratingbar.setRating(Float.parseFloat(product.getRating()));

                        }else{
                            Log.d("---------------","Data gelmedi.");
                        }
                    }
                });
    }

    private void productAddToCart() {
        amount = productQuantity * Float.parseFloat(prod_price.getText().toString());
        String tarih,saat;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        tarih=sdf.format(cal.getTime());
        SimpleDateFormat sdf2=new SimpleDateFormat("hh:-mm-ss");
        saat=sdf.format(cal.getTime());

        //verileri çekmek için model oluşturabiliriz fakat bu sefer HashMap kullanacağız.
        HashMap<String, Object> product = new HashMap<String, Object>();
        product.put("product_name",name);
        product.put("price",prod_price.getText().toString());
        product.put("date",tarih);
        product.put("time",saat);
        product.put("quantity",String.valueOf(productQuantity));
        product.put("amount",String.valueOf(amount));

        //şuanki giriş yapmış olan kullanıcının bilgilerini kullanacağımız için doc.. yaptık.
        db.collection("carts").document(auth.getCurrentUser().getUid())
                .collection("usercarts").add(product)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            Tools.showMessage("Ürün sepete Eklendi.");
                        }else{
                            Tools.showMessage("Ürün eklenemedi.");
                        }
                    }
                });


    }
}