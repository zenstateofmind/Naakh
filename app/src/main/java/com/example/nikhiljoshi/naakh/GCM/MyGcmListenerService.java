package com.example.nikhiljoshi.naakh.GCM;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.UI.translate.Translate;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by nikhiljoshi on 3/17/16.
 */
public class MyGcmListenerService extends GcmListenerService{
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.droid)
                .setContentTitle("Test")
                .setContentText(message);
        Notification notifyDetails = mBuilder.build();
        PendingIntent myIntent = PendingIntent.getActivity(this, 0, new Intent(this, Translate.class), 0);
        notificationManager.notify(1, notifyDetails);
    }

}
