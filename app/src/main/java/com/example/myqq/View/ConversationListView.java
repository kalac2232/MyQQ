package com.example.myqq.View;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.myqq.R;
import com.example.myqq.Utilts.ConstantValue;
import com.example.myqq.Utilts.SharePreferenceUtil;

/**
 *
 * Created by 97210 on 3/11/2018.
 */

public class ConversationListView extends ListView {

    private float mStartY;
    private float mStartX;
    private View mHeaderView;
    private int goo_MeasuredHeight;
    private int mScreenWidth;
    private View listviewSecondItem;
    private RelativeLayout rl_listview_head;
    private LinearLayout ll_refreshing;
    private GooView mGooView;
    private Boolean isRefreshAnimFinish = false;
    private float mPaddingOffset;

    private int statusBarHeight;
    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearStatus();
        }
    };

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
    private void init() {
        mHeaderView = View.inflate(getContext(), R.layout.layout_listview_head,null);
        mGooView = (GooView) mHeaderView.findViewById(R.id.goo_list_head);
        rl_listview_head = (RelativeLayout) mHeaderView.findViewById(R.id.rl_listview_head);
        ll_refreshing = (LinearLayout) mHeaderView.findViewById(R.id.ll_refreshing);
        //手动测量width 和 height
        mHeaderView.measure(0,0);
        goo_MeasuredHeight = mGooView.getMeasuredHeight();
        //获取屏幕的宽度
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        //设置俩点的初始值
        mGooView.initPointsCenter(mScreenWidth /2,goo_MeasuredHeight/2);
        mHeaderView.setPadding(0, -goo_MeasuredHeight, 0, 0);
        //给listview添加头布局
        addHeaderView(mHeaderView);
        statusBarHeight = SharePreferenceUtil.getInt(getContext(), ConstantValue.STATUSBARHEIGHT,-1);
        mGooView.setOnAnimStateChangeListener(new GooView.OnAnimStateChangeListener() {
            @Override
            public void onAnimUnFinish() {

            }

            @Override
            public void onAnimFinish() {
                final ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.progress_anim);
                final AnimationDrawable animDrawable = (AnimationDrawable) imageView.getDrawable();
                RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                param.addRule(RelativeLayout.CENTER_HORIZONTAL);
                param.topMargin = 15;
                rl_listview_head.addView(imageView,param);
                animDrawable.start();
                //模拟获取消息的状态
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rl_listview_head.removeView(imageView);
                        ll_refreshing.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ll_refreshing.setVisibility(View.GONE);
                                isRefreshAnimFinish = true;
                            }
                        }, 1000);
                    }
                }, 1000);

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //onTouchEvent中的mStartY时从这里获取的 因为onTouchEvent不执行ACTION_DOWN事件，佷无奈
                Log.i(TAG, "onInterceptTouchEvent: ACTION_DOWN");
                mStartY = event.getY();
                mStartX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onInterceptTouchEvent: ACTION_MOVE");
                float moveY = event.getY();
                float moveX = event.getX();
                //如果不拦截 则会将事件传入下一个控件导致卡顿
                if (Math.abs(moveX - mStartX) < Math.abs(moveY - mStartY) && (moveY - mStartY)>0) {
                    return true;
                }

                break;

        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //这里的语句不执行 为什么？母鸡呀！
                mStartY = event.getY();
                Log.i(TAG, "onTouchEvent: 0");
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                float yOffset = moveY - mStartY;
                listviewSecondItem = getChildAt(1);
                // 当前第一个可见条目的索引值为0,并且第二个条目（就是第一个聊天框）距上端的距离正好为headerview的宽度
                if(getFirstVisiblePosition()==0 && listviewSecondItem.getTop() == mHeaderView.getMeasuredHeight()){
                    if ((-goo_MeasuredHeight + yOffset) > 0) {
                        Log.i(TAG, "onTouchEvent: 1");
                        mHeaderView.setPadding(0, 0, 0, 0);
                        //更改gooview的高度为Y的偏移量
                        ViewGroup.LayoutParams params;
                        params = mGooView.getLayoutParams();
                        params.height = (int) yOffset;
                        mGooView.setLayoutParams(params);

                        //Log.i(TAG, "onTouchEvent: rl_listview_head.getY();" + rl_listview_head.getY());
                        //Log.i(TAG, "onTouchEvent: ll_refreshing.getY();" + ll_refreshing.getY());
                        //更新Sticky圆的中心点
                        mGooView.updataStickyCenter(mScreenWidth /2, (mGooView.getStickyCenter().y + event.getY() - mPaddingOffset));
                        mPaddingOffset = event.getY();
                    } else {
                        Log.i(TAG, "onTouchEvent: 2");
                        mPaddingOffset = event.getY();
                        if (yOffset >= 0) {
                            Log.i(TAG, "onTouchEvent: rl_listview_head.getY();" + rl_listview_head.getY());
                            mHeaderView.setPadding(0, (int) (-goo_MeasuredHeight + (int)yOffset), 0, 0);
                        } else {
                            //初始化俩点的初始值
                            mGooView.initPointsCenter(mScreenWidth /2,goo_MeasuredHeight/2);
                            //恢复状态
                            mGooView.clearStatus();
                            return super.onTouchEvent(event);
                        }
                    }
                } else {
                    //当未将刷新栏拉出并向下滑时调用
                    Log.i(TAG, "onTouchEvent: 3");
                    //startY = moveY;
                    return super.onTouchEvent(event);
                    //headerView.setPadding(0, (int) ((offset + yOffset)/1.5), 0, 0);
                }
                
                return  true;
                //break;
            case MotionEvent.ACTION_UP:
                //将高度恢复
                ViewGroup.LayoutParams params;
                params = mGooView.getLayoutParams();
                params.height = goo_MeasuredHeight;
                mGooView.setLayoutParams(params);
                //如果刷新成功页面还在显示
                if (!isRefreshAnimFinish && mGooView.getDestroy()) {
                    //先将刷新页面继续显示
                    mHeaderView.setPadding(0, 0, 0, 0);
                    //创建一个线程来判断刷新页面的状态
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                //当刷新成功的页面消失
                                if (isRefreshAnimFinish) {
                                    //给handler发送一个空消息 告诉可以更新ui了（使界面恢复初始状态）
                                    handler.sendEmptyMessage(0);
                                    break;
                                }
                            }
                        }
                    }).start();

                } else {
                    //恢复默认状态
                    clearStatus();
                }

                break;


        }
        return super.onTouchEvent(event);
    }
    public void clearStatus() {
        mGooView.initPointsCenter(mScreenWidth /2,goo_MeasuredHeight/2);
        //恢复状态
        mGooView.clearStatus();
        mHeaderView.setPadding(0, -goo_MeasuredHeight, 0, 0);
        isRefreshAnimFinish = false;
    }
}
