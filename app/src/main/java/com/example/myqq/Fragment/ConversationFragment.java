package com.example.myqq.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.myqq.Adapter.ConversationAdapter;
import com.example.myqq.R;

/**
 * Created by 97210 on 2/17/2018.
 */

public class ConversationFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ConversationFragment";
    Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversation_fragment, null);
        init(view);

        return view;
    }

    private void init(View view) {
        //找到控件
        Button head_btn_add = (Button) view.findViewById(R.id.head_btn_add);
        ImageView iv_headicon = (ImageView) view.findViewById(R.id.iv_headicon);
        ListView lv_conversation = (ListView) view.findViewById(R.id.lv_conversation);
        //给listView设置数据适配器
        lv_conversation.setAdapter(new ConversationAdapter(context));

        //设置监听
        head_btn_add.setOnClickListener(this);
        iv_headicon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_btn_add:
                showpopupwindow(v);
                break;
            case R.id.iv_headicon:
                //因为SlideMenuView类中的open方法暂时不会静态处理 则先用模拟手指滑动打开侧栏
                getActivity().dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN, 400, 500, 0));
                getActivity().dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_MOVE, 400+500, 500, 0));
                getActivity().dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_UP, 400+500, 500, 0));
                break;
        }

    }

    private void showpopupwindow(View v) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_window, null);
        //构造方法public PopupWindow(View contentView, int width, int height, boolean focusable)  focusable让PopupWindow获得焦点
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //相对某个控件的位置（正左下方），无偏移
        popupWindow.showAsDropDown(v);
        //让点击后退键是关闭弹窗
        popupWindow.setOutsideTouchable(true);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.8f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //当popupwindow dismiss时将界面恢复
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }
}
