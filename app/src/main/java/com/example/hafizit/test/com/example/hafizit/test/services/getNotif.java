package com.example.hafizit.test.com.example.hafizit.test.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Hafiz IT on 04/08/2017.
 */

public class getNotif extends IntentService {

    public getNotif() {
        super(getNotif.class.getName());
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {

                        }
                    }
                }
            }).start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
