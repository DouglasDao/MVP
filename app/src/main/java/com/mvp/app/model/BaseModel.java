package com.mvp.app.model;


import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.library.CustomException;
import com.library.ExceptionTracker;
import com.library.Log;
import com.mvp.app.common.Constants;
import com.mvp.app.common.utils.CodeSnippet;
import com.mvp.app.model.dto.response.BaseResponse;
import com.mvp.app.model.listener.IBaseModelListener;
import com.mvp.app.model.webservice.ApiClient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseModel<T extends BaseResponse> implements IBaseModelListener<T> {

    protected String TAG = getClass().getSimpleName();
    protected IBaseModelListener<T> iBaseModelListener;
    private long mCurrentTaskId = -1;
    private CodeSnippet mCodeSnippet;
    private Response<T> response;
    private FragmentActivity fragmentActivity;
    private Observer<Response<T>> observer = new Observer<Response<T>>() {
        @Override
        public void onCompleted() {
            if (response.isSuccessful() && response.body() != null) {
                Log.d(TAG, "body: " + response.body());
                T result = response.body();
                if (response.code() == Constants.InternalHttpCode.SUCCESS_CODE) {  // If HTTP status goes 200
                    onSuccessfulApi(mCurrentTaskId, result);
                } else {
                    onFailureApi(mCurrentTaskId, new CustomException(result));
                }
            } else {
                try {
                    Converter<ResponseBody, T> converter = ApiClient.getClient().responseBodyConverter(BaseResponse.class, new Annotation[0]);
                    CustomException customException = new CustomException(converter.convert(response.errorBody()));
                    Log.d(TAG, "CustomException Error code : " + customException.getCode());

                    if (response.code() == Constants.InternalHttpCode.UNAUTH_CODE) {  // If HTTP status goes 401
                        mCodeSnippet.onLogout(fragmentActivity);
                    }
                    if (response.code() == Constants.ErrorCode.UNPROCESSED_ENTITY) { // If HTTP status goes 422
                        onUnProcessedResultApi(mCurrentTaskId, response.errorBody());
                        return;
                    }

                    onFailureApi(mCurrentTaskId, customException);

                } catch (IOException | NullPointerException e) {
                    ExceptionTracker.track(e);
                    onFailureApi(mCurrentTaskId, new CustomException(response.code(), "Our server is under maintenance. Kindly try after some time!"));
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "onError : " + e.getMessage());
            onFailureApi(mCurrentTaskId, new CustomException(500, e.getMessage()));
        }

        @Override
        public void onNext(@NonNull Response<T> response) {
            BaseModel.this.response = response;
        }
    };

    protected BaseModel(FragmentActivity fragmentActivity, IBaseModelListener<T> iBaseModelListener) {
        this.fragmentActivity = fragmentActivity;
        this.iBaseModelListener = iBaseModelListener;
        mCodeSnippet = new CodeSnippet(fragmentActivity);
    }

    protected void enQueueTask(long taskId, Observable<Response<T>> tSub) {
        this.mCurrentTaskId = taskId;
        if (mCodeSnippet.hasNetworkConnection()) {
            tSub.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext(Observable::error)
                    .subscribe(observer);
        } else {
            mCodeSnippet.showNetworkMessage();
        }
    }
}
