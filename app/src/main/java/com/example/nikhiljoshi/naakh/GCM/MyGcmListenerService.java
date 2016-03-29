package com.example.nikhiljoshi.naakh.GCM;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.UI.Verification.Verification;
import com.example.nikhiljoshi.naakh.UI.translate.Translate;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.gcm.GcmReceiver;

import java.util.Date;

/**
 * Created by nikhiljoshi on 3/17/16.
 */
public class MyGcmListenerService extends GcmListenerService{



    @Override
    public void onMessageReceived(String from, Bundle data) {

        final String notification_type = data.getString("notification_type");
        Intent intent;
        PendingIntent pendingIntent;

        if (notification_type.equalsIgnoreCase("TRANSLATION_AVAILABLE")) {
            intent = new Intent(this, Translate.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(Translate.class)
                    .addNextIntent(intent);

            pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            intent = new Intent(this, Verification.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(Verification.class)
                    .addNextIntent(intent);

            pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        String message = data.getString("message");
        String title = data.getString("title");
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{0, 100})
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX);

        Notification notifyDetails = mBuilder.build();

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);

        notificationManager.notify(notificationId, notifyDetails);
    }

}

