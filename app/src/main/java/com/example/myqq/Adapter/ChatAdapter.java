package com.example.myqq.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myqq.Bean.Message;
import com.example.myqq.R;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter {
    private Context mContext;
    private final ArrayList<Message> mMessages;
    private final static int MESSAGE_FRIEND=1;
    private final static int MESSAGE_ME=0;
    public ChatAdapter(Context context) {
        mContext = context;
        mMessages = new ArrayList<>();
        Message message1 = new Message("你好", 0);
        Message message2 = new Message("我不好", 1);
        Message message3 = new Message("你为啥不好", 0);
        Message message4 = new Message("因为没对象", 1);
        Message message5 = new Message("哦", 0);
        mMessages.add(message1);
        mMessages.add(message2);
        mMessages.add(message3);
        mMessages.add(message4);
        mMessages.add(message5);

    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeftViewHolder leftViewHolder;
        RightViewHolder rightViewHolder;
        switch (getItemViewType(position)) {
            case MESSAGE_ME:
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.chat_dialog_right_item, null);
                    rightViewHolder = new RightViewHolder(convertView);
                    convertView.setTag(rightViewHolder);
                } else {
                    rightViewHolder = (RightViewHolder) convertView.getTag();
                }
                rightViewHolder.my_message.setText(mMessages.get(position).getMessage());
                break;
            case MESSAGE_FRIEND:
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.chat_dialog_left_item, null);
                    leftViewHolder = new LeftViewHolder(convertView);
                    convertView.setTag(leftViewHolder);
                } else {
                    leftViewHolder = (LeftViewHolder) convertView.getTag();
                }
                leftViewHolder.friend_message.setText(mMessages.get(position).getMessage());
                break;
        }

        return convertView;
    }
    static class LeftViewHolder {
        ImageView leftHeadIcon;
        TextView friend_message;
        LeftViewHolder(View convertView) {
            friend_message = convertView.findViewById(R.id.tv_chat_friend_message);
            leftHeadIcon = convertView.findViewById(R.id.iv_chat_headicon_friend);
        }

    }
    static class RightViewHolder {
        ImageView rightHeadIcon;
        TextView my_message;
        RightViewHolder(View convertView) {
            my_message = convertView.findViewById(R.id.tv_chat_me_message);
            rightHeadIcon = convertView.findViewById(R.id.iv_chat_headicon_me);
        }

    }
    public void addMessage(Message message) {
        mMessages.add(message);
    }
}
