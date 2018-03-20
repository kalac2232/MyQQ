package com.example.myqq.Adapter;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myqq.R;
import com.example.myqq.Utilts.ConstantValue;
import com.example.myqq.Utilts.SharePreferenceUtil;
import com.example.myqq.View.GooView;

import static android.content.ContentValues.TAG;

/**
 * Created by 97210 on 2/24/2018.
 */

public class ConversationAdapter extends BaseAdapter implements View.OnTouchListener {
    Context mContext;

    private GooView gooView;
    private WindowManager windowManager;
    private final int statusBarHeight;

    public ConversationAdapter(Context context) {
        this.mContext = context;
        statusBarHeight = ConstantValue.statusBarHeight;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private View recordView = null;//当前正在操作的粘性控件
    private String number;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bt_Stick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: tv_Stick");
            }
        });
        viewHolder.bt_MarkingUnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: tv_MarkingUnread");
            }
        });
        viewHolder.bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: bt_delete");
            }
        });
        //获取未读消息数量
        number = viewHolder.tv_newsnumber.getText().toString();
        viewHolder.tv_newsnumber.setOnTouchListener(this);
        return convertView;
    }

    @Override
    //tv_newsnumber的touch事件
    public boolean onTouch(View v, MotionEvent event) {
        if (gooView == null) {
            gooView = new GooView(mContext);

        }
        gooView.setOnAnimStateChangeListener(new GooView.OnAnimStateChangeListener() {
            @Override
            //当动画尚未结束时执行
            public void onAnimUnFinish() {

            }
            @Override
            //当动画结束后执行
            public void onAnimFinish() {
                //当动画执行结束后，清除粘性控件和相应的状态
                if (gooView != null) {

                    windowManager.removeView(gooView);
                    //判断textview是否可以显示出来
                    if (!gooView.getDestroy()) {
                        recordView.setVisibility(View.VISIBLE);
                    }
                }
                gooView = null;
            }
        });
        v.getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
                int[] location = new  int[2] ;
                v.getLocationInWindow(location);
                //设置不动圆的圆心
                gooView.initPointsCenter(location[0]+v.getMeasuredWidth()/2,location[1]+v.getMeasuredHeight()/2-statusBarHeight);
                gooView.setRadius(v.getMeasuredWidth()/2);
                gooView.setNewNumber(number);
                //设置规则
                mParams.format = PixelFormat.TRANSLUCENT;
                mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
                mParams.gravity = Gravity.TOP | Gravity.LEFT;
                //在屏幕上添加粘性控件
                windowManager.addView(gooView,mParams);
                v.setVisibility(View.GONE);
                recordView = v; //记录当前操作的粘性控件
                break;
            case MotionEvent.ACTION_MOVE:

                gooView.updataDragCenter(event.getRawX(),event.getRawY()-statusBarHeight);

                break;
            case MotionEvent.ACTION_UP:
                //将事件交由gooview的onTouchEvent处理
                gooView.onTouchEvent(event);

                break;
        }

        return true;
    }


    static class ViewHolder {
        ImageView headIcon;
        TextView tv_name,tv_lastMessage,tv_lastTime,tv_newsnumber;
        Button bt_Stick,bt_MarkingUnread,bt_delete;
        ViewHolder(View convertView) {
            bt_Stick = (Button) convertView.findViewById(R.id.tv_Stick);
            bt_MarkingUnread = (Button) convertView.findViewById(R.id.tv_MarkingUnread);
            bt_delete = (Button) convertView.findViewById(R.id.tv_delete);
            tv_newsnumber = (TextView) convertView.findViewById(R.id.tv_newsnumber);
        }

    }


}
