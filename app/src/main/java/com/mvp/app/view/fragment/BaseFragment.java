package com.mvp.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvp.app.common.utils.CodeSnippet;
import com.mvp.app.presenter.ipresenter.IPresenter;
import com.mvp.app.view.iview.IView;

public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IView {

    protected T iPresenter;

    protected View vRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vRootView = LayoutInflater.from(getContext()).inflate(attachLayout(), container, false);
        iPresenter = initializer();
        iPresenter.onCreatePresenter(getArguments());
        return vRootView;
    }

    abstract int attachLayout();

    abstract T initializer();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void showProgressbar() {
        ((IView) getActivity()).showProgressbar();
    }

    @Override
    public void dismissProgressbar() {
        try {
            ((IView) getActivity()).dismissProgressbar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showSnackBar(String message) {
        ((IView) getActivity()).showSnackBar(message);
    }

    @Override
    public void showNetworkMessage() {
        ((IView) getActivity()).showNetworkMessage();
    }

    @Override
    public CodeSnippet getCodeSnippet() {
        return ((IView) getActivity()).getCodeSnippet();
    }

    @Override
    public void showSnackBar(@NonNull View view, String message) {
        ((IView) getActivity()).showSnackBar(view, message);
    }


    public void replaceFragment(Bundle bundle, @IdRes int containerViewId, @NonNull Fragment fragment, @NonNull String fragmentTag) {
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert iPresenter != null;
        iPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }

}
