package com.a1440707245.mwh.naplayerdemo;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by Modona on 2016/12/10.
 */

public class MyVideoView extends VideoView {
    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context) {
        super(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        this.getHolder().setFixedSize(width,height);//设置分辨率
        setMeasuredDimension(width, height);
    }
}
