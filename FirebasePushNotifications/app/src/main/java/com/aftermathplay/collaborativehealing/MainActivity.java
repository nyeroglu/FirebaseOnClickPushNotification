package com.aftermathplay.collaborativehealing;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends Activity {

    private static final String TAG = "FFC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: OnXreate");
        FirebaseMessaging.getInstance().subscribeToTopic("NEWS");
    }


}
