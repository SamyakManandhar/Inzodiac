package com.alphaa.inzodiac.tabmodule.activity.categorymodule;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityWesternBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.utility.PDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CategoryActivity extends BaseActivity implements View.OnClickListener , ApiCallBackInterFace.WesternInfoCallback {

    ActivityWesternBinding binding;
    private ArrayList<CategoryResponse.DataItem> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_western);
        inItView();
    }


    private void inItView() {
        list = new ArrayList<>();
        new CategoryPresenter(this,this).westernData();
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
        PDialog.pdialog(this);
    }

    @Override
    public void hideLoaderProcess() {
        PDialog.hideDialog();
    }

    @Override
    public void onApiErrorResponse(String Errorresponse, String error_type) {

    }
}