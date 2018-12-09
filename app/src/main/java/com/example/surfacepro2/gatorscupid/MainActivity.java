package com.example.surfacepro2.gatorscupid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.surfacepro2.gatorscupid.constant.ApiUrl;
import com.example.surfacepro2.gatorscupid.constant.State;
import com.example.surfacepro2.gatorscupid.model.Response;
import com.example.surfacepro2.gatorscupid.model.User;
import com.example.surfacepro2.gatorscupid.util.VolleyUtil;
import com.example.surfacepro2.gatorscupid.view.ProfileNameActivity;
import com.example.surfacepro2.gatorscupid.view.SwipeActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    Button signupButton;
    ProgressBar progressBar;
    View viewForFading;
    EditText emailAddress;
    Spinner domainSpinner;
    EditText password;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        signupButton = findViewById(R.id.signupButton);
        emailAddress = findViewById(R.id.emailInputText);
        gson = new Gson();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Method on click of signup button
     * @param view
     */
    public void onSignupClick(View view){

        // to highlight email box wth red border on null input
        emailAddress = findViewById(R.id.emailInputText);
        domainSpinner = findViewById(R.id.spinner);
        if(emailAddress.getText().toString().isEmpty()){
            emailAddress.setHint("Enter email");
            emailAddress.setHintTextColor(Color.RED);
            return;
        }

        // progressbar is made visible and fade background when loading
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //fade background
        viewForFading = findViewById(R.id.fadebackground);
        viewForFading.setVisibility(View.VISIBLE);

        Log.i("GatorCupid","MainActivity>>onSignupClick: signupButtom Clicked!");

        // using volley to make register user request.
        String email = emailAddress.getText().toString() + domainSpinner.getSelectedItem().toString();
        Log.i("GatorCupid","MainActivity>>onSignupClick: Entered email:"+email);
        VolleyUtil.getInstance(getApplicationContext()).getRequestQueue().add(registerEmail(email));
    }



    /**
     * Method to show simple dialogue
     * @param title
     * @param message
     */
    private void showDialogue(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(title)
                .setMessage(message);

        AlertDialog dlg = builder.create();
        dlg.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("GatorCupid","MainActivity>>showDialogue: Ok button pressed");
            }
        });
        dlg.show();
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    /**
     * Method to make register email request to server
     * @param email
     * @return
     */
    private JsonObjectRequest registerEmail(final String email){
        //make server request to send emailaddress
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", email);


        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, ApiUrl.API+ApiUrl.SIGNUP, new JSONObject(postParam),
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("GatorCupid", "MainActivity>>registerEmail"+response.toString());

                        //hide the progressbar
                        progressBar.setVisibility(View.INVISIBLE);
                        viewForFading.setVisibility(View.INVISIBLE);
                        //show dialogue
                        //TODO: Fix the dialogues not showing.
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext()).setTitle(getString(R.string.signup_request_dialogue_title))
                                .setMessage(getString(R.string.signup_request_dialogue_message));

                        AlertDialog dlg = builder.create();
                        dlg.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("GatorCupid","Ok button pressed");
                            }
                        });
                        dlg.show();*/
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.INVISIBLE);
                        viewForFading.setVisibility(View.INVISIBLE);
                        // error
                        if(error != null) {
                            Log.d("GatorCupid", "MainActivity>>registerEmail" + new String(error.networkResponse.data));

                            Response resp = gson.fromJson(new String(error.networkResponse.data), Response.class);
                            showToast(resp.getMessage().toString());
                            //TODO: Fix the dialogues not showing
                           /* AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext()).setTitle(getString(R.string.error_dialogue_title))
                                    .setMessage(getString(R.string.error_dialogue_message));

                            AlertDialog dlg = builder.create();
                            dlg.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.i("GatorCupid","Ok button pressed");

                                }
                            });
                            dlg.show();*/

                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        return postRequest;
    }


    /**
     * Method to be called on click of signin button
     * @param view
     */
    public void onSigninClick(View view){

        // to highlight email box and password wth red hint on null input
        emailAddress = findViewById(R.id.emailInputText);
        domainSpinner = findViewById(R.id.spinner);
        password = findViewById(R.id.passwordText);

        if(emailAddress.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            emailAddress.setHint("Enter email");
            emailAddress.setHintTextColor(Color.RED);
            password.setHint("Enter Password");
            emailAddress.setHintTextColor(Color.RED);
            return;
        }

        // progressbar is made visible and fade background when loading
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //fade background
        viewForFading = findViewById(R.id.fadebackground);
        viewForFading.setVisibility(View.VISIBLE);

        Log.i("GatorCupid","MainActivity>>onSigninClick: signinButtom Clicked!");

        // using volley to make register user request.
        String email = emailAddress.getText().toString() + domainSpinner.getSelectedItem().toString();
        String passwordText = password.getText().toString();

        Log.i("GatorCupid","MainActivity>>onSigninClick: Entered email:"+email + "->Entered Password:"+password);
        VolleyUtil.getInstance(getApplicationContext()).getRequestQueue().add(signIn(email, passwordText));
    }

    /**
     * Method to make volley request
     * @param email
     * @param passwordText
     */
    private JsonObjectRequest signIn(final String email, String passwordText) {

        //make server request to send emailaddress and password
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", email);
        postParam.put("code", passwordText);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, ApiUrl.API+ApiUrl.SIGNIN, new JSONObject(postParam),
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("GatorCupid","MainActivity>>signIn: signin response:"+ response.toString());

                        //hide the progressbar
                        progressBar.setVisibility(View.INVISIBLE);
                        viewForFading.setVisibility(View.INVISIBLE);
                        Response resp = gson.fromJson(response.toString(), Response.class);
                        showToast(resp.getMessage().toString());

                        /**
                         * Navigate to user profile info screen
                         */
                        Log.i("GatorCupid","MainActivity>>signIn:"+resp.getData().toString());

                        User user = null;
                        try {
                            user = gson.fromJson(response.getString("data"), User.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Log.i("GatorCupid","Is Profile created:" + user.getIsProfileCreated().getValue());
                        if(user.getIsProfileCreated() == State.FALSE.getValue()) {
                            Intent intent = new Intent(getApplicationContext(), ProfileNameActivity.class);
                            //passing user object to next activity
                            intent.putExtra("user",user);
                            startActivityForResult(intent, 0);
                        }else if (user.getIsProfileCreated() == State.TRUE.getValue()) {
                            Intent intent = new Intent(getApplicationContext(), SwipeActivity.class);
                            //passing user object to next activity
                            intent.putExtra("user",user);
                            startActivityForResult(intent, 0);
                        }

                        //show dialogue
                        //TODO: Fix the dialogues not showing.
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext()).setTitle(getString(R.string.signup_request_dialogue_title))
                                .setMessage(getString(R.string.signup_request_dialogue_message));

                        AlertDialog dlg = builder.create();
                        dlg.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("GatorCupid","Ok button pressed");
                            }
                        });
                        dlg.show();*/


                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.INVISIBLE);
                        viewForFading.setVisibility(View.INVISIBLE);
                        // error
                        if(error != null) {
                            Log.d("GatorCupid",  "MainActivity>>signIn:"+error.networkResponse.data.toString());
                            Response resp = gson.fromJson(new String(error.networkResponse.data), Response.class);
                            showToast(resp.getMessage());
                            //TODO: Fix the dialogues not showing.
                            /* AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext()).setTitle(getString(R.string.error_dialogue_title))
                                    .setMessage(getString(R.string.error_dialogue_message));

                            AlertDialog dlg = builder.create();
                            dlg.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.i("GatorCupid","Ok button pressed");

                                }
                            });
                            dlg.show();*/
                           // clear the contents on error
                           emailAddress.setText("");
                           password.setText("");
                           showToast(getString(R.string.error_invalid_credentials));
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        return postRequest;
    }
}
