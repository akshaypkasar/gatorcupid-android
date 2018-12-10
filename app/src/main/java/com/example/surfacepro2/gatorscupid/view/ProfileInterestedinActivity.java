package com.example.surfacepro2.gatorscupid.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.example.surfacepro2.gatorscupid.R;
import com.example.surfacepro2.gatorscupid.model.User;

public class ProfileInterestedinActivity extends AppCompatActivity {

    Spinner interestedInSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_interestedin);
    }

    public void onContinueClick(View view) {
        Log.i("GatorCupid","ProfileInterestedinActivity>>onContinueClick: ContinueButton Clicked!");

        interestedInSpinner = findViewById(R.id.interestedinSpinner);
        Integer interestedIn = interestedInSpinner.getSelectedItemPosition();

        //set values to the user object received from MainActivity
        User user = (User)getIntent().getSerializableExtra("user");
        user.setInterestedIn(interestedIn);

        //Call next screen by ProfileGender Activity
        Intent intent = new Intent(getApplicationContext(), ProfilePicActivity.class);
        //passing updated user object to next activity
        intent.putExtra("user",user);
        startActivityForResult(intent, 0);

    }
}
