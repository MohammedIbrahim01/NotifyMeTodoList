package com.example.abdelazim.code_05_notifymetodolist.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;
import com.example.abdelazim.code_05_notifymetodolist.todo_done.TodoDoneActivity;


public class NotificationUtils {


    public static final String CHANNEL_ID = "channel-id";
    public static final String CHANNEL_NAME = "channel-name";
    public static final int RC_PENDING_INTENT = 1000;

    private static Context mContext;


    public static Notification getTodoNotification(Context context, Todo todo) {

        mContext = context;

        //get NotificationManager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //create NotificationChannel if Android O or newer
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(notificationChannel);
        }


        /**
         * create NotificationCompat.Builder
         *
         * and specify Notification attributes
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setLargeIcon(largeIcon())
                .setContentTitle(todo.getTitle())
                .setContentText(todo.getTitle())
                .setContentIntent(pendingIntent())
                .setDefaults(Notification.DEFAULT_SOUND |
                        Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setColor(Color.GREEN)
                .setAutoCancel(true);

        /**
         * set Priority on builder if Android between JELLY_BEAN and O
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(Notification.PRIORITY_HIGH);
        }

        return builder.build();
    }


    /**
     * create PendingIntent to open TodoDoneActivity when clicked on the notification
     */
    private static PendingIntent pendingIntent() {

        Intent startActivityIntent = new Intent(mContext, TodoDoneActivity.class);

        return PendingIntent.getActivity(mContext,
                RC_PENDING_INTENT,
                startActivityIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }


    /**
     * create Bitmap for largeIcon
     */
    private static Bitmap largeIcon() {

        Resources res = mContext.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, android.R.drawable.alert_dark_frame);
        return bitmap;
    }
}
