package com.example.myqq.Activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myqq.R;
import com.example.myqq.Utilts.ConstantValue;
import com.example.myqq.Utilts.SharePreferenceUtil;
import com.example.myqq.View.LoginEditText;

public class LoginActivity extends Activity {

    private ImageView qq;
    private Context mContext;
    private LinearLayout ll_index;
    private LinearLayout ll_tip;
    private LinearLayout ll_login;
    private LoginEditText let_loginname;
    private LoginEditText let_pw;
    private String user = "123456";
    private String pw = "123456";
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        init();

    }

    private void init() {
        //sign_in 登陆
        Button sign_in = (Button) findViewById(R.id.sign_in);
        Button bt_login_in = (Button) findViewById(R.id.bt_login_in);
        qq = (ImageView) findViewById(R.id.qq);

        ll_index = (LinearLayout) findViewById(R.id.ll_index);
        ll_tip = (LinearLayout) findViewById(R.id.ll_tip);
        ll_login = (LinearLayout) findViewById(R.id.ll_login);

        let_loginname = (LoginEditText) findViewById(R.id.let_loginname);
        let_pw = (LoginEditText) findViewById(R.id.let_pw);
        //获取屏幕的宽高
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        final int width = outMetrics.widthPixels;
        final int height = outMetrics.heightPixels;
        final Button sign_up = (Button) findViewById(R.id.sign_up);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_index.setVisibility(View.GONE);
                ll_login.setVisibility(View.VISIBLE);
                ll_tip.setVisibility(View.VISIBLE);
                //动画
                ObjectAnimator//
                        .ofFloat(qq, "translationX", 0.0F,-width/3.2f)//
                        .setDuration(500)//
                        .start();
                ObjectAnimator//
                        .ofFloat(ll_login, "translationY", 0.0F, -height/6)//
                        .setDuration(500)//
                        .start();
            }
        });
        bt_login_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }
}
