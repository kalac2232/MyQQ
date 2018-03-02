package com.example.myqq.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by 97210 on 2/24/2018.
 */

public class ConversationListView extends FrameLayout {
    private View listContent;
    private View listDelete;

    public ConversationListView(Context context) {
        super(context);
        init();
    }

    public ConversationListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConversationListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private static final String TAG = "ConversationListView";




    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        listContent = getChildAt(0);
        listDelete = getChildAt(1);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        listContent.layout(left,top,right,bottom);
        listDelete.layout(right,top,right+listDelete.getMeasuredWidth(),bottom);
    }

    private void init() {

    }
}
