package com.example.surfacepro2.gatorscupid.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.surfacepro2.gatorscupid.R;
import com.example.surfacepro2.gatorscupid.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfileAgeActivity extends AppCompatActivity {

    EditText ageText;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_age);

        ageText = (EditText) findViewById(R.id.ageEditText);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                view.setSpinnersShown(true);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        ageText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ProfileAgeActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ageText.setText(sdf.format(calendar.getTime()));
    }

    public void onContinueClick(View view) {
        Log.i("GatorCupid", "ProfileAgeActivity>>onContinueClick: ContinueButton Clicked!");

        //set values to the user object received from MainActivity
        User user = (User) getIntent().getSerializableExtra("user");
        user.setBirthYear(ageText.getText().toString());

        //Call next screen by ProfileGender Activity
        Intent intent = new Intent(getApplicationContext(), ProfileInterestedinActivity.class);
        //passing updated user object to next activity
        intent.putExtra("user", user);
        startActivityForResult(intent, 0);

    }


}