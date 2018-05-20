package com.mvp.app.view.activity;

import com.mvp.app.R;
import com.mvp.app.presenter.SplashPresenter;
import com.mvp.app.presenter.ipresenter.ISplashPresenter;
import com.mvp.app.view.iview.ISplashView;

public class SplashActivity extends BaseActivity<ISplashPresenter> implements ISplashView {

    @Override
    int attachLayout() {
        return R.layout.activity_splash;
    }

    @Override
    ISplashPresenter initialize() {
        return new SplashPresenter(this);
    }
}
