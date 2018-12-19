package com.example.myqq.activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.myqq.fragment.AppFragment;
import com.example.myqq.fragment.ContactsFragment;
import com.example.myqq.fragment.ConversationFragment;
import com.example.myqq.R;
import com.example.myqq.utilts.ConstantValue;
import com.example.myqq.utilts.SharePreferenceUtil;
import com.example.myqq.view.ContentLinearLayout;
import com.example.myqq.view.NaviBarRadioButton;
import com.example.myqq.view.SlideMenuView;


/**
 *
 * Created by 97210 on 2/14/2018.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private ContactsFragment contactsFragment;
    private AppFragment appFragment;
    private ConversationFragment conversationFragment;
    private Fragment currentFragment = null;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = this;
        init();
    }

    private void init() {
        //找到按钮控件
        NaviBarRadioButton rb_conversation = (NaviBarRadioButton) findViewById(R.id.rb_conversation);
        NaviBarRadioButton rb_contact = (NaviBarRadioButton) findViewById(R.id.rb_contact);
        NaviBarRadioButton rb_apps = (NaviBarRadioButton) findViewById(R.id.rb_apps);
        SlideMenuView slideMenuView = findViewById(R.id.slidingview);



        TextView sliding_nickname = (TextView) findViewById(R.id.tv_sliding_nickname);

        String nickname = SharePreferenceUtil.getString(this, ConstantValue.QQ_NICKNAME, "飞翔的企鹅");
        // 设置昵称
        sliding_nickname.setText(nickname);
        // 设置头像
        ImageView sliding_headIcon = (ImageView) findViewById(R.id.iv_sliding_headIcon);
        //sliding_headIcon.setImageDrawable(userInfo.getHeadImage());
        sliding_headIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

        conversationFragment = new ConversationFragment(slideMenuView);
        contactsFragment = new ContactsFragment();
        appFragment = new AppFragment();
        //默认显示消息Fragment
        showFragment(conversationFragment);

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
