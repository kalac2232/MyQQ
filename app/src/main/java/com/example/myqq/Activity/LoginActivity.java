package com.example.myqq.Activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myqq.R;
import com.example.myqq.Utilts.ConstantValue;
import com.example.myqq.Utilts.SharePreferenceUtil;
import com.example.myqq.View.LoginEditText;

public class LoginActivity extends Activity implements View.OnClickListener, TextWatcher {

    private ImageView qq;
    private Context mContext;
    private LinearLayout ll_index;
    private LinearLayout ll_tip;
    private LinearLayout ll_login;
    private LoginEditText let_loginname;
    private LoginEditText let_pw;
    private String user = "123456";
    private String pw = "123456";
    private Button sign_up;
    private Button sign_in;
    private Button bt_login_in;
    private ImageView iv_headicon;
    private ImageView iv_name_arrows;
    private ImageView iv_name_clean;
    private ImageView iv_pw_hide;
    private ImageView iv_pw_clean;
    private boolean nameFocus = false;
    private boolean passwordFocus = false;
    private boolean passwordHide = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initUI();
        initListener();

    }

    private void initListener() {

        sign_in.setOnClickListener(this);
        bt_login_in.setOnClickListener(this);
        iv_name_arrows.setOnClickListener(this);
        iv_name_clean.setOnClickListener(this);
        iv_pw_clean.setOnClickListener(this);
        iv_pw_hide.setOnClickListener(this);

        let_loginname.addTextChangedListener(this);
        let_loginname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                nameFocus = hasFocus;
                if (!hasFocus && let_loginname.length()>0) {
                    iv_name_clean.setVisibility(View.GONE);
                } else if (hasFocus && let_loginname.length()>0){
                    iv_name_clean.setVisibility(View.VISIBLE);
                }
            }
        });
        let_pw.addTextChangedListener(this);
        let_pw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                passwordFocus = hasFocus;
                //如果文本框中尚有文字，并没有焦点则删除清除图标
                if (!hasFocus && let_pw.length()>0) {
                    iv_pw_clean.setVisibility(View.GONE);
                } else if (hasFocus && let_pw.length()>0){
                    iv_pw_clean.setVisibility(View.VISIBLE);
                }
                if (hasFocus) {
                    iv_pw_hide.setVisibility(View.VISIBLE);
                } else {
                    iv_pw_hide.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initUI() {
        //sign_in 登陆
        sign_in = (Button) findViewById(R.id.sign_in);//初始页的登陆按钮
        sign_up = (Button) findViewById(R.id.sign_up);//初始页的注册按钮

        qq = (ImageView) findViewById(R.id.qq); //上方的qq图标

        ll_index = (LinearLayout) findViewById(R.id.ll_index); //包裹初始界面登陆和新用户注册的linearlayout
        ll_tip = (LinearLayout) findViewById(R.id.ll_tip);//包裹阅读条款的linearlayout
        ll_login = (LinearLayout) findViewById(R.id.ll_login);//包裹输入框和登陆的linearlayout



        let_loginname = (LoginEditText) findViewById(R.id.let_loginname);//用户名输入框
        let_pw = (LoginEditText) findViewById(R.id.let_pw); //密码输入框
        bt_login_in = (Button) findViewById(R.id.bt_login_in);//登陆按钮
        //这个好像没什么用，先留着
        RelativeLayout rl_loginnanme = (RelativeLayout) findViewById(R.id.rl_loginnanme); //包裹用户名输入框一整条的控件
        RelativeLayout rl_pw = (RelativeLayout) findViewById(R.id.rl_pw); //包裹密码输入框一整条的控件

        //头像预览图标
        iv_headicon = (ImageView) findViewById(R.id.iv_headicon);
        //下拉图标
        iv_name_arrows = (ImageView) findViewById(R.id.iv_name_arrows);
        //清除输入文本图标
        iv_name_clean = (ImageView) findViewById(R.id.iv_name_clean);
        //切换密码输入模式图标
        iv_pw_hide = (ImageView) findViewById(R.id.iv_pw_hide);
        //清除输入密码图标
        iv_pw_clean = (ImageView) findViewById(R.id.iv_pw_clean);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login_in:
                String QQnumber = let_loginname.getText().toString();
                String Password = let_pw.getText().toString();
                if (QQnumber.equals(user) && Password.equals(pw)) {
                    //跳转到主页面
                    startActivity(new Intent(mContext,HomeActivity.class));
                    SharePreferenceUtil.putBoolean(mContext, ConstantValue.ISLOGININ,true);
                    finish();
                } else {
                    Toast.makeText(mContext,"检查账号和密码",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sign_in:
                //获取屏幕的宽高
                WindowManager manager = this.getWindowManager();
                DisplayMetrics outMetrics = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(outMetrics);
                final int width = outMetrics.widthPixels;
                final int height = outMetrics.heightPixels;
                ll_index.setVisibility(View.GONE);
                ll_login.setVisibility(View.VISIBLE);
                ll_tip.setVisibility(View.VISIBLE);
                //设置动画
                ObjectAnimator//
                        .ofFloat(qq, "translationX", 0.0F,-width/3.2f)//
                        .setDuration(500)//
                        .start();
                ObjectAnimator//
                        .ofFloat(ll_login, "translationY", 0.0F, -height/6)//
                        .setDuration(500)//
                        .start();
                break;
            case R.id.iv_name_arrows:
                break;
            case R.id.iv_name_clean:

                let_loginname.setText("");

                break;
            case R.id.iv_pw_clean:
                let_pw.setText("");
                break;
            case R.id.iv_pw_hide:
                //将密码隐藏状态置反
                passwordHide = !passwordHide;
                //根据状态设置密码输入的类型
                if (passwordHide) {
                    //更改图标
                    iv_pw_hide.setImageResource(R.drawable.unhide_pw);
                    let_pw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    iv_pw_hide.setImageResource(R.drawable.hide_pw);
                    let_pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                //输入框光标一直在输入文本后面
                Editable etable = let_pw.getText();
                Selection.setSelection(etable, etable.length());

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    private static final String TAG = "LoginActivity";
    //文本改变时会调用此方法
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //如果当前选中的文本框为用户名框
        String scan_user = s.toString();
        if (nameFocus) {
            //当不为空的时候显示清除图标
            if (s.length() > 0) {
                iv_name_clean.setVisibility(View.VISIBLE);
                //判断用户名获取图标
                if (scan_user.equals(user)) {

                    iv_headicon.setVisibility(View.VISIBLE);
                } else {
                    iv_headicon.setVisibility(View.GONE);
                }
            } else {
                iv_name_clean.setVisibility(View.GONE);
            }
        } else {
            if (s.length() > 0) {
                iv_pw_clean.setVisibility(View.VISIBLE);
            } else {
                iv_pw_clean.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
