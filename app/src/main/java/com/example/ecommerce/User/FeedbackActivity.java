package com.example.ecommerce.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.ecommerce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    DatabaseReference reference;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        reference = FirebaseDatabase.getInstance().getReference().child("Feedback");
        user = FirebaseAuth.getInstance().getCurrentUser();

        HashMap<String,String> map = new HashMap();
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RatingBar simpleRatingBar =  findViewById(R.id.simpleRatingBar);
        simpleRatingBar.setNumStars(5);
        editText = findViewById(R.id.Feadback_edt);
        button = findViewById(R.id.Save_btn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float ratingNumber = simpleRatingBar.getRating();
                System.out.println("PRINTZIAD"+ratingNumber);
                map.put("Feedback",editText.getText().toString());
                map.put("Rate",String.valueOf(ratingNumber));
                reference.child("Feedback").child(user.getUid()).setValue(map);
                Intent intent = new Intent(FeedbackActivity.this,UserMainActivity.class);
                startActivity(intent);
                intent.putExtra(UserMainActivity.TAG_ACTIVITY_FROM, "fromCFOA");
            }
        });


    }
    }
