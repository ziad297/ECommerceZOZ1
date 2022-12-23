package com.example.ecommerce.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerce.Model.Order;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReciveFeedbackFragement extends Fragment{


    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recive_feedback_fragement, container, false);

        recyclerView = view.findViewById(R.id.recive_feedback);


        FirebaseRecyclerOptions<ReciveFeedbackFragement> options =
                new FirebaseRecyclerOptions.Builder<ReciveFeedbackFragement>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Feedback").child("Feedback"), ReciveFeedbackFragement.class)
                        .build();



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Feedback").child("Feedback");
        adapter = new FirebaseRecyclerAdapter<ReciveFeedbackFragement,MyViewHolder>(options){


            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull ReciveFeedbackFragement model) {

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_feedback, parent, false);
                return new MyViewHolder(view);
            }


        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        return view;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView UserName , Feedback, Rate;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            UserName = itemView.findViewById(R.id.UserName);
            Feedback = itemView.findViewById(R.id.Feedback);
            Rate = itemView.findViewById(R.id.Rate);
            cardView = itemView.findViewById(R.id.feed_card);


        }
    }
}