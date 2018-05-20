package com.mvp.app.presenter.ipresenter;

import android.content.Intent;
import android.os.Bundle;

import com.mvp.app.common.utils.SharedPref;

public interface IPresenter {

    void onCreatePresenter(Bundle bundle);

    void onStartPresenter();

    void onStopPresenter();

    void onPausePresenter();

    void onResumePresenter();

    void onDestroyPresenter();

    void onActivityResultPresenter(int requestCode, int resultCode, Intent data);

    SharedPref getSharedPref();

}
