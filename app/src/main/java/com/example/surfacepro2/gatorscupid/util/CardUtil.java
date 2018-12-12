package com.example.surfacepro2.gatorscupid.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.surfacepro2.gatorscupid.constant.ApiUrl;
import com.example.surfacepro2.gatorscupid.constant.GcConstant;
import com.example.surfacepro2.gatorscupid.model.BrowseProfile;
import com.example.surfacepro2.gatorscupid.model.Card;
import com.example.surfacepro2.gatorscupid.model.Response;
import com.example.surfacepro2.gatorscupid.model.User;
import com.example.surfacepro2.gatorscupid.view.BrowseProfilesActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindorks.placeholderview.SwipeDirectionalView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardUtil {

    private static final String TAG = "Utils";

    public static void loadProfiles(final Context context, User user, final SwipeDirectionalView swipeView, final Point cardViewHolderSize, final Card.Callback callback) throws JSONException {

        String url = ApiUrl.API+ApiUrl.GET_BROWSE_PROFILE;
        JsonObjectRequest postRequest;
        final Gson gson = new Gson();
        final BrowseProfile browseProfile = new BrowseProfile();

        url = url.replaceAll("<id>", user.getId().toString());
        url = url.replaceAll("<id1>", GcConstant.DEFAULT_PAGE_NO.toString());
        url = url.replaceAll("<id2>", GcConstant.DEFAULT_PAGE_SIZE.toString());

        postRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(gson.toJson(user)),
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("GatorCupid","CardUtil>>loadProfiles: updateUserProfile response:"+ response.toString());

                        Response resp = gson.fromJson(response.toString(), Response.class);
                        Log.d("GatorCupid","CardUtil>>loadProfiles: response Data:"+resp.getData().toString());

                        BrowseProfile profiles = null;
                        try {
                            profiles = gson.fromJson(response.getString("data"), BrowseProfile.class);
                            browseProfile.setProfiles(profiles.getProfiles());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        initializeSwipeCards(profiles.getProfiles(), swipeView,  context, cardViewHolderSize, callback);
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if(error != null) {
                            Log.d("GatorCupid",  "CardUtil>>loadProfiles: updateUserProfile ERROR response:"+error.networkResponse.data.toString());
                            Response resp = gson.fromJson(new String(error.networkResponse.data), Response.class);
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

        VolleyUtil.getInstance(context).getRequestQueue().add(postRequest);

    }

    private static void initializeSwipeCards(List<User> profileList, SwipeDirectionalView swipeView, Context context, Point cardViewHolderSize, Card.Callback callback) {
        for (User profile : profileList) {
            swipeView.addView(new Card(context, profile, cardViewHolderSize, callback));
        }
    }

    private static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json = null;
        InputStream is = null;
        try {
            AssetManager manager = context.getAssets();
            Log.d(TAG, "path " + jsonFileName);
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static Point getDisplaySize(WindowManager windowManager) {
        try {
            if (Build.VERSION.SDK_INT > 16) {
                Display display = windowManager.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display.getMetrics(displayMetrics);
                return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
            } else {
                return new Point(0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Point(0, 0);
        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
