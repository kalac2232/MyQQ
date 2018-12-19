package com.example.myqq.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 这个起到当滑动菜单打开时，判断点击的位置，如果在滑动菜单外，则将事件拦截并消费，使事件传不到listview上，并将滑动菜单关闭
 */
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
        if(slideMenu!=null && slideMenu.isMenuOpen()){
            //如果slideMenu打开则应该拦截并消费掉事件
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(slideMenu!=null && slideMenu.isMenuOpen()){
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
