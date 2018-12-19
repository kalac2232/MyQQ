package com.example.myqq.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myqq.R;
import com.example.myqq.net.netbean.RegisterResultBean;
import com.example.myqq.utilts.ProperTies;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Properties;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;

/**
 * 注册页
 * Created by 97210 on 2018/3/23.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

    private Context mContext;
    private Button bt_register;
    private ImageView select_headIcon;
    private EditText register_nickname;
    private EditText register_pw;
    private EditText register_telephone;

    /**
     * 选中的头像编号
     */
    private int SELECTED_HEADICONNUMBER = 1;
    private PopupWindow popupWindow;
    private LinearLayout ll_register;
    private LinearLayout ll_register_success;
    private TextView tv_register_qQnumber;
    private Button bt_gotoLogin;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        initUI();
    }

    private void initUI() {
        bt_register = (Button) findViewById(R.id.bt_register); //注册按钮
        select_headIcon = (ImageView) findViewById(R.id.select_headIcon); //选择头像的按钮与显示当前选中头像的图标
        register_nickname = (EditText) findViewById(R.id.register_nickname); //昵称输入框
        register_pw = (EditText) findViewById(R.id.register_pw); //密码输入框
        register_telephone = (EditText) findViewById(R.id.register_telephone); // 电话输入框

        ll_register = (LinearLayout) findViewById(R.id.ll_register); //注册页
        ll_register_success = (LinearLayout) findViewById(R.id.ll_register_success); //注册成功页
        tv_register_qQnumber = (TextView) findViewById(R.id.tv_register_QQnumber); //申请成功QQ号码的显示TextView
        bt_gotoLogin = (Button) findViewById(R.id.bt_gotoLogin); //跳去登陆页的按钮

        bt_register.setOnClickListener(this);
        select_headIcon.setOnClickListener(this);
        bt_gotoLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                RegisterClick();
                break;
            case R.id.select_headIcon:
                //显示popupwindow 进行选择头像
                showpopupwindow(v);
                break;
            case R.id.bt_gotoLogin:
                //跳转到登陆页
                Intent intent = new Intent(this, LoginActivity.class);
                mContext.startActivity(intent);
                finish();
                break;
            case R.id.iv_headicon_0:
                SELECTED_HEADICONNUMBER = 0;
                updataHeadIcon();
                popupWindow.dismiss();
                break;
            case R.id.iv_headicon_1:
                SELECTED_HEADICONNUMBER = 1;
                updataHeadIcon();
                popupWindow.dismiss();
                break;
            case R.id.iv_headicon_2:
                SELECTED_HEADICONNUMBER = 2;
                updataHeadIcon();
                popupWindow.dismiss();
                break;
            case R.id.iv_headicon_3:
                SELECTED_HEADICONNUMBER = 3;
                updataHeadIcon();
                popupWindow.dismiss();
                break;
            case R.id.iv_headicon_4:
                SELECTED_HEADICONNUMBER = 4;
                updataHeadIcon();
                popupWindow.dismiss();
                break;
            case R.id.iv_headicon_5:
                SELECTED_HEADICONNUMBER = 5;
                updataHeadIcon();
                popupWindow.dismiss();
                break;
            case R.id.iv_headicon_6:
                SELECTED_HEADICONNUMBER = 6;
                updataHeadIcon();
                popupWindow.dismiss();
                break;
            case R.id.iv_headicon_7:
                SELECTED_HEADICONNUMBER = 7;
                updataHeadIcon();
                popupWindow.dismiss();
                break;
        }
    }

    /**
     * 实现注册按钮的点击功能
     */
    private void RegisterClick() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        String nickname = register_nickname.getText().toString();
        String password = register_pw.getText().toString();
        String telephone = register_telephone.getText().toString();
        if (nickname.equals("")) {
            register_nickname.startAnimation(shake);
        }
        if (password.equals("")) {
            register_pw.startAnimation(shake);
        }
        if (telephone.equals("")) {
            register_telephone.startAnimation(shake);
        }
        if (!nickname.equals("") && !password.equals("") && !telephone.equals("")) {
            RegisterRequestByOkHttp(password, telephone, nickname, String.valueOf(SELECTED_HEADICONNUMBER));
        }

    }

    /**
     * 注册QQ号
     *
     * @param password        用户输入的密码
     * @param telephone       用户输入的电话号码 用于找回密码
     * @param nickame         昵称
     * @param headImageNumber 头像代号
     */
