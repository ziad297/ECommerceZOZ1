package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ecommerce.Admin.RepoLoginActivity;
import com.example.ecommerce.Admin.AdminPanelActivity;
import com.example.ecommerce.Admin.AdminLoginActivity;
import com.example.ecommerce.Admin.AdminMainActivity;
import com.example.ecommerce.User.UserLoginActivity;
import com.example.ecommerce.User.UserMainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private ImageView mIconImage;
    private TextView mModeText;
    private LinearLayout mLinearLayout;
    private CardView reportCard, AdminCard,userCard;
    private ConstraintLayout layout;

    private static final int RED = 0xffFF8080;
    private static final int BLUE = 0xff8080FF;

    private static final int GREEN = 0xff45CE30;


    private static final int WHITE = 0xffFFFFFF;
    private static final int GREY = 0xffecf0f1;

    private static final int C1 = 0xff47535E;
    private static final int C2 = 0xff758AA2;
    private static final int C3 = 0xff487EB0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        layout = findViewById(R.id.mainStart);


        init();
      // initialAnimation();






        reportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, RepoLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        AdminCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, AdminLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, UserLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        }


    private void init() {
        mModeText = findViewById(R.id.textView_mode);
        mIconImage = findViewById(R.id.imageViewIconImage);
        mLinearLayout  = findViewById(R.id.linearLayout);
        reportCard = findViewById(R.id.Report);
        AdminCard = findViewById(R.id.Admin);
        userCard = findViewById(R.id.Customer);
    }

//    private void initialAnimation() {
//        mLinearLayout.animate().alpha(0f).setDuration(1);
//        TranslateAnimation translateAnimation =new TranslateAnimation(0,0,0,400);
//
//        translateAnimation.setDuration(3200);
//        translateAnimation.setFillAfter(false);
//        translateAnimation.setAnimationListener(new MyAnimationListner());
//
//
//
//        mIconImage.setAnimation(translateAnimation);
//    }


//    private class MyAnimationListner implements Animation.AnimationListener {
//        @Override
//        public void onAnimationStart(Animation animation) {
//
//        }
//
//        @Override
//        public void onAnimationEnd(Animation animation) {
//           // layout.setBackgroundColor(ContextCompat.getColor(StartActivity.this, R.color.colorAccent));
//
//
//            mModeText.setVisibility(View.GONE);
//            mIconImage.clearAnimation();
//            mIconImage.setVisibility(View.GONE);
//            mLinearLayout.animate().alpha(1f).setDuration(1500);
//
//            //backgroundAnimation();
//
//
//        }
//
//        @Override
//        public void onAnimationRepeat(Animation animation) {
//
//        }
//    }

//    private void backgroundAnimation() {
//        ValueAnimator colorAnim = ObjectAnimator.ofInt(layout, "backgroundColor", WHITE,C3);
//        colorAnim.setDuration(5000);
//        colorAnim.setEvaluator(new ArgbEvaluator());
//        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
//        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
//        colorAnim.start();
//    }


    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() !=null){

            SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);


               String type = sharedPreferences.getString("type","");


            if (type.equals("User")){
                   startActivity(new Intent(StartActivity.this , UserMainActivity.class));
                   finish();
               }else if(type.equals("Admin")){
                   startActivity(new Intent(StartActivity.this , AdminMainActivity.class));
                   finish();

               }else if(type.equals("Report")){
                startActivity(new Intent(StartActivity.this , AdminPanelActivity.class));
                finish();

            }else if(type.equals("none")){

            }



//            FirebaseDatabase.getInstance().getReference().child("User").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        if (snapshot.getKey().toString().equals(FirebaseAuth.getInstance().getUid())){
//                            String type ="User";
//
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//            if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("testadmin@gmail.com")){
//                startActivity(new Intent(StartActivity.this , AdminPanelActivity.class));
//                finish();
//            }else if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@gmail.com")){
//                startActivity(new Intent(StartActivity.this , adminMainActivity.class));
//                finish();
//            }
//            else{
//                startActivity(new Intent(StartActivity.this , MainActivity.class));
//                finish();
//            }

        }

    }
}