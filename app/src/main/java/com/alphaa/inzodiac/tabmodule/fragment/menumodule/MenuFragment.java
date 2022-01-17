package com.alphaa.inzodiac.tabmodule.fragment.menumodule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.app.prefs.PrefHelper;
import com.alphaa.inzodiac.base.BaseFragment;
import com.alphaa.inzodiac.databinding.FragmentMenuBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.FilterFragment;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.PDialog;
import com.alphaa.inzodiac.westerncompatibility.WesternCompatibilityData;
import com.alphaa.inzodiac.westerncompatibility.WesternCompatibilityModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener, ApiCallBackInterFace.menuInfoCallback

{
    String TAG = getClass().getSimpleName();

    private static String catid = "";
    private static String UserFilterData = "";
    private FragmentMenuBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static ImageView ivfilter;
    private ArrayList<UserMenuModel.Datum> list;

    String chinese_fromfragment="",western_fromfragment="";



    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        catid =param1;
        UserFilterData =param2;
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);


        Log.e("TAG", "newInstance: "+catid );





        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu , container, false);

        PrefHelper prefHelper = new PrefHelper(getContext());
        catid = prefHelper.getCatId();
        Log.e(TAG, "onCreateView: 222222222  : "+prefHelper.getCatId() );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivfilter = view.findViewById(R.id.ivfilter);
        list = new ArrayList<>();
        inItView();
        //userListProcess();





    }

    @Override
    public void onResume() {
        super.onResume();
        userListProcess();

    }

    private void userListProcess(){

        Log.e(TAG, catid+" userListProcess: Userfilter data "+UserFilterData );
        if (!UserFilterData.isEmpty()){
            UserMenuModel userMenuModel = new Gson().fromJson(UserFilterData,UserMenuModel.class);
            list = userMenuModel.getData();
            if (list.size() == 0){
                binding.notfound.setVisibility(View.VISIBLE);
            }else {
                binding.notfound.setVisibility(View.GONE);

                getwesternzodaicMatching(catid);

            }

           // Log.e(TAG, "userListProcess: user list "+list.get(0).getCategoryId() );
        }else {
            String userid = getPrefHelper().getUserId();
            Log.e(TAG, userid+"  :userListProcess: "+catid );




            chinese_fromfragment = getArguments().getString("chinese");
            western_fromfragment = getArguments().getString("western");

            //Log.e(TAG, western_fromfragment+"  :onViewCreated: cccccccccccc "+chinese_fromfragment );



            Log.e(TAG, western_fromfragment+"  :userListProcess: abc111 string 1111111111  "+chinese_fromfragment );

            if (western_fromfragment==null&&chinese_fromfragment==null){

                String othweusergender = AppSession.getStringPreferences(getContext(), Constants.OTHERUSERGENDER);
                String orientation = AppSession.getStringPreferences(getContext(), Constants.USERORIENTATION);

                Log.e(TAG, "userListProcess: 77777766 777777777  "+othweusergender );
                new MenuPresenter(activity, this).UserListData(catid, userid, "", "", "", "", "", "", "",
                        "","","",othweusergender,"");

            }else {
                Log.e(TAG, "userListProcess: 77777766 8888888" );
                new MenuPresenter(activity, this).UserListUsingZodaic(chinese_fromfragment,western_fromfragment);
            }

            /*try {
               int abc =  Integer.parseInt(catid);

                new MenuPresenter(activity, this).UserListData(catid, userid, "", "", "", "", "", "", "");

                Log.e(TAG, "userListProcess: abc111 number "+abc );
            } catch(NumberFormatException e) {
                Log.e(TAG, western_fromfragment+"  :userListProcess: abc111 string "+chinese_fromfragment );
                new MenuPresenter(activity, this).UserListUsingZodaic(chinese_fromfragment,western_fromfragment);


            } catch(NullPointerException e) {
                Log.e(TAG, "userListProcess: abc111 " );
            }*/




        }
        binding.ll.setVisibility(View.VISIBLE);
        setAdapter();
    }

    private void setAdapter() {
        MenuAdapter adapter  =new MenuAdapter(activity,list);
        GridLayoutManager manager = new GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter( adapter );
    }

    private void inItView() {
        setClicks(binding.ivfilter);
    }


    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivfilter: {
                FilterFragment filterFragment = FilterFragment.newInstance(catid, "");
                replaceFragment(filterFragment, binding.frame.getId(), true);
                binding.frame.setVisibility(View.VISIBLE);
                binding.ll.setVisibility(View.GONE);
            }
            break;

        }
    }

    @Override
    public void onmenuInfoResponse(String s) {


        //Log.e(TAG, "onmenuInfoResponse: 11111111 "+s );

        String s1 = s.replace("null","\"\"");
        UserMenuModel userMenuModel = new Gson().fromJson(s1,UserMenuModel.class);
        list = userMenuModel.getData();
        Log.e(TAG, catid+"  onmenuInfoResponse: list size "+list.size() );
        if (list.size() == 0){
            binding.notfound.setVisibility(View.VISIBLE);
        }else {
            binding.notfound.setVisibility(View.GONE);

        }



        //western zodiac matching data==============================categry 8888888888========start
        getwesternzodaicMatching(catid);
        //western zodiac matching data==============================categry 8888888888========ends


        setAdapter();
    }

    private void getwesternzodaicMatching(String categoryid) {
        //if (catid.equalsIgnoreCase(categoryid)) {

        Log.e(TAG, "getwesternzodaicMatching: catid  "+categoryid );
            //sort listing according weastern================================================start
            String westernzodiac = AppSession.getStringPreferences(getContext(), Constants.WESTERNZODAICSIGN);

            ArrayList<WesternCompatibilityModel> arrayList = WesternCompatibilityData.getwesterncompatibilitydata();


            //westernzodiac = "libra";

            Log.e(TAG, arrayList.size() + "  onmenuInfoResponse: western " + westernzodiac);
            int getzodaicpos = 0;
            for (int i = 0; i < arrayList.size(); i++) {

                if (arrayList.get(i).getZodiac().equalsIgnoreCase(westernzodiac.toLowerCase())) {
                    Log.e(TAG, "onmenuInfoResponse: for loop " + arrayList.get(i).getZodiac());
                    getzodaicpos = i;

                    break;
                }
            }

            Log.e(TAG, "onmenuInfoResponse: pos " + getzodaicpos);
            String cc = "";

            if (categoryid.equalsIgnoreCase("1")) {
                cc = arrayList.get(getzodaicpos).getClassicCompatibility();
            }else if (categoryid.equalsIgnoreCase("2")) {
                cc = arrayList.get(getzodaicpos).getOppositesAttract();
            }else if (categoryid.equalsIgnoreCase("8")) {
                cc = arrayList.get(getzodaicpos).getOurPick();
            }else if (categoryid.equalsIgnoreCase("7")) {
                cc = arrayList.get(getzodaicpos).getWildCard();
            }else if (categoryid.equalsIgnoreCase("6")) {
                cc = arrayList.get(getzodaicpos).getBusiness();
            }else if (categoryid.equalsIgnoreCase("5")) {
                cc = arrayList.get(getzodaicpos).getFriends();
            }else if (categoryid.equalsIgnoreCase("4")) {
                cc = arrayList.get(getzodaicpos).getPassionPotential();
            }else if (categoryid.equalsIgnoreCase("3")) {
                cc = arrayList.get(getzodaicpos).getMoreUnderstandingRequired();
            }

            List<String> zodaiclist;
            zodaiclist = Arrays.asList(cc.split("\\s*,\\s*"));

            Log.e(TAG+list.size(), cc + "  onmenuInfoResponse: cccccc " + zodaiclist.size());


       /* for (int u = 0; u <list.size() ; u++) {
            Log.e(TAG, "getwesternzodaicMatching: userlist "+list.get(u).getName() );
        }*/


//        for (int j = 0; j < list.size() ; j++) {
            for (int j = list.size() - 1; j >= 0; j--) {
                Log.e(TAG, list.get(j).getName() + "  onmenuInfoResponse: match " + list.get(j).getWestern_zodaic());

                boolean checkwestern = false;
                for (int k = 0; k < zodaiclist.size(); k++) {
                    Log.e(TAG, list.get(j).getWestern_zodaic().toLowerCase() + " onmenuInfoResponse: mmmmmmmmm " + zodaiclist.get(k).toLowerCase());



                    if (list.get(j).getWestern_zodaic().toLowerCase().equalsIgnoreCase(zodaiclist.get(k).toLowerCase().trim())) {
                        Log.e(TAG, "onmenuInfoResponse: ppppppp "+list.get(j).getWestern_zodaic() );
                        checkwestern = true;
                        break;
                    }
                }
                if (checkwestern == false) {

                    Log.e(TAG, "onmenuInfoResponse: removed " + list.get(j).getWestern_zodaic());
                    list.remove(j);
                    //j=0;
                }
            }

            Log.e(TAG, "onmenuInfoResponse: list size1111111 " + list.size());
            //sort listing according weastern================================================ends

        //}
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