package com.example.ecommerce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ecommerce.Fragments.Adapter.SearchProductAdapter;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchProductActivity extends AppCompatActivity {

    private EditText searchbar;
    ImageView voice;
    EditText editText;
    //private String accessType;


    private RecyclerView productsRecyclerView, categoryRecyclerView;

//    private List<String> mProducts;
//    private List<String> mProductTypes;
//
    private List<Product> mProductList;
    private  String mCategory;

    private List<String> mCategories;

    private SearchProductAdapter productAdapter, categoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        voice = findViewById(R.id.voice_search);
        editText =findViewById(R.id.etSearch);
        searchbar =findViewById(R.id.etSearch);
        productsRecyclerView = findViewById(R.id.recycler_view_products);
        productsRecyclerView.setHasFixedSize(true);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryRecyclerView = findViewById(R.id.recycler_view_category);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        mProducts = new ArrayList<>();
//        mProductTypes =new ArrayList<>();
        mProductList = new ArrayList<>();
        mCategories = new ArrayList<>();

        productAdapter = new SearchProductAdapter(this,mProductList,true);
        categoryAdapter = new SearchProductAdapter(this,mCategories);

        productsRecyclerView.setAdapter(productAdapter);
        categoryRecyclerView.setAdapter(categoryAdapter);






        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")){
//                    mProducts.clear();
//                    mProductTypes.clear();
                    mProductList.clear();
                    mCategories.clear();
                    productAdapter.notifyDataSetChanged();
                    categoryAdapter.notifyDataSetChanged();
                    Log.d("theartist", "changed 21232243234243 " +charSequence);
                }
                else{
                searchProduct(charSequence.toString());
                searchCategory(charSequence.toString().toLowerCase());
                Log.d("theartist", "changed 2   1232243234243 " +charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySpeechRecognizer();
            }
        });

    }

    private void searchCategory(String s) {


            Query query = FirebaseDatabase.getInstance().getReference().child("Products")
                    .orderByChild("category").startAt(s).endAt(s + "\uf8ff");

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mCategories.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        mCategories.add(product.getCategory());

                    }
                    Set<String> set = new HashSet<>(mCategories);   //removes duplicate categories
                    mCategories.clear();
                    mCategories.addAll(set);

                    categoryAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



    }

    private void searchProduct(String s) {


            Query query = FirebaseDatabase.getInstance().getReference().child("Products")
                    .orderByChild("name").startAt(s).endAt(s + "\uf8ff");

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    mProductList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);

                        mProductList.add(product);
                    }
                    productAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("theartist", "onStart: Umain ");
        status("Online : Searching Product");
    }

    private void status(String status) {

        HashMap<String,Object> map = new HashMap<>();
        map.put("status",status);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(map);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("theartist", "onPause: Umain ");
        status("offline");
    }
    private static final int SPEECH_REQUEST_CODE = 0;

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, SPEECH_REQUEST_CODE);

    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            editText.setText(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}