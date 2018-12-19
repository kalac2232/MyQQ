package com.example.myqq.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.myqq.activity.ChatActivity;
import com.example.myqq.adapter.ConversationAdapter;
import com.example.myqq.bean.UserInfo;
import com.example.myqq.dao.UserDAO.UserDAOUtil;
import com.example.myqq.R;
import com.example.myqq.view.ConversationListView;
import com.example.myqq.view.SlideMenuView;

/**
 * Created by 97210 on 2/17/2018.
 */

public class ConversationFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ConversationFragment";

    Context context;
    SlideMenuView slideMenuView;
    private ImageView mIvHeadIcon;

    public ConversationFragment() {

    }
    @SuppressLint("ValidFragment")
    public ConversationFragment(SlideMenuView slideMenuView) {
        this.slideMenuView = slideMenuView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, null);
        init(view);

        return view;
    }

    private void init(View view) {
        //找到控件
        Button head_btn_add = (Button) view.findViewById(R.id.head_btn_add);
        mIvHeadIcon = (ImageView) view.findViewById(R.id.iv_main_headicon);
        ConversationListView lv_conversation = view.findViewById(R.id.lv_conversation);
        //根据用户设置头像

        //mIvHeadIcon.setImageDrawable(userInfo.getHeadImage());
        //给listView设置数据适配器
        ConversationAdapter conversationAdapter = new ConversationAdapter(context);
        lv_conversation.setAdapter(conversationAdapter);

        //读取消息列表
        //设置监听
        head_btn_add.setOnClickListener(this);
        mIvHeadIcon.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_btn_add:
                showPopupWindow(v);
                break;
            case R.id.iv_main_headicon:
;               slideMenuView.openSlidingMenu();
                break;
        }

    }

    private void showPopupWindow(View v) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_window, null);
        //构造方法public PopupWindow(View contentView, int width, int height, boolean focusable)  focusable让PopupWindow获得焦点
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //相对某个控件的位置（正下方），无偏移
        popupWindow.showAsDropDown(v);
        //让点击后退键是关闭弹窗
        popupWindow.setOutsideTouchable(true);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.8f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //当popupwindow dismiss时将界面恢复
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }
}
