package com.example.myqq.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myqq.Fragment.AppFragment;
import com.example.myqq.Fragment.ContactsFragment;
import com.example.myqq.Fragment.NewsFragment;
import com.example.myqq.R;

/**
 *
 * Created by 97210 on 2/14/2018.
 */
public class HomeActivity extends Activity implements View.OnClickListener {

    private ContactsFragment contactsFragment;
    private AppFragment appFragment;
    private NewsFragment newsFragment;
    private Button bt_contacts;
    private Button bt_app;
    private Button bt_news;
    private Fragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        bt_contacts = (Button) findViewById(R.id.bt_contacts);
        bt_app = (Button) findViewById(R.id.bt_app);
        bt_news = (Button) findViewById(R.id.bt_news);

        newsFragment = new NewsFragment();
        contactsFragment = new ContactsFragment();
        appFragment = new AppFragment();

        showFragment(newsFragment);

        bt_news.setOnClickListener(this);
        bt_contacts.setOnClickListener(this);
        bt_app.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_news:
                showFragment(newsFragment);
                break;
            case R.id.bt_contacts:
                showFragment(contactsFragment);
                break;
            case R.id.bt_app:
                showFragment(appFragment);
                break;
        }

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

}
