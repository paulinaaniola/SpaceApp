package com.example.paulina.marsjanie.Service;

import android.app.Activity;
import android.util.Log;

import com.example.paulina.marsjanie.CalendarActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ServiceRequest {

    private String urlStart;
    private String urlEnd;
    private String finalPhoto;
    private boolean isPhotoDownloaded = false;

    public ServiceRequest() {
        urlStart = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=";
        urlEnd ="&api_key=lWqRwWGkHJLzY95i2w4plNwIlKFxHykD6gePltTC";
        finalPhoto = "0";
    }

   public void getPhoto(final Activity activity, String date) throws IOException {
        String url = urlStart + date + urlEnd;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();
                String link;
                final ArrayList<String> photoLinksList = new ArrayList<String>();

                if (myResponse != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(myResponse);
                        JSONArray photos = jsonObj.getJSONArray("photos");
                        for(int i =0; i<20; i++) {
                            JSONObject c = photos.getJSONObject(i);
                            photoLinksList.add(c.getString("img_src"));
                        }
                    } catch (final JSONException e) {
                        Log.e("Couldn't", "Json parsing error: " + e.getMessage());
                    }
                } else {
                    Log.e("Couldn't", "Couldn't get json from server.");
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(photoLinksList.size() != 0) {
                            ((CalendarActivity) activity).openActivityWithPhoto(photoLinksList);
                        } else {
                            ((CalendarActivity) activity).displayToast();
                        }
                    }
                });

            }
        });
    }
}
