package com.example.administrator.myapplication2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Administrator on 2016/3/22.
 */
public class Lock extends View {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_PATH = 1;
    public static final int STATE_CORRECT = 2;
    public static final int STATE_INCORRECT = 3;

    private Paint mPaint;
    private int mWidth;
    private int mRadius;
    private int mState = STATE_NORMAL;

    public Lock(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = mWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mState) {
            case STATE_NORMAL:
                mPaint.setColor(Color.parseColor("#ff0000"));
                break;
            case STATE_PATH:
                mPaint.setColor(Color.parseColor("#00ff00"));
                break;
            case STATE_CORRECT:
                mPaint.setColor(Color.parseColor("#0000ff"));
                break;
            case STATE_INCORRECT:
                mPaint.setColor(Color.parseColor("#123456"));
                break;
        }
        canvas.drawCircle(mRadius, mRadius, mRadius-1, mPaint);
        canvas.drawCircle(mRadius, mRadius, 20, mPaint);
    }

    public void setState(int state) {
        this.mState = state;
        invalidate();
    }
}
