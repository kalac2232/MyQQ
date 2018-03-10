package com.example.myqq.Activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Window;

import com.example.myqq.R;
import com.example.myqq.Utilts.ConstantValue;
import com.example.myqq.Utilts.SharePreferenceUtil;

public class SplashActivity extends Activity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        mContext = this;
        //获取状态栏的高度，存放到sp中
        /**
         * 获取状态栏高度——方法
         * */

        if (SharePreferenceUtil.getInt(mContext,ConstantValue.STATUSBARHEIGHT,-1) == -1) {
            int statusBarHeight = -1;
            //获取status_bar_height资源的ID
            int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusBarHeight = mContext.getResources().getDimensionPixelSize(resourceId);
            }
            //存入sp中
            SharePreferenceUtil.putInt(mContext,ConstantValue.STATUSBARHEIGHT,statusBarHeight);
        }

        //1秒后启动登陆页或主页
        new Handler().postDelayed(new Runnable(){
            public void run() {
                //execute the task
                //从xml中取出记录是否成功登陆的值
                boolean isloginin = SharePreferenceUtil.getBoolean(mContext, ConstantValue.ISLOGININ, false);
                if (isloginin) {
                    startActivity(new Intent(mContext,HomeActivity.class));
                } else {

                    startActivity(new Intent(mContext, LoginActivity.class));
                }
                finish();
                overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
            }
        }, 1000);
    }
}
