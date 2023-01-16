package com.example.markett.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.markett.R;
import com.example.markett.adapters.BestSellersAdapter;
import com.example.markett.adapters.CategoryHomeAdapter;
import com.example.markett.databinding.BestSellersItemBinding;
import com.example.markett.databinding.FragmentHomeBinding;
import com.example.markett.models.BestSellersModel;
import com.example.markett.models.CategoryHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<BestSellersModel> bestSellers;
    BestSellersAdapter bestSellersAdapter;
    RecyclerView bestSellersRv;

    ArrayList<CategoryHome> categoryHome;
    CategoryHomeAdapter categoryHomeAdapter;
    RecyclerView categoryHomeRv;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //burada root olarak isimlendiriyoruz fragmentı.çünkü arama yaparken home da arasın istiyoruz
        //eğer isimlendirmezsek başka yerde arar. Bunun olmasını istemiyoruz.
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        bestSellersRv= root.findViewById(R.id.rv_best_seller);
        /*buna layout create etmemiz lazım
        getcontext() yani buluduğum contexti al, yatay ilerlemesini istediğimiz için horizontal diyorum ve tersten sıralamasını istemediğim
        için false diyorum*/
        bestSellersRv.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        /*üsttedi kod çalışmazsa
        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        llm.setOrientation((RecyclerView.HORIZONTAL));
        llm.setReverseLayout(false);
        bestsellersRv.setLayoutManager(llm);*/

        //bütün bu üsttekiler dizi için referansdı. Diziyi oluşturmamız gerekiyor..
        bestSellers = new ArrayList<>();
        bestSellersAdapter= new BestSellersAdapter(getActivity(),bestSellers);
        bestSellersRv.setAdapter(bestSellersAdapter);

        categoryHome = new ArrayList<>();
        categoryHomeAdapter= new CategoryHomeAdapter(getActivity(), categoryHome);
        categoryHomeRv=root.findViewById(R.id.rv_categories);
        categoryHomeRv.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        categoryHomeRv.setAdapter(categoryHomeAdapter);

        //artık bunları saklamak için firebase üretmemiz lazım
        getCloudData();
        return root;
    }

    //databaseden sebzeleri meyveleri çekmek için fonksiyon yazıyoruz.
    private void getCloudData() {
        db.collection("bestsellers").
                //işlem tamamlandığında çağırılacak fonk.
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //eğer veriler geldiyse
                if (task.isComplete()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Log.d("---------------", doc.getData().toString());
                        //bestsellersModel sınıfından bir nesne oluşturuyor ve o nesnenin referansını bmye atıyor.
                        //böylelikle tek tek eşleştirmeye gerek kalmadan verileri alıyor.
                        BestSellersModel bm = doc.toObject(BestSellersModel.class);
                        //şimdi aldığımız verileri bir dizinin içine koymamız gerekiyor.
                        //verileri koyduğumuzda model değişmiş demektir.
                        bestSellers.add(bm);
                        //model değiştiyse adaptör de değişmiş demektir.
                        bestSellersAdapter.notifyDataSetChanged();

                    }

                } else {
                    Log.d("---------------", "Verileri getiremedik...");
                }
            }
        });
        db.collection("categoryHome").
                //işlem tamamlandığında çağırılacak fonk.
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //eğer veriler geldiyse
                if (task.isComplete()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Log.d("---------------", doc.getData().toString());
                        //bestsellersModel sınıfından bir nesne oluşturuyor ve o nesnenin referansını bmye atıyor.
                        //böylelikle tek tek eşleştirmeye gerek kalmadan verileri alıyor.
                        CategoryHome ch = doc.toObject(CategoryHome.class);
                        //şimdi aldığımız verileri bir dizinin içine koymamız gerekiyor.
                        //verileri koyduğumuzda model değişmiş demektir.
                        categoryHome.add(ch);
                        //model değiştiyse adaptör de değişmiş demektir.
                        categoryHomeAdapter.notifyDataSetChanged();

                    }

                } else {
                    Log.d("---------------", "Verileri getiremedik...");
                }
            }
        });
    }


}