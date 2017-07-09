package com.example.paulina.marsjanie;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.app_title_text_view)
    TextView appTitleTV;
    @BindView(R.id.app_subtitle_text_view)
    TextView appSubtitleTV;

    private final int SPLASH_DISPLAY_LENGTH = 1500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initLogoFont();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this,Calendar.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void initLogoFont(){
        Typeface pangolinTF = Typeface.createFromAsset(getAssets(),"Pangolin-Regular.ttf");
        appTitleTV.setTypeface(pangolinTF);
        appSubtitleTV.setTypeface(pangolinTF);
    }
}