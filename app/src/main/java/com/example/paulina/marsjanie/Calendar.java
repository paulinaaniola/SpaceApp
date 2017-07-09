package com.example.paulina.marsjanie;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;
import static com.example.paulina.marsjanie.R.id.showPhotoLayout;
import static com.example.paulina.marsjanie.R.id.simpleCalendarView;

/**
 * Created by Paulina on 29.05.2017.
 */


public class Calendar extends AppCompatActivity {

    public String dzien;
    public String miesiac;
    public String rok;
    public String date;
    long selectedDate;


    @BindView (R.id.simpleCalendarView)
    CalendarView Calendar;
    @BindView(R.id.showPhoto)
    ImageView showPhotoButton;
    @BindView(R.id.showPhotoLayout)
    RelativeLayout showPhotoLayout;
    @BindView(R.id.showPhotoLabel)
    TextView showPhotoLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        ButterKnife.bind(this);
        setupFonts();
        getCurrentDate();
        doSmth();
        setupButton();
    }

    private void setupFonts(){
        Typeface pangolinTF = Typeface.createFromAsset(getAssets(),"Pangolin-Regular.ttf");
        showPhotoLabel.setTypeface(pangolinTF);
    }

  private  void getCurrentDate(){
      long dateLong = Calendar.getDate();
      date = String.valueOf(dateLong);
  }
    public void doSmth() {


           // selectedDate = Calendar.getDate();
           // Date currentDate = new Date(selectedDate);
           // DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       // CalendarView Calendar = (CalendarView) findViewById(R.id.simpleCalendarView);
            Calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    dzien = String.valueOf(dayOfMonth);
                    month++;
                    miesiac = String.valueOf(month);
                    rok = String.valueOf(year);
                    date = rok+"-"+miesiac+"-"+dzien;
                    Log.v("cos", dzien + miesiac + rok);
                }
            });
    }

    public void setupButton()
    {
        showPhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(Calendar.this, MainActivity.class)
                        .putExtra("id", date);
                Calendar.this.startActivity(myIntent);
            }
        });
    }
}