package com.alphaa.inzodiac.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;


public class BaseBottomDialog extends BottomSheetDialogFragment implements View.OnFocusChangeListener {
    private BaseActivity mActivity;

    public BaseActivity  getBaseActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
         mActivity = (BaseActivity) context;
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void show(FragmentManager fragmentManager, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(tag);
        if (prevFragment != null) {
            transaction.remove(prevFragment);
        }
        transaction.addToBackStack(null);
        show(transaction, tag);
    }

    void dismissDialog(String tag ) {
        hideKeyboard();
        dismiss();
    }

    protected void hideKeyboard() {
        mActivity.hideKeyboard();

    }

    protected void setLoading(Boolean isLoading) {
    }

    /* protected fun setLoading(progressBar: ProgressBar, isLoading: Boolean) {
         if (mActivity != null) {
             mActivity!!.setLoading(progressBar, isLoading)
         }
     }*/



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            hideKeyboard();
        }
    }
}
