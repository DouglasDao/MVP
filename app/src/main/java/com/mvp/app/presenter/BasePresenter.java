package com.mvp.app.presenter;

import android.content.Intent;

import com.mvp.app.common.utils.SharedPref;
import com.mvp.app.presenter.ipresenter.IPresenter;
import com.mvp.app.view.iview.IView;


public abstract class BasePresenter<V extends IView> implements IPresenter {

    protected String TAG = getClass().getSimpleName();

    protected V iView;

    public BasePresenter() {

    }

    public BasePresenter(V iView) {
        this.iView = iView;
        iView.bindPresenter(this);
    }

    @Override
    public void onStartPresenter() {

    }

    @Override
    public void onStopPresenter() {

    }

    @Override
    public void onPausePresenter() {

    }

    @Override
    public void onResumePresenter() {

    }

    @Override
    public void onDestroyPresenter() {

    }

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public SharedPref getSharedPref() {
        return iView.getSharedPref();
    }

    /*protected String getStringRes(int res){
        return iView.getActivity().getString(res);
    }

    protected int getIntegerRes(int res){
        return iView.getActivity().getResources().getInteger(res);
    }*/

}
