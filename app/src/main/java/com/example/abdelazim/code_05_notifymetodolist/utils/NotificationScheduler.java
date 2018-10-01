package com.example.abdelazim.code_05_notifymetodolist.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;

import java.util.Calendar;

public class NotificationScheduler {

    private static Context mContext;

    public static void schedule(Context context, Todo newTodo) {

        mContext = context;

        Notification notification = NotificationUtils.getTodoNotification(context, newTodo);

        scheduleNotification(notification, newTodo.getNotificationTime());
    }

    private static void scheduleNotification(Notification notification, Calendar calendar) {

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, NotificationPublisher.class);
        intent.putExtra(NotificationPublisher.KEY_NOTIFICATION, notification);
        intent.putExtra(NotificationPublisher.KEY_NOTIFICATION_ID, 1000);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 3030, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (alarmManager != null) {
            Log.i("WWW", "scheduleNotification: " + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.HOUR_OF_DAY));
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}