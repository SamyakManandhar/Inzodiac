package com.alphaa.inzodiac.tabmodule.fragment.updateprmodule;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseFragment;
import com.alphaa.inzodiac.billing.PaymentDetailActivity;
import com.alphaa.inzodiac.databinding.FragmentUpdatePremiumBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.utility.PDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdatePremiumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePremiumFragment extends BaseFragment implements ApiCallBackInterFace.favouriteInfoCallback ,UpdatePremAdapter.onClick{
    String TAG = getClass().getSimpleName();
    private FragmentUpdatePremiumBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<PlanData.Datum> list;
    private static String check = "";
    String planId,planAmount, PlanActive_not;

    public static UpdatePremiumFragment newInstance(String param1, String param2) {
        UpdatePremiumFragment fragment = new UpdatePremiumFragment();
        Bundle args = new Bundle();
        check = param1;
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_premium , container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        String uid = getPrefHelper().getUserId();
        new PlanPresenter(activity,this).UserPlanList(uid);
        setAdapter();

        binding.btnUpdate.setOnClickListener(v -> {
            
            if (planId!=null) {
                if (PlanActive_not.equals("0")) {
                    startActivity(new Intent(getContext(), PaymentDetailActivity.class)
                            .putExtra("planId", "1").putExtra("planAmount", planAmount).putExtra("plan_for", "1").putExtra("comesfrom","updatepremiumfragment"));
                } else {
                    Toast.makeText(activity, "You have already purchase this plan", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(activity, "Please select any plan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        UpdatePremAdapter adapter  =new UpdatePremAdapter(activity,list,this);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter( adapter );

//        planId = list.get(0).getId();
//        planAmount = list.get(0).getPrice();
//        PlanActive_not = list.get(0).getIs_plan();
    }

    @Override
    public void onfavouriteInfoResponse(String s) {
        Log.e(TAG, "on plan purchase list: sss "+s );
        list.clear();
        String s1 = s.replace("null","\"\"");
        if (s1.contains("[{")) {
            PlanData model = new Gson().fromJson(s1, PlanData.class);
            list = model.getData();
            Log.e(TAG, "on plan purchase list: list "+list.size() );
        }
//        if (list.size() == 0){
//            binding.notfound.setVisibility(View.VISIBLE);
//        }else {
//            binding.notfound.setVisibility(View.GONE);
//
//        }
        setAdapter();
    }

    @Override
    public void ondislikeInfoResponse(String s) {

    }

    @Override
    public void showLoaderProcess() {
        if (check.isEmpty() || !check.equals("1")) {
            PDialog.pdialog(activity);
        }
    }

    @Override
    public void hideLoaderProcess() {
        PDialog.hideDialog();
    }

    @Override
    public void onApiErrorResponse(String Errorresponse, String error_type) {
    }

    @Override
    public void onEvent(View view, int pos) {
        planId = list.get(pos).getId();
        planAmount = list.get(pos).getPrice();
        PlanActive_not = list.get(pos).getIs_plan();
    }
}