package com.example.paulina.marsjanie;

import android.content.Intent;
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
    TextView dateOfPhotoText;
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
    public String date;
    ArrayList<String> photoLinksList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        photoLinksList = extras.getStringArrayList("photoLink");
        date = extras.getString("date");

        initPager();
        setupButtons();
    }

    private void setupButtons() {
        checkDifferentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, CalendarActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        dateOfPhoto.setText(date);
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
            return galleryPhotoFragments.get(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}

