package com.example.myqq.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myqq.R;
import com.example.myqq.net.netbean.LoginResultBean;
import com.example.myqq.utilts.ConstantValue;
import com.example.myqq.utilts.ProperTies;
import com.example.myqq.utilts.SharePreferenceUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Properties;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class LoginActivity extends Activity implements View.OnClickListener, TextWatcher {

    private ImageView iv_qqicon;
    private static Context mContext;
    private LinearLayout ll_guide;
    private LinearLayout ll_tip;
    private LinearLayout ll_login;
    private EditText let_loginname;
    private EditText let_pw;
    private Button sign_up;
    private TextView sign_up2;
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
    private RelativeLayout rl_loginnanme;
    private int width;
    private int screenHeight;
    private ObjectAnimator qqIconMoveAnim;
    private ObjectAnimator loginMoveAnim;
    private int qqIconMoveDis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        //获取屏幕的宽高
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        width = outMetrics.widthPixels;
        screenHeight = outMetrics.heightPixels;
        initView();
        initListener();

    }



    private void startAnim() {
        ll_guide.setVisibility(View.INVISIBLE);
        ll_login.setVisibility(View.VISIBLE);
        ll_tip.setVisibility(View.VISIBLE);

        //设置动画

        qqIconMoveAnim = ObjectAnimator.ofFloat(iv_qqicon, "translationX", 0.0F, -(iv_qqicon.getLeft() - rl_loginnanme.getLeft()));
        qqIconMoveAnim.setDuration(500);

        loginMoveAnim = ObjectAnimator.ofFloat(ll_login, "translationY", 0.0F, -(ll_login.getTop() - iv_qqicon.getBottom() - screenHeight / 20));
        loginMoveAnim.setDuration(500);
        //开启动画
        qqIconMoveAnim.start();
        loginMoveAnim.start();
    }
    private void initListener() {

        sign_in.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        bt_login_in.setOnClickListener(this);
        iv_name_arrows.setOnClickListener(this);
        iv_name_clean.setOnClickListener(this);
        iv_pw_clean.setOnClickListener(this);
        iv_pw_hide.setOnClickListener(this);
        sign_up2.setOnClickListener(this);
        let_loginname.addTextChangedListener(this);
        //当用户名输入框中的文字不为空时显示清除按钮
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

    private void initView() {
        //sign_in 登陆
        sign_in = (Button) findViewById(R.id.sign_in);//初始页的登陆按钮
        sign_up = (Button) findViewById(R.id.sign_up);//初始页的注册按钮
        sign_up2 = (TextView) findViewById(R.id.sign_up2);//登陆页的注册按钮

        iv_qqicon = (ImageView) findViewById(R.id.iv_login_qqicon); //上方的qq图标

        ll_guide = (LinearLayout) findViewById(R.id.ll_guide); //包裹初始界面登陆和新用户注册的linearlayout
        ll_tip = (LinearLayout) findViewById(R.id.ll_tip);//包裹阅读条款的linearlayout
        ll_login = (LinearLayout) findViewById(R.id.ll_login);//包裹输入框和登陆的linearlayout



        let_loginname = (EditText) findViewById(R.id.let_loginname);//用户名输入框
        let_pw = (EditText) findViewById(R.id.let_pw); //密码输入框
        bt_login_in = (Button) findViewById(R.id.bt_login_in);//登陆按钮
        //这个好像没什么用，先留着
        //包裹用户名输入框一整条的控件
        rl_loginnanme = (RelativeLayout) findViewById(R.id.rl_loginnanme);
        RelativeLayout rl_pw = (RelativeLayout) findViewById(R.id.rl_pw); //包裹密码输入框一整条的控件

        //头像预览图标
        iv_headicon = (ImageView) findViewById(R.id.iv_main_headicon);
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
            case R.id.sign_in:
                startAnim();
                break;
            case R.id.bt_login_in:
                //点击登陆操作
                final String QQnumber = let_loginname.getText().toString();
                final String Password = let_pw.getText().toString();
                //非空判断
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                if (QQnumber.equals("")) {
                    let_loginname.startAnimation(shake);
                }
                if (Password.equals("")) {
                    let_pw.startAnimation(shake);
                }

                if (!QQnumber.equals("") && !Password.equals("") ) {
                    //登陆操作
                    LoginRequestByOkHttp(QQnumber,Password);
                }

                break;

            case R.id.sign_up:
                //注册操作
            case R.id.sign_up2:
                //注册操作
                //点下注册按钮的跳转到注册界面
                mContext.startActivity(new Intent(this,RegisterActivity.class));

                break;
            case R.id.iv_name_arrows:
                break;
            case R.id.iv_name_clean:

                let_loginname.setText("");
                iv_headicon.setVisibility(View.GONE);
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



    /**
     * 登陆操作
     * @param QQNumber 要验证的QQ号
     * @param password 密码
     */
    public void LoginRequestByOkHttp(final String QQNumber, final String password) {
        //请求地址
        String Servlet = "LoginServlet";
        //从配置文件中读取服务器地址
        Properties proper = ProperTies.getProperties(mContext);
        String url = proper.getProperty("serverUrl") + Servlet;
        //拿到OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //构建FormBody,传入参数
        FormBody formBody = new FormBody.Builder()
                .add("QQNumber", QQNumber)
                .add("Password", password)
                .build();

        //构建Request,将FormBody作为Post方法的参数传入
        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        //将Request封装为Call
        Call call = client.newCall(request);
        //调用请求,重写回调方法
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(mContext, "Post Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                LoginResultBean loginResultBean = gson.fromJson(string, LoginResultBean.class);
                String result = loginResultBean.getParams().getResult();
                if (result.equals("success")) {
                    //登录成功
                    String nickname = loginResultBean.getParams().getNickname();
                    SharePreferenceUtil.putString(mContext, ConstantValue.QQ_Number,QQNumber);
                    SharePreferenceUtil.putString(mContext, ConstantValue.QQ_NICKNAME,nickname);
                    Log.i(TAG, "onResponse: 登录成功");
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    //登录失败
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext,"检查账号和密码",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

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
                //TODO 联网获取头像稍后再做
                //判断用户名获取图标
//                if (scan_user.equals(user)) {
//                    iv_headicon.setVisibility(View.VISIBLE);
//                } else {
//                    iv_headicon.setVisibility(View.GONE);
//                }
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
