package com.mvp.app.model.webservice;


import com.mvp.app.model.dto.response.ContactsResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

public interface ApiInterface {
    @GET("contacts/")
    Observable<Response<ContactsResponse>> getContacts();
}
