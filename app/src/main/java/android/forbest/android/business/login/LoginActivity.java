package android.forbest.android.business.login;

import android.forbest.android.R;
import android.forbest.android.base.BaseHttpBean;
import android.forbest.android.base.BaseRxActivity;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2018/1/2.
 */

public class LoginActivity extends BaseRxActivity {

    @Override
    protected boolean needHandlerResult(BaseHttpBean result) {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
