package com.example.myqq.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myqq.Bean.UserInfo;
import com.example.myqq.DAO.UserDAO.UserDAOUtil;
import com.example.myqq.R;
import com.example.myqq.Utilts.ConstantValue;
import com.example.myqq.Utilts.SharePreferenceUtil;


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

        //1秒后启动登陆页或主页
        new Handler().postDelayed(new Runnable(){
            public void run() {
                /*
                //从数据库中查询是否存在userinfo对象
                UserDAOUtil userDAOUtil = new UserDAOUtil(mContext);
                final UserInfo userInfo = userDAOUtil.querytUser();
                */
                String userId = SharePreferenceUtil.getString(mContext, ConstantValue.USERID, null);
                if (userId != null) {
                    startActivity(new Intent(mContext,HomeActivity.class));
                } else {
                    //如果不存在则跳转到登陆界面
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    //打个标记 标识是哪个页面跳转到的登陆页面
                    intent.putExtra("ClassName","SplashActivity");
                    startActivity(intent);
                }
                finish();
                //页面跳转动画
                overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
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
