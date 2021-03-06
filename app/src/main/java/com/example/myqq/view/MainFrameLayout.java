package com.example.myqq.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * 装不同fragment的FrameLayout
 * Created by 97210 on 2/28/2018.
 */

public class MainFrameLayout extends FrameLayout {
    private float startX;
    private float startY;
    public MainFrameLayout(Context context) {
        super(context);
    }

    public MainFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final String TAG = "MainFrameLayout";

//    public boolean onInterceptTouchEvent(MotionEvent event) {
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.i(TAG, "ACTION_DOWN: ACTION_DOWN");
//                startX = event.getX();
//                startY = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i(TAG, "ACTION_MOVE: ACTION_MOVE");
//                float xOffset = event.getX() - startX;
//                float yOffset = event.getY() - startY;
////                Log.i(TAG, "xOffset: xOffset " + xOffset);
////                Log.i(TAG, "yOffset: yOffset " + yOffset);
//                if (Math.abs(xOffset) > Math.abs(yOffset) && xOffset > 0) {
//                    Log.i(TAG, "onInterceptTouchEvent: true");
//                    return true;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.i(TAG, "ACTION_UP: ACTION_UP");
//                break;
//        }
//        return super.onInterceptTouchEvent(event);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ");
        return false;
    }
}
