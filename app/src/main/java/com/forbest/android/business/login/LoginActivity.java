package com.forbest.android.business.login;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.forbest.android.R;
import com.forbest.android.base.BaseActivity;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2018/1/2.
 */

public class LoginActivity extends BaseActivity {

    private LoginFragment loginFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        initFragment();
    }

    private void initFragment() {
        loginFragment = LoginFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, loginFragment);
        ft.commit();
    }

    @Override
    protected void initData() {
        initPresenter();
    }

    private void initPresenter() {
        LoginPresenter presenter = new LoginPresenter(loginFragment);
    }
}
