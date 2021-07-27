package com.aman.sdk;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import static androidx.core.app.NotificationCompat.PRIORITY_MAX;

public class MyService extends Service {
    private WifiManager wifiManager;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        //do heavy work on a background thread

        wifiManager = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);

        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));

        new Thread(new Runnable() {
            @Override
            public void run() {

                Looper.prepare();

                while (true) {
                    Log.d("Test","00000000000000");

                    scanWifi();

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        //stopSelf();

        return START_NOT_STICKY;
    }

    boolean prevState = false;

//    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//    // notificationID allows you to update the notification later on.
//    mNotificationManager.notify(notificationID, mBuilder.build());

    private void scanWifi() {
        wifiManager.getWifiState();
        boolean isAvailable=isNetworkAvailable();
        if(isAvailable) {
            Log.d("WIFI",  " Connected - Aman " );
            if(prevState == false) {
                startForeground();
                Intent intent = new Intent(this, ActionActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                prevState = true;
            }
            else {
                NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notifManager.cancelAll();
            }
        }
        else{
            Log.d("WIFI",  " Disconnected - Aman " );
            if(prevState == true) {
                startForeground();
                Intent intent = new Intent(this, ActionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                prevState = false;
            }
            else {
                NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notifManager.cancelAll();
            }
        }
//        wifiManager.startScan();
        Toast.makeText(this, "Scanning WiFi ...", Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) { }
        }
    };

    private void startForeground() {

        String channelId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChannel("my_service", "My Background Service");
        } else {
            channelId = "";
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId );
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(PRIORITY_MAX)
                .setContentTitle("New Notification")
                .setContentText("Click to open it...")
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


        notificationBuilder.setContentIntent(contentIntent);
//        startForeground(1, notification);
        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


        // Builds the notification and issues it.
        mNotificationManager.notify(101, notificationBuilder.build());

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName){
        NotificationChannel chan = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);

        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);
        return channelId;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}