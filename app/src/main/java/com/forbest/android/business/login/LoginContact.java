package com.forbest.android.business.login;

import com.forbest.android.base.BasePresenter;
import com.forbest.android.base.BaseView;

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

        void login(String userName, String password);

    }
}
