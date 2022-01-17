package com.alphaa.inzodiac.presentcallback;

interface  BaseInterface {
    void showLoaderProcess();
    void hideLoaderProcess();
    void onApiErrorResponse(String Errorresponse,String error_type);
}
