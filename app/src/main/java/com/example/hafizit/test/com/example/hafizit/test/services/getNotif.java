package com.example.hafizit.test.com.example.hafizit.test.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.hafizit.test.R;
import com.example.hafizit.test.activity.searchVeneu;

/**
 * Created by Hafiz IT on 04/08/2017.
 */

public class getNotif extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(){
        return START_STICKY;
    }

    public void onCreate(){
        super.onCreate();
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 1;
                    while(true) {
                        PushNotification(i, "My notification", "Hello World!");
                        try {
                            Thread.sleep(60*1000);
                        } catch (Exception e) {

                        }
                        i++;
                    }
                }
            }).start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void PushNotification(int id, String title, String desc){
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, searchVeneu.class), 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(desc);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotificationManager.notify(id, mBuilder.build());
    }
}
