package com.example.myqq.View;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import static android.content.ContentValues.TAG;

/**
 *
 * Created by 97210 on 2/14/2018.
 */

public class SlideMenuView extends ViewGroup {

    private static final int MAIN_STATE = 0;
    private static final int MENU_STATE = 1;
    private View leftMenu;
    private View content;
    private float downX;
    private float startX;
    private int currentState = MAIN_STATE; // 当前模式
    private Scroller scroller;
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
    private void init() {
        // 初始化滚动器, 数值模拟器
        scroller = new Scroller(getContext());
    }
    /**
     * @param widthMeasureSpec 当前控件的宽度测量规则
     * @param heightMeasureSpec 高度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //找到左面的菜单栏
        leftMenu = getChildAt(0);
        //指定菜单栏的宽高
        leftMenu.measure(leftMenu.getLayoutParams().width,heightMeasureSpec);
        //找到主界面
        content = getChildAt(1);
        content.measure(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        leftMenu.layout(-leftMenu.getMeasuredWidth(),0,0,b);
        content.layout(l,t,r,b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                downX = event.getX();
                startX = downX;
                Log.i(TAG, "onTouchEvent: downX"+ downX);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                Log.i(TAG, "onTouchEvent: moveX"+ moveX);
                //变化量
                int scrollX = (int)(downX - moveX);
                //计算将要滚动的位置,判断是否会超出去
                int newScrollPosition = getScrollX() + scrollX;
                //限定左边界
                //如果超出去了，直接到末尾不动
                if (newScrollPosition < -leftMenu.getMeasuredWidth()) {
                    scrollTo(-leftMenu.getMeasuredWidth(),0);
                } else if (newScrollPosition > 0){ //限定右边界
                    scrollBy(0,0);
                }  else {

                    scrollBy(scrollX,0);
                }
                downX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                Log.i(TAG, "onTouchEvent: upX:"+endX);
                int menuCenter = (int) (-leftMenu.getMeasuredWidth() / 2.0f);
                if (getScrollX() < menuCenter) {
                    //打开
                    currentState = MENU_STATE;
                    updateCurrentContent();
                } else {
                    currentState = MAIN_STATE;
                    updateCurrentContent();

                }
                //如果已经打开了菜单页，则点击右侧关闭
                if (currentState == MENU_STATE && startX == endX && endX > leftMenu.getMeasuredWidth()) {
                    currentState = MAIN_STATE;
                    updateCurrentContent();

                }

                break;
        }

        return true;

    }

    /**
     * 根据当前的状态, 执行 关闭/开启 的动画
     */
    private void updateCurrentContent() {
        int startX = getScrollX();
        int dx = 0;
        // 平滑滚动
        if(currentState == MENU_STATE){
            // 打开菜单
            dx = -getChildAt(0).getMeasuredWidth() - startX;

        } else {

            dx = 0 - startX;
        }

        // startX: 开始的x值
        // startY: 开始的y值
        // dx: 将要发生的水平变化量. 移动的x距离
        // dy: 将要发生的竖直变化量. 移动的y距离
        // duration : 数据模拟持续的时长

        // 1. 开始平滑的数据模拟
        int duration = Math.abs(dx * 2); // 0 -> 1200
        scroller.startScroll(startX, 0, dx, 0, duration);

        invalidate();// 重绘界面 -> drawChild() -> computeScroll();
    }

    //2. 维持动画的继续
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){ // 直到duration事件以后, 结束
            // true, 动画还没有结束
            // 获取当前模拟的数据, 也就是要滚动到的位置
            int currX = scroller.getCurrX();
            scrollTo(currX, 0); // 滚过去
            invalidate(); // 重绘界面-> drawChild() -> computeScroll();循环
        }
    }

    public void open(){
        currentState = MENU_STATE;
        updateCurrentContent();
    }
    public void close(){
        currentState = MAIN_STATE;
        updateCurrentContent();
    }
    //调用可进行弹出和缩入的操作
    public void switchState(){
        if(currentState == MAIN_STATE){
            open();
        }else {
            close();
        }
    }

    public int getCurrentState(){
        return currentState;
    }
}
