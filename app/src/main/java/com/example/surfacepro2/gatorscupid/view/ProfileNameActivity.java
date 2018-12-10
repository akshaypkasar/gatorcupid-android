package com.example.surfacepro2.gatorscupid.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.surfacepro2.gatorscupid.R;
import com.example.surfacepro2.gatorscupid.model.User;

public class ProfileNameActivity extends AppCompatActivity {

    EditText nameEditText;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_name);
    }

    public void onContinueClick(View view) {
        Log.i("GatorCupid","ProfileNameActivity>>onContinueClick: ContinueButton Clicked!");

        nameEditText = findViewById(R.id.nameEditText);
        String userName = nameEditText.getText().toString();
        if(userName.isEmpty()) {
            nameEditText.setHint("Enter email");
            nameEditText.setHintTextColor(Color.RED);
            return;
        }

        //set values to the user object received from MainActivity
        User user = (User)getIntent().getSerializableExtra("user");
        user.setName(userName);

        //Call next screen by ProfileGender Activity
        Intent intent = new Intent(getApplicationContext(), ProfileGenderActivity.class);
        //passing updated user object to next activity
        intent.putExtra("user",user);
        startActivityForResult(intent, 0);

    }
}
