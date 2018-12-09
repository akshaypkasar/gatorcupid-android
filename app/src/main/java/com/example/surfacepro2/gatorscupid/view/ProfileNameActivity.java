package com.example.surfacepro2.gatorscupid.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.surfacepro2.gatorscupid.R;

public class ProfileNameActivity extends AppCompatActivity {

    EditText nameText;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_name);
    }

    public void onContinueClick(View view) {
        Log.i("GatorCupid","ProfileNameActivity>>onContinueClick: ContinueButton Clicked!");

        nameText = findViewById(R.id.nameEditText);
        if(nameText.getText().toString().isEmpty()) {
            nameText.setHint("Enter email");
            nameText.setHintTextColor(Color.RED);
            return;
        }

    }
}
