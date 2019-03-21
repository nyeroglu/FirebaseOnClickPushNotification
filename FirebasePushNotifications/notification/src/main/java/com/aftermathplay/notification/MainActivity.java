package com.aftermathplay.notification;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Mainactivity from notification manager");
        FirebaseMessaging.getInstance().subscribeToTopic("NEWS");
    }


}
