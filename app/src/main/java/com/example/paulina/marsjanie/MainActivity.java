package com.example.paulina.marsjanie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity {

    @BindView(R.id.dateOfPhoto)
    TextView dateOfPhoto;
    @BindView(R.id.dateOfPhotoText)
    TextView getDateOfPhotoText;
    @BindView(R.id.checkDifferentDate)
    RelativeLayout checkDifferentDate;
    @BindView(R.id.checkDifferentDateLabel)
    TextView checkDifferentDateLabel;

    TextView txtString;
    String jeden = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=";
    String data = "2017-5-22";
    String trzy ="&api_key=lWqRwWGkHJLzY95i2w4plNwIlKFxHykD6gePltTC";
    public String url;
    ArrayList<HashMap<String, String>> contactList;
    String userName = "cos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        userName = getIntent().getExtras().getString("id");
        data = userName;
        url = jeden + data +trzy;
        Log.e("cos", userName);
        Log.e("url", url);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupButtons();
        setupFonts();

        contactList = new ArrayList<>();
        txtString = (TextView) findViewById(R.id.txtString);

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupButtons() {
        checkDifferentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainActivity.this, Calendar.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    private void setupFonts(){
        Typeface pangolinTF = Typeface.createFromAsset(getAssets(),"Pangolin-Regular.ttf");
        dateOfPhoto.setTypeface(pangolinTF);
        getDateOfPhotoText.setTypeface(pangolinTF);
        checkDifferentDateLabel.setTypeface(pangolinTF);
    }

    void run() throws IOException {

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

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String id = null;

                        if (myResponse != null) {
                            try {
                                JSONObject jsonObj = new JSONObject(myResponse);

                                // Getting JSON Array node
                                JSONArray contacts = jsonObj.getJSONArray("photos");

                                // looping through All Contacts
                                for (int i = 0; i < contacts.length(); i++) {
                                    JSONObject c = contacts.getJSONObject(i);

                                    id = c.getString("img_src");
                                    String name = c.getString("name");
                                    String email = c.getString("email");
                                    String address = c.getString("address");
                                    String gender = c.getString("gender");

                                    // Phone node is JSON Object
                                    JSONObject phone = c.getJSONObject("phone");
                                    String mobile = phone.getString("mobile");
                                    String home = phone.getString("home");
                                    String office = phone.getString("office");

                                    // tmp hash map for single contact
                                    HashMap<String, String> contact = new HashMap<>();

                                    // adding each child node to HashMap key => value
                                    contact.put("id", id);
                                    contact.put("name", name);
                                    contact.put("email", email);
                                    contact.put("mobile", mobile);

                                    // adding contact to contact list
                                    contactList.add(contact);
                                }
                            } catch (final JSONException e) {
                                Log.e("Couldn't", "Json parsing error: " + e.getMessage());

                            }
                        } else {
                            Log.e("Couldn't", "Couldn't get json from server.");
                        }
                        new DownloadImageTask((ImageView) findViewById(R.id.photoFromMars))
                                .execute(id);

                        if(id == null)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),
                                            "Unfortunetelly, there is no photo from this day yet",
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                            });
                        }
                        dateOfPhoto.setText(data);
                    }
                });
            }
        });
    }
}

