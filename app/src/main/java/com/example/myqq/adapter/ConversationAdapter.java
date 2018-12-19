package com.example.myqq.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.example.myqq.activity.ChatActivity;
import com.example.myqq.R;
import com.example.myqq.bean.NewestMessage;
import com.example.myqq.utilts.ConstantValue;
import com.example.myqq.view.GooView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 97210 on 2/24/2018.
 */

public class ConversationAdapter extends BaseAdapter implements View.OnTouchListener {
    Context mContext;

    private GooView gooView;
    private WindowManager windowManager;
    private final int statusBarHeight;
    private List<NewestMessage> messageList;

    public ConversationAdapter(Context context) {
        this.mContext = context;
        statusBarHeight = ConstantValue.statusBarHeight;
        initData();

    }

    private void initData() {
        messageList = new ArrayList<NewestMessage>();
        messageList.add(new NewestMessage(R.drawable.headimage_0, "Java高级交流群", "晓敏老师:不对啊","14:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_2, "Android开发交流群", "简单:好想躺在床上上班啊","14:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_3, "群通知", "最新公开课视频已经上传完毕，需要的加晓敏老师QQ","15:12"));

        messageList.add(new NewestMessage(R.drawable.headimage_2, "高级装逼扯淡群", "狼伯:叫我兰博","14:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_4, "欢乐青年二逼多", "励志要成为车手的年轻人:你不能这样骗我，虽然我年轻","14:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_5, "彼尔维何。", "按理说30秒轮询的","17:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_6, "腾讯云点播产品交流", "腾讯云点播产品交流 :大神们有 设过 点播和直播的水印的吗","13:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_7, "Java高级交流群", "晓敏老师:不对啊","14:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_0, "Android开发交流群", "简单:好想躺在床上上班啊","14:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_1, "群通知", "最新公开课视频已经上传完毕，需要的加晓敏老师QQ","15:12"));

        messageList.add(new NewestMessage(R.drawable.headimage_2, "高级装逼扯淡群", "狼伯:叫我兰博","14:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_3, "欢乐青年二逼多", "励志要成为车手的年轻人:你不能这样骗我，虽然我年轻","14:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_4, "彼尔维何。", "按理说30秒轮询的","17:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_5, "腾讯云点播产品交流", "腾讯云点播产品交流 :大神们有 设过 点播和直播的水印的吗","13:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_2, "Java高级交流群", "晓敏老师:不对啊","14:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_1, "Android开发交流群", "简单:好想躺在床上上班啊","14:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_7, "群通知", "最新公开课视频已经上传完毕，需要的加晓敏老师QQ","15:12"));

        messageList.add(new NewestMessage(R.drawable.headimage_5, "高级装逼扯淡群", "狼伯:叫我兰博","14:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_6, "欢乐青年二逼多", "励志要成为车手的年轻人:你不能这样骗我，虽然我年轻","14:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_2, "彼尔维何。", "按理说30秒轮询的","17:28"));

        messageList.add(new NewestMessage(R.drawable.headimage_1, "腾讯云点播产品交流", "腾讯云点播产品交流 :大神们有 设过 点播和直播的水印的吗","13:28"));

    }

    @Override
    public int getCount() {
        return messageList.size();
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
            viewHolder = new ViewHolder(convertView,mContext);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.MainContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: MainContent");
                Intent intent = new Intent(mContext, ChatActivity.class);
                mContext.startActivity(intent);
                Activity activity = (Activity) mContext;
                //开启动画
                activity.overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
            }
        });
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
        NewestMessage newestMessage = messageList.get(position);
        viewHolder.tv_lastMessage.setText(newestMessage.getLastMessage());
        viewHolder.tv_lastTime.setText(newestMessage.getLastMessageTime());
        viewHolder.headIcon.setImageResource(newestMessage.getHeadiconId());
        viewHolder.tv_contact.setText(newestMessage.getContactName());


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
        TextView tv_contact,tv_lastMessage,tv_lastTime,tv_newsnumber;
        Button bt_Stick,bt_MarkingUnread,bt_delete;
        View MainContent;
        ViewHolder(View convertView,Context context) {
            MainContent = convertView.findViewById(R.id.ll_content);
            headIcon = MainContent.findViewById(R.id.iv_newest_headicon);
            tv_contact = MainContent.findViewById(R.id.tv_newest_contact);
            tv_lastMessage = MainContent.findViewById(R.id.tv_newest_lastmessage);
            tv_lastTime = MainContent.findViewById(R.id.tv_newest_lasttime);

            bt_Stick = (Button) convertView.findViewById(R.id.tv_Stick);
            bt_MarkingUnread = (Button) convertView.findViewById(R.id.tv_MarkingUnread);
            bt_delete = (Button) convertView.findViewById(R.id.tv_delete);
            tv_newsnumber = (TextView) convertView.findViewById(R.id.tv_newsnumber);
        }

    }


}
