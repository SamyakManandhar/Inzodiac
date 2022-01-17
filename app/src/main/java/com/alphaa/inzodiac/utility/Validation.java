package com.alphaa.inzodiac.utility;

/**
 * Created by Ravi Birla on 17,December,2018
 */


import android.content.Context;
import android.net.Uri;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alphaa.inzodiac.R;


public class Validation {
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z.]+\\.+[a-z]+";
    private Context context;

    public Validation(Context context) {
        this.context = context;
    }

    private String getString(EditText editText) {
        return editText.getText().toString();
    }


    //New Code
    public boolean isNameValid(EditText editText) {
        if (getString(editText).isEmpty()) {
            Toast.makeText(context, context.getString(R.string.namerequired), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }





    public boolean isCountryValid(TextView textView) {
        if (textView.getText().toString().isEmpty()) {
            Toast.makeText(context, "Please select country", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isCityValid(TextView textView) {
        if (textView.getText().toString().isEmpty()) {
            Toast.makeText(context, "Please select city", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }



    public boolean isChatValid(EditText editText) {
        if (getString(editText).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isheightValid(String editText) {
        if (editText.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.comnamerequired), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isaboutValid(EditText editText) {
        if (getString(editText).isEmpty()) {
            Toast.makeText(context, context.getString(R.string.aboutrequired), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    public boolean isMcValid(EditText editText) {
        if (getString(editText).isEmpty()) {

            Toast.makeText(context, context.getString(R.string.mcrequired), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isTruckValid(EditText editText) {
        if (getString(editText).isEmpty()) {
            Toast.makeText(context, context.getString(R.string.truckrequired), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isAddressValid(EditText editText) {
        if (getString(editText).isEmpty()) {
            Toast.makeText(context, context.getString(R.string.addressrequired), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    public boolean isEmailValid(EditText editText) {
        if (getString(editText).isEmpty()) {
            Toast.makeText(context, context.getString(R.string.emailrequired), Toast.LENGTH_SHORT).show();

            return false;
        } else if (!getString(editText).matches(emailPattern)) {
            Toast.makeText(context, context.getString(R.string.emailinvalid), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    public boolean isMobileNoValid(EditText editText) {
        if (getString(editText).isEmpty()) {
//            editText.setError(context.getString(R.string.lastNameEmptyError));
//            editText.requestFocus();
            Toast.makeText(context, context.getString(R.string.mobilerequired), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    public boolean isdateValid(EditText editText) {
        if (getString(editText).isEmpty()) {
//            editText.setError(context.getString(R.string.lastNameEmptyError));
//            editText.requestFocus();
            Toast.makeText(context, context.getString(R.string.daterequired), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    public boolean isoriValid(String editText) {
        if (editText.isEmpty()) {
//            editText.setError(context.getString(R.string.lastNameEmptyError));
//            editText.requestFocus();
            Toast.makeText(context, context.getString(R.string.oriequired), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean islookValid(String editText) {
        if (editText.isEmpty()) {
//            editText.setError(context.getString(R.string.lastNameEmptyError));
//            editText.requestFocus();
            Toast.makeText(context, context.getString(R.string.lookequired), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    public boolean ispasswordValid(EditText editText) {
        if (getString(editText).isEmpty()) {
//            editText.setError(context.getString(R.string.lastNameEmptyError));
//            editText.requestFocus();
            Toast.makeText(context, context.getString(R.string.passrequired), Toast.LENGTH_SHORT).show();
            return false;
        } else if (getString(editText).length() < 6) {
            Toast.makeText(context, context.getString(R.string.minpassrequired), Toast.LENGTH_SHORT).show();
            return false;

        } else {
            return true;
        }
    }


    public boolean isImageUpload(Uri profileImageBitmap) {
        if (profileImageBitmap == null) {
            // Utility.showToast(context,"Please select profile picture",0);
            Toast.makeText(context, "Please select profile picture", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }


}
