package com.example.myqq.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.myqq.Fragment.AppFragment;
import com.example.myqq.Fragment.ContactsFragment;
import com.example.myqq.Fragment.ConversationFragment;
import com.example.myqq.R;
import com.example.myqq.View.NaviBarRadioButton;

/**
 *
 * Created by 97210 on 2/14/2018.
 */
public class HomeActivity extends Activity implements View.OnClickListener {

    private ContactsFragment contactsFragment;
    private AppFragment appFragment;
    private ConversationFragment conversationFragment;
    private Fragment currentFragment = null;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = this;
        //实现状态栏透明
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        init();
    }

    private void init() {


        conversationFragment = new ConversationFragment();
        contactsFragment = new ContactsFragment();
        appFragment = new AppFragment();

        showFragment(conversationFragment);
        NaviBarRadioButton rb_conversation =  (NaviBarRadioButton) findViewById(R.id.rb_conversation);
        NaviBarRadioButton rb_contact = (NaviBarRadioButton) findViewById(R.id.rb_contact);
        NaviBarRadioButton rb_apps = (NaviBarRadioButton) findViewById(R.id.rb_apps);

        rb_conversation.setOnClickListener(this);
        rb_contact.setOnClickListener(this);
        rb_apps.setOnClickListener(this);

    }



    /**
     * 显示相应的fragment
     * @param fragment 要显示的fragment
     */
    public void showFragment(Fragment fragment) {
        //创建fragmentManager对象
        FragmentManager fragmentManager = getFragmentManager();
        //开启事务 创建事务对象
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if (!fragment.isAdded()) {
            if (currentFragment != null) {
                //隐藏fragment
                fragmentTransaction.hide(currentFragment);
            }
            fragmentTransaction.add(R.id.fl_content,fragment);
        } else {
            if (currentFragment != null) {
                fragmentTransaction.hide(currentFragment);
            }
            fragmentTransaction.show(fragment);
        }
        //全局变量，记录当前显示的fragment
        currentFragment = fragment;
        fragmentTransaction.commit();
    }
    private float startY;
    private float startX;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_conversation:
                showFragment(conversationFragment);
                break;
            case R.id.rb_contact:
                showFragment(contactsFragment);
                break;
            case R.id.rb_apps:
                showFragment(appFragment);
                break;
        }
    }
}
