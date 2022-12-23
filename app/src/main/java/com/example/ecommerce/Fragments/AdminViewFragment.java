package com.example.ecommerce.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.admin;
import com.example.ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class AdminViewFragment extends Fragment {


    RecyclerView recyclerView;

    FirebaseRecyclerAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_view_sellers, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("admins");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        recyclerView = view.findViewById(R.id.admin_recycler_view);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query= FirebaseDatabase.getInstance().getReference().child("admins");



        FirebaseRecyclerOptions<admin> options =
                new FirebaseRecyclerOptions.Builder<admin>()
                        .setQuery(query, admin.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<admin, adminViewHolder>(options) {

            @Override
            public adminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item, parent, false);

                return new adminViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(adminViewHolder holder, int position, admin admin) {

                holder.adminid.setText(admin.getId());

                holder.adminname.setText(admin.getName());
                holder.adminnumber.setText(String.valueOf(admin.getNumber()));
                holder.adminemail.setText(admin.getEmail());
                holder.adminaddress.setText(admin.getAddress());
                holder.admincity.setText(admin.getCity());
                holder.adminstate.setText(admin.getState());

                Glide.with(getContext()).load(admin.getImageUrl()).into(holder.adminimage);


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
}
class adminViewHolder extends RecyclerView.ViewHolder{

    TextView adminid,adminname,adminnumber,adminemail,adminaddress,admincity,adminstate;
ImageView adminimage;

    public adminViewHolder(@NonNull View itemView) {
        super(itemView);
        adminid = itemView.findViewById(R.id.adminId);
        adminimage = itemView.findViewById(R.id.adminimage);
        adminname = itemView.findViewById(R.id.adminname);
        adminnumber = itemView.findViewById(R.id.adminnumber);
        adminemail = itemView.findViewById(R.id.adminemail);
        adminaddress = itemView.findViewById(R.id.adminaddress);
        admincity = itemView.findViewById(R.id.admincity);
        adminstate = itemView.findViewById(R.id.adminstate);
     
    }
}
