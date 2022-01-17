package com.alphaa.inzodiac.tabmodule.fragment.likemodule;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseFragment;
import com.alphaa.inzodiac.databinding.FragmentLikeBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.ui.activity.quizmodule.QuizInstructionActivity;
import com.alphaa.inzodiac.utility.PDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LikeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LikeFragment extends BaseFragment implements ApiCallBackInterFace.favouriteInfoCallback,FavoriteAdapter.OnClick {
    String TAG = getClass().getSimpleName();

    private FragmentLikeBinding binding;
    private static String check = "";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<FavoriteModel.Datum> list;

    public static LikeFragment newInstance(String param1, String param2) {
        LikeFragment fragment = new LikeFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_like , container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        binding.etsearch.addTextChangedListener(watcherClass_search);
        String uid = getPrefHelper().getUserId();
//        new FavoritePresenter(activity,this).UserFavoriteListData(uid,"");
//        setAdapter();

        binding.llPlaygame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), QuizInstructionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setAdapter() {
        FavoriteAdapter adapter  =new FavoriteAdapter(activity, list,this);
        GridLayoutManager manager = new GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter( adapter );
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
    public void onfavouriteInfoResponse(String s) {
        Log.e(TAG, "onfavouriteInfoResponse: sss "+s );
        list.clear();
        String s1 = s.replace("null","\"\"");
        if (s1.contains("[{")) {
            FavoriteModel model = new Gson().fromJson(s1, FavoriteModel.class);
            list = model.getData();
            Log.e(TAG, "onfavouriteInfoResponse: list "+list.size() );
        }
        if (list.size() == 0){
            binding.notfound.setVisibility(View.VISIBLE);
        }else {
            binding.notfound.setVisibility(View.GONE);

        }
        setAdapter();
    }

    @Override
    public void ondislikeInfoResponse(String s) {


        Log.e(TAG, "ondislikeInfoResponse: "+s );

        String uid = getPrefHelper().getUserId();
        new FavoritePresenter(activity,this).UserFavoriteListData(uid,"");
        setAdapter();
    }


    private final TextWatcher watcherClass_search = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {

            if (editable == binding.etsearch.getEditableText()) {
                check = "1";
                callSearchApi();
            }
            else {
                check ="";
            }
        }
    };


    private void callSearchApi(){
        String uid = getPrefHelper().getUserId();
        new FavoritePresenter(activity,this).UserFavoriteListData(uid,binding.etsearch.getText().toString());
    }

    @Override
    public void dislikeClick(String otheruserid) {
        String uid = getPrefHelper().getUserId();
        new FavoritePresenter(activity,this).dislikeRequest(uid,otheruserid);

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.e(TAG, "onResume: fragment " );
        String uid = getPrefHelper().getUserId();
        new FavoritePresenter(activity,this).UserFavoriteListData(uid,"");
        setAdapter();
    }
}