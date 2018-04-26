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

import io.rong.imlib.RongIMClient;

public class SplashActivity extends Activity {

    private Context mContext;
    private static final String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        mContext = this;
        //获取状态栏的高度，存放到sp中
        /**
         * 获取状态栏高度——方法
         * */

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

        //1秒后启动登陆页或主页
        new Handler().postDelayed(new Runnable(){
            public void run() {

                //从数据库中查询是否存在userinfo对象
                UserDAOUtil userDAOUtil = new UserDAOUtil(mContext);
                final UserInfo userInfo = userDAOUtil.querytUser();
                //boolean isloginin = SharePreferenceUtil.getBoolean(mContext, ConstantValue.ISLOGININ, false);
                if (userInfo != null) {
                    RongIMClient.connect(userInfo.getToken(), new RongIMClient.ConnectCallback() {
                        @Override
                        public void onTokenIncorrect() {
                            Toast.makeText(mContext,"token无效",Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "onTokenIncorrect: "+userInfo.getToken());
                        }

                        @Override
                        public void onSuccess(String userId) {
                            Toast.makeText(mContext,userId+"登陆成功",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            Toast.makeText(mContext,"登陆失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                    startActivity(new Intent(mContext,HomeActivity.class));
                } else {
                    //如果不存在则跳转到登陆界面
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("ClassName","SplashActivity");
                    startActivity(intent);
                }
                finish();
                overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
            }
        }, 1000);
    }
}
