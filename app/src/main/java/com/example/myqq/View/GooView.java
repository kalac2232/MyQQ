package com.example.myqq.View;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myqq.R;
import com.example.myqq.Utilts.ConstantValue;
import com.example.myqq.Utilts.GeometryUtil;
import com.example.myqq.Utilts.SharePreferenceUtil;


/**
 * Created by 97210 on 3/7/2018.
 */

public class GooView extends View {
    private Paint roundPaint;
    private Paint textPaint;
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/android";
    private String type;
    private PointF dragCenter;
    private PointF stickyCenter;
    private int statusBarHeight;
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
        roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
        roundPaint.setColor(Color.RED);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.WHITE);
        //textPaint.setTextSize(35);
        dragCenter = new PointF();
        stickyCenter = new PointF();
        statusBarHeight = SharePreferenceUtil.getInt(getContext(), ConstantValue.STATUSBARHEIGHT,-1);
    }

    private static final String TAG = "GooView";


    public void setRadius(float Radius) {
        dragRadius = Radius;
        stickyRadius = Radius;
    }

    private float dragRadius;//拖拽圆的半径
    private float stickyRadius;//固定圆的半径

    private PointF[] dragPoints;
    private PointF[] stickyPoints;

    public void setNewNumber(String newNumber) {
        this.newNumber = newNumber;
        textPaint.setTextSize(dragRadius * 1.3f);
    }

    private String newNumber;
    private PointF controlPoint;
    private float maxDistanse = 180;//最大绘制贝塞尔曲线圆心距
    private double lineK;//斜率
    boolean isDragOutOfRange = false;//是否超出了拖拽范围
    boolean canDestroy = false;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取动态圆的半径
        stickyRadius = getStickRadius();
        //判断是否超出了拖拽范围
        if (GeometryUtil.getDistanceBetween2Points(dragCenter,stickyCenter) < maxDistanse && !isDragOutOfRange) {
            isDragOutOfRange = false;
        }
        else {
            isDragOutOfRange = true;
        }

        //求斜率
        float xOffset = dragCenter.x - stickyCenter.x;
        float yOffset = dragCenter.y - stickyCenter.y;
        if (xOffset != 0) {
            lineK = yOffset/xOffset;
        }
        //获取连接贝塞尔曲线的4个点
        dragPoints = GeometryUtil.getIntersectionPoints(dragCenter,dragRadius,lineK);
        stickyPoints = GeometryUtil.getIntersectionPoints(stickyCenter,stickyRadius,lineK);
        //绘制动态圆
        canvas.drawCircle(dragCenter.x,dragCenter.y,dragRadius, roundPaint);


        
        //计算控制点
        controlPoint = GeometryUtil.getPointByPercent(dragCenter,stickyCenter,0.618f);
        //判断是否超过了最大距离 超过就不绘制曲线
        if (!isDragOutOfRange) {
            //绘制固定圆
            canvas.drawCircle(stickyCenter.x, stickyCenter.y,stickyRadius, roundPaint);

            //绘制贝塞尔曲线
            Path path = new Path();
            path.moveTo(stickyPoints[0].x,stickyPoints[0].y);//设置起点
            path.quadTo(controlPoint.x,controlPoint.y,dragPoints[0].x,dragPoints[0].y);//使用贝塞尔曲线连接
            path.lineTo(dragPoints[1].x,dragPoints[1].y);
            path.quadTo(controlPoint.x,controlPoint.y,stickyPoints[1].x,stickyPoints[1].y);
            canvas.drawPath(path, roundPaint);
        }
        // 画数字
        canvas.drawText(newNumber, dragCenter.x , dragCenter.y + dragRadius /2f, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (!isDragOutOfRange && GeometryUtil.getDistanceBetween2Points(dragCenter,stickyCenter) < maxDistanse) {
                    //执行弹动动画
                    //动画的形式回去
                    ValueAnimator valueAnimator = ObjectAnimator.ofFloat(1);
                    final PointF startPointF = new PointF(dragCenter.x, dragCenter.y);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            //动画执行的百分比
                            float animatedFraction = animator.getAnimatedFraction();
                            PointF pointF = GeometryUtil.getPointByPercent(startPointF, stickyCenter, animatedFraction);
                            dragCenter.set(pointF);
                            if (animatedFraction == 1f) {
                                canDestroy = false;
                                //实现回调中的动画未完成方法
                                if (listener != null ) {
                                    listener.onAnimFinish();
                                }
                            } else {
                                if (listener != null ) {
                                    listener.onAnimUnFinish();
                                }
                            }
                            invalidate();
                        }
                    });
                    //设置动画执行时间
                    valueAnimator.setDuration(250);
                    //设置回弹动画
                    valueAnimator.setInterpolator(new OvershootInterpolator(3));
                    valueAnimator.start();
                } else {
                    //实现爆炸效果
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageResource(R.drawable.bubble_pop_anim);
                    final AnimationDrawable animDrawable = (AnimationDrawable) imageView.getDrawable();

                    final BubbleLayout bubbleLayout = new BubbleLayout(getContext());
                    bubbleLayout.setCenter((int) dragCenter.x, (int) dragCenter.y );

                    bubbleLayout.addView(imageView, new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT));

                    final WindowManager windowManager;
                    WindowManager.LayoutParams layoutParams;

                    windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                    layoutParams = new WindowManager.LayoutParams();
                    layoutParams.format = PixelFormat.TRANSLUCENT;
                    windowManager.addView(bubbleLayout, layoutParams);

                    animDrawable.start();
                    // 播放结束后，删除该bubbleLayout
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            windowManager.removeView(bubbleLayout);
                        }
                    }, 501);
                    canDestroy = true;
                    if (listener != null ) {
                        //实现回调中的方法
                        listener.onAnimFinish();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 动态获取固定圆应该缩小成的半径
     * @return 固定圆的半径
     */
    private float getStickRadius() {
        float centerDistanse = GeometryUtil.getDistanceBetween2Points(dragCenter,stickyCenter);
        float fraction = centerDistanse/maxDistanse;//圆心占总距离的百分比
        //因为dragRadius为定值，所以此处用dragRadius的值
        float radius = GeometryUtil.evaluateValue(fraction,dragRadius,0f);
        return radius;
    }

    /**
     * 更新拖拽圆的圆心
     * @param x
     * @param y
     */
    public void updataDragCenter(float x, float y) {
        dragCenter.set(x,y);
        invalidate();
    }

    /**
     * 初始化固定圆的圆心
     * @param x
     * @param y
     */
    public void initStickyCenter(float x, float y) {
        stickyCenter.set(x,y);
        dragCenter.set(x,y);
        invalidate();
    }
    public boolean getCanDestroy() {
        return canDestroy;
    }


    private OnAnimStateChangeListener listener;
    public void setOnAnimStateChangeListener(OnAnimStateChangeListener listener){
        this.listener = listener;
    }

    public interface OnAnimStateChangeListener{
        void onAnimUnFinish();
        void onAnimFinish();
    }
    public void clearStatus() {
        isDragOutOfRange = false;
        canDestroy = false;
    }
}
