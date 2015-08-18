package com.example.helloworld.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.exacttarget.etpushsdk.event.RegistrationEvent;
import com.example.helloworld.HelloWorldApplication;
import com.example.helloworld.R;
import com.example.helloworld.ui.activities.MainActivity;

import java.util.Date;

public class MtAlarmReceiver extends BroadcastReceiver {
    private static final String TAG = MtAlarmReceiver.class.getSimpleName();

    public MtAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
            Get the data out of our bundle
         */
        Bundle bundle = intent.getExtras();
        RegistrationEvent registrationEvent = (RegistrationEvent) bundle.getSerializable(HelloWorldApplication.EXTRAS_REGISTRATION_EVENT);
        long eventTimeInMillis = registrationEvent != null ? registrationEvent.getLastSent() : 0;

        /*
            Create our notification
         */
        Log.v(TAG, String.format("Notification created for updates sent at %1$d", eventTimeInMillis));

        Date date = new Date(eventTimeInMillis);
        String eventTimeAsString = android.text.format.DateFormat.format("yyyy-MM-dd kk:mm:ss", date).toString();

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.ic_stat_helloworld)
                        .setContentTitle(context.getString(R.string.notification_title))
                        .setContentText(String.format(context.getString(R.string.notification_body), eventTimeAsString));

        Intent notificationIntent = new Intent(context, MainActivity.class);
        /*
            Set our Category and Action flags in addition to using our launcher activity so the
            application will act as if we've pressed the icon from our app drawer when the notification
            is clicked.

            What's the difference?  If you do not set these flags the notification just opens
            MainActivity (bull in a china shop style).  If you set these flags then you will be
            returned to the app's currently visible activity.
         */
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(R.id.mt_notification, builder.build());
    }
}
