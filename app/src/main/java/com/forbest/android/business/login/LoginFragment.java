package com.forbest.android.business.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bestfor.android.ImageLoader;
import com.forbest.android.R;
import com.forbest.android.base.BaseFragment;
import com.forbest.android.util.TextUtil;
import com.forbest.android.util.ToastUtil;
import com.forbest.android.vo.login.LoginBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseFragment implements LoginContact.View{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private LoginContact.Presenter mPresenter;

    @BindView(R.id.username)
    EditText mUserName;
    @BindView(R.id.password)
    EditText mPassWord;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void initData() {
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530698138366&di=49d3116d9c7ca12c97c3f95fbf5e2f04&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F72f082025aafa40f99d4e82aa764034f78f01932.jpg";
//        ImageLoader.getInstance().displayRoundImage(this.getContext(), url, mIvLogo,R.mipmap.ic_launcher, 20);
        Toast.makeText(getContext(), "我又悄悄的改了个Bug，哈哈", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_login;
    }

    @OnClick({R.id.email_sign_in_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.email_sign_in_button:
                if (!checkLogin()) {
                    return;
                }
                String userName = mUserName.getText().toString().trim();
                String password = mPassWord.getText().toString().trim();
                mPresenter.login(getContext(), userName, password, TAG, 0, true);
                break;
        }
    }

    /**
     * 登录校验
     */
    public boolean checkLogin() {
        if (TextUtil.isEmpty(mUserName.getText().toString().trim())) {
            ToastUtil.show(getString(R.string.place_input_username));
            return false;
        }
        if (TextUtil.isEmpty(mPassWord.getText().toString().trim())) {
            ToastUtil.show(getString(R.string.place_input_password));
            return false;
        }
        return true;
     }

    @Override
    public void setPresenter(LoginContact.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onError(int whichRequest, Throwable throwable) {
        ToastUtil.show(throwable.getMessage());
    }

    @Override
    public void showLoading(int whichRequest) {

    }

    @Override
    public void dismissLoading(int whichReqeust) {

    }

    @Override
    public void showLoginResult(LoginBean loginBean) {
        ToastUtil.show("登录成功");
    }

}
