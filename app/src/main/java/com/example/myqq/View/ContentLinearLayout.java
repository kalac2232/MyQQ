package com.example.myqq.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ContentLinearLayout extends LinearLayout {
    public ContentLinearLayout(Context context) {
        super(context);
    }

    public ContentLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ContentLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private SlideMenuView slideMenu;
    public void setSlideMenu(SlideMenuView slideMenu){
        this.slideMenu = slideMenu;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(slideMenu!=null && slideMenu.getMenuState()){
            //如果slideMenu打开则应该拦截并消费掉事件
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(slideMenu!=null && slideMenu.getMenuState()){
            if(event.getAction()== MotionEvent.ACTION_UP){
                //抬起则应该关闭slideMenu
                slideMenu.closeSlidingMenu();
            }
            //如果slideMenu打开则应该拦截并消费掉事件
            return true;
        }
        return super.onTouchEvent(event);
    }
}
