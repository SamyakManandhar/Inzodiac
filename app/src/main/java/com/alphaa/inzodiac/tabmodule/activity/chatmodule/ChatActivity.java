package com.alphaa.inzodiac.tabmodule.activity.chatmodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaa.inzodiac.MainActivity;
import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.RetrofitConnrect.ApiInterface;
import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.addminustoken.TokenAddPresenter;
import com.alphaa.inzodiac.base.BaseActivity;
import com.alphaa.inzodiac.databinding.ActivityChatBinding;
import com.alphaa.inzodiac.databinding.LogoutDialogBinding;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.retrofit_chatting_notification.MessageData;
import com.alphaa.inzodiac.retrofit_chatting_notification.RequestNotificaton;
import com.alphaa.inzodiac.retrofit_chatting_notification.RetrofitClientFirebase;
import com.alphaa.inzodiac.retrofit_chatting_notification.SendNotificationModel;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailPresenter;
import com.alphaa.inzodiac.ui.activity.signupmodule.UserdatailModel;
import com.alphaa.inzodiac.utility.AppConstants;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.FirebaseUserModel;
import com.alphaa.inzodiac.utility.PDialog;
import com.alphaa.inzodiac.utility.Utility;
import com.alphaa.inzodiac.utility.Validation;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.khirr.library.foreground.Foreground;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.alphaa.inzodiac.utility.Utility.SimpleDateTimeFormat;

public class ChatActivity extends BaseActivity implements View.OnClickListener , ApiCallBackInterFace.TokenAddCallBack {
    String TAG = getClass().getSimpleName();

    private ActivityChatBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private String userName = "", userid = "", otherName = "", otherid = "", useremail = "",otheruserfirebasetoken="";
    private Validation validation;
    private ArrayList<Chat> chatList;
    private DatabaseReference databaseReference;
    private Main_Chat_Adapter main_chat_adapter;
    private Map<String, Chat> map;
    private boolean ischeck = true;
    private String lastIndexmessagekey;
    private int listIndex = 0, increment = 0, totalCount = 0, tempCount = 0;
    private Boolean isCompleteChatLoad = false, isLoadFirst = true;
    private long deleteTime;
    private Uri temPhoto;
    private Uri image_FirebaseURL;


