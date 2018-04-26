package com.example.myqq.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.myqq.Activity.ChatActivity;
import com.example.myqq.Adapter.ConversationAdapter;
import com.example.myqq.Bean.UserInfo;
import com.example.myqq.DAO.UserDAO.UserDAOUtil;
import com.example.myqq.R;

import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by 97210 on 2/17/2018.
 */

public class ConversationFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ConversationFragment";

    Context context;
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
        ImageView iv_main_headicon = (ImageView) view.findViewById(R.id.iv_main_headicon);
        ListView lv_conversation = (ListView) view.findViewById(R.id.lv_conversation);
        //根据用户设置头像
        //查询登陆的用户信息
        UserDAOUtil userDAOUtil = new UserDAOUtil(context);
        UserInfo userInfo = userDAOUtil.querytUser();
        iv_main_headicon.setImageDrawable(userInfo.getHeadImage());
        //给listView设置数据适配器
        ConversationAdapter conversationAdapter = new ConversationAdapter(context);
        lv_conversation.setAdapter(conversationAdapter);
        lv_conversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,"i"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
                Activity activity = (Activity) ConversationFragment.this.context;
                //开启动画
                activity.overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
            }
        });
        //读取消息列表
        requsetConversation();
        //设置监听
        head_btn_add.setOnClickListener(this);
        iv_main_headicon.setOnClickListener(this);
    }

    /**
     * 读取消息列表
     */
    private void  requsetConversation() {

        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {

            @Override
            public void onSuccess(List<Conversation> conversations) {
                if (conversations == null) {
                    Log.i(TAG, "onSuccess: 本地为空 应该向服务器请求数据");
                } else {

                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_btn_add:
                showpopupwindow(v);
                break;
            case R.id.iv_main_headicon:
;
                break;
        }

    }

    private void showpopupwindow(View v) {
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
