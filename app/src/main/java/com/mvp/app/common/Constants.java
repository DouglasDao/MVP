package com.mvp.app.common;


public interface Constants {


    interface BundleKey {
    }

    interface RequestParams {
    }

    interface BroadCastType {

    }

    interface RequestCodes {
    }

    interface ApiRequestKey {
        String GOOGLE_PLACES_API_KEY = "";
    }

    interface Permission {

    }

    interface ErrorCode {
        int UNPROCESSED_ENTITY = 422;
    }

    interface InternalHttpCode {
        int SUCCESS_CODE = 200;
        int UNAUTH_CODE = 401;

    }

    interface HttpErrorMessage {
        String INTERNAL_SERVER_ERROR = "server maintance error";
    }

    interface StripeKey {
        String sKey = "";
    }
}
