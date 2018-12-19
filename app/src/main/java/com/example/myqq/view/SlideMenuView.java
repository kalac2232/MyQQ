package com.example.myqq.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myqq.R;
import com.example.myqq.utilts.ChatListMenuManager;
import com.example.myqq.utilts.ConstantValue;
import com.example.myqq.utilts.SharePreferenceUtil;

/**
 * Created by 97210 on 2/14/2018.
 */
public class SlideMenuView extends FrameLayout {

    private View leftMenu;
    private ContentLinearLayout content;
    ViewDragHelper viewDragHelper;
    private int leftMenuX;
    private FrameLayout frameLayout;
    private ImageView shadowImage;
    private float clickDownX;
    private float startX;
    private float startY;
    private static final String TAG = "SlideMenuView";
    /**
     * 最小滑动单位
     */
    private int mTouchSlop = 20;
    /**
     * 当前是否为打开状态
     */
    private boolean isOpenMenu = false;


    public SlideMenuView(Context context) {
        super(context);
        initView();
    }

    public SlideMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 当DragLayout的xml布局的结束标签被读取完成会执行该方法，此时会知道自己有几个子View了 一般用来初始化子View的引用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        leftMenu = getChildAt(0);
        content = (ContentLinearLayout) getChildAt(1);
        // 先将内容控件从原来的根布局中移除
        removeView(content);
        // 创建新的容器
        frameLayout = new FrameLayout(getContext());
        // 添加到容器中
        frameLayout.addView(content);
        // 新建阴影ImageView
        shadowImage = new ImageView(getContext());

        // 设置阴影的颜色
        shadowImage.setBackgroundColor(Color.parseColor("#99000000"));
        shadowImage.setAlpha(0f);
        // 也添加到容器中
        frameLayout.addView(shadowImage);
        // 把新的容器放到旧的容器中相同的位置
        addView(frameLayout, 1);
        content.setSlideMenu(this);

    }

    private void initView() {
        viewDragHelper = ViewDragHelper.create(this, callback);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        leftMenuX = leftMenu.getMeasuredWidth() - getMeasuredWidth() / 10;
        leftMenu.layout(
                -leftMenu.getMeasuredWidth() + getMeasuredWidth() / 10, 0, getMeasuredWidth() / 10, bottom);
        content.layout(left, top, right, bottom);
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i(TAG, "onInterceptTouchEvent: ACTION_DOWN");
            startX = event.getX();
            startY = event.getY();
            // 如果点击位置在屏幕左侧，则拦截这个事件并消费
            if (startX <= content.getMeasuredWidth() / 20
                    && !ChatListMenuManager.IsOpen()
                    && !isOpenMenu) {
                Log.i(TAG, "dispatchTouchEvent: 左侧，准备侧滑打开侧滑栏");
                return true;
            }
        }
        // return false;
        return viewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 将触摸事件交给ViewDragHelper来解析处理
        viewDragHelper.processTouchEvent(event);

        return true;
    }

    ViewDragHelper.Callback callback =
            new ViewDragHelper.Callback() {
                /** 用于判断是否捕获当前child的触摸事件 child: 当前触摸的子View return: true:就捕获并解析 false：不处理 */
                @Override
                public boolean tryCaptureView(View child, int pointerId) {
                    return child == frameLayout;
                }

                @Override
                public int clampViewPositionHorizontal(View child, int left, int dx) {
                    // 如果滑动后的超过了所限定的位置
                    if (left > leftMenu.getMeasuredWidth()) {
                        // 恢复到未发生变化前的数值
                        isOpenMenu = true;
                        return left - dx;
                    }
                    if (left < 0) {
                        isOpenMenu = false;
                        return left - dx;
                    }
                    return left;
                }

                /**
                 * 当child的位置改变的时候执行,一般用来做其他子View的伴随移动 changedView：位置改变的child left：child当前最新的left top:
                 * child当前最新的top dx: 本次水平移动的距离 dy: 本次垂直移动的距离
                 */
                @Override
                public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                    super.onViewPositionChanged(changedView, left, top, dx, dy);

                    // 计算view移动的百分比
                    float fraction = left * 1f / leftMenu.getMeasuredWidth();

                    // 计算view当前应该移动到什么位置
                    float v = leftMenuX * fraction;

                    leftMenu.layout(
                            -leftMenuX + (int) v,
                            leftMenu.getTop(),
                            -leftMenuX + leftMenu.getMeasuredWidth() + (int) v,
                            leftMenu.getBottom());
                    // 2.执行一系列的伴随动画
                    executeAnim(fraction);
                }

                /** 手指抬起的执行该方法， releasedChild：当前抬起的view xvel: x方向的移动的速度 正：向右移动， 负：向左移动 yvel: y方向移动的速度 */
                @Override
                public void onViewReleased(View releasedChild, float xvel, float yvel) {
                    super.onViewReleased(releasedChild, xvel, yvel);
                    int centerLeft = leftMenu.getLeft() + leftMenu.getMeasuredWidth() / 2;
                    if (centerLeft < 0) {
                        closeSlidingMenu();
                    } else {
                        openSlidingMenu();
                    }
                }
            };

    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(SlideMenuView.this);
        }
    }

    private void executeAnim(float fraction) {

        shadowImage.setAlpha(fraction);
    }

    // 关闭侧边栏
    public void closeSlidingMenu() {
        isOpenMenu = false;
        // 向左缓慢移动
        viewDragHelper.smoothSlideViewTo(frameLayout, 0, 0);
        invalidate();
    }

    // 开启侧边栏
    public void openSlidingMenu() {
        isOpenMenu = true;
        viewDragHelper.smoothSlideViewTo(frameLayout, leftMenu.getMeasuredWidth(), 0);

        invalidate();
    }

    public boolean isMenuOpen() {
        return isOpenMenu;
    }
}
