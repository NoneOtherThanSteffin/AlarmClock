package clock.alarm.steff.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String SWITCH_ACTIVITY = "switch_activity";
    private static final String PACKAGE_NAME = "clock.alarm.steff.alarmclock";
    private static final String MAIN_ACTIVITY_NAME = "clock.alarm.steff.alarmclock.MainActivity";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alarmUri == null) {
//            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        }
//        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
//        ringtone.play();

        Intent i = new Intent();
        i.setClassName(PACKAGE_NAME, MAIN_ACTIVITY_NAME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(SWITCH_ACTIVITY, SWITCH_ACTIVITY);
        context.startActivity(i);
    }

//    private void startScheduledPUService() {
//        Context ctx = getApplication();
//        /** this gives us the time for the first trigger.  */
//        Calendar cal = Calendar.getInstance();
//        PendingIntent servicePendingIntent;
//        am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
//        long interval = 300000; // 5 minutes in milliseconds
//        Intent serviceIntent = new Intent(ctx, UploadService.class);
//        // make sure you **don't** use *PendingIntent.getBroadcast*, it wouldn't work
//        servicePendingIntent = PendingIntent.getService(ctx, UploadService.SERVICE_ID /* integer constant used to identify the service*/
//                , serviceIntent
//                , PendingIntent.FLAG_CANCEL_CURRENT);  /* FLAG to avoid creating a second service if there's already one running*/
//
//        // there are other options like setInexactRepeating, check the docs
//        am.setRepeating(AlarmManager.RTC_WAKEUP,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
//                cal.getTimeInMillis(),
//                interval,
//                servicePendingIntent);
//    }


}