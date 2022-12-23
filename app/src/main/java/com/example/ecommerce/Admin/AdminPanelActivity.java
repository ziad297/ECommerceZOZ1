package com.example.ecommerce.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ecommerce.Fragments.AdminHomeFragment;
import com.example.ecommerce.Fragments.AdminVerifyProductsFragment;
import com.example.ecommerce.Fragments.AdminViewFragment;
import com.example.ecommerce.Fragments.AdminViewUsersFragment;
import com.example.ecommerce.Fragments.ChartFragment;
import com.example.ecommerce.Fragments.FeedBackFragment;
import com.example.ecommerce.Fragments.OrdersFragment;
import com.example.ecommerce.Fragments.ReciveFeedbackFragement;
import com.example.ecommerce.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class AdminPanelActivity extends AppCompatActivity {


    private final String TAG = "theartist";
    DatabaseReference mRootRef;
    FirebaseAuth mAuth;
    //Texte

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AdminHomeFragment()).commit();
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (savedInstanceState == null){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new ReciveFeedbackFragement()).commit();
//                    Intent i = new Intent(AdminPanelActivity.this,FeedBackFragment.class);
//                    startActivity(i);
                }
                return true;
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectorFragment = new AdminHomeFragment();
                        break;
                    case R.id.nav_order:
                        selectorFragment = new OrdersFragment();
                        break;
                    case R.id.nav_product:
                        selectorFragment = new AdminVerifyProductsFragment();
                        break;

                    case R.id.nav_user:
                        selectorFragment = new AdminViewUsersFragment();
                        break;
                    case R.id.nav_chart:
                        selectorFragment = new ChartFragment();
                        break;
//                     case R.id.nav_feedback:
//                    selectorFragment = new FeedBackFragment();
//                        break;
                }
                if (selectorFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectorFragment).commit();
                }
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
}
