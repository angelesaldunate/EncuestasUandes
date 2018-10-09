package com.example.angeles.encuestasuandes.Classes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

/**
 * Created by e440 on 09-10-18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    @Override
    public void onNewToken(String token) {
        Log.d("FIREBASE CLOUD MESSAGING", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

}
