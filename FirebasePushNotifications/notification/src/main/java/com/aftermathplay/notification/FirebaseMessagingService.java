package com.aftermathplay.notification;


import android.R;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;


import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;




/**
 * Created by User on 2/20/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService  {
    private static final String TAG = "FirebaseMessagingServic";

    public FirebaseMessagingService() {
        Log.d(TAG, "FirebaseMessagingService: "+ FirebaseMessaging.INSTANCE_ID_SCOPE);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                JSONObject data = new JSONObject(remoteMessage.getData());

                this.sendNotification(data.toString(), data.getString("title"), data.getString("body"), data.getString("click_action"));

                String jsonMessage = data.getString("extra_information");
                Log.d(TAG, "onMessageReceived: \n" +
                        "Extra Information: " + jsonMessage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Check if message contains a ®notification payload.
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle(); //get title
            String message = remoteMessage.getNotification().getBody(); //get message
            String click_action = remoteMessage.getNotification().getClickAction(); //get click_action

            Log.d(TAG, "Message Notification Title: " + title);
            Log.d(TAG, "Message Notification Body: " + message);
            Log.d(TAG, "Message Notification click_action: " + click_action);
            JSONObject data = new JSONObject(remoteMessage.getData());
            JSONObject newdata = new JSONObject();


            try {
                newdata.put("notificationParameters",data.getString("notificationParameters"));
                newdata.put("notificationType",data.get("notificationType"));
                newdata.put("notificationAction",data.get("notificationAction"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            sendNotification(newdata.toString(),title, message,click_action);
        }
    }

    @Override
    public void onDeletedMessages() {

    }

    private void sendNotification(String notification, String title, String messageBody, String click_action) {
        Intent intent;


            intent = new Intent(this, SendUnity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        intent.putExtra("notification", notification);

        PendingIntent pendingIntentService = PendingIntent.getService(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.sym_def_app_icon)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntentService)
                .setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(Bitmap.createBitmap(25,256, Bitmap.Config.RGB_565))
                );

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onNewToken(String s) {
        Log.d(TAG, "Refreshed token: " + s);
        super.onNewToken(s);
    }
}
