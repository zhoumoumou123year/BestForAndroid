package com.forbest.android.business.login;

import android.content.Context;

import com.forbest.android.net.RxObserver;
import com.forbest.android.vo.login.LoginBean;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2018/1/5.
 */

public class LoginPresenter implements LoginContact.Presenter {

    private LoginContact.View mView;
    private LoginModel mModel;

    public LoginPresenter(LoginContact.View view) {
        this.mView = view;
        view.setPresenter(this);
        mModel = new LoginModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void login(Context context, String userName, String password, String key,int whichRequest ,boolean isShowDialog) {
        mModel.getLogin(userName, password).subscribe(new RxObserver<LoginBean>(context, key, whichRequest, isShowDialog) {

            @Override
            public void onStart(int whichRequest) {
                super.onStart(whichRequest);
                mView.showLoading(whichRequest);
            }

            @Override
            public void onSuccess(int whichRequest, LoginBean loginBean) {
                mView.dismissLoading(whichRequest);
                mView.showLoginResult(loginBean);
            }

            @Override
            public void onError(int whichRequest, Throwable throwable) {
                mView.onError(whichRequest, throwable);
                mView.dismissLoading(whichRequest);
            }
        });
    }
}
