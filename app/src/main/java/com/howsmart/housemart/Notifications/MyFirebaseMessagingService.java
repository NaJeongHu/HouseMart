package com.howsmart.housemart.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.howsmart.housemart.ChatActivity;
import com.howsmart.housemart.MainActivity;
import com.howsmart.housemart.R;

import java.util.HashMap;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    private static final int NOTIFICATION_REQUEST_CODE = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            String firebaseId = remoteMessage.getData().get("receiverFirebaseId");
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if(firebaseUser != null && firebaseId.equals(firebaseUser.getUid())){
                sendNotification(remoteMessage);
            }
        }
    }

    @Override
    public void onNewToken(String token) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("token", token);
        reference.updateChildren(hashMap);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String nickname = remoteMessage.getData().get("receiverNickname");
        String message = remoteMessage.getData().get("message");
        String senderFirebaseId = remoteMessage.getData().get("senderFirebaseId");

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("FirebaseId", senderFirebaseId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_REQUEST_CODE, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.housemartlogo)
                        .setContentTitle(nickname)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_REQUEST_CODE, notificationBuilder.build());
    }
}