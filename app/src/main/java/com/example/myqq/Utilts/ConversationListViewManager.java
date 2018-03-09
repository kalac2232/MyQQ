package com.example.myqq.Utilts;

import com.example.myqq.View.ConversationListView;

/**
 * 单例模式，用于管理ConversationListView
 * Created by 97210 on 3/7/2018.
 */

public class ConversationListViewManager {
    private ConversationListViewManager() {

    }
    private static ConversationListViewManager mInstance = new ConversationListViewManager();
    /**
     * 用来记录当前打开的滑块，为空为无记录，不为空则当前的滑块为打开状态
     */
    private ConversationListView currentLayout;
    public static ConversationListViewManager getInstance() {
        return mInstance;
    }

    /**
     * 记录当前打开的滑块
     * @param currentLayout
     */
    public void setCurrentLayout(ConversationListView currentLayout){
        this.currentLayout = currentLayout;
    }

    /**
     * 关闭已打开的滑块
     */
    public void closeCurrentLayout(){
        if (currentLayout != null ){
            currentLayout.Close();
        }
    }
    /**
     * 清除记录值
     */
    public void clearCurrentLayout() {
        currentLayout = null;
    }
    /**
     * 判断当前是否可以进行滑动
     * @return true 可以
     */
    public boolean isCanSwipe(ConversationListView Layout){
        //如果无记录，
        if (currentLayout == null) {
            return true;
        } else {
            //需要判断打开的滑块是否为当前的滑块，如果为当前的滑块，则可以滑动
            return currentLayout == Layout;
        }
    }
}
