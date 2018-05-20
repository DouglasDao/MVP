package com.mvp.app.model.listener;


import com.library.CustomException;

import okhttp3.ResponseBody;

public interface IBaseModelListener<T> {

    void onSuccessfulApi(long taskId, T response);

    void onFailureApi(long taskId, CustomException e);

    void onUnProcessedResultApi(long taskId, ResponseBody e);
}
