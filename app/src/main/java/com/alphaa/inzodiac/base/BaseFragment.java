package com.alphaa.inzodiac.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alphaa.inzodiac.app.prefs.PrefHelper;
import com.alphaa.inzodiac.app.utils.KeyboardUtil;
import com.alphaa.inzodiac.app.utils.StackSet;


public class BaseFragment extends Fragment {

    private StackSet<Fragment> stackSet = new StackSet<>();
    protected Activity activity;
    protected PrefHelper prefHelper;

    @Override
    public void onAttach(@NonNull Context context) {
        this.activity = (Activity) context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefHelper = new PrefHelper(activity);
    }

    public PrefHelper getPrefHelper() {
        return prefHelper;
    }


    protected final void replaceFragment(@NonNull Fragment fragment, @IdRes int containerId, boolean addToBackStack) {
        try {
            FragmentManager fm = getChildFragmentManager();
            String fragmentName = fragment.getClass().getName();
            fm.beginTransaction().replace(containerId, fragment, fragmentName).addToBackStack(null).commit();
            //if (addToBackStack) stackSet.push(fragment);
            hideKeyboard();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    protected void hideKeyboard() {
        if (activity.getCurrentFocus() != null)
            KeyboardUtil.hideKeyboard(activity, activity.getCurrentFocus());
    }
    protected final StackSet<Fragment> getChildBackStack() {
        return stackSet;
    }

    protected final Fragment getCurrentChildFragment() {
        return stackSet.peek();
    }

    protected final void replaceChildFragment(@NonNull Fragment fragment, @IdRes int containerId, boolean addToBackStack) {
        try {
            FragmentManager fm = getChildFragmentManager();
            String fragmentName = fragment.getClass().getName();
            fm.beginTransaction().replace(containerId,fragment,fragmentName).commit();
            if (addToBackStack) stackSet.push(fragment);
            ((BaseActivity) activity).hideKeyboard();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
