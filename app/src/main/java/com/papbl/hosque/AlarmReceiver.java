package com.papbl.hosque;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.papbl.hosque.activities.MenuAdminActivity;
import com.papbl.hosque.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TYPE_NEWUSER = "NewUser";
    public static final String TYPE_NEWBENCANA = "NewBencana";
    private static final String EXTRA_TYPE = "type";


    private final static int ID_NEWUSER = 100;
    private final static int ID_NEWBENCANA = 101;

    private int idNotifUser;
    private int idNotifBencana;
    private int newUser = 0;
    private int newBencana = 0;

    @Override
    public void onReceive(final Context context, Intent intent) {
        idNotifUser = 1;
        idNotifBencana = 2;
        String type = intent.getStringExtra(EXTRA_TYPE);
        if (type.equalsIgnoreCase(TYPE_NEWUSER)) {
            checkNewUsers(new NotifikasiCallback() {
                @Override
                public void onSuccess(int newUser) {
                    if (newUser != 0) {
                        showAlarmNotification(context, "User Baru", "Ada " + newUser + " user yang belum diverifikasi", idNotifUser);
                    }
                }

                @Override
                public void onError(boolean failure) {

                }
            });
        } if (type.equalsIgnoreCase(TYPE_NEWBENCANA)) {
            Log.d("cek", "masuk typebencana" + type);

        }
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        Intent intent = new Intent(context.getApplicationContext(), MenuAdminActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.delsa)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }

    public void setNewUsersAndBencanaAlarm(Context context, String type) {
        Log.d("cek", "masuk setrepeating" + type);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NEWUSER, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10 * 60 * 1000, pendingIntent);
        }
    }


    private void checkNewUsers(final NotifikasiCallback notifikasiCallback) {
        Log.d("cek", "masuk checknewusers");
        DatabaseReference sewaRef = FirebaseDatabase.getInstance().getReference().child("Users");
        sewaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    if (!dt.child("status").getValue().toString().equalsIgnoreCase("")) {
                        final User mUser = dt.getValue(User.class);
                        if (!mUser.isStatus() && !mUser.getFotoIdentitas().equals("")) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String id = user.getUid();

                            DatabaseReference adminInfo = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                            adminInfo.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String kota = dataSnapshot.child("kota").getValue().toString();
                                    String fotoIdentitas = dataSnapshot.child("fotoIdentitas").getValue().toString();
                                    if(kota.equalsIgnoreCase(mUser.getKota()) && !fotoIdentitas.equals("")){
                                        newUser++;
                                    }
                                    notifikasiCallback.onSuccess(newUser);
                                    notifikasiCallback.onError(false);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        newUser = 0;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
