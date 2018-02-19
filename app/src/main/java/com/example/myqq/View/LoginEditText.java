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
import android.widget.ImageView;

import com.example.myqq.R;

import static android.content.ContentValues.TAG;

/**
 *
 * Created by 97210 on 2/13/2018.
 */

public class LoginEditText extends EditText {
    private Paint paint;
    public LoginEditText(Context context) {
        super(context);

    }

    public LoginEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        //可以自定义画笔的颜色
        paint.setColor(Color.WHITE);


    }

    public LoginEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

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

}
