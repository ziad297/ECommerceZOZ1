package com.example.ecommerce.Admin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Fragments.AdminProductsFragment;
import com.example.ecommerce.Fragments.ModelProfileFragment;
import com.example.ecommerce.Fragments.OrdersFragment;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.Model.admin;
import com.example.ecommerce.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminMainActivity extends AppCompatActivity {
    final String TAG = "theartist";

    CircleImageView mProfileImage;
    TextView mUsername;
    Toolbar mToolbar;

    FirebaseUser fUser;
    DatabaseReference adminRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mProfileImage = findViewById(R.id.profile_image);
        mUsername = findViewById(R.id.adminName);


        fUser = FirebaseAuth.getInstance().getCurrentUser();
        adminRef = FirebaseDatabase.getInstance().getReference().child("Admin").child(fUser.getUid());
        System.out.println("USERID"+fUser.getUid());
        System.out.println("USERID"+fUser.getUid());
        System.out.println("USERID"+fUser.getUid());
        System.out.println("USERID"+fUser.getUid());
        System.out.println("USERID"+fUser.getUid());

        adminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               admin admin = dataSnapshot.getValue(admin.class);
                mUsername.setText(admin.getName());


                    mProfileImage.setImageResource(R.mipmap.ic_launcher);
                if (!admin.getImageUrl().equals("default")){

                    Glide.with(getApplicationContext()).load(admin.getImageUrl()).into(mProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

       ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
       viewPagerAdapter.addFragments(new OrdersFragment(),"Orders");
      viewPagerAdapter.addFragments(new AdminProductsFragment(),"My Products");
       viewPagerAdapter.addFragments(new ModelProfileFragment(),"Profile");



        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String>   titles;

        ViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragments(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //status("Online");
    }

//    private void status(String status) {
//
//        HashMap<String,Object> map = new HashMap<>();
//        map.put("status",status);
//        FirebaseDatabase.getInstance().getReference("admins").child(FirebaseAuth.getInstance().getUid()).updateChildren(map);
//    }

    @Override
    protected void onPause() {
        super.onPause();
        //status("offline");
    }

}