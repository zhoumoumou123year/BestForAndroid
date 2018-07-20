package com.forbest.android.util.listener;

import android.view.View;

/**
 * @Description: 自定义防重复点击监听
 * @Author : ZhouHui
 * @Date : 2018/7/18.
 */
public abstract class OnMultiClickListener implements View.OnClickListener {

    /**
     * 点击间隔时间
     */
    private static final long CLICK_DELAY_TIME = 1000;

    /**
     * 上次点击时间
     */
    private static long mLastClickTime;

    public abstract void onMultiClick(View view);

    @Override
    public void onClick(View view) {
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - mLastClickTime) >= CLICK_DELAY_TIME) {
            mLastClickTime = currentClickTime;
            onMultiClick(view);
        }
    }
}
