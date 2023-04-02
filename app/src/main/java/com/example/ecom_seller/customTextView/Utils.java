package com.example.ecom_seller.customTextView;

import android.content.Context;
import android.graphics.Typeface;

public class Utils {

    //Khai báo font chữ
    private static Typeface opensansbold;

    public static Typeface getOpensansbold(Context context) {
        if(opensansbold == null){
            //khởi tạo
            opensansbold = Typeface.createFromAsset(context.getAssets(),
                    "fonts/OpenSans-Bold.ttf");
        }
        return opensansbold;
    }

    public static void setOpensansbold(Typeface opensansbold) {
        Utils.opensansbold = opensansbold;
    }


}