    //vedio ad
    private RewardedAd mRewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        initView();
    }

    private void initView() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        chatList = new ArrayList<>();
        map = new HashMap<>();
        validation = new Validation(this);
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPurple);
        setClicks(binding.btnSend, binding.ivpin, binding.ivBack,binding.llMenu,binding.lyDeleteChat);
        getuserData();
        chatshow();





        /*if (AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE)!=null){
            if (AppSession.getStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE).matches("no")){
                showAdMob();
            }
        }*/
        showAdMob();



    }

    private void showAdMob() {
        /*=============================reward video=======================*/

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull @org.jetbrains.annotations.NotNull InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, getString(R.string.admob_vedioid),
                adRequest, new RewardedAdLoadCallback(){

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.e(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }


                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.e(TAG, "Ad was loaded.");

                        if (mRewardedAd != null) {
                            Activity activityContext = ChatActivity.this;
                            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {

                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    // Handle the reward.
                                    Log.e(TAG, "The user earned the reward.");
                                    int rewardAmount = rewardItem.getAmount();
                                    String rewardType = rewardItem.getType();




                                    new TokenAddPresenter(ChatActivity.this, ChatActivity.this  )
                                            .addToken(AppSession.getStringPreferences(getApplicationContext(), Constants.USEREId)
                                                    ,"1"
                                                    ,"add");

                                }

                            });
                        } else {
                            Log.e(TAG, "The rewarded ad wasn't ready yet.");
                        }

                    }
                });






        /*=============================reward video=======================*/
    }

    private void setClicks(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }


    private void getuserData() {
        UserdatailModel userdatailModel = new Gson().fromJson(getPrefHelper().getUserDataModel(), UserdatailModel.class);
        userName = userdatailModel.getData().getName();
        userid = userdatailModel.getData().getId();
        useremail = userdatailModel.getData().getEmail();

        if (getIntent().getStringExtra("otherid") != null) {
            otherid = getIntent().getStringExtra("otherid");
            otherName = getIntent().getStringExtra("otherName");
            binding.tvName.setText(otherName);
        }

        Log.e(TAG, otherName+"  getuserData: otherid "+otherid );
        BackgroundUpdate();
        onlineOfflinStatus("online");
    }


    private void chatMassage() {
        String pushkey = firebaseDatabase.getReference().child(Constants.CHAT_ROOMS_TABLE).push().getKey();

        Log.e(TAG, "chatMassage: push key "+pushkey );
        String chatmsg = binding.mainActivityEditText.getText().toString().trim();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String device_token = FirebaseInstanceId.getInstance().getToken();
        String firebaseToken = FirebaseInstanceId.getInstance().getToken();

        // user chat
        Chat mychat = new Chat();
        mychat.firebaseToken = device_token;
        mychat.message = chatmsg;
        mychat.name = userName;
        if (image_FirebaseURL != null) {
            mychat.imageUrl = image_FirebaseURL.toString();
            mychat.message = "";
            mychat.isImage = 1;
            String holdKeyForImage = pushkey;
        } else {
            mychat.imageUrl = "";
            mychat.message = chatmsg;
            mychat.isImage = 0;
        }
        mychat.timestamp = ServerValue.TIMESTAMP;
        mychat.uid = userid;
        mychat.lastMsg = "";
        mychat.firebaseToken = firebaseToken;
        mychat.readstatus = true;

        //other Userchat
        Chat otherchat = new Chat();
        otherchat.firebaseToken = device_token;
        otherchat.message = chatmsg;
        otherchat.name = otherName;
        otherchat.timestamp = ServerValue.TIMESTAMP;
        otherchat.uid = otherid;
        if (image_FirebaseURL != null) {
            otherchat.imageUrl = image_FirebaseURL.toString();
            otherchat.message = "";
            otherchat.isImage = 1;
        } else {
            otherchat.imageUrl = "";
            otherchat.message = chatmsg;
            otherchat.isImage = 0;
        }
        otherchat.lastMsg = "";
        otherchat.firebaseToken = firebaseToken;
        otherchat.readstatus = false;

        String pushid = database.push().getKey();

        database.child(Constants.CHAT_ROOMS_TABLE).child(userid).child(otherid).child(pushid).setValue(mychat);
        database.child(Constants.CHAT_ROOMS_TABLE).child(otherid).child(userid).child(pushid).setValue(mychat);

        database.child(Constants.CHAT_HISTORY_TABLE).child(userid).child(otherid).setValue(otherchat);
        database.child(Constants.CHAT_HISTORY_TABLE).child(otherid).child(userid).setValue(mychat);

        Log.e(TAG, userName+" chatMassage: other userid "+userid );

        //sendNotificationToOtherUser("hhh","hello",firebaseToken);

        //getOtherUserData(userid);

        getOtherUserData(binding.mainActivityEditText.getText().toString(),"",firebaseToken,otherid,userName,userid);

        /*if (isNotification) {
            if (image_FirebaseURL != null) {
                if (firebaseToken != null && otherUserInfo != null) {
                    sendPushNotificationToReceiver(username, "Image", username, userid, firebaseToken);
                }
            } else {
                if (firebaseToken != null && otherUserInfo != null)
                    sendPushNotificationToReceiver(username, chatmsg, username, userid, firebaseToken);
            }
        }
        image_FirebaseURL = null;*/



        image_FirebaseURL = null;
        binding.mainActivityEditText.setText("");
    }


    public void bothEndChatList() {

        Log.e(TAG, "bothEndChatList: " );
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.CHAT_ROOMS_TABLE).child(userid).child(otherid);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                chat.banner_date = Utility.getDateBanner(ChatActivity.this, chat.getTimestamp());
                chatList.add(chat);
                int size = chatList.size() - 1;
                main_chat_adapter.notifyItemInserted(size);
                binding.recyclerView.scrollToPosition(size);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getChatLoadMore(dataSnapshot.getKey());
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void chatshow() {

        Log.e(TAG, "chatshow: " );

        if (Utility.checkInternetConnection(this)) {
            bothEndChatList();

           // Log.e(TAG, "chatshow: 11111111 "+chatList.get(0).getUid() );
            main_chat_adapter = new Main_Chat_Adapter(ChatActivity.this, chatList, userid, new Main_Chat_Adapter.AdapterPositionListener() {
                @Override
                public void getPosition(final int position) {
                    binding.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                                if (chatList.size() > position) {

                                }
                            }
                        }

                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                        }
                    });
                }
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.recyclerView.setLayoutManager(linearLayoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(main_chat_adapter);
            binding.recyclerView.scrollToPosition(map.size() - 1);

            binding.swipeRefreshLayout.setOnRefreshListener(() -> {
                ischeck = true;
                if (lastIndexmessagekey != null) {
                    if (totalCount >= tempCount) {
                        increment += 10;
                        getChatLoadMore(lastIndexmessagekey);
                    } else {
                        binding.swipeRefreshLayout.setEnabled(false);
                    }
                } else {
                    binding.swipeRefreshLayout.setRefreshing(false);
                }

            });


        } else {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
        }
    }

    private void getChatLoadMore(String dataKey) {

        Log.e(TAG, "getChatLoadMore: " );
        Query query = databaseReference.orderByKey().endAt(dataKey).limitToLast(50);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                tempCount += 1;

                if (totalCount < tempCount) isCompleteChatLoad = true;

                assert chat != null;
                if ((Long) chat.getTimestamp() > deleteTime) {
                    if (ischeck) {
                        lastIndexmessagekey = dataSnapshot.getKey();
                        ischeck = false;
                        listIndex = increment;
                    }
                    getChatDataInmap(dataSnapshot.getKey(), chat);
                } else {
                    map.remove(dataSnapshot.getKey());
                    chatList.clear();
                    main_chat_adapter.notifyDataSetChanged();
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                tempCount += 1;

                if (totalCount < tempCount) isCompleteChatLoad = true;
                assert chat != null;

                if ((Long) chat.timestamp > deleteTime) {
                    if (ischeck) {
                        lastIndexmessagekey = dataSnapshot.getKey();
                        ischeck = false;
                        listIndex = increment;
                    }
                    getChatDataInmap(dataSnapshot.getKey(), chat);
                } else {
                    map.remove(dataSnapshot.getKey());
                    chatList.clear();
                    main_chat_adapter.notifyDataSetChanged();
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getChatDataInmap(String key, Chat chat) {


        Log.e(TAG, "getChatDataInmap:11 "+chat.getUid() );


        binding.swipeRefreshLayout.setRefreshing(false);
        if (chat != null) {
            chat.banner_date = Utility.getDateBanner(this, chat.getTimestamp());
            map.put(key, chat);
            chatList.clear();
            Collection<Chat> values = map.values();
            chatList.addAll(values);
        }
        shortList();
        if (listIndex == 0) {
            binding.recyclerView.scrollToPosition(chatList.size() - 1);
        } else if (chatList.size() != (totalCount - 10)) {
            binding.recyclerView.scrollToPosition(19);
        } else if (chatList.size() == totalCount - 10) {
            binding.swipeRefreshLayout.setEnabled(false);
            isCompleteChatLoad = true;
        }
        if (totalCount <= 30) {
            isCompleteChatLoad = true;
            isLoadFirst = true;
            binding.swipeRefreshLayout.setEnabled(false);
        }

    }

    //List Shorting
    private void shortList() {
        Collections.sort(chatList, (a1, a2) -> {
            if (a1.getTimestamp() == null || a2.getTimestamp() == null)
                return -1;
            else {
                Long long1 = Long.valueOf(String.valueOf(a1.getTimestamp()));
                Long long2 = Long.valueOf(String.valueOf(a2.getTimestamp()));
                return long1.compareTo(long2);
            }
        });

        main_chat_adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send: {
                if (validation.isChatValid(binding.mainActivityEditText) || image_FirebaseURL != null) {
                    chatMassage();
                }
            }

            break;
            case R.id.ly_delete_chat: {
                binding.cvChatMenu.setVisibility(View.GONE);
                binding.icotraingle.setVisibility(View.GONE);
                backDialog();
            }
            break;

            case R.id.ll_menu: {
                if (binding.cvChatMenu.getVisibility() == View.VISIBLE) {
                    binding.cvChatMenu.setVisibility(View.GONE);
                    binding.icotraingle.setVisibility(View.GONE);
                } else{
                    binding.cvChatMenu.setVisibility(View.VISIBLE);
                    binding.icotraingle.setVisibility(View.VISIBLE);
                }
            }
            break;

            case R.id.ivBack: {
                onBackPressed();
                binding.cvChatMenu.setVisibility(View.GONE);
                binding.icotraingle.setVisibility(View.GONE);
            }
            break;

            case R.id.ivpin: {
                binding.cvChatMenu.setVisibility(View.GONE);
                binding.icotraingle.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= 23) {
                    if (activity.checkSelfPermission(READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        callIntent(AppConstants.MY_PERMISSIONS_REQUEST_EXTERNAL);
                    } else {
                        callIntent(AppConstants.RESULT_LOAD);
                    }
                } else {
                    callIntent(AppConstants.RESULT_LOAD);
                }
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0) {
            switch (requestCode) {
                case AppConstants.RESULT_LOAD: {
                    temPhoto = data.getData();
                    PDialog.pdialog(this);
                    creatFirebaseProfilePicUrl(temPhoto);
                }
                break;

                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }

        }

    }


    private void creatFirebaseProfilePicUrl(Uri selectedImageUri) {
        StorageReference storageRef;
        FirebaseStorage storage;
        FirebaseApp app;
        app = FirebaseApp.getInstance();
        assert app != null;
        storage = FirebaseStorage.getInstance(app);

        storageRef = storage.getReference("images/");
        StorageReference photoRef = storageRef.child(selectedImageUri.getLastPathSegment());
        photoRef.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
            Log.e("image", String.valueOf(image_FirebaseURL));
            image_FirebaseURL = uri;
            binding.btnSend.callOnClick();
            PDialog.hideDialog();
        }).addOnFailureListener(e -> {
            PDialog.hideDialog();
            Toast
                    .makeText(ChatActivity.this,
                            "Failed " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                    .show();
        }));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onlineOfflinStatus("offline");
    }




    private void onlineOfflinStatus(String status) {
        UserdatailModel userdatailModel = new Gson().fromJson(getPrefHelper().getUserDataModel(), UserdatailModel.class);

        if (userdatailModel!=null) {
            String userid = userdatailModel.getData().getId();
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            ChatLastSeen userModel = new ChatLastSeen();
            if (status.equals("online")) {
                userModel.chatstatus = "online";
            } else {
                userModel.chatlastdate = ServerValue.TIMESTAMP;
                userModel.chatstatus = "offline";

            }
            getLastSeenData();
            database.child(Constants.USER_LAST_STATUS).child(userid).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        }
    }

    private void getLastSeenData() {
        FirebaseDatabase.getInstance().getReference().child(Constants.USER_LAST_STATUS).child(otherid).
                addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue(FirebaseUserModel.class) != null) {
                            ChatLastSeen infoFCM = dataSnapshot.getValue(ChatLastSeen.class);
                            if (infoFCM.chatstatus.equals("online")) {
                                binding.tvlastseen.setText("online");

                            } else {
                                binding.tvlastseen.setText("Last Seen " + SimpleDateTimeFormat(infoFCM.chatlastdate));

                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Data", databaseError.getMessage());
                        PDialog.hideDialog();
                    }
                });


    }



    @SuppressLint("SetTextI18n")
    public void backDialog() {
        LogoutDialogBinding binding;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.logout_dialog, null, false);
        dialog.setContentView(binding.getRoot());
        binding.cat.setText("Are you sure you want to delete conversation with "+otherName+"?");
        binding.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        binding.yes.setOnClickListener(v -> {
            firebaseDatabase.getReference().child(Constants.CHAT_HISTORY_TABLE).child(userid).child(otherid).setValue(null);
            firebaseDatabase.getReference().child(Constants.CHAT_ROOMS_TABLE).child(userid).child(otherid).setValue(null);
            map.clear();
            chatList.clear();
            main_chat_adapter.notifyDataSetChanged();
            dialog.dismiss();
        });
        dialog.show();
    }


    private void BackgroundUpdate(){
        final Foreground.Listener foregroundListener = new Foreground.Listener() {
            @Override
            public void foreground() {
                Log.e("Foreground", "Go to foreground");
                onlineOfflinStatus("online");
            }
            @Override
            public void background() {
                Log.e("Foreground", "Go to background");
                onlineOfflinStatus("offline");

            }
        };
        Foreground.Companion.addListener(foregroundListener);

    }










