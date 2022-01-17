package com.alphaa.inzodiac.utility;

import android.content.Context;
import android.net.ParseException;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Constants {
    public static final String WITHOUTLOGIN = "withoutLogin";
    public static final String LOGIN = "login";
    public static final String USERNAME = "Username";
    public static final String USEREMAIL = "Useremail";
    public static final String USEREId = "Usereid";

    public static final String USERPHONE = "Userphone";
    public static final String USERIMAGE = "userimg";


    public static final String USEREIDSIGNUP = "UsereidSignup";
    public static final String USER_NAME_SIGNUP = "USER_NAME_SIGNUP";
    public static final String USER_EMAIL_SIGNUP = "USER_EMAIL_SIGNUP";
    public static final String USER_PHONE_SIGNUP = "USER_PHONE_SIGNUP";
    public static final String USER_PASS_SIGNUP = "User_Pass_Signup";
    public static final String USER_FB_TOKEN_SIGNUP = "User_FirebaseToken_Signup";

    public static final String AUTH_FLOW = "AUTH_FLOW"; // register
    public static final String AUTH_TYPE = "AUTH_TYPE"; // social
    public static final String IS_COMPLETED_PROFILE = "IS_COMPLETED_PROFILE"; // social

    public static final String SHIPPINGCHARG = "Shippingcharg";
    public static final String SUBTOTAL = "Subtotal";
    public static final String INVOICEID = "Invoiceid";
    public static final String MOBILENUMBER = "Mobilenumber";
    public static final String AFTERlOGIN = "afterlogin";
    public static final String CHANNEL_ID = "my_channel_01";
    public static final String CHANNEL_NAME = "Sweet Application";
    public static final String CHANNEL_DESCRIPTION = "www.sweet.com";
    public static final String ADDRESS = "address";
    public static final String LOCATION = "Location";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String PINCODE = "pincode";
    public static final String TOKEN = "token";
    public static final String HOMEFRAGMENT = "HomeFragment";
    public static final String OFFERFRAGMENT = "offerfragment";
    public static final String SEARCHFRAGMENT = "Searchfragment";
    public static final String ADDRESSPAYMENTFRAGMENT = "paymentfragment";
    public static final String PAYMENTFRAGMENT = "paymentfragment";
    public static final String CARDFREAGMENT = "cartfragment";
    public static final String NOTIFICATIONFREAGMENT = "notificationfragment";
    public static final String SheduleDate = "SheduleDate";
    public static final String sheduleTime = "sheduleTime";

    public static final String USERBIRTHDATE = "userbirthdate";

    public static final String USERINTERESTED_IN = "userinetrestedin";
    public static final String OTHERUSERGENDER = "otherusergender";
    public static final String USERORIENTATION = "userorientation";
    public static final String USERPREFFERDHOROSCOPE = "userpreferhoroscope";

    public static final String USER_TABLE = "users";
    public static final String USER_LAST_STATUS = "laststatus";
    public static final String ONLINE_TABLE = "online";
    public static final String CHAT_ROOMS_TABLE = "chat_rooms";
    public static final String CHAT_HISTORY_TABLE = "chat_history";
    public static final String CHAT_BLOCK_TABLE = "BlockUsers";
    public static final String CHAT_BLOCKED_BY = "blockedBy";

    public static final String STOREID = "storeid";





    //profile detail
    public static final String GENDER = "gender";
    public static final String ETHNICITY = "ethnicity";
    public static final String BODYTYPE = "bodytype";
    public static final String HEIGHT = "height";
    public static final String HAIRCOLOR = "haircolor";
    public static final String EYECOLOR = "eyecolor";
    public static final String HOROSCOPETYPE = "horoscopetype";

    public static final String MARRIED = "married";
    public static final String CHILDERN = "children";
    public static final String SMOKE = "smoke";
    public static final String DRINK = "drink";
    public static final String AGE = "age";
    public static final String ABOUT = "about";


    public static final String WESTERNZODAICSIGN = "westernzodaic";
    public static final String CHINESEZODAICSIGN = "chinesezodaic";


    //token subscription
    public static final String TOTAL_TOKEN = "total_token";
    public static final String IS_SUBSCRIBE = "is_subscribe";


    public static final String PHONE_COUNTRY_CODE = "+44";


    public static final void customToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

 /*   public static void customSnackbar(String msg, Context context, View view) {
        TSnackbar snackbar = TSnackbar.make(view, msg, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.BLACK);
        View snackbarView = snackbar.getView();
        ViewGroup.LayoutParams params = snackbarView.getLayoutParams();
        params.height = 100;
        snackbarView.setLayoutParams(params);
        snackbarView.setBackgroundColor(context.getResources().getColor(R.color.snackColor));
        TextView mTextView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        mTextView.setGravity(Gravity.CENTER);
        snackbar.show();
    }*/

    public static String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yyyy";

//        String inputPattern = "yyyy-MM-dd HH:mm:ss";
//        String outputPattern = "dd-MMM-yyyy h:mm a";

//        String inputPattern = "yyyy-MM-dd";
//        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static boolean isPostalCodeValid(String postalcode) {
        return postalcode.matches("^[1-9][0-9]{5}$");
    }

}
