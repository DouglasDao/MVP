package com.mvp.app.view.iview;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.library.CustomException;
import com.mvp.app.common.utils.CodeSnippet;
import com.mvp.app.common.utils.SharedPref;


public interface IView<P> {

    void showMessage(String message);

    void showMessage(int resId);

    void showMessage(CustomException e);

    void showProgressbar();

    void dismissProgressbar();

    FragmentActivity getActivity();

    void showSnackBar(String message);

    void showSnackBar(@NonNull View view, String message);

    void showNetworkMessage();

    void bindPresenter(P iPresenter);

    CodeSnippet getCodeSnippet();

    SharedPref getSharedPref();


}