//    public void RegisterRequest(final String password,final String telephone,final String nickame,final String headImageNumber) {
//        //请求地址
//        String Servlet = "RegisterServlet";
//        //从配置文件中读取服务器地址
//        Properties proper = ProperTies.getProperties(mContext);
//        String url = proper.getProperty("serverUrl") + Servlet;
//        String tag = "RegisterRequest";
//
//        //取得请求队列
//        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
//        //防止重复请求，所以先取消tag标识的请求队列
//        requestQueue.cancelAll(tag);
//        //创建响应监听对象
//        Response.Listener<String> responseListner = new Response.Listener<String>() {
//            String registerNumber;
//            //定义消息对象
//            Message msg = new Message();
//
//            @Override
//            public void onResponse(String response) {
//                Log.i(TAG, "onResponse: response" + response);
//                try {
//                    JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
//                    String result = jsonObject.getString("Result");
//                    if (result.equals("success")) {
//                        //注册成功
//                        registerNumber = jsonObject.getString("RegisterNumber");
//                        //将注册页不可见
//                        ll_register.setVisibility(View.GONE);
//                        //设置申请成功的QQ号
//                        tv_register_qQnumber.setText(registerNumber);
//                        //设置注册页可见
//                        ll_register_success.setVisibility(View.VISIBLE);
//                    } else {
//                        //注册失败
//                        registerNumber = null;
//                        Toast.makeText(mContext,"申请失败",Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    //做自己的请求异常操作，如Toast提示（“无网络连接”等）
//                    Log.e(TAG, e.getMessage(), e);
//                    Toast.makeText(mContext,"抛异常",Toast.LENGTH_SHORT).show();
//                    Log.i(TAG, "onResponse: 无网络连接");
//                }
//
//
//            }
//
//        };
//        //错误监听对象
//        Response.ErrorListener errorListener = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
//                Log.e(TAG, error.getMessage(), error);
//            }
//        };
//        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
//        final StringRequest request = new StringRequest(Request.Method.POST, url, responseListner, errorListener) {
//            @Override
//            //在这里设置POST参数
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Password", password);  //注⑥
//                params.put("Telephone", telephone);
//                params.put("Nickname", nickame);
//                params.put("HeadImageNumber", headImageNumber);
//                return params;
//            }
//        };
//        //设置Tag标签
//        request.setTag(tag);
//
//        //将请求添加到队列中
//        requestQueue.add(request);
//
//
//    }
    public void RegisterRequestByOkHttp(final String password, final String telephone, final String nickame, final String headImageNumber) {
        //请求地址
        String Servlet = "RegisterServlet";
        //从配置文件中读取服务器地址
        Properties proper = ProperTies.getProperties(mContext);
        String url = proper.getProperty("serverUrl") + Servlet;
        String tag = "RegisterRequest";

        //拿到OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //构建FormBody,传入参数
        FormBody formBody = new FormBody.Builder()
                .add("Password", password)
                .add("Telephone", telephone)
                .add("Nickname", nickame)
                .add("HeadImageNumber", headImageNumber)
                .build();

        //构建Request,将FormBody作为Post方法的参数传入
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        //将Request封装为Call
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final RegisterResultBean registerResultBean = gson.fromJson(string, RegisterResultBean.class);
                String result = registerResultBean.getParams().getResult();
                if (result.equals("success")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String registerNumber = registerResultBean.getParams().getRegisterNumber();
                            //将注册页不可见
                            ll_register.setVisibility(View.GONE);
                            //设置申请成功的QQ号
                            tv_register_qQnumber.setText(registerNumber);
                            //设置注册页可见
                            ll_register_success.setVisibility(View.VISIBLE);
                        }
                    });


                } else {
                    //注册失败

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }


    /**
     * 根据当前选中的头像编号更新显示出的头像
     */
    private void updataHeadIcon() {
        switch (SELECTED_HEADICONNUMBER) {
            case 0:
                select_headIcon.setImageDrawable(getResources().getDrawable(R.drawable.headimage_0));
                break;
            case 1:
                select_headIcon.setImageDrawable(getResources().getDrawable(R.drawable.headimage_1));
                break;
            case 2:
                select_headIcon.setImageDrawable(getResources().getDrawable(R.drawable.headimage_2));
                break;
            case 3:
                select_headIcon.setImageDrawable(getResources().getDrawable(R.drawable.headimage_3));
                break;
            case 4:
                select_headIcon.setImageDrawable(getResources().getDrawable(R.drawable.headimage_4));
                break;
            case 5:
                select_headIcon.setImageDrawable(getResources().getDrawable(R.drawable.headimage_5));
                break;
            case 6:
                select_headIcon.setImageDrawable(getResources().getDrawable(R.drawable.headimage_6));
                break;
            case 7:
                select_headIcon.setImageDrawable(getResources().getDrawable(R.drawable.headimage_7));
                break;
        }
    }

    private static final String TAG = "RegisterActivity";

    /**
     * 显示popupwindow
     *
     * @param v popupwindow的父控件
     */
    private void showpopupwindow(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_select_headicon, null);
        //构造方法public PopupWindow(View contentView, int width, int height, boolean focusable)  focusable让PopupWindow获得焦点
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        view.findViewById(R.id.iv_headicon_0).setOnClickListener(this);
        view.findViewById(R.id.iv_headicon_1).setOnClickListener(this);
        view.findViewById(R.id.iv_headicon_2).setOnClickListener(this);
        view.findViewById(R.id.iv_headicon_3).setOnClickListener(this);
        view.findViewById(R.id.iv_headicon_4).setOnClickListener(this);
        view.findViewById(R.id.iv_headicon_5).setOnClickListener(this);
        view.findViewById(R.id.iv_headicon_6).setOnClickListener(this);
        view.findViewById(R.id.iv_headicon_7).setOnClickListener(this);


        //设置动画 **设置动画必须在  showAtLocation() 方法之前.否则不起作用**
        popupWindow.setAnimationStyle(R.style.PopAnim);
        //显示的位置
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, v.getHeight() / 2);
        //让点击后退键是关闭弹窗
        popupWindow.setOutsideTouchable(true);

        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.8f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //当popupwindow dismiss时将界面恢复
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

    }

}
