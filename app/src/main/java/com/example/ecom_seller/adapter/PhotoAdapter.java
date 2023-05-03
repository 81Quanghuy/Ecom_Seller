package com.example.ecom_seller.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ecom_seller.fragment.PhotoFragment;
import com.example.ecom_seller.model.Photo;

import java.util.List;

public class PhotoAdapter extends FragmentStateAdapter {

    private List<Photo> mListPhoto;
    public PhotoAdapter(@NonNull FragmentActivity fragmentActivity, List<Photo> photos) {
        super(fragmentActivity);
        mListPhoto = photos;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Photo photo = mListPhoto.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_photo", photo);

        //set Dữ liệu cho fragment
        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(bundle);

        return photoFragment;
    }

    @Override
    public int getItemCount() {
        if(mListPhoto != null)
            return mListPhoto.size();
        return 0;
    }
}
