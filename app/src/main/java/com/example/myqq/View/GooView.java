package com.example.myqq.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;



/**
 * Created by 97210 on 3/7/2018.
 */

public class GooView extends View {
    private Paint paint;
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/android";
    private String type;
    private PointF dragCenter;
    private PointF stickyCenter;
    private PointF centerPointF;
    public GooView(Context context) {
        super(context);
        init();
    }

    public GooView(Context context, AttributeSet attrs) {
        super(context, attrs);
        type = attrs.getAttributeValue(NAMESPACE, "text");
        init();
    }

    public GooView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
        paint.setColor(Color.RED);
        dragCenter = new PointF();
        stickyCenter = new PointF();
    }

    private static final String TAG = "GooView";


    private float dragRadius = 26f;//拖拽圆的半径
    private float stickyRadius = 26f;//固定圆的半径

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        dragCenter.set(getMeasuredWidth()/2,getMeasuredHeight()/2);
        stickyCenter.set(getMeasuredWidth()/2,getMeasuredHeight()/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: dragCenter"+dragCenter.x+" " + dragCenter.y);
        canvas.drawCircle(dragCenter.x,dragCenter.y,dragRadius,paint);
        canvas.drawCircle(stickyCenter.x, stickyCenter.y,stickyRadius,paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dragCenter.set(event.getRawX(),event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent: 11");
                dragCenter.set(event.getRawX(),event.getRawY());

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    public void updataDragCenter(float x, float y) {
        dragCenter.set(x,y);
        invalidate();
    }
}
