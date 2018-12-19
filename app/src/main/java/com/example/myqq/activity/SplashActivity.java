package com.example.myqq.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.example.myqq.R;
import com.example.myqq.utilts.ConstantValue;
import com.example.myqq.utilts.SharePreferenceUtil;


public class SplashActivity extends Activity {

    private Context mContext;
    private static final String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        initView();
        initData();
        final String userId = SharePreferenceUtil.getString(mContext, ConstantValue.QQ_Number, null);
        //1秒后启动登陆页或主页
        new Handler().postDelayed(new Runnable(){
            public void run() {


                if (userId != null) {
                    startActivity(new Intent(mContext,HomeActivity.class));
                } else {
                    //如果不存在则跳转到登陆界面
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
                finish();

            }
        }, 1000);
    }

    private void initView() {

    }
    private void initData() {
        //获取状态栏的高度，存放到全局静态变量中
        if (ConstantValue.statusBarHeight == -1) {
            int statusBarHeight = -1;
            //获取status_bar_height资源的ID
            int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusBarHeight = mContext.getResources().getDimensionPixelSize(resourceId);
            }
            ConstantValue.statusBarHeight = statusBarHeight;
        }
    }
}
