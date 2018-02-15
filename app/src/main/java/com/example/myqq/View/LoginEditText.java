package com.example.myqq.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.icu.text.DisplayContext;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.myqq.R;

import static android.content.ContentValues.TAG;

/**
 *
 * Created by 97210 on 2/13/2018.
 */

public class LoginEditText extends EditText implements View.OnTouchListener, TextWatcher, View.OnFocusChangeListener {
    private Paint paint;

    private Drawable clean;
    private Drawable unhidepw;
    private Drawable hidepw;
    private Drawable unhidepw_clean;
    private Drawable hidepw_clean;
    boolean isEyeChecked = false;
    boolean isPWEdit = false;
    public LoginEditText(Context context) {
        super(context);
        init();
    }

    public LoginEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        //可以自定义画笔的颜色
        paint.setColor(Color.WHITE);
        init();

    }

    public LoginEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        clean = ContextCompat.getDrawable(getContext(), R.drawable.clean);
        unhidepw = ContextCompat.getDrawable(getContext(), R.drawable.unhidepw);
        hidepw = ContextCompat.getDrawable(getContext(), R.drawable.hidepw);
        unhidepw_clean = ContextCompat.getDrawable(getContext(), R.drawable.unhidepw_clean);
        hidepw_clean = ContextCompat.getDrawable(getContext(), R.drawable.hidepw_clean);
        //将图标画成白色
        clean.setTint(Color.WHITE);
        unhidepw.setTint(Color.WHITE);
        hidepw.setTint(Color.WHITE);
        unhidepw_clean.setTint(Color.WHITE);
        hidepw_clean.setTint(Color.WHITE);
        setOnTouchListener(this);
        addTextChangedListener(this);
        setOnFocusChangeListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**canvas画直线，从左下角到右下角，this.getHeight()-2是获得父edittext的高度，但是必须要-2这样才能保证
         * 画的横线在edittext上面，那样才看得见，如果不-2，你可以试一试看一下是否能看得见。
         * public void drawLine (float startX, float startY, float stopX, float stopY, Paint paint)
         */
        canvas.drawLine(10, this.getHeight()-2, this.getWidth()-10, this.getHeight()-2, paint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            //如果为密码框 且在图片区域 且获取到了焦点
            if (v.getId() == R.id.let_pw  && eventX >= getWidth() - unhidepw_clean.getIntrinsicWidth() && eventX <= getWidth() && hasFocus()) {
                if (eventX >= getWidth() - unhidepw_clean.getIntrinsicWidth()/2) {

                    setText("");
                } else {
                    isEyeChecked = !isEyeChecked;
                    if (isEyeChecked) {
                        //设置输入框的输入类型
                        setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        //输入框光标一直在输入文本后面
                        Editable etable = getText();
                        Selection.setSelection(etable, etable.length());
                        if (length() > 0) {
                            setCompoundDrawablesWithIntrinsicBounds(null, null, unhidepw_clean, null);
                        }else {
                            setCompoundDrawablesWithIntrinsicBounds(null, null, unhidepw, null);
                        }

                    } else {
                        if (length() > 0) {
                            setCompoundDrawablesWithIntrinsicBounds(null, null, hidepw_clean, null);
                        }else {
                            setCompoundDrawablesWithIntrinsicBounds(null, null, hidepw, null);
                        }
                        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        //输入框光标一直在输入文本后面
                        Editable etable = getText();
                        Selection.setSelection(etable, etable.length());
                    }
                }
            } else if (eventX >= getWidth() - clean.getIntrinsicWidth() && eventX <= getWidth()){
                setText("");
            }


        }
        return false;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    //当不为密码框且输入的文字时显示清除图标 为空不显示

    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (isPWEdit) {
            if (text.length() > 0) {
                //如果是睁着眼的
                if (isEyeChecked) {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, unhidepw_clean, null);
                } else {
                    //如果是闭着眼的
                    setCompoundDrawablesWithIntrinsicBounds(null, null, hidepw_clean, null);
                }

            } else {
                //如果是睁着眼的
                if (isEyeChecked) {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, unhidepw, null);
                } else {
                    //如果是闭着眼的
                    setCompoundDrawablesWithIntrinsicBounds(null, null, hidepw, null);
                }
            }
        } else {
            if (text.length() > 0) {
                setCompoundDrawablesWithIntrinsicBounds(null, null, clean, null);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    //当焦点发生改变
    public void onFocusChange(View v, boolean hasFocus) {

        Log.i(TAG, "onFocusChange: v.getId();" + v.getId());
        //如果文本框获取焦点
        if (hasFocus) {
            //并且为密码框
            if (v.getId() == R.id.let_pw) {
                isPWEdit = true;
                //如果是不隐藏密码
                if (isEyeChecked) {
                    if (length() > 0) {
                        setCompoundDrawablesWithIntrinsicBounds(null, null, unhidepw_clean, null);
                    } else {
                        setCompoundDrawablesWithIntrinsicBounds(null, null, unhidepw, null);
                    }

                } else {
                    if (length() > 0) {
                        setCompoundDrawablesWithIntrinsicBounds(null, null, hidepw_clean, null);
                    } else {
                        setCompoundDrawablesWithIntrinsicBounds(null, null, hidepw, null);
                    }
                }
            } else {
                isPWEdit = false;
                if (length() > 0) {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, clean, null);
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }

            }

        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }


    }
}
