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
