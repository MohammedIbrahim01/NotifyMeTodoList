package com.example.abdelazim.code_05_notifymetodolist.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationPublisher extends BroadcastReceiver {

    public static final String KEY_NOTIFICATION_ID = "key-notification-id";
    public static final String KEY_NOTIFICATION = "key-notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("WWW", "onReceive: received");
        Notification notification = intent.getParcelableExtra(KEY_NOTIFICATION);
        int notificationId = intent.getIntExtra(KEY_NOTIFICATION_ID, 1);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
    }
}
