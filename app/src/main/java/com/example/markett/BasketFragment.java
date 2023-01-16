package com.example.markett;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.markett.adapters.CartModelAdapter;
import com.example.markett.models.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BasketFragment extends Fragment {
    ArrayList<CartModel> cartItems;
    RecyclerView rv;
    CartModelAdapter cartModelAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    Button buy;
    TextView totalPrice;
    public BasketFragment() {

    }

    public static BasketFragment newInstance(String param1, String param2) {
        BasketFragment fragment = new BasketFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_basket, container, false);
        rv = root.findViewById(R.id.cd_recyclerView);
        firebaseAuth =FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        cartItems = new ArrayList<>();
        cartModelAdapter = new CartModelAdapter(getActivity(),cartItems);
        rv.setAdapter(cartModelAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        totalPrice=root.findViewById(R.id.cd_amountt);


        getBasketFromFirebase();
        return root;
    }

    private void getBasketFromFirebase() {
        db.collection("carts").document(firebaseAuth.getCurrentUser().getUid()).collection("users").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if(task.isSuccessful()){
                         for (DocumentSnapshot dc :task.getResult().getDocuments()) {
                             CartModel cm=dc.toObject(CartModel.class);
                             cartItems.add(cm);

                            }
                        }
                    }
                });
    }
}