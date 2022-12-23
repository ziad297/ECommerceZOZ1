package com.example.ecommerce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.Fragments.FeedBackFragment;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;


public class UpdateUserInfoActivity extends AppCompatActivity {

    public static final String TAG_ACTIVITY_FROM = "WhichActivity";
    EditText etName, etPhoneNumber, etState, etCity, etAddress;
    Button saveButton;
    String productprice;
    FloatingActionButton locationFAB;
    FirebaseUser fuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        init();
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            return;
        } else {


            etState.setText(bundle.getString("state"));
            etCity.setText(bundle.getString("city"));
        }


        fuser = FirebaseAuth.getInstance().getCurrentUser();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String number = etPhoneNumber.getText().toString();
//                String email = etEmail.getText().toString();
                String state = etState.getText().toString();
                String city = etCity.getText().toString();
                String address = etAddress.getText().toString();
                updateInfo(name, number, state, city, address);

            }
        });

        locationFAB.setOnClickListener(view -> {
            startActivity(new Intent(UpdateUserInfoActivity.this, MapsActivity.class));
        });

    }

    private void updateInfo(String name, String number, String state, String city, String address) {
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(number) && TextUtils.isEmpty(city) && TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("number", number);
            //  map.put("email",email);
            map.put("address", address);
            map.put("city", city);

            FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    startActivity(new Intent(UpdateUserInfoActivity.this, ConfirmFinalOrderActivity.class)
                            .putExtra(ConfirmFinalOrderActivity.TAG_ACTIVITY_FROM, "From Cart")
                            .putExtra("totalamount", productprice));
                }
            });


        }
    }

    private void init() {

        etName = findViewById(R.id.et_name);
        etPhoneNumber = findViewById(R.id.et_phoneNumber);
        // etEmail = findViewById(R.id.et_email);
        etState = findViewById(R.id.et_state);
        etCity = findViewById(R.id.et_city);
        etAddress = findViewById(R.id.et_address);
        saveButton = findViewById(R.id.button_saveAddress);
        locationFAB = findViewById(R.id.loc_btn);

    }


}