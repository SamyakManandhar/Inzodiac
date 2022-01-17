package com.alphaa.inzodiac.tabmodule.fragment.messagemodule;

import android.annotation.SuppressLint;
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

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.base.BaseFragment;
import com.alphaa.inzodiac.databinding.FragmentMessageBinding;
import com.alphaa.inzodiac.tabmodule.activity.chatmodule.Chat;
import com.alphaa.inzodiac.ui.activity.signupmodule.UserdatailModel;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.FirebaseUserModel;
import com.alphaa.inzodiac.utility.PDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends BaseFragment {

    String TAG = getClass().getSimpleName();


    private FragmentMessageBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Map<String, Chat> mapList = new HashMap<>();
    private List<Chat> chatList;
    private List<FirebaseUserModel> userList;
    private MessageAdapter adapter;
    private String userid ="";
    private int read = 0;

    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message , container, false);
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e(TAG, "onViewCreated: " );
        inItView();
    }

    private void inItView(){
        chatList = new ArrayList<>();
        userList = new ArrayList<>();
        setAdapter();
        getuserData();
    }

    private void setAdapter() {
        adapter  =new MessageAdapter(activity,chatList);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter( adapter );
    }
    private void getuserData(){
        if (getPrefHelper().getUserDataModel() != null && !getPrefHelper().getUserDataModel().isEmpty()) {
            UserdatailModel userdatailModel = new Gson().fromJson(getPrefHelper().getUserDataModel(), UserdatailModel.class);
            userid = userdatailModel.getData().getId();
            msglst();
        }else {
            binding.notfound.setVisibility(View.VISIBLE);
        }

    }


    public void msglst() {

        PDialog.pdialog(activity);

        Log.e(TAG, "msglst: userid "+userid );

        FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_HISTORY_TABLE).child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.e(TAG, "onDataChange: dataSnapshot "+dataSnapshot );
                if (dataSnapshot.getValue() == null) {
                    binding.notfound.setVisibility(View.VISIBLE);
                    PDialog.hideDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                PDialog.hideDialog();
            }
        });

        DatabaseReference databaseArtists = FirebaseDatabase.getInstance().getReference(Constants.CHAT_HISTORY_TABLE).child(userid);
        databaseArtists.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e(TAG, "onChildAdded: ss "+s );
                if (dataSnapshot.getValue(Chat.class) != null) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    gettingDataFromUserTable(dataSnapshot.getKey(), chat);
                    binding.notfound.setVisibility(View.GONE);
                }
                else {
                    PDialog.hideDialog();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //  Chat chat = dataSnapshot.getValue(Chat.class);

                Log.e(TAG, "onChildChanged: "+s );

                if (dataSnapshot.getValue(Chat.class) != null) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    gettingDataFromUserTable(dataSnapshot.getKey(), chat);
                    binding.notfound.setVisibility(View.GONE);
                }
                else {
                    PDialog.hideDialog();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                PDialog.hideDialog();
                for (int i = 0; i < chatList.size(); i++) {
                    if (chatList.get(i).uid.equals(dataSnapshot.getKey())) {
                        chatList.remove(i);
                    }
                }
                if (chatList.size() == 0) {
                    binding.notfound.setVisibility(View.VISIBLE);
                } else {
                    binding.notfound.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                PDialog.hideDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                PDialog.hideDialog();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void gettingDataFromUserTable(final String key, final Chat chat) {
        FirebaseDatabase.getInstance().getReference().child(Constants.USER_TABLE).child(key).

                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.e(TAG, "onDataChange: gettingDataFromUserTable "+dataSnapshot );
                        if (dataSnapshot.getValue(FirebaseUserModel.class) != null) {
                            FirebaseUserModel infoFCM = dataSnapshot.getValue(FirebaseUserModel.class);
                            userList.add(infoFCM);
                            PDialog.hideDialog();
                            for (FirebaseUserModel userInfoFCM : userList) {
                                if (userInfoFCM.uid.equals(key)) {

                                    chat.profilePic = userInfoFCM.profilePic;
                                    chat.name = userInfoFCM.name;
                                    chat.firebaseToken = userInfoFCM.firebaseToken;
                                    chat.uid = key;
                                    chat.onoffstatus = userInfoFCM.onoffstatus;
                                    chat.subscription = userInfoFCM.subscription;
                                }
                            }
                            mapList.put(chat.uid, chat);
                            chatList.clear();
                            Collection<Chat> values = mapList.values();
                            chatList.addAll(values);
                            if (chatList.size() != 0){
                                for (int i = 0; i <chatList.size(); i++) {
                                    if (chatList.get(i).readstatus) {
                                        read++;
                                    }
                                }
                                if (read == 0){
                                    binding.tvReadTxt.setVisibility(View.GONE);
                                }else {
                                    binding.tvReadTxt.setVisibility(View.VISIBLE);
                                    binding.tvReadTxt.setText("You have "+ read +" unread mesages");
                                    read = 0;
                                }

                            }
                            // msglstAdapter.notifyDataSetChanged();
                            shortList();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Data",databaseError.getMessage());
                        PDialog.hideDialog();
                    }
                });


        FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_HISTORY_TABLE).child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    binding.notfound.setVisibility(View.VISIBLE);
                    PDialog.hideDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                PDialog.hideDialog();
            }
        });

    }



    private void shortList() {
        Collections.sort(chatList, new Comparator<Chat>() {

            @Override
            public int compare(Chat a1, Chat a2) {
                if (a1.timestamp == null || a2.timestamp == null)
                    return -1;
                else {
                    Long long1 = Long.valueOf(String.valueOf(a1.timestamp));
                    Long long2 = Long.valueOf(String.valueOf(a2.timestamp));
                    return long2.compareTo(long1);
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

}



