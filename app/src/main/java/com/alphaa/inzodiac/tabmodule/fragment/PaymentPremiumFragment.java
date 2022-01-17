package com.alphaa.inzodiac.tabmodule.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseFragment;
import com.alphaa.inzodiac.databinding.FragmentPaymentPremiumBinding;
import com.alphaa.inzodiac.tabmodule.fragment.tokenmodule.TokenFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentPremiumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentPremiumFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentPaymentPremiumBinding binding;

    public static PaymentPremiumFragment newInstance(String param1, String param2) {
        PaymentPremiumFragment fragment = new PaymentPremiumFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_premium , container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.update.setOnClickListener(v -> {
            TokenFragment filterFragment = TokenFragment.newInstance("", "");
            replaceFragment(filterFragment, binding.frame.getId(), true);
            binding.frame.setVisibility(View.VISIBLE);
            binding.rl.setVisibility(View.GONE);
        });
    }
}