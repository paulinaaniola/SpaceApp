package com.example.paulina.marsjanie;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryPhotoFragment extends Fragment{

    private String linkToPhoto;

    @BindView(R.id.gallery_element_image_view)
    ImageView galleryElementImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_photo_fragment, container, false);
        ButterKnife.bind(this, view);

        super.onActivityCreated(savedInstanceState);

        downloadPhoto(getContext());

        return view;
    }

    public void setLink(String linkToPhoto){
        this.linkToPhoto = linkToPhoto;
    }

    public void downloadPhoto(Context context){
        Picasso.with(context).load(linkToPhoto).into(galleryElementImageView);
    }
}
