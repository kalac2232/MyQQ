package com.example.myqq.Activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DownloadManager;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myqq.R;
import com.example.myqq.Utilts.ConstantValue;
import com.example.myqq.Utilts.ProperTies;
import com.example.myqq.Utilts.SharePreferenceUtil;
import com.example.myqq.View.LoginEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LoginActivity extends Activity implements View.OnClickListener, TextWatcher {

    private ImageView qq;
    private static Context mContext;
    private LinearLayout ll_index;
    private LinearLayout ll_tip;
    private LinearLayout ll_login;
    private LoginEditText let_loginname;
    private LoginEditText let_pw;
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
                //点击登陆操作
                String QQnumber = let_loginname.getText().toString();
                String Password = let_pw.getText().toString();
                //登陆操作
                LoginRequest(QQnumber,Password);

                break;
            case R.id.sign_in:
                //引导界面的登陆按钮  按下后会跳到真正登陆界面
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
            case R.id.sign_up:
                //注册操作
                //TODO 点下注册按钮的跳转到注册界面
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
     * @param password 和密码
     */
    public static void LoginRequest(final String QQNumber, final String password) {
        //请求地址
        String Servlet = "LoginServlet";
        //从配置文件中读取服务器地址
        Properties proper = ProperTies.getProperties(mContext);
        String url = proper.getProperty("serverUrl") + Servlet;
        String tag = "Login";
        Log.i(TAG, "LoginRequest: url:" + url);
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);
        //创建响应监听对象
        Response.Listener<String> responseListner = new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.i(TAG, "onResponse: response" + response);
                try {
                    JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                    String result = jsonObject.getString("Result");
                    if (result.equals("success")) {
                        //做自己的登录成功操作，如页面跳转
                        //跳转到主页面
                        //TODO 还需要一个将用户数据写入本地数据库的操作，否则第二次打开无法显示昵称头像等信息
                        mContext.startActivity(new Intent(mContext,HomeActivity.class));
                        SharePreferenceUtil.putBoolean(mContext, ConstantValue.ISLOGININ,true);
                        //TODO 处理接收到的数据，并需要单例模式，将接收到的数据存放起来
                        String nickname = jsonObject.getString("Nickname");
                        String headImageNumber = jsonObject.getString("HeadImageNumber");
                        //关闭当前activity
                        Activity activity = (Activity) mContext;
                        activity.finish();
                    } else {
                        //登录失败操作
                        Toast.makeText(mContext,"检查账号和密码",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //做自己的请求异常操作，如Toast提示（“无网络连接”等）
                    Log.e(TAG, e.getMessage(), e);
                    Toast.makeText(mContext,"抛异常",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onResponse: 抛异常");
                }
            }

        };
        //错误监听对象
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                Log.e(TAG, error.getMessage(), error);
            }
        };
        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url, responseListner, errorListener) {
            @Override
            //在这里设置POST参数
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("QQNumber", QQNumber);  //注⑥
                params.put("Password", password);
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
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
