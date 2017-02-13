package com.practica.jmm.mascotaspreferidas.notification;

import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.practica.jmm.mascotaspreferidas.MainActivity;
import com.practica.jmm.mascotaspreferidas.R;
import com.practica.jmm.mascotaspreferidas.Top5mascotas;
import com.practica.jmm.mascotaspreferidas.restApi.ConstantesRestApi;

/**
 * Created by sath on 2/02/17.
 */


public class NotificationService extends FirebaseMessagingService {


    private static final String TAG = "FIREBASE";
    private static int NOTIFICATION_ID = 001;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        /*       if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
*/
        // Check if message contains a notification payload.
        //if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());


        //Intent i = new Intent(this, MainActivity.class);
        Intent perfil = new Intent();
        perfil.setAction("PERFIL");
        PendingIntent pendingIntentPerfil = PendingIntent.getBroadcast(this, 0,perfil,PendingIntent.FLAG_ONE_SHOT);
        Intent like = new Intent();
        like.setAction("LIKE");
        PendingIntent pendingIntentLike = PendingIntent.getBroadcast(this, 0,like,PendingIntent.FLAG_ONE_SHOT);
        Intent user = new Intent();
        user.setAction("USER");
        user.putExtra("USER", ConstantesRestApi.UsuarioInsta);
        PendingIntent pendingIntentUser = PendingIntent.getBroadcast(this, 0,user,PendingIntent.FLAG_ONE_SHOT);


//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,i,PendingIntent.FLAG_ONE_SHOT);
            Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Action actionPerfil = new NotificationCompat.Action.Builder(R.drawable.cat_footprint_48,
                    getString(R.string.Ver_perfil), pendingIntentPerfil).build();

        NotificationCompat.Action actionLike = new NotificationCompat.Action.Builder(R.drawable.unlike,
                getString(R.string.DesacerLike), pendingIntentLike).build();

        NotificationCompat.Action actionUser = new NotificationCompat.Action.Builder(R.drawable.imagen,
                getString(R.string.VerMediaUser), pendingIntentUser).build();

        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender()
                   // .setHintHideIcon(true)
                    .setBackground(BitmapFactory.decodeResource(getResources(),R.drawable.fondo20))
                    //.setGravity(Gravity.CENTER_VERTICAL)
                    ;

            NotificationCompat.Builder notif = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.my_topic_48white)
                    .setContentTitle("Notificación")
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setSound(sonido)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntentPerfil)
                    .extend(wearableExtender.addAction(actionPerfil))
                    .setContentIntent(pendingIntentLike)
                    .extend(wearableExtender.addAction(actionLike))
                    .setContentIntent(pendingIntentUser)
                    .extend(wearableExtender.addAction(actionUser))

                    ;
        //.addAction(R.drawable.hand,getString(R.string.Ver_perfil), pendingIntent);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(NOTIFICATION_ID,notif.build());
        //}


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below
    }
}
