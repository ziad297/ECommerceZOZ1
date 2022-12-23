package com.example.ecommerce.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.ecommerce.R;
import com.example.ecommerce.User.ConfirmFinalOrderActivity;
import com.example.ecommerce.User.UserMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import hotchemi.android.rate.AppRate;

public class FeedBackFragment extends Fragment {
    EditText editText;
    Button button;
    DatabaseReference reference;
    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_back, container, false);
        reference = FirebaseDatabase.getInstance().getReference().child("Feedback");
        user = FirebaseAuth.getInstance().getCurrentUser();

        HashMap<String,String> map = new HashMap();
        RatingBar simpleRatingBar =  view.findViewById(R.id.simpleRatingBar);
        simpleRatingBar.setNumStars(5);
        editText = view.findViewById(R.id.Feadback_edt);
        button = view.findViewById(R.id.Save_btn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float ratingNumber = simpleRatingBar.getRating();
                System.out.println("PRINTZIAD"+ratingNumber);
                map.put("Feedback",editText.getText().toString());
                map.put("Rate",String.valueOf(ratingNumber));
                reference.child("Feedback").child(user.getUid()).setValue(map);
                Intent i = new Intent(getActivity(), ConfirmFinalOrderActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        return view;
    }
}