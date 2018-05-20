package com.mvp.app.model;

import android.support.v4.app.FragmentActivity;

import com.library.CustomException;
import com.mvp.app.model.dto.response.ContactsResponse;
import com.mvp.app.model.listener.IBaseModelListener;
import com.mvp.app.model.webservice.ApiClient;
import com.mvp.app.model.webservice.ApiInterface;

import okhttp3.ResponseBody;

/**
 * Created by Dell on 30-04-2018.
 */

public class ContactsModel extends BaseModel<ContactsResponse> {
    private long mCurrentTaskId = -1;


    public ContactsModel(FragmentActivity fragmentActivity, IBaseModelListener<ContactsResponse> iBaseModelListener) {
        super(fragmentActivity, iBaseModelListener);
    }

    public void getContactsList(long taskId) {
        this.mCurrentTaskId = taskId;
        enQueueTask(mCurrentTaskId, ApiClient.getClient().create(ApiInterface.class).getContacts());
    }

    @Override
    public void onSuccessfulApi(long taskId, ContactsResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }


    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }

    @Override
    public void onUnProcessedResultApi(long taskId, ResponseBody e) {
        iBaseModelListener.onUnProcessedResultApi(taskId, e);
    }
}