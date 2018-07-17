package com.forbest.android.business.main;

import com.forbest.android.R;
import com.forbest.android.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initSmartRefresh();

    }

    /**
     * 初始化refresh
     */
    private void initSmartRefresh() {
//        ClassicsHeader classicsHeader = new ClassicsHeader(this);
//        classicsHeader.setAccentColor(Color.parseColor("red"));
//        mSmartRefreshLayout.setPrimaryColors(Color.parseColor("blue"));
//        mSmartRefreshLayout.setRefreshHeader(classicsHeader);
//        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mSmartRefreshLayout.finishRefresh(2000);
//            }
//        });
//        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                mSmartRefreshLayout.finishLoadMore(2000);
//            }
//        });
    }

    @Override
    protected void initData() {

    }
}
