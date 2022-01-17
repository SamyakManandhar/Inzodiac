package com.alphaa.inzodiac.app.prefs;

public interface Constants {
    /**
     * Retrofit constants
     */
    Long CONNECTION_TIMEOUT = 5 * 60L;
    Long READ_TIMEOUT = 5 * 60L;
    String KEY_USER_DETAILS = "user_details";
    String dimension_unit_list = "dimension_unit_list";
    String OnBoarding = "OnBoarding";
    String Token = "Token";



    /**
     * Keys below
     */
    String KEY_RESULT_EXTRA = "result_extra";
    String KEY_BUNDLE_PARAM = "extra_data";
    public static final String INTENT_EXTRA_ALBUM = "album";
    public static final String INTENT_EXTRA_IMAGES = "images";
    public static final String INTENT_EXTRA_LIMIT = "limit";
    public static final int DEFAULT_LIMIT = 10;
    public static final int REQUEST_CODE = 2000;


    /**
     * Request code constants
     */
    int PICK_IMAGE_REQUEST = 1001;
    int CAPTURE_IMAGE_REQUEST = 1002;
    int STORAGE_PERMISSION = 1003;
    int LOCATION_PERMISSION = 1004;
    int PLACE_PICKER_REQUEST = 1005;
    int COUPON_FILTER_REQUEST = 1006;
    int COUNTRY_REQUEST = 1007;
}
