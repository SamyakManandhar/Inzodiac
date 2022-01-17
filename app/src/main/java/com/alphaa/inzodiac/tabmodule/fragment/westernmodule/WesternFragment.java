package com.alphaa.inzodiac.tabmodule.fragment.westernmodule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseFragment;
import com.alphaa.inzodiac.databinding.FragmentWesternBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.fragment.categoryfragmentmodule.CategoryFragment;
import com.alphaa.inzodiac.tabmodule.fragment.menumodule.MenuFragment;
import com.alphaa.inzodiac.utility.PDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

public class WesternFragment extends BaseFragment implements View.OnClickListener, ApiCallBackInterFace.WesChinesInfoCallback {

    String TAG = getClass().getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String check;
    FragmentWesternBinding binding;
    private ArrayList<WesternModel.Datum> list;
    private ArrayList<ChinesModel.Datum> list1;
    private String cid = "";

    String zodaicname_chinese="",zodaicname_western="";

    public static WesternFragment newInstance(String param1, String param2) {
        WesternFragment fragment = new WesternFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        check = param1;
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_western, container, false);
        Log.e("western frage ", "onCreateView: ");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        inItView();
    }


    private void inItView() {
        list = new ArrayList<>();
        if (check.equals("1")) {
            new WesternPresenter(getActivity(), this).westernData();
        } else {
            new WesternPresenter(getActivity(), this).chinesData();
        }
        setClicks(binding.ivback,binding.send);
        setAdapter();
    }

    private void setAdapter() {
//        WesternAdapter adapter = new WesternAdapter(activity, list, pos -> cid = list.get(pos).getId());




        WesternAdapter adapter = new WesternAdapter(activity, list, pos -> {
            cid = list.get(pos).getId();
            zodaicname_western = list.get(pos).getName();
        });
        GridLayoutManager manager = new GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
    }

    private void setAdapter1() {
        ChinesAdapter adapter = new ChinesAdapter(activity, list1, pos -> {
            cid = list1.get(pos).getId();

            zodaicname_chinese = list1.get(pos).getName();
        });
        GridLayoutManager manager = new GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
    }


    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivback: {
                CategoryFragment categoryFragment = CategoryFragment.newInstance("", "");
                replaceFragment(categoryFragment, binding.frame.getId(), true);
                binding.frame.setVisibility(View.VISIBLE);
                binding.rl.setVisibility(View.GONE);
            }
            break;
            case R.id.send: {


                Log.e(TAG, zodaicname_chinese+"   :onClick: ciddddddd:  "+cid );
                /*getPrefHelper().setCatId(cid);
                MenuFragment menuFragment = MenuFragment.newInstance(cid, "");
                replaceFragment(menuFragment, binding.frame.getId(), true);
                binding.frame.setVisibility(View.VISIBLE);
                binding.rl.setVisibility(View.GONE);*/


              /*  getPrefHelper().setCatId(zodaicname_western);
                MenuFragment menuFragment = MenuFragment.newInstance(zodaicname_western,zodaicname_chinese );
                replaceFragment(menuFragment, binding.frame.getId(), true);
                binding.frame.setVisibility(View.VISIBLE);
                binding.rl.setVisibility(View.GONE);*/


                if (zodaicname_western.isEmpty()&&zodaicname_chinese.isEmpty()){
                    Toast.makeText(activity, "Please select zodaic sign", Toast.LENGTH_SHORT).show();

                    return;
                }


                Bundle bundle = new Bundle();
                bundle.putString("chinese", zodaicname_chinese);
                bundle.putString("western", zodaicname_western);
                MenuFragment fragobj = new MenuFragment();
                fragobj.setArguments(bundle);

                replaceFragment(fragobj, binding.frame.getId(), true);
                binding.frame.setVisibility(View.VISIBLE);
                binding.rl.setVisibility(View.GONE);




            }
            break;

        }
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

    @Override
    public void onWesChinesInfoResponse(String s) {

        Log.e(TAG, "onWesChinesInfoResponse: wester "+s );
        String s1 = s.replace("null", "\"\"");
        WesternModel westernResponse =
                new Gson().fromJson(s1, WesternModel.class);
        list = westernResponse.getData();
        setAdapter();
    }

    @Override
    public void onChinesInfoResponse(String s) {
        Log.e(TAG, "onChinesInfoResponse: chinese "+s );
        String s1 = s.replace("null", "\"\"");
        ChinesModel chinesModel =
                new Gson().fromJson(s1, ChinesModel.class);
        list1 = chinesModel.getData();
        setAdapter1();
    }
}