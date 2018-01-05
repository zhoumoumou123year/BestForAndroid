package com.forbest.android.business.login;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2018/1/5.
 */

public class LoginPresenter implements LoginContact.Presenter {

    private LoginContact.View mView;

    public LoginPresenter(LoginContact.View view) {
        this.mView = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void login(String userName, String password) {

    }
}
