package com.example.ecom_seller.customTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class OpenSansBold extends AppCompatTextView {
    public OpenSansBold(@NonNull Context context) {
        super(context);
        setFontsTextView();
    }

    public OpenSansBold(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFontsTextView();
    }

    public OpenSansBold(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontsTextView();
    }
    private void setFontsTextView(){
        Typeface typeface = Utils.getOpensansbold(getContext());
        setTypeface(typeface);
    }
}
