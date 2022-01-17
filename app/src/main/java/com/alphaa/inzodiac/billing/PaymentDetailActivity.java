package com.alphaa.inzodiac.billing;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient;
import com.alphaa.inzodiac.addminustoken.TokenAddPresenter;
import com.alphaa.inzodiac.presentcallback.ApiCallBackInterFace;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailActivity;
import com.alphaa.inzodiac.ui.activity.signupmodule.UserdatailModel;
import com.alphaa.inzodiac.utility.AppSession;
import com.alphaa.inzodiac.utility.Constants;
import com.alphaa.inzodiac.utility.FirebaseUserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.alphaa.inzodiac.RetrofitConnrect.RetrofitClient.IMAGE_BASE_URL;
import static com.alphaa.inzodiac.app.InZodiaApp.getPrefHelper;

public class PaymentDetailActivity extends AppCompatActivity {
    public static final String PUBLISHABLE_KEY = "pk_test_51HrMxNLqFB0iRXesrCflkGh9GthEIT57SuPw7cRtMQ5CLSFyb7VE2wlgWiVul2CETfn8lCBFp3ols0qlDBSfEmSD00WKztbuiH";

    //public static final String PUBLISHABLE_KEY = "pk_live_lgBzDCH2idMeZWFu8DVT4AiQ00DOAlWoPI";

    String TAG = this.getClass().getSimpleName();
    Button btn_next;
    TextView tv_total;
    RelativeLayout relonline, relcod;
    AppCompatImageView iv_cod, iv_online;
    TextView tv_cod, tv_online;
    String planId, planAmount;
    EditText et_holdername;
    String paymethode = "card";
    UserdatailModel userData;
    EditText et_cardno, et_month, etyr, et_cvv;
    //    public static final String PUBLISHABLE_KEY = "pk_live_lgBzDCH2idMeZWFu8DVT4AiQ00DOAlWoPI";
    private Card card;
    private ProgressDialog progressdialog;

    ImageView ivBack;

