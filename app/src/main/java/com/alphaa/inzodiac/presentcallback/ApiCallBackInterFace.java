package com.alphaa.inzodiac.presentcallback;

public interface ApiCallBackInterFace {

    interface FilterInfoCallback extends BaseInterface{
        void oncountryInfoResponse(String s);
        void oncityInfoResponse(String s);
        void onFilterInfoResponse(String s);
    }

    interface WesternInfoCallback extends BaseInterface{
        void onwesternInfoResponse(String s);
    }

    interface DetailInfoCallback extends BaseInterface{
        void ondetailInfoResponse(String s);
        void onFavoriteInfoResponse(String s);
        void onSendRequesr(String s);
    }

    interface ProfileDetailInfoCallback extends BaseInterface{
        void onProfiledetailInfoResponse(String s);
    }

    interface WesChinesInfoCallback extends BaseInterface{
        void onWesChinesInfoResponse(String s);
        void onChinesInfoResponse(String s);
    }

    interface menuInfoCallback extends BaseInterface{
        void onmenuInfoResponse(String s);
    }

    interface favouriteInfoCallback extends BaseInterface{
        void onfavouriteInfoResponse(String s);
        void ondislikeInfoResponse(String s);
    }

    interface forgetPasswordInfoCallback extends BaseInterface{
        void onforgetPasswordInfoResponse(String s);
    }


    interface AboutInfoCallback extends BaseInterface{
        void onAboutInfoResponse(String s);
    }


    interface signupInfoCallback extends BaseInterface  {
        void onsignupInfoResponse(String s);
    }

    interface loginInfoCallback extends BaseInterface  {
        void onLoginInfoResponse(String s);
        void onSocialLoginInfoResponse(String email,String name,String image);

    }

    interface phoneVerifyInfoCallback extends BaseInterface  {
        void onCheckSamePhoneResponse(Boolean b);
    }

    interface updatePhoneNumberCallback extends BaseInterface  {
        void onUpdatePhoneResponse(Boolean b);
    }

    /////////////

    interface QuizRequestAcceptRejectCallback extends BaseInterface{
        void onAcceptRejectRequesr(String s);
    }


    interface CategoryUpdateCallback extends BaseInterface{
        void onCategoryUpdateResponse(String s);
    }


    interface LikeRequestCallback extends BaseInterface{
        void onLikeRequestResponse(String s);
    }


    interface ContactUsCallback extends BaseInterface{
        void oncontactusInfoResponse(String s);
    }
    interface TokenAddCallBack extends BaseInterface{
        void onTokenAdd(String s);
    }

}
