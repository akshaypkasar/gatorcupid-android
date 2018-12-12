package com.example.surfacepro2.gatorscupid.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.example.surfacepro2.gatorscupid.R;
import com.example.surfacepro2.gatorscupid.model.User;

public class ProfileGenderActivity extends AppCompatActivity {

    Spinner genderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_gender);
    }

    public void onContinueClick(View view) {
        Log.i("GatorCupid","ProfileGenderActivity>>onContinueClick: ContinueButton Clicked!");

        genderSpinner = findViewById(R.id.genderSpinner);
        Integer genderSelected = genderSpinner.getSelectedItemPosition() + 1;

        //set values to the user object received from MainActivity
        User user = (User)getIntent().getSerializableExtra("user");
        user.setGender(genderSelected);

        //Call next screen by ProfileGender Activity
        Intent intent = new Intent(getApplicationContext(), ProfileAgeActivity.class);
        //passing updated user object to next activity
        intent.putExtra("user",user);
        startActivityForResult(intent, 0);

    }
}
