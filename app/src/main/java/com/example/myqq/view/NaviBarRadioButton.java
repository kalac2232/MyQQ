package com.example.myqq.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.myqq.R;

/**
 * Created by 97210 on 2/20/2018.
 */

public class NaviBarRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    private LayerDrawable selected_layerDrawable;
    private LayerDrawable normal_layerDrawable;
    private StateListDrawable stalistDrawable;
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/android";
    private int iconWidth = 100;
    private int iconHeight = 100;
    private int iconOffset = 10;
    private int initialFaceOffset = 0;//初始小图标的偏移量
    private static final String TAG = "NaviBarRadioButton";
    private Drawable icon_selected;
    private Drawable smallIcon_Selected;
    private Drawable icon_Normal;
    private Drawable smallIcon_Normal;
    private String type;
    private Context mContext;

    public NaviBarRadioButton(Context context) {
        super(context);
        mContext = context;
    }

    public NaviBarRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        type = attrs.getAttributeValue(NAMESPACE, "text");
        init(type);

    }

    public NaviBarRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private void init(final String type) {
        switch (type) {
            case "消息":
                icon_selected = ContextCompat.getDrawable(mContext, R.drawable.skin_tab_icon_conversation_selected);
                smallIcon_Selected = ContextCompat.getDrawable(mContext, R.drawable.face_conversation_selected);
                icon_Normal = ContextCompat.getDrawable(mContext, R.drawable.skin_tab_icon_conversation_normal);
                smallIcon_Normal = ContextCompat.getDrawable(mContext, R.drawable.face_conversation_normal);
                initialFaceOffset = 7;
                break;
            case "联系人":
                icon_selected = ContextCompat.getDrawable(mContext, R.drawable.skin_tab_icon_contact_selected);
                smallIcon_Selected = ContextCompat.getDrawable(mContext, R.drawable.face_contact_selected);
                icon_Normal = ContextCompat.getDrawable(mContext, R.drawable.skin_tab_icon_contact_normal);
                smallIcon_Normal = ContextCompat.getDrawable(mContext, R.drawable.face_contact_normal);
                break;
            case "动态":
                icon_selected = ContextCompat.getDrawable(mContext, R.drawable.skin_tab_icon_plugin_selected);
                smallIcon_Selected = ContextCompat.getDrawable(mContext, R.drawable.face_plugin_selected);
                icon_Normal = ContextCompat.getDrawable(mContext, R.drawable.skin_tab_icon_plugin_normal);
                smallIcon_Normal = ContextCompat.getDrawable(mContext, R.drawable.skin_tab_icon_plugin_normal);
                break;
        }

        selected_layerDrawable = new LayerDrawable(new Drawable[]{icon_selected, smallIcon_Selected});
        selected_layerDrawable.setLayerInset(0, 0, 0, 0, 0);
        selected_layerDrawable.setLayerInset(1, 0, 0, 0, 0);
        selected_layerDrawable.setBounds(0, 0, iconWidth, iconHeight);

        normal_layerDrawable = new LayerDrawable(new Drawable[]{icon_Normal, smallIcon_Normal});
        normal_layerDrawable.setLayerInset(0, 0, 0, 0, 0);
        normal_layerDrawable.setLayerInset(1, initialFaceOffset, 0, -initialFaceOffset, 0);
        normal_layerDrawable.setBounds(0, 0, iconWidth, iconHeight);


        //初始化一个空对象
        stalistDrawable = new StateListDrawable();
        //获取对应的属性值 Android框架自带的属性 attr
        int state_checked = android.R.attr.state_checked;

        stalistDrawable.addState(new int[]{state_checked}, selected_layerDrawable);
        stalistDrawable.addState(new int[]{-state_checked}, normal_layerDrawable);
        stalistDrawable.setBounds(0, 0, iconWidth, iconHeight);

        setCompoundDrawables(null, stalistDrawable, null, null);

        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setTextColor(Color.parseColor("#ef1ea3d8"));
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rb_scale_anim);
                    startAnimation(animation);
                } else {
                    setTextColor(Color.parseColor("#7f8393"));
                }
            }
        });
    }


    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:


                float y2 = event.getY() - getHeight() / 2;
                float x2 = event.getX() - getWidth() / 2;
                double l = Math.sqrt((x2 * x2 + y2 * y2));

                int y1 = (int) (iconOffset * y2 / l);
                int x1 = (int) (iconOffset * x2 / l);
                if (!type.equals("动态")) {
                    normal_layerDrawable.setLayerInset(1, x1, y1, -x1, -y1);
                    selected_layerDrawable.setLayerInset(1, x1, y1, -x1, -y1);
                } else {
                    selected_layerDrawable.setLayerInset(1, x1 / 2, y1 / 2, -x1 / 2, -y1 / 2);
                }

                selected_layerDrawable.setBounds(0 + x1 / 2, 0 + y1 / 2, iconWidth + x1 / 2, iconHeight + y1 / 2);

                normal_layerDrawable.setBounds(0 + x1 / 2, 0 + y1 / 2, iconWidth + x1 / 2, iconHeight + y1 / 2);
                //setCompoundDrawables(null, stalistDrawable,null,null);
                return true;
            case MotionEvent.ACTION_UP:
                selected_layerDrawable.setLayerInset(1, 0, 0, 0, 0);
                selected_layerDrawable.setBounds(0, 0, iconWidth, iconHeight);
                normal_layerDrawable.setLayerInset(1, initialFaceOffset, 0, -initialFaceOffset, 0);
                normal_layerDrawable.setBounds(0, 0, iconWidth, iconHeight);
                //setCompoundDrawables(null, stalistDrawable,null,null);
                break;
        }

        return super.onTouchEvent(event);
        //
    }


}
