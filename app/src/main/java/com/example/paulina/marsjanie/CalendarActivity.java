package com.example.paulina.marsjanie;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paulina.marsjanie.Service.ServiceManager;
import com.example.paulina.marsjanie.Service.ServiceRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarActivity extends AppCompatActivity {

    private String dzien;
    private String miesiac;
    private String rok;
    private String date;
    private long selectedDate;
    private ServiceRequest client;
    private boolean isAnyDaySelected = false;

    @BindView (R.id.simpleCalendarView)
    CalendarView Calendar;
    @BindView(R.id.showPhoto)
    ImageView showPhotoButton;
    @BindView(R.id.showPhotoLayout)
    RelativeLayout showPhotoLayout;
    @BindView(R.id.showPhotoLabel)
    TextView showPhotoLabel;
    @BindView(R.id.select_day)
    TextView selectDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);
        setupFonts();
        convertDateToString();
        setupButton();
    }

    private void setupFonts(){
        Typeface pangolinTF = Typeface.createFromAsset(getAssets(),"Pangolin-Regular.ttf");
        showPhotoLabel.setTypeface(pangolinTF);
        Typeface archivoTF = Typeface.createFromAsset(getAssets(),"ArchivoBlack-Regular.ttf");
        selectDate.setTypeface(archivoTF);
    }

    private  void getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        date = currentDateandTime;
    }
    public void convertDateToString() {
        Calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dzien = String.valueOf(dayOfMonth);
                month++;
                miesiac = String.valueOf(month);
                rok = String.valueOf(year);
                date = rok+"-"+miesiac+"-"+dzien;
                isAnyDaySelected = true;
                Log.v("cos", dzien + miesiac + rok);
            }
        });
    }

    public void setupButton() {
        showPhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String photo = "0";
                if(!isAnyDaySelected) {
                    getCurrentDate();
                    getPhoto(date);
                } else {
                    getPhoto(date);
                }
            }
        });
    }

    public void openActivityWithPhoto(ArrayList<String> photoLinksList) {
        Intent myIntent = new Intent(CalendarActivity.this, MainActivity.class);
        Bundle extras = new Bundle();
        extras.putStringArrayList("photoLink", photoLinksList);
        extras.putString("date",date);
        myIntent.putExtras(extras);
        startActivity(myIntent);
    }

    public void getPhoto(String date) {
        try {
            ServiceManager.getInstance().getPhoto(this, date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayToast()
    {
        Toast.makeText(this, "Unfortunately, there is no photos from this day", Toast.LENGTH_LONG).show();
    }
}