package com.aftermathplay.notification;


import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class SendUnity extends Service {
    private static final String TAG = "SendUnity";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if (UnityPlayer.currentActivity != null) {
            Log.d(TAG, "onStartCommand: " + UnityPlayer.currentActivity.getClass().getName());
            UnityPlayer.currentActivity.startActivity(new Intent(this, UnityPlayer.currentActivity.getClass()).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_NEW_TASK));
        }
        else
        {
            try {
                Class<?> c = Class.forName("com.google.firebase.MessagingUnityPlayerActivity");
                Intent launchIntent = new Intent(this, c);
                launchIntent.setPackage(null);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                this.startActivity(launchIntent);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

          //  startActivity((new Intent(this, UnityPlayer.class)).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_NEW_TASK));
        }
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UnityPlayer.currentActivity.onActivityReenter(0,null);
        }
*/

        UnityPlayer.UnitySendMessage("FirebasePushNotification", "OnClickNotificationHandler", intent.getStringExtra("notification"));
        Log.d(TAG, "onCreate: OnClickNotificationHandler");
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: onbind");
        return null;
    }
}
