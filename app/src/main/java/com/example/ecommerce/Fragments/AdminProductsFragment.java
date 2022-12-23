package com.example.ecommerce.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.R;
import com.example.ecommerce.Admin.SelectProductCategoryActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.grpc.Context;

public class AdminProductsFragment extends Fragment {



    EditText searchbar;
    private Button addproduct;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;
    DatabaseReference reference;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_products, container, false);





        addproduct = view.findViewById(R.id.addProductadmin);
        searchbar = view.findViewById(R.id.searchProductsadmin);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  startActivity(new Intent(getContext(), SelectProductCategoryActivity.class));
            }
        });


        Query query=FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("adminid")
                .equalTo(FirebaseAuth.getInstance().getUid());

        reference = FirebaseDatabase.getInstance().getReference("Product");


        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .build();


         adapter = new FirebaseRecyclerAdapter<Product, MyViewHolder>(options) {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_info_item, parent, false);
                return new MyViewHolder(view);
            }

            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(MyViewHolder holder, int position, Product product) {

                holder.productName.setText("Name : "+product.getName());
                holder.productId.setText("Product Id : "+product.getProductid());
                holder.productPrice.setText("Price : $"+product.getPrice());
                holder.productDesc.setText("Description : "+product.getDescription());
                holder.productState.setText("Processing State : "+product.getState());


                Glide.with(getContext()).load(product.getImageUrl()).into(holder.productImage);
            }


        };

        recyclerView.setAdapter(adapter);





        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();
    }
}

 class MyViewHolder extends RecyclerView.ViewHolder{

    TextView productName,productState,productId,productPrice,productDesc;
    ImageView productImage;
    CardView cardView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.productName);
        productState = itemView.findViewById(R.id.productState);
        productId = itemView.findViewById(R.id.productId);
        productPrice = itemView.findViewById(R.id.productPrice);
        productDesc = itemView.findViewById(R.id.productDescription);
        productImage = itemView.findViewById(R.id.productImage);
        cardView = itemView.findViewById(R.id.Product_card);
    }
}