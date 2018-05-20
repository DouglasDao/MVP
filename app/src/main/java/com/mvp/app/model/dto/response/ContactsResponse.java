package com.mvp.app.model.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvp.app.model.common.ContactsData;

import java.util.List;

/**
 * Created by Dell on 30-04-2018.
 */

public class ContactsResponse extends BaseResponse {
    @SerializedName("contacts")
    @Expose
    private List<ContactsData> data = null;

    public List<ContactsData> getData() {
        return data;
    }

    public void setData(List<ContactsData> data) {
        this.data = data;
    }
}
