package com.example.ecommerce.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.Order;
import com.example.ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class    OrdersFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;
  //  private Class<?> cls;

    String model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        String type = sharedPreferences.getString("type", "");
        switch (type) {
            case "User":
                //     cls = UserProductListActivity.class;
                model = "userid";
                break;
            case "admin":
                //    cls = adminAddProductsActivity.class;
                model = "adminid";
                break;
        }


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Orders").child("Orders"), Order.class)
                        .build();



        adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {
            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
                return new OrderViewHolder(view);
            }

            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order order) {



                Glide.with(view).load(order.getProductimageUrl()).into(holder.productImage);

                holder.orderid.setText("Orderid : "+order.getOrderid());
                holder.timedate.setText("Ordered on :"+order.getTime()+"   "+order.getDate());
                holder.userid.setText("Userid : "+order.getUserid());
                holder.username.setText("Name : "+order.getUsername());
                holder.userphone.setText("Phone : "+order.getUserphone());
                holder.useraddress.setText("Address : "+order.getUseraddress());
                holder.productname.setText(""+order.getProductname());
                holder.productid.setText("Productid : "+order.getProductid());
                holder.productprice.setText("Price : "+order.getProductprice());
                holder.quantity.setText("Quantity : "+order.getQuantity());
                holder.totalamount.setText("Total Amount  : $"+order.getTotalamount());
//                holder.adminid.setText("adminid : "+order.getadminid());


                Log.d("theartist", "onBindViewHolder: "+order.getOrderid());





            }


        };

        recyclerView.setAdapter(adapter);

        return view;
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

    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView orderid, timedate, userid, username, userphone, useraddress,
                productname, productid, productprice, quantity,totalamount,adminid;

        ImageView productImage;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.orderid);
            timedate = itemView.findViewById(R.id.timedate);

            userid = itemView.findViewById(R.id.userid);
            username = itemView.findViewById(R.id.username);
            userphone = itemView.findViewById(R.id.userphone);
            useraddress = itemView.findViewById(R.id.useraddress);

            productname = itemView.findViewById(R.id.productname);
            productid = itemView.findViewById(R.id.productid);
            productprice = itemView.findViewById(R.id.productprice);
            quantity = itemView.findViewById(R.id.quantity);
            totalamount = itemView.findViewById(R.id.totalamount);
            adminid = itemView.findViewById(R.id.adminId);
            productImage = itemView.findViewById(R.id.productImage);




        }
    }


}


