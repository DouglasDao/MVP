package com.mvp.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.library.CustomException;
import com.mvp.app.common.utils.CodeSnippet;
import com.mvp.app.common.utils.SharedPref;
import com.mvp.app.presenter.ipresenter.IPresenter;
import com.mvp.app.view.iview.IView;
import com.mvp.app.view.widget.CustomProgressbar;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView<P> {

    public View mParentView;
    protected String TAG = getClass().getSimpleName();
    protected CustomProgressbar mCustomProgressbar;
    protected P iPresenter;
    protected CodeSnippet mCodeSnippet;
    private SharedPref mSharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(attachLayout());
        ButterKnife.bind(this);
        mSharedPref = new SharedPref(this);
        iPresenter = initialize();
        iPresenter.onCreatePresenter(getIntent().getExtras());
    }

    abstract int attachLayout();

    abstract P initialize();

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mParentView = getWindow().getDecorView().findViewById(android.R.id.content);
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void bindPresenter(P iPresenter) {
        this.iPresenter = iPresenter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        assert iPresenter != null;
        iPresenter.onStartPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        assert iPresenter != null;
        iPresenter.onResumePresenter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        assert iPresenter != null;
        iPresenter.onPausePresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();
        assert iPresenter != null;
        iPresenter.onStopPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        assert iPresenter != null;
        iPresenter.onDestroyPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert iPresenter != null;
        iPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(CustomException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private CustomProgressbar getProgressBar() {
        if (mCustomProgressbar == null) mCustomProgressbar = new CustomProgressbar(this);
        return mCustomProgressbar;
    }

    @Override
    public void showProgressbar() {
        try {
            getProgressBar().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismissProgressbar() {
        runOnUiThread(() -> {
            try {
                getProgressBar().dismissProgress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void showSnackBar(String message) {
        if (mParentView != null) {
            Snackbar snackbar = Snackbar.make(mParentView, message, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
    }

    @Override
    public void showSnackBar(@NonNull View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    public void showNetworkMessage() {
        if (mParentView != null) {
            Snackbar snackbar = Snackbar.make(mParentView, "No Network found!", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.setAction("Settings", view -> mCodeSnippet.showNetworkSettings());
            snackbar.show();
        }
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    public CodeSnippet getCodeSnippet() {
        if (mCodeSnippet == null) {
            mCodeSnippet = new CodeSnippet(getActivity());
            return mCodeSnippet;
        }
        return mCodeSnippet;
    }

    @Override
    public SharedPref getSharedPref() {
        return mSharedPref;
    }
}