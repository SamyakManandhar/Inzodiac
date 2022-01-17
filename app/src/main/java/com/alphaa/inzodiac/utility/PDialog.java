package com.alphaa.inzodiac.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;


import com.alphaa.inzodiac.R;

import java.util.Objects;

/**
 * Created by Ravi Birla on 26,December,2018
 */
public class PDialog {

     static ProgressDialog pDialog;

    public static boolean pdialog(Context context)
    {
        pDialog = new ProgressDialog(context);
        Objects.requireNonNull(pDialog.getWindow()).setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.setContentView(R.layout.my_progress);
        return false;
    }


    public static void hideDialog() {

        if (pDialog!=null && pDialog.isShowing())
            pDialog.dismiss();
    }
   }
