package com.example.myqq.View;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.myqq.Utilts.ConversationListViewManager;

/**
 * Created by 97210 on 2/24/2018.
 */

public class ConversationListView extends FrameLayout {
    private View listContent;
    private View listDelete;
    ViewDragHelper viewDragHelper;
    private float startY;
    private float startX;
    private float start1X;
    private float start1Y;

    public ConversationListView(Context context) {
        super(context);
        init();
    }

    public ConversationListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConversationListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private static final String TAG = "ConversationListView";

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        listContent = getChildAt(0);
        listDelete = getChildAt(1);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        listContent.layout(left,top,right,bottom);
        listDelete.layout(right,top,right+listDelete.getMeasuredWidth(),bottom);
    }
    enum SwipeState{
        Open,Close;
    }
    private void init() {
        viewDragHelper = ViewDragHelper.create(this,callback);
    }
    private SwipeState currentState = SwipeState.Close;//记录当前状态，默认为关闭
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean result = viewDragHelper.shouldInterceptTouchEvent(event);
        //判断当前是否可以进行滑动,如果可以滑动，直接交给ontouch处理
        if (!ConversationListViewManager.getInstance().isCanSwipe(this) ) {
            //如果不可以滑动先关闭已经打开的滑块
            ConversationListViewManager.getInstance().closeCurrentLayout();
            result = true;
        }
        return result;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                start1X = event.getX();
//                start1Y = event.getY();
//                Log.i(TAG, "dispatchTouchEvent: ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.i(TAG, "dispatchTouchEvent: ACTION_UP");
//                float end1X = event.getX();
//                float end1Y = event.getY();
//                if (start1X == end1X && start1Y == end1Y) {
//                    return false;
//                }
//                break;
//        }
//        return super.dispatchTouchEvent(event);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //判断当前是否可以进行滑动
        if ( !ConversationListViewManager.getInstance().isCanSwipe(this) ) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                Log.i(TAG, "onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent: ACTION_MOVE");
                float endX = event.getX();
                float endY = event.getY();
                float xOffset = endX - startX;
                float yOffset = endY - startY;

                if (Math.abs(xOffset) > Math.abs(yOffset)) {
                    //请求父控件不要拦截事件
                    requestDisallowInterceptTouchEvent(true);
                }
                startX = endX;
                startY = endY;

                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent: ACTION_UP");
//                float end1X = event.getX();
//                float end1Y = event.getY();
//                if (start1X == end1X && start1Y == end1Y) {
//                    return false;
//                }
                break;
        }
        // 将触摸事件交给ViewDragHelper来解析处理
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == listContent || child == listDelete;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return listDelete.getMeasuredWidth();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == listContent) {
                if (left > 0) {
                    left = 0;
                }
                if (left < -listDelete.getMeasuredWidth()) {
                    left = -listDelete.getMeasuredWidth();
                }
            } else if (child == listDelete) {
                if (left > listContent.getMeasuredWidth()) {
                    left = listContent.getMeasuredWidth();
                }
                if (left < listContent.getMeasuredWidth()-listDelete.getMeasuredWidth()) {
                    left = listContent.getMeasuredWidth()-listDelete.getMeasuredWidth();
                }
            }
            return left;
        }

        @Override
        /**
         * 当child的位置改变的时候执行,一般用来做其他子View的伴随移动 changedView：位置改变的child
         * left：child当前最新的left top: child当前最新的top dx: 本次水平移动的距离 dy: 本次垂直移动的距离
         */
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == listContent) {
                listDelete.layout(listDelete.getLeft()+dx,listDelete.getTop()+dy,listDelete.getRight()+dx,listDelete.getBottom()+dy);
            } else if (changedView == listDelete) {
                listContent.layout(listContent.getLeft()+dx,listContent.getTop()+dy,listContent.getRight()+dx,listContent.getBottom()+dy);
            }
            //判断当前的状态为开启还是为关闭
            if (listContent.getLeft() == 0 && currentState != SwipeState.Close) {
                currentState = SwipeState.Close;
                //清空Manager中的记录值
                ConversationListViewManager.getInstance().clearCurrentLayout();
            }else if (listContent.getLeft() == -listDelete.getMeasuredWidth() && currentState != SwipeState.Open) {
                currentState = SwipeState.Open;
                //将当前滑块传给Manager管理起来
                ConversationListViewManager.getInstance().setCurrentLayout(ConversationListView.this);
            }
        }
        /**
         * 手指抬起的执行该方法， releasedChild：当前抬起的view xvel: x方向的移动的速度 正：向右移动， 负：向左移动
         * yvel: y方向移动的速度
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            int center = listContent.getMeasuredWidth() - listDelete.getMeasuredWidth()/2;
            if ( listContent.getRight() < center ) {
                Open();

            } else {
                Close();
            }
        }

    };
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(ConversationListView.this);
        }
    }
    public void Open() {
        viewDragHelper.smoothSlideViewTo(listDelete, listContent.getMeasuredWidth() - listDelete.getMeasuredWidth(), 0);
        invalidate();
    }
    public void Close() {
        viewDragHelper.smoothSlideViewTo(listDelete, listContent.getMeasuredWidth(), 0);
        invalidate();
    }
}