/* private void onlineOfflinStatus(String status) {
        UserdatailModel userdatailModel = new Gson().fromJson(getPrefHelper().getUserDataModel(), UserdatailModel.class);
        String userName = userdatailModel.getData().getName();
        String userid = userdatailModel.getData().getId();
        String useremail = userdatailModel.getData().getEmail();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseUserModel userModel = new FirebaseUserModel();
        userModel.name = userName;
        userModel.email = useremail;
        userModel.profilePic = "";
        userModel.uid = userid;
        userModel.onoffstatus = "online";
        if (status.equals("online")) {
            userModel.chatstatus = "online";
            getLastSeenData();
        } else {
            userModel.chatlastdate = ServerValue.TIMESTAMP;
            userModel.chatstatus = "offline";
            getLastSeenData();

        }

        database.child(Constants.USER_TABLE).child(userid).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    private void getLastSeenData() {
        FirebaseDatabase.getInstance().getReference().child(Constants.USER_TABLE).child(otherid).
                addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue(FirebaseUserModel.class) != null) {
                            FirebaseUserModel infoFCM = dataSnapshot.getValue(FirebaseUserModel.class);
                            if (infoFCM.chatstatus.equals("online")){
                                binding.tvlastseen.setText("online");

                            }else {
                                binding.tvlastseen.setText("Last Seen "+SimpleDateTimeFormat(infoFCM.chatlastdate));

                            }

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Data", databaseError.getMessage());
                        PDialog.hideDialog();
                    }
                });


    }*/
















    private void sendNotificationToOtherUser(String body,String title,String token,String otheruname,String uid) {

//        SendNotificationModel sendNotificationModel = new SendNotificationModel(body, title,"22","ajay");

        Log.e(TAG, "sendNotificationToOtherUser: "+body+"  : "+title+"  : "+uid+"  : "+otheruname );

        SendNotificationModel sendNotificationModel = new SendNotificationModel(new MessageData(body,title,uid,otheruname));
        RequestNotificaton requestNotificaton = new RequestNotificaton();
        requestNotificaton.setSendNotificationModel(sendNotificationModel);
        //token is id , whom you want to send notification ,
        requestNotificaton.setToken(token);

        Call<ResponseBody> call = RetrofitClientFirebase.getInstance()
                .getApi().sendNotificationOnChatting(requestNotificaton);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.e("notification sent ", "done "+response.body().toString());
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getOtherUserData(String body1,String title1,String token,String otheruid,String uname,String userid){

        FirebaseDatabase.getInstance().getReference().child(Constants.USER_TABLE).child(otheruid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Log.e(TAG, "onDataChange111: dataSnapshot1111 "+dataSnapshot.child("firebaseToken") );

                //String response = String.valueOf(dataSnapshot.child("firebaseToken").getValue().toString());
                //Log.e(TAG, otheruid+"  onDataChange: response "+response );

                //sendNotificationToOtherUser( body1, title1, response, uname,userid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                PDialog.hideDialog();
            }
        });

    }


    @Override
    public void onTokenAdd(String s) {

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













