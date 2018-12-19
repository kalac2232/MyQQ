package com.example.myqq.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myqq.adapter.ChatAdapter;
import com.example.myqq.R;

public class ChatActivity extends BaseActivity implements TextWatcher, View.OnClickListener {

    private ListView lv_chat;
    private EditText et_message;
    private ImageView iv_seating;
    private static final String TAG = "ChatActivity";
    private int mVirtualKeyHeight;
    private View rootLayout;
    private Button btn_can_not_send;
    private Button btn_sendmessage;
    private Button bt_chat_goback;
    private Context mContext;
    private ChatAdapter mChatAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mContext = this;
        //状态栏字体深色化
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        init();
    }

    private void init() {
        lv_chat = (ListView) findViewById(R.id.lv_chat);
        mChatAdapter = new ChatAdapter(mContext);
        lv_chat.setAdapter(mChatAdapter);
        et_message = (EditText) findViewById(R.id.et_message);
        iv_seating = (ImageView) findViewById(R.id.iv_Seating);
        btn_can_not_send = (Button) findViewById(R.id.btn_can_not_send);
        bt_chat_goback = (Button) findViewById(R.id.bt_chat_goback);
        btn_sendmessage = (Button) findViewById(R.id.btn_sendmessage);
        rootLayout = this.getWindow().getDecorView();
        KeyboardOnGlobalChangeListener keyboardOnGlobalChangeListener = new KeyboardOnGlobalChangeListener();
        //监听输入法的显示的状态
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardOnGlobalChangeListener);

        //一旦输入框中的内容发生的改变 则更换显示的按钮
        et_message.addTextChangedListener(this);
        btn_sendmessage.setOnClickListener(this);
        bt_chat_goback.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            //如果输入框中不为空  则显示可以发送的按钮
            btn_can_not_send.setVisibility(View.GONE);
            btn_sendmessage.setVisibility(View.VISIBLE);
        } else if (s.length() == 0) {
            btn_can_not_send.setVisibility(View.VISIBLE);
            btn_sendmessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_chat_goback:
                finish();
                break;
            case R.id.btn_sendmessage:
                String text = et_message.getText().toString();
                com.example.myqq.bean.Message message = new com.example.myqq.bean.Message(text, 0);
                mChatAdapter.addMessage(message);
                mChatAdapter.notifyDataSetChanged();
                et_message.setText("");
                break;
        }

    }

    class KeyboardOnGlobalChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            // 获取当前页面窗口的显示范围
            rootLayout.getWindowVisibleDisplayFrame(r);
            //获取屏幕高度
            int screenHeight = rootLayout.getRootView().getHeight();
            //获取输入法的高度 (r.bottom本应该是等于屏幕高度的 但是还包括了虚拟按键的高度 所以获取的输入法的高度还包括了一个虚拟按键的高度)
            int softHeight = screenHeight - (r.bottom);
            //如果测量的输入法高度大于屏幕的1/5，则说明输入法打开
            if (softHeight > screenHeight / 5) {
                //将聊天输入框（顶）上去
                riseEditText(softHeight - mVirtualKeyHeight);
                lv_chat.setSelection(mChatAdapter.getCount()-1);
            } else {
                //如果小于1/5，说明输入法未打开，记录此时的测量值（实际为虚拟按键的高度）
                mVirtualKeyHeight = softHeight;
                //将聊天输入框（降）下来
                dropEditText();
            }
        }
    }

    /**
     * 升聊天输入框 （设置聊天框下占位控件的高度）
     * @param keyboardHeight 升起的高度
     */
    private void riseEditText(int keyboardHeight) {
        ViewGroup.LayoutParams params = iv_seating.getLayoutParams();
        params.height = keyboardHeight;

        iv_seating.setLayoutParams(params);
    }

    /**
     * 收起输入法的时候降下输入框
     */
    private void dropEditText() {
        ViewGroup.LayoutParams params = iv_seating.getLayoutParams();
        //如果之前就是为关闭状态，则直接退出
        if (params.height == 0) {
            return;
        }
        params.height = 0;

        iv_seating.setLayoutParams(params);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
    }

}
