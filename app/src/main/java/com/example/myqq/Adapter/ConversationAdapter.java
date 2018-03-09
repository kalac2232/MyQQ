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
import com.example.myqq.View.GooView;

import static android.content.ContentValues.TAG;

/**
 * Created by 97210 on 2/24/2018.
 */

public class ConversationAdapter extends BaseAdapter {
    Context context;
    private int statusBarHeight;

    public ConversationAdapter(Context context) {
        this.context = context;
        /**
         * 获取状态栏高度——方法
         * */
        statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_list, null);
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

        viewHolder.tv_newsnumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
                GooView gooView = new GooView(context);
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
                        int[] location = new  int[2] ;
                        v.getLocationInWindow(location);
                        //mParams.format = PixelFormat.TRANSLUCENT;
                        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
                        mParams.gravity = Gravity.TOP | Gravity.LEFT;
                        mParams.width = v.getWidth();
                        mParams.height = v.getHeight();
                        //设置gooview出现的位置
                        mParams.x = location[0];
                        mParams.y = location[1] - statusBarHeight;
                        windowManager.addView(gooView,mParams);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //gooView.updataDragCenter(event.getRawX(),event.getRawY());
                        break;
                    case MotionEvent.ACTION_UP:
                        //windowManager.removeView(gooView);
                        break;
                }
                //将事件交由gooview的onTouchEvent处理
                //gooView.onTouchEvent(event);
                return true;
            }
        });
        return convertView;
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
