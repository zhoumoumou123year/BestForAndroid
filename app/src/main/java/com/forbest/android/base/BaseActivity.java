package com.forbest.android.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.forbest.android.net.RxManager;

import butterknife.ButterKnife;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2017/12/26.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        TAG = getPackageName() + "." + getClass().getSimpleName();
        initView();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    protected void onDestroy() {
        RxManager.getInstance().clear(TAG);
        super.onDestroy();
    }
}
