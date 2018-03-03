package com.example.kelvin.instagramclone.utils;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by kelvin on 2/27/18.
 */

public class sqaureImageView extends AppCompatImageView {
    public sqaureImageView(Context context) {
        super(context);
    }

    public sqaureImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public sqaureImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
