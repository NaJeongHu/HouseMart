package com.example.publicdatacompetition;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
//import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService  extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingService";

    /**
     * 구글 토큰을 얻는 값입니다.
     * 아래 토큰은 앱이 설치된 디바이스에 대한 고유값으로 푸시를 보낼때 사용됩니다.
     * **/

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("Firebase", "FirebaseInstanceIDService : " + s);
    }

    /**
     * 메세지를 받았을 경우 그 메세지에 대하여 구현하는 부분입니다.
     * **/
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
//            sendNotification(remoteMessage);
        }
    }


    /**
     * remoteMessage 메세지 안애 getData와 getNotification이 있습니다.
     * 이부분은 차후 테스트 날릴때 설명 드리겠습니다.
     * **/
//    private void sendNotification(RemoteMessage remoteMessage) {
//
//        String title = remoteMessage.getData().get("title");
//        String message = remoteMessage.getData().get("message");
//
//        /**
//         * 오레오 버전부터는 Notification Channel이 없으면 푸시가 생성되지 않는 현상이 있습니다.
//         * **/
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            String channel = "채널";
//            String channel_nm = "채널명";
//
//            NotificationManager notichannel = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            NotificationChannel channelMessage = new NotificationChannel(channel, channel_nm,
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            channelMessage.setDescription("채널에 대한 설명.");
//            channelMessage.enableLights(true);
//            channelMessage.enableVibration(true);
//            channelMessage.setShowBadge(false);
//            channelMessage.setVibrationPattern(new long[]{100, 200, 100, 200});
//            notichannel.createNotificationChannel(channelMessage);
//
//            NotificationCompat.Builder notificationBuilder =
//                    new NotificationCompat.Builder(this, channel)
//                            .setSmallIcon(R.drawable.ic_launcher_background)
//                            .setContentTitle(title)
//                            .setContentText(message)
//                            .setChannelId(channel)
//                            .setAutoCancel(true)
//                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
//
//            NotificationManager notificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            notificationManager.notify(9999, notificationBuilder.build());
//
//        } else {
//            NotificationCompat.Builder notificationBuilder =
//                    new NotificationCompat.Builder(this, "")
//                            .setSmallIcon(R.drawable.ic_launcher_background)
//                            .setContentTitle(title)
//                            .setContentText(message)
//                            .setAutoCancel(true)
//                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
//
//            NotificationManager notificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            notificationManager.notify(9999, notificationBuilder.build());
//
//        }
//
//
//        //google reference
////        Intent intent = new Intent(this, MainActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
////                PendingIntent.FLAG_ONE_SHOT);
////
////        String channelId = getString(R.string.default_notification_channel_id);
////        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////        NotificationCompat.Builder notificationBuilder =
////                new NotificationCompat.Builder(this, channelId)
////                        .setSmallIcon(R.mipmap.ic_launcher)
////                        .setContentTitle(getString(R.string.fcm_message))
////                        .setContentText(messageBody)
////                        .setAutoCancel(true)
////                        .setSound(defaultSoundUri)
////                        .setContentIntent(pendingIntent);
////
////        NotificationManager notificationManager =
////                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////
////        // Since android Oreo notification channel is needed.
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            NotificationChannel channel = new NotificationChannel(channelId,
////                    "Channel human readable title",
////                    NotificationManager.IMPORTANCE_DEFAULT);
////            notificationManager.createNotificationChannel(channel);
////        }
////
////        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
}