    String comesfrom,tokens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        inItView();
    }

    private void inItView() {
        ivBack = findViewById(R.id.ivBack);
        etyr = findViewById(R.id.et_yr);
        et_cardno = findViewById(R.id.et_cardno);
        et_month = findViewById(R.id.et_month);
        et_cvv = findViewById(R.id.et_cvv);
        tv_cod = findViewById(R.id.tv_cod);
        tv_online = findViewById(R.id.tv_online);
        iv_cod = findViewById(R.id.iv_cod);
        iv_online = findViewById(R.id.iv_online);
        relonline = findViewById(R.id.relonline);
        relcod = findViewById(R.id.relcod);
        tv_total = findViewById(R.id.tv_total_price);
        btn_next = findViewById(R.id.btn_paynow);
        et_holdername = findViewById(R.id.et_holdername);

        Intent intent = getIntent();
        planId = intent.getStringExtra("planId");
        planAmount = intent.getStringExtra("planAmount");
        comesfrom = intent.getStringExtra("comesfrom");
        tokens = intent.getStringExtra("tokens");
        tv_total.setText("£" + planAmount);

        Log.e(TAG, comesfrom+" inItView: planid "+planId+"   : "+tokens );

        String uid = getPrefHelper().getUserId();
        String userdata = getPrefHelper().getUserDataModel();
        userData = new Gson().fromJson(userdata, UserdatailModel.class);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: " + paymethode);
                if (et_cardno.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentDetailActivity.this, "Please enter card no.", Toast.LENGTH_SHORT).show();
                } else if (et_month.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentDetailActivity.this, "Please enter expirary month", Toast.LENGTH_SHORT).show();
                } else if (etyr.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentDetailActivity.this, "Please enter expirary year", Toast.LENGTH_SHORT).show();

                } else if (et_cvv.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentDetailActivity.this, "Please enter cvv no.", Toast.LENGTH_SHORT).show();
                } else {

                    progressdialog = new ProgressDialog(PaymentDetailActivity.this);

                    card = new Card(et_cardno.getText().toString(), Integer.parseInt(et_month.getText().toString()),
                            Integer.parseInt(etyr.getText().toString()), et_cvv.getText().toString()

                                /*"4242424242424242", //card number
                                2, //expMonth
                                2023,//expYear
                                "123"*/
                       /* "test", "11111", "2222222", "33333",
                        "ind", "us", "12", "23", "us"
                        , "4", CardBrand.Visa, CardFunding.Debit
                        , "no", "us", "doller"
                        , "qqq", "yes", "1",
*/
                    );
                    payStripe();
                }
            }
        });
    }


    ///////////////////////////
    private void payStripe() {
        boolean validation = card.validateCard();
        if (validation) {
            try {
                new Stripe(PUBLISHABLE_KEY).createToken(
                        card,
                        PUBLISHABLE_KEY,
                        new TokenCallback() {
                            @Override
                            public void onError(Exception error) {
                                Log.e(TAG, "onError() returned: " + error.getMessage());
                            }

                            @Override
                            public void onSuccess(Token token) {
                                Log.e(TAG, "onSuccess: " + token.getId());
                                payment(token.getId());
                            }
                        });
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
        } else if (!card.validateNumber()) {
            Toast.makeText(PaymentDetailActivity.this,
                    "Stripe - The card number that you entered is invalid",
                    Toast.LENGTH_LONG).show();
        } else if (!card.validateExpiryDate()) {
            Toast.makeText(PaymentDetailActivity.this,
                    "Stripe - The expiration date that you entered is invalid",
                    Toast.LENGTH_LONG).show();
        } else if (!card.validateCVC()) {
            Toast.makeText(PaymentDetailActivity.this,
                    "Stripe - The CVC code that you entered is invalid",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(PaymentDetailActivity.this,
                    "Stripe - The card details that you entered are invalid",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void payment(String tokenId) {
        startProgress("Loading");
        BillingParm billingRequestPojo = new BillingParm();
        billingRequestPojo.setUser_id(userData.getData().getId());
        billingRequestPojo.setPhone_no(userData.getData().getPhone());
        billingRequestPojo.setAccount_holder_name(userData.getData().getName());
        billingRequestPojo.setEmail(userData.getData().getEmail());
        billingRequestPojo.setPayment_status("1");
        billingRequestPojo.setTrx_id(tokenId);
        billingRequestPojo.setPayment_for("1");
        billingRequestPojo.setPlan_id(planId);
        billingRequestPojo.setAmount(planAmount);

        Log.e(TAG, "payment: "+new Gson().toJson(billingRequestPojo) );

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().billing_api(billingRequestPojo);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

                try {
                    switch (response.code()) {
                        case 200: {

                            //set subscription
                            if (comesfrom.contains("updatepremiumfragment")){
                                AppSession.setStringPreferences(getApplicationContext(), Constants.IS_SUBSCRIBE, "yes");

                                addUserFirebaseDatabase("yes",userData.getData().getId());

                                Log.e(TAG, "onResponse: 11111111 "+userData.getData().getId() );

                            }else {


                                new TokenAddPresenter(PaymentDetailActivity.this, new ApiCallBackInterFace.TokenAddCallBack() {
                                    @Override
                                    public void onTokenAdd(String s) {

                                    }

                                    @Override
                                    public void showLoaderProcess() {

                                    }

                                    @Override
                                    public void hideLoaderProcess() {

                                    }

                                    @Override
                                    public void onApiErrorResponse(String Errorresponse, String error_type) {

                                    }
                                })
                                        .addToken(AppSession.getStringPreferences(getApplicationContext(), Constants.USEREId)
                                                ,tokens
                                                ,"add");



                            }


                            finishProgress();
                            Log.e(TAG, "onResponse: payment detail activity" + response.body());



                            // String stresult = response.body().string();
                            String stresult = null;
                            try {
                                stresult = Objects.requireNonNull(response.body()).string();
                            } catch (IOException e) {
                                Log.e(TAG, "onResponse: error " + e.getMessage());
                                e.printStackTrace();
                            }

                            Log.e(TAG, "response111111 " + stresult);
                            Gson gson = new Gson();
                            //FavoriteModel favoriteModel =  gson.fromJson(response.body().toString(),FavoriteModel.class);
                            Toast.makeText(PaymentDetailActivity.this, "Payment Successfully", Toast.LENGTH_SHORT).show();


                            finish();


                            break;
                        }
                        case 400:
                        case 401:
                        case 500: {
                            finishProgress();
                            String result = Objects.requireNonNull(response.errorBody()).string();
                            Log.d("response400", result);
                            JSONObject jsonObject = new JSONObject(result);
                            String statusCode = jsonObject.optString("status");
                            String msg = jsonObject.optString("message");
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                finishProgress();
            }
        });

    }


    private void startProgress(String title) {
        progressdialog.setTitle(title);
        progressdialog.setMessage("Please Wait");
        progressdialog.show();
    }

    private void finishProgress() {
        progressdialog.dismiss();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



    private void addUserFirebaseDatabase(String subscription,String uId) {

        if (getPrefHelper().getUserDataModel() != null && !getPrefHelper().getUserDataModel().isEmpty()) {
            UserdatailModel userdatailModel = new Gson().fromJson(getPrefHelper().getUserDataModel(), UserdatailModel.class);
            String userName = userdatailModel.getData().getName();
            String userid = userdatailModel.getData().getId();
            String useremail = userdatailModel.getData().getEmail();
            String userprofile = IMAGE_BASE_URL + userdatailModel.getData().getProfilePic().get(0);
            String usersubscription = userdatailModel.getData().getSubscription();
            String usertoken = userdatailModel.getData().getFirebase_token();
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            FirebaseUserModel userModel = new FirebaseUserModel();
            userModel.name = userName;
            userModel.email = useremail;
            userModel.profilePic = userprofile;
            userModel.uid = userid;
            userModel.readstatus = false;
            userModel.onoffstatus = "online";
            userModel.subscription = subscription;
            userModel.firebaseToken =usertoken;
            database.child(Constants.USER_TABLE).child(userid).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });


        }

    }

}




