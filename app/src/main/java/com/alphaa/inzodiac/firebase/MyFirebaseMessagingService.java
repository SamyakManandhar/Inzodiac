package com.alphaa.inzodiac.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.alphaa.inzodiac.R;
import com.alphaa.inzodiac.retrofit_chatting_notification.MessageData;
import com.alphaa.inzodiac.tabmodule.activity.chatmodule.ChatActivity;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailActivity;
import com.alphaa.inzodiac.tabmodule.activity.tabmodule.TabActivity;
import com.alphaa.inzodiac.ui.activity.likerequest.LikeRequestActivity;
import com.alphaa.inzodiac.ui.activity.likerequest.LikeRequestActivityNew;
import com.alphaa.inzodiac.ui.activity.quizmodule.QuizAcceptRegectActivity;
import com.alphaa.inzodiac.ui.activity.quizmodule.QuizInstructionActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Rahul on 1/1/2020.
 */

//class extending FirebaseMessagingService
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    Intent intent;;
    String TAG = getClass().getSimpleName();

    String userid,other_user_id,request_id,username,status;


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Log.e("NEW_TOKEN",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);

       /* // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());

            if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
               // scheduleJob();
            } else {
                // Handle message within 10 seconds
               // handleNow();
            }
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }*/


        //Log.e(TAG, "onMessageReceived: 555555555  "+remoteMessage.getNotification().getBody());
        Log.e(TAG, "onMessageReceived: 6666666  "+remoteMessage.getData() );
        Log.e(TAG,"sendNotification1 message222222 "+ remoteMessage.getNotification());
        //Log.e(TAG,"sendNotification1 message44444 "+ remoteMessage.get());
        String massage = remoteMessage.getData().get("message");
        Log.e(TAG,remoteMessage.getData().get("title")+"   sendNotification1 message1111 "+ massage);





        if (massage.contains("Please take turns to answer each question, there are 36 questions")){

            Log.e(TAG, "onMessageReceived: mmmmmmmmmmmmmmmmmm" );

            sendNotificationGamerequestaccept(massage);
        }

        if (massage.contains("Like request accept successfully")){
            sendNotificationlikerequestacceptaccept(massage);
        }






        //sendNotification(remoteMessage.getData().put("message", "message"));


        ////==============================================like data



        String like_data = remoteMessage.getData().get("like_data");

        Log.e(TAG, "onMessageReceived: likedata "+like_data );

        if (like_data!=null){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(like_data);

                Log.e(TAG, "onMessageReceived: chatting json "+jsonObject );

                String otherid = jsonObject.getString("user_id");
                String username = jsonObject.getString("username");
                String body ="send like request for your profile" ;//jsonObject.getString("body");
                String request_id = jsonObject.getString("request_id");

                String other_username = jsonObject.getString("other_username");


                Log.e(TAG, "onMessageReceived: chatting name "+username );
                Log.e(TAG, "onMessageReceived: chatting otherid "+otherid );
                Log.e(TAG, "onMessageReceived: chatting body "+body );
                //Log.e(TAG, "onMessageReceived: chatting message "+message );

                sendNotificationLikedata(body,"",other_username,otherid,request_id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




        ////////////==========================================message data

        String messageData = remoteMessage.getData().get("messageData");
        Log.e(TAG, "onMessageReceived: MessageData data "+ messageData);

        if (messageData!=null){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(messageData);

                Log.e(TAG, "onMessageReceived: chatting json "+jsonObject );

               String otherid = jsonObject.getString("otherid");
               String otherName = jsonObject.getString("otherName");
               String body = jsonObject.getString("body");
               String message = jsonObject.getString("message");

                Log.e(TAG, "onMessageReceived: chatting name "+otherName );
                Log.e(TAG, "onMessageReceived: chatting otherid "+otherid );
                Log.e(TAG, "onMessageReceived: chatting body "+body );
                Log.e(TAG, "onMessageReceived: chatting message "+message );

                sendNotificationChatting(body,"",otherName,otherid);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ////////////==========================================message data







        //all data--------------------------------------------game data

        String gamedata = remoteMessage.getData().get("game_data");
        Log.e(TAG, "onMessageReceived: game data "+gamedata );
        //Log.e(TAG, "onMessageReceived: game data 111111  "+gamedata.length() );


        if (gamedata!=null) {
            if (gamedata.length() != 2) {
                try {
                    JSONObject jsonObject = new JSONObject(gamedata);

                    userid = jsonObject.getString("user_id");
                    other_user_id = jsonObject.getString("other_user_id");
                    request_id = jsonObject.getString("request_id");
                    status = jsonObject.getString("status");
                    username = jsonObject.getString("other_username");

                    Log.e(TAG, "onMessageReceived: userid " + userid);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                sendNotification1(remoteMessage.getData().put("message", "message"),
                        remoteMessage.getData().put("flag", "flag"), massage, userid, other_user_id, status, username, request_id);

            }
        }
        ///============================================================game data



        if (gamedata==null&&messageData==null){
            sendNotificationWithoutGameData(remoteMessage.getData().put("message", "message"),
                    remoteMessage.getData().put("flag", "flag"), massage);
        }



    }



    private void sendNotification1(String messageBody, String flag, String msg,String m_userid,String m_otheruserid,String m_status,String m_username,String m_request_id) {
          Log.d(TAG,"sendNotification1: 0000  "+ msg);


        if (messageBody.toLowerCase().startsWith("Would".toLowerCase())){
            intent = new Intent(this, QuizAcceptRegectActivity.class);
            Log.e(TAG, "sendNotification1: QuizAcceptRegectActivity " );

        }else if (messageBody.toLowerCase().startsWith("Unfortunately".toLowerCase())){
            intent = new Intent(this, TabActivity.class);
            Log.e(TAG, "sendNotification1: MainActivity " );
        }else if (messageBody.toLowerCase().startsWith("Please".toLowerCase())){
            Log.e(TAG, "sendNotification1: QuizActivity " );
            intent = new Intent(this, QuizInstructionActivity.class);
        }

        //intent = new Intent(this, QuizAcceptRegectActivity.class);
        intent.putExtra("comesfrom", "pending_intent");
        intent.putExtra("m_userid", m_userid);
        intent.putExtra("m_otheruserid", m_otheruserid);
        intent.putExtra("m_status", m_status);
        intent.putExtra("m_username", m_username);
        intent.putExtra("m_request_id", m_request_id);


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data", flag);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, /*(int) when*/0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.notification_channel);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);




        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }






    private void sendNotificationWithoutGameData(String messageBody, String flag, String msg) {
        Log.d(TAG,"sendNotification1: 0000  "+ msg);

        intent = new Intent(this, TabActivity.class);

        /*//intent = new Intent(this, QuizAcceptRegectActivity.class);
        intent.putExtra("comesfrom", "pending_intent");
        intent.putExtra("m_userid", m_userid);
        intent.putExtra("m_otheruserid", m_otheruserid);
        intent.putExtra("m_status", m_status);
        intent.putExtra("m_username", m_username);
        intent.putExtra("m_request_id", m_request_id);
*/

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data", flag);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, /*(int) when*/0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.notification_channel);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }














    private void sendNotificationChatting(String messageBody,String title,String username,String userid) {



        intent = new Intent(this, ChatActivity.class);
        intent.putExtra("comesfrom", "pending_intent");
        intent.putExtra("otherName", username);
        intent.putExtra("otherid", userid);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, /*(int) when*/0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.notification_channel);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(username)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


    private void sendNotificationLikedata(String messageBody,String title,String username,String userid, String request_id) {


      //  if (messageBody.equalsIgnoreCase("123")){
            intent = new Intent(this, LikeRequestActivityNew.class);
      //  }


        intent.putExtra("comesfrom", "pending_intent");
        intent.putExtra("otherName", username);
        intent.putExtra("otherid", userid);

        intent.putExtra("request_id", request_id);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, /*(int) when*/0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.notification_channel);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(username)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }



    //send notificatin after game request accept
    private void sendNotificationGamerequestaccept(String messageBody) {


        intent = new Intent(this, QuizInstructionActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.putExtra("data", flag);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, /*(int) when*/0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.notification_channel);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }









    private void sendNotificationlikerequestacceptaccept(String messageBody) {


        intent = new Intent(this, TabActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.putExtra("data", flag);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, /*(int) when*/0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.notification_channel);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }




}