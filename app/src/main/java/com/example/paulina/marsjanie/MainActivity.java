package com.example.paulina.marsjanie;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.dateOfPhoto)
    TextView dateOfPhoto;
    @BindView(R.id.dateOfPhotoText)
    TextView getDateOfPhotoText;
    @BindView(R.id.checkDifferentDate)
    RelativeLayout checkDifferentDate;
    @BindView(R.id.checkDifferentDateLabel)
    TextView checkDifferentDateLabel;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    ScreenSlidePagerAdapter mPagerAdapter;
    private static final int NUM_PAGES = 5;
    TextView txtString;
    String jeden = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=";
    String data = "2017-5-22";
    String trzy ="&api_key=lWqRwWGkHJLzY95i2w4plNwIlKFxHykD6gePltTC";
    public String url;
    ArrayList<String> photoLinksList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        photoLinksList = getIntent().getExtras().getStringArrayList("photoLink");
        url = jeden + data +trzy;

        setupButtons();

        initPager();
        txtString = (TextView) findViewById(R.id.txtString);
    }

    private void setupPhoto() {
       // Picasso.with(this).load(photoLinksList.get(0)).into(photoMarsImageView);
    }

    private void setupButtons() {
        checkDifferentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, CalendarActivity.class);
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

    public void navigateToCalendar(){
        Intent myIntent = new Intent(MainActivity.this, CalendarActivity.class);
        MainActivity.this.startActivity(myIntent);
    }


    private void initPager() {
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), 5, photoLinksList);
        viewPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> galleryPhotoFragments;
        ScreenSlidePagerAdapter(FragmentManager fm, int numberOfPhotos, ArrayList<String> photoLinksList) {
            super(fm);
            galleryPhotoFragments = new ArrayList<>();
            for(int i=0; i<numberOfPhotos; i++){
                GalleryPhotoFragment newFragment = new GalleryPhotoFragment();
                newFragment.setLink((String) photoLinksList.get(i));
                galleryPhotoFragments.add(newFragment);
            }
        }

        @Override
        public Fragment getItem(int position) {
//            ((GalleryPhotoFragment) galleryPhotoFragments.get(position)).downloadPhoto(getApplicationContext());
            return galleryPhotoFragments.get(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}

