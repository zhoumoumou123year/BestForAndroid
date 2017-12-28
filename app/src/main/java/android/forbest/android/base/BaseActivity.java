package android.forbest.android.base;

import android.app.ProgressDialog;
import android.forbest.android.util.ToastUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2017/12/26.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initProgressDialog();
        initView();
        initData();
    }

    protected abstract int getLayoutId();

    /**
     * 初始化进度条
     */
    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    protected abstract void initView();

    protected abstract void initData();

    public void showProgress(String msg) {
        mProgressDialog.setMessage(msg);
    }

    public void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showToast(String msg) {
        ToastUtil.show(msg);
    }

}
