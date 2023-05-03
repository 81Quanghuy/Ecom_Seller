package com.example.ecom_seller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ecom_seller.R;
import com.example.ecom_seller.model.Photo;

public class PhotoFragment extends Fragment {

    private View mView;

    //Gán layout cho photo fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_photo, container, false);

        Bundle bundle = getArguments();
        Photo photo = (Photo) bundle.getSerializable("object_photo");

        ImageView imageView = mView.findViewById(R.id.img_photo_product);
        Glide.with(getActivity())
                .load(photo.getResources())
                .into(imageView);
        return mView;
    }
}
