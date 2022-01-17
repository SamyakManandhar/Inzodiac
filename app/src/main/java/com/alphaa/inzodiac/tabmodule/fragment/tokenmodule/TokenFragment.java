package com.alphaa.inzodiac.tabmodule.fragment.tokenmodule;

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
import com.alphaa.inzodiac.databinding.FragmentTokenBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.utility.PDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TokenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TokenFragment extends BaseFragment implements View.OnClickListener ,ApiCallBackInterFace.favouriteInfoCallback, TokenAdapter.onClick{
    String TAG = getClass().getSimpleName();
    private ArrayList<TokenModel.Datum> list;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentTokenBinding binding;
    private static String check = "";
    String planId,planAmount,PlanActive,tokens;

      public static TokenFragment newInstance(String param1, String param2) {
        TokenFragment fragment = new TokenFragment();
        Bundle args = new Bundle();
        check = param1;
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_token, container, false);

        Log.e(TAG, "onCreateView: 1111111" );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();

        setClicks(binding.gotoPayment);
        String uid = getPrefHelper().getUserId();
        new TokenPresenter(activity,this).TokenListData(uid);
    }
    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }

    private void setAdapter() {
        TokenAdapter adapter = new TokenAdapter(0,activity,list,this);
        LinearLayoutManager manager = new LinearLayoutManager(activity,  LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);

        LinearLayoutManager manager1 = new LinearLayoutManager(activity,  LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewToken.setLayoutManager(manager1);
        setAdapter_totalReward(0);
        binding.tvJustwatch.setText(list.get(0).getVideo_per_day());
        binding.tvRewardtoken.setText(list.get(0).getReward_token());
        planId = list.get(0).getId();
        planAmount = list.get(0).getPrice();
        PlanActive = list.get(0).getIs_plan();
        tokens = list.get(0).getTokenName();
    }

    @Override
    public void onfavouriteInfoResponse(String s) {
        Log.e(TAG, "onTokenResponse: sss "+s );
        list.clear();
        String s1 = s.replace("null","\"\"");
        if (s1.contains("[{")) {
            TokenModel model = new Gson().fromJson(s1, TokenModel.class);
            list = model.getData();
            Log.e(TAG, "onTokenResponse: list "+list.size() );
        }
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

    private void setAdapter_totalReward(int pos) {
        TokenRewardAdapter adapter = new TokenRewardAdapter(activity,list.get(pos).getBenifits());
        binding.recyclerViewToken.setAdapter(adapter);
    }

    @Override
    public void onEvent(View view, int pos) {
        binding.tvJustwatch.setText(list.get(pos).getVideo_per_day());
        binding.tvRewardtoken.setText(list.get(pos).getReward_token());
        planId = list.get(pos).getId();
        planAmount = list.get(pos).getPrice();
        PlanActive = list.get(pos).getIs_plan();
        tokens = list.get(pos).getTokenName();
          setAdapter_totalReward(pos);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.goto_payment: {
                
                if (planId!=null) {
                    if (PlanActive.equals("0")) {
                        startActivity(new Intent(getContext(), PaymentDetailActivity.class)
                                .putExtra("planId", planId).putExtra("tokens",tokens).putExtra("planAmount", planAmount).putExtra("plan_for", "2").putExtra("comesfrom","tokenfragment"));
                    } else {
                        Toast.makeText(activity, "You have already purchase this token", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(activity, "Please select any token", Toast.LENGTH_SHORT).show();
                }
            }

            break;
        }
    }
}