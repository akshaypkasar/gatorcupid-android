package com.example.surfacepro2.gatorscupid.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.surfacepro2.gatorscupid.R;
import com.example.surfacepro2.gatorscupid.constant.ApiUrl;
import com.example.surfacepro2.gatorscupid.constant.State;
import com.example.surfacepro2.gatorscupid.model.Response;
import com.example.surfacepro2.gatorscupid.model.User;
import com.example.surfacepro2.gatorscupid.util.CommonUtil;
import com.example.surfacepro2.gatorscupid.util.VolleyUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfilePicActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    ImageView imageView;
    String profilePicInString;
    ProgressBar progressBar;
    boolean profilePicSelected = false;
    final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic);
    }

    public void showFileChooser(View view) {

        Log.i("GatorCupid","ProfilePicActivity>>showFileChooser: ChoosePicButton Clicked!");

        Intent pickImageIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageIntent.setType("image/*");
        pickImageIntent.putExtra("aspectX", 1);
        pickImageIntent.putExtra("aspectY", 1);
        pickImageIntent.putExtra("scale", true);
        pickImageIntent.putExtra("outputFormat",
                Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST);
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       imageView = findViewById(R.id.addPicButton);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Bitmap lastBitmap = null;
                lastBitmap = bitmap;
                //encoding image to string
                profilePicInString = CommonUtil.getStringFromImage(lastBitmap);
                Bitmap bmpImg = CommonUtil.getBitMapFromString(profilePicInString);
                imageView.setImageBitmap(bmpImg);
                profilePicSelected = true;
                imageView.setBackground(null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void onDoneButtonClick(View view) throws JSONException {

        Log.i("GatorCupid","ProfilePicActivity>>onDoneButtonClick: Done Buttom Clicked!");

        if(profilePicSelected == false) {
            CommonUtil.showToast(getApplicationContext(),Toast.LENGTH_LONG,"Profile Pic cannot be empty! Please select an image.");
            return;
        }

        progressBar = findViewById(R.id.progressBaronProfilePic);
        progressBar.setVisibility(View.VISIBLE);

        //set values to the user object received from MainActivity
        User user = (User)getIntent().getSerializableExtra("user");
        user.setProfilePic(profilePicInString);

        //make update user request
        VolleyUtil.getInstance(getApplicationContext()).getRequestQueue().add(updateUserProfile(user));
    }

    private JsonObjectRequest updateUserProfile(final User user) throws JSONException {

        String url = ApiUrl.API+ApiUrl.UPDATE_USER_PROFILE;


        url = url.replaceFirst("<id>", user.getId().toString());
        Log.i("GatorCupid", "ProfilePicActivity>>updateUserProfile: Request URL:"+url);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(gson.toJson(user)),
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("GatorCupid","ProfilePicActivity>>updateUserProfile: updateUserProfile response:"+ response.toString());

                        //hide the progressbar
                        progressBar.setVisibility(View.INVISIBLE);
                        Response resp = gson.fromJson(response.toString(), Response.class);
                        CommonUtil.showToast(getApplicationContext(), Toast.LENGTH_LONG,resp.getMessage());

                        /**
                         * Navigate to browse profile screen
                         */
                        Intent intent = new Intent(getApplicationContext(), BrowseProfilesActivity.class);
                        //passing user object to next activity
                        intent.putExtra("user",user);
                        startActivityForResult(intent, 0);
                    }
                },
                    new com.android.volley.Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            progressBar.setVisibility(View.INVISIBLE);
                            // error
                            if(error != null) {
                                Log.d("GatorCupid",  "ProfilePicActivity>>updateUserProfile:"+error.networkResponse.data.toString());
                                Response resp = gson.fromJson(new String(error.networkResponse.data), Response.class);
                                CommonUtil.showToast(getApplicationContext(), Toast.LENGTH_LONG,resp.getMessage());
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
