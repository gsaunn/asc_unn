package campusevents.michael.android.com.campusevents;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by User Pc on 6/24/2015.
 */
public class SyncDataReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // restart alarm on reboot
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent alarmIntent = new Intent(context,SyncData.class);
            PendingIntent pendingIntent = PendingIntent.getService(context,0,alarmIntent,0);
            //repeat every 20 secs
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),20 * 1000,pendingIntent);

        }
    }
}
