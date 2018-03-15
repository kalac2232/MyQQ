package com.example.myqq.View;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

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
    private float defaultRadius = 50f;

    private PointF dragCenter;
    private PointF stickyCenter;
    private int statusBarHeight;

    private float dragRadius;//拖拽圆的半径
    private float stickyRadius;//固定圆的半径
    private float maxRadius;//用于记录圆的半径，不会变化

    public int measuredWidth;
    public int measuredHeight;

    private static final String TAG = "GooView";

    private PointF[] dragPoints;
    private PointF[] stickyPoints;


    private String newNumber;
    private PointF controlPoint;
    private float maxDistanse = 180;//最大绘制贝塞尔曲线圆心距
    private double lineK;//斜率
    boolean isDragOutOfRange = false;//是否超出了拖拽范围
    boolean isDestroy = false;
    public GooView(Context context) {
        super(context);
        init();
    }

    public GooView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public GooView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();
    }

    private void init(AttributeSet attrs) {

        roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
        roundPaint.setColor(Color.GRAY);
        //Log.i(TAG, "init: getX: " + getX() + "getWidth: " + getMeasuredWidth());
        dragCenter = new PointF();
        stickyCenter = new PointF();
        setRadius(defaultRadius);
        statusBarHeight = SharePreferenceUtil.getInt(getContext(), ConstantValue.STATUSBARHEIGHT,-1);

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

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if (isDestroy) {
            return;
        }
        //获取动态圆的半径
        stickyRadius = getStickRadius();
        //判断是否超出了拖拽范围
        if (GeometryUtil.getDistanceBetween2Points(dragCenter,stickyCenter) < maxDistanse) {
            isDragOutOfRange = false;
        }
        else {
            isDragOutOfRange = true;
        }
        //如果是画listview中的才进行圆半径的改变
        if (roundPaint.getColor() == Color.GRAY) {
            //获取应该缩小为的半径
            dragRadius = getDragRadius();

            //如果对于listview头中的gooview来说一旦断裂就不需要绘制了
            if (isDragOutOfRange && !isDestroy) {

                isDestroy = true;
                if (listener != null ) {
                    //实现回调中的方法
                    listener.onAnimFinish();
                }
                return;
            }

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
        if (newNumber != null) {
            canvas.drawText(newNumber, dragCenter.x , dragCenter.y + dragRadius /2f, textPaint);
        }
        if (roundPaint.getColor() == Color.GRAY) {
            //画圆中的刷新图标
            Bitmap select_icon = ((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.refresh_icon)).getBitmap();
            Matrix matrix = new Matrix();
            float centerDistanse = GeometryUtil.getDistanceBetween2Points(dragCenter,stickyCenter);
            float fraction = centerDistanse/maxDistanse;//圆心占总距离的百分比
            //对图标进行缩放
            matrix.postScale(0.6f-fraction*0.2f,0.6f-fraction*0.2f);
            select_icon = Bitmap.createBitmap(select_icon,0,0,select_icon.getWidth(),select_icon.getHeight(),matrix,true);
            canvas.drawBitmap(select_icon,dragCenter.x-select_icon.getWidth()/2,dragCenter.y-select_icon.getHeight()/2,roundPaint);
        }

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
                                isDestroy = false;
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

                    final AnimLayout animLayout = new AnimLayout(getContext());
                    animLayout.setCenter((int) dragCenter.x, (int) dragCenter.y );

                    animLayout.addView(imageView, new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT));

                    final WindowManager windowManager;
                    WindowManager.LayoutParams layoutParams;

                    windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                    layoutParams = new WindowManager.LayoutParams();
                    layoutParams.format = PixelFormat.TRANSLUCENT;
                    layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
                    windowManager.addView(animLayout, layoutParams);

                    animDrawable.start();
                    // 播放结束后，删除该bubbleLayout
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            windowManager.removeView(animLayout);
                        }
                    }, 501);
                    isDestroy = true;
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
     * 动态获取拖拽圆应该变成的半径
     * @return 拖拽圆的半径
     */
    private float getDragRadius() {
        float centerDistanse = GeometryUtil.getDistanceBetween2Points(dragCenter,stickyCenter);
        float fraction = centerDistanse/maxDistanse;//圆心占总距离的百分比
        //因为maxRadius为定值，所以此处用maxRadius的值
        float radius = GeometryUtil.evaluateValue(fraction,maxRadius,maxRadius*0.6f);
        return radius;
    }
    /**
     * 动态获取固定圆应该变成的半径
     * @return 固定圆的半径
     */
    private float getStickRadius() {
        float centerDistanse = GeometryUtil.getDistanceBetween2Points(dragCenter,stickyCenter);
        float fraction = centerDistanse/maxDistanse;//圆心占总距离的百分比
        //因为maxRadius为定值，所以此处用maxRadius的值
        float radius = GeometryUtil.evaluateValue(fraction,maxRadius,maxRadius*0.1f);
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
     * 更新固定圆的圆心
     * @param x
     * @param y
     */
    public void updataStickyCenter(float x, float y) {
        stickyCenter.set(x,y);
        invalidate();
    }
    /**
     * 初始化固定圆的圆心
     * @param x
     * @param y
     */
    public void initPointsCenter(float x, float y) {
        stickyCenter.set(x,y);
        dragCenter.set(x,y);
        invalidate();
    }
    public boolean getDestroy() {
        return isDestroy;
    }

    /**
     * 设置要显示的数字
     * @param newNumber
     */
    public void setNewNumber(String newNumber) {
        this.newNumber = newNumber;
        textPaint.setTextSize(dragRadius * 1.3f);
    }

    /**
     * 设置俩圆的半径
     * @param Radius
     */
    public void setRadius(float Radius) {
        dragRadius = Radius;
        stickyRadius = Radius;
        maxRadius = Radius;
    }

    public PointF getDragCenter() {
        return dragCenter;
    }

    public PointF getStickyCenter() {
        return stickyCenter;
    }

    //定义动画结束接口 用于回调
    public interface OnAnimStateChangeListener{
        void onAnimUnFinish();
        void onAnimFinish();
    }

    private OnAnimStateChangeListener listener;

    public void setOnAnimStateChangeListener(OnAnimStateChangeListener listener){
        this.listener = listener;
    }

    /**
     * 恢复默认状态
     */
    public void clearStatus() {
        isDragOutOfRange = false;
        isDestroy = false;

        dragRadius = defaultRadius;
        stickyRadius = defaultRadius;

    }
}
