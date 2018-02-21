package com.example.myqq.View;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 *
 * Created by 97210 on 2/14/2018.
 */

public class SlideMenuView extends FrameLayout {

    private View leftMenu;
    private View content;
    ViewDragHelper viewDragHelper;
    private int leftMenuX;
    private FrameLayout frameLayout;
    private ImageView shadowImage;
    private float clickDownX;

    public SlideMenuView(Context context) {
        super(context);
        init();
    }


    public SlideMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
    /**
     * 当DragLayout的xml布局的结束标签被读取完成会执行该方法，此时会知道自己有几个子View了 一般用来初始化子View的引用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        leftMenu = getChildAt(0);
        content = getChildAt(1);
        //先将内容控件从原来的根布局中移除
        removeView(content);
        //创建新的容器
        frameLayout = new FrameLayout(getContext());
        //添加到容器中
        frameLayout.addView(content);
        //新建阴影ImageView
        shadowImage = new ImageView(getContext());

        //设置阴影的颜色
        shadowImage.setBackgroundColor(Color.parseColor("#99000000"));
        shadowImage.setAlpha(0f);
        //也添加到容器中
        frameLayout.addView(shadowImage);
        //把新的容器放到旧的容器中相同的位置
        addView(frameLayout, 1);

    }
    private void init() {
        viewDragHelper = ViewDragHelper.create(this,callback);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        leftMenuX = leftMenu.getMeasuredWidth()-getMeasuredWidth()/10;
        leftMenu.layout(-leftMenu.getMeasuredWidth() + getMeasuredWidth()/10,0,getMeasuredWidth()/10,bottom);
        content.layout(left,top,right,bottom);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 让ViewDragHelper帮我们判断是否应该拦截
        boolean result = viewDragHelper.shouldInterceptTouchEvent(ev);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 将触摸事件交给ViewDragHelper来解析处理
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                clickDownX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX();

                if (clickDownX == upX && leftMenu.getLeft() == 0 && clickDownX > leftMenu.getRight()) {
                    close();
                }
                break;
        }
        viewDragHelper.processTouchEvent(event);
        return true;
    }
    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        /**
         * 用于判断是否捕获当前child的触摸事件 child: 当前触摸的子View return: true:就捕获并解析 false：不处理
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == frameLayout;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left>leftMenu.getMeasuredWidth()) {
                return  left-dx;
            }
            if (left<0) {
                return  left-dx;
            }
            return left;
        }
        /**
         * 当child的位置改变的时候执行,一般用来做其他子View的伴随移动 changedView：位置改变的child
         * left：child当前最新的left top: child当前最新的top dx: 本次水平移动的距离 dy: 本次垂直移动的距离
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);


            //计算view移动的百分比
            float fraction  = left *1f/ leftMenu.getMeasuredWidth();


            float v = leftMenuX * fraction;


            leftMenu.layout(-leftMenuX + (int)v, leftMenu.getTop(),
                    -leftMenuX + leftMenu.getMeasuredWidth() + (int)v, leftMenu.getBottom());
            //2.执行一系列的伴随动画
            executeAnim(fraction);

        }
        /**
         * 手指抬起的执行该方法， releasedChild：当前抬起的view xvel: x方向的移动的速度 正：向右移动， 负：向左移动
         * yvel: y方向移动的速度
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            int centerLeft = leftMenu.getLeft() + leftMenu.getMeasuredWidth()/2;
            if ( centerLeft < 0) {
                close();
            } else {
                open();
            }
        }

    };
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(SlideMenuView.this);
        }
    }
    private void executeAnim(float fraction){

        shadowImage.setAlpha(fraction);

    }
    //关闭侧边栏
    public void close () {
        //向左缓慢移动
        viewDragHelper.smoothSlideViewTo(frameLayout, 0, 0);
        ViewCompat.postInvalidateOnAnimation(SlideMenuView.this);
    }
    //开启侧边栏
    public void open () {
        viewDragHelper.smoothSlideViewTo(frameLayout, leftMenu.getMeasuredWidth(), 0);
        ViewCompat.postInvalidateOnAnimation(SlideMenuView.this);
    }
}