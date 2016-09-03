package com.example.administrator.myapplication2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/3/22.
 */
public class V extends View{
    private Paint mPaint;
    private Path mPath;
    public V(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public V(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#00ff00"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);

        mPath = new Path();
        mPath.moveTo(166,166);
        mPath.lineTo(332,300);
        mPath.lineTo(498,166);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
    }
}
