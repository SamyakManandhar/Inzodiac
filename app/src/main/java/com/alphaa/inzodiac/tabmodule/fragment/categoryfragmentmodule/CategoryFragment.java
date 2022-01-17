package com.alphaa.inzodiac.tabmodule.fragment.categoryfragmentmodule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseFragment;
import com.alphaa.inzodiac.databinding.ActivityWesternBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.categorymodule.CategoryAdapter;
import com.alphaa.inzodiac.tabmodule.activity.categorymodule.CategoryPresenter;
import com.alphaa.inzodiac.tabmodule.activity.categorymodule.CategoryResponse;
import com.alphaa.inzodiac.tabmodule.fragment.westernmodule.WesternFragment;
import com.alphaa.inzodiac.utility.PDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CategoryFragment extends BaseFragment implements View.OnClickListener , ApiCallBackInterFace.WesternInfoCallback {

    private ArrayList<CategoryResponse.DataItem> list;
    ActivityWesternBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_western , container, false);


        Log.e("western frage ", "onCreateView: " );
        inItView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void inItView() {
        list = new ArrayList<>();
        new CategoryPresenter(getActivity(),this).westernData();
        //setClicks(binding.western,binding.chinese);
        setAdapter();
    }

    private void setAdapter() {
        CategoryAdapter adapter = new CategoryAdapter(activity,list);
        GridLayoutManager manager = new GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
    }

    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
           case R.id.western : {
               WesternFragment profileFragment = WesternFragment.newInstance("1", "");
               replaceFragment(profileFragment, binding.frame.getId(), true);
               binding.frame.setVisibility(View.VISIBLE);
               binding.rl.setVisibility(View.GONE);

            }
            break;

            case R.id.chinese : {
               WesternFragment profileFragment = WesternFragment.newInstance("2", "");
               replaceFragment(profileFragment, binding.frame.getId(), true);
               binding.frame.setVisibility(View.VISIBLE);
               binding.rl.setVisibility(View.GONE);

            }
            break;
        }
    }


    @Override
    public void onwesternInfoResponse(String s) {
        CategoryResponse westernResponse =
                new Gson().fromJson(s, CategoryResponse.class);
        list = westernResponse.getData();
        setAdapter();
    }

    @Override
    public void showLoaderProcess() {
        PDialog.pdialog(activity);
    }

    @Override
    public void hideLoaderProcess() {
        PDialog.hideDialog();
    }

    @Override
    public void onApiErrorResponse(String Errorresponse, String error_type) {

    }
}