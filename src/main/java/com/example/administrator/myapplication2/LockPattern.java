package com.example.administrator.myapplication2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/22.
 */
public class LockPattern extends RelativeLayout {
    private static final String TAG = "TAG";
    private int mItemWidth;
    private ArrayList<Lock> mLockList = new ArrayList<>();
    private ArrayList<Integer> mCheckedItems = new ArrayList<>();

    private Paint mPathPaint;
    private Path mPath;

    private Point mStartPoint;
    private Point mEndPoint = new Point();

    public LockPattern(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockPattern(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPathPaint = new Paint();
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(40);
        mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        mPathPaint.setColor(Color.parseColor("#123456"));
        mPathPaint.setAlpha(60);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mLockList.size() < 1) {
            mItemWidth = (int) (getMeasuredWidth() / 6.5);
            addLocks();
        }
        for (int i = 0; i < 16; i++) {
            View v = getChildAt(i);
            v.measure(MeasureSpec.EXACTLY + mItemWidth, MeasureSpec.EXACTLY + mItemWidth);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int row, column;
        for (int i = 0; i < 16; i++) {
            row = i / 4;
            column = i % 4;
            View v = getChildAt(i);
            v.layout((int) (0.5 * mItemWidth + column * 1.5 * mItemWidth), (int) (0.5 * mItemWidth + row * 1.5 * mItemWidth),
                    (int) (2.0 * mItemWidth + column * 1.5 * mItemWidth), (int) (2.0 * mItemWidth + row * 1.5 * mItemWidth));
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawPath(mPath, mPathPaint);
        if (mStartPoint != null) {
            canvas.drawLine(mStartPoint.x, mStartPoint.y, mEndPoint.x, mEndPoint.y, mPathPaint);
        }
    }


    private void addLocks() {
        Lock l;
        for (int i = 0; i < 16; i++) {
            l = new Lock(getContext());
            mLockList.add(l);
            addView(l);
        }
    }

    private Point getLocation(int item) {
        int row, column;
        row = item / 4;
        column = item % 4;
        int x = (int) ((column * 1.5 + 1) * mItemWidth);
        int y = (int) ((row * 1.5 + 1) * mItemWidth);
//        Log.i(TAG, "item = " + item + ", x = " + x + " ,y = " + y);
        return new Point(x, y);
    }

    private int getItem(int x, int y) {
        int row, column;
        for (int i = 0; i < 16; i++) {
            row = i / 4;
            column = i % 4;
            if (x > 0.5 * mItemWidth + column * 1.5 * mItemWidth && x < 1.5 * mItemWidth + column * 1.5 * mItemWidth
                    && y > 0.5 * mItemWidth + row * 1.5 * mItemWidth && y < 1.5 * mItemWidth + row * 1.5 * mItemWidth) {
                return i;
            }
        }

        return -1;
    }

    private void reset() {
        mCheckedItems.clear();
        mPath.reset();
        for (int i = 0; i < 16; i++) {
            mLockList.get(i).setState(Lock.STATE_NORMAL);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                reset();
                break;

            case MotionEvent.ACTION_MOVE:
                int item = getItem(x, y);
                if (item != -1 && !mCheckedItems.contains(item)) {
                    mLockList.get(item).setState(Lock.STATE_PATH);
                    Point p = getLocation(item);
                    mCheckedItems.add(item);
                    Log.i(TAG, "item = " + mCheckedItems.size());
                    if (mCheckedItems.size() == 1) {
                        mPath.moveTo(p.x, p.y);
                        mStartPoint = new Point();
                        Log.i(TAG, "moveTo>>>>" + p.x + "," + p.y);
                    } else {
                        mPath.lineTo(p.x, p.y);
                        Log.i(TAG, "lineTo>>>>" + p.x + "," + p.y);
                    }
                    mStartPoint.set(p.x, p.y);
                }
                mEndPoint.set(x, y);
                break;

            case MotionEvent.ACTION_UP:
                mStartPoint = null;
                break;
        }
        invalidate();
        return true;
    }
}
