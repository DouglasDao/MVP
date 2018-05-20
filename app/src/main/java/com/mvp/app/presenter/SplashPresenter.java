package com.mvp.app.presenter;

import android.os.Bundle;

import com.library.CustomException;
import com.library.Log;
import com.mvp.app.model.ContactsModel;
import com.mvp.app.model.dto.response.ContactsResponse;
import com.mvp.app.model.listener.IBaseModelListener;
import com.mvp.app.presenter.ipresenter.ISplashPresenter;
import com.mvp.app.view.iview.ISplashView;

import okhttp3.ResponseBody;

public class SplashPresenter extends BasePresenter<ISplashView> implements ISplashPresenter {

    private ContactsModel contactsModel;

    private IBaseModelListener<ContactsResponse> iBaseModelListener = new IBaseModelListener<ContactsResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, ContactsResponse response) {
            iView.dismissProgressbar();
            Log.d(TAG, "Email : " + response.getData().get(0).getEmail());
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iView.dismissProgressbar();
            iView.showMessage(e.getException());
        }

        @Override
        public void onUnProcessedResultApi(long taskId, ResponseBody e) {
            iView.dismissProgressbar();
        }
    };

    public SplashPresenter(ISplashView iSplashView) {
        super(iSplashView);
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        apiCallGetContacts();
    }

    private void apiCallGetContacts() {
        iView.showProgressbar();
        contactsModel = new ContactsModel(iView.getActivity(), iBaseModelListener);
        contactsModel.getContactsList(1);
    }
}
