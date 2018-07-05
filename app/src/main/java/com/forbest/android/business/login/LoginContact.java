package com.forbest.android.business.login;

import android.content.Context;

import com.forbest.android.base.BasePresenter;
import com.forbest.android.base.BaseView;
import com.forbest.android.vo.login.LoginBean;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2018/1/5.
 */

public interface LoginContact {

    interface View extends BaseView<Presenter> {

        void showLoginResult(LoginBean loginBean);

    }

    interface Presenter extends BasePresenter {

        void login(Context context, String userName, String password, String key,int whichRequest ,boolean isShowDialog);

    }
}
