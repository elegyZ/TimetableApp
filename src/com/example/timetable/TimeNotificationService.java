package com.example.timetable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class TimeNotificationService extends Service 
{
	@Override
	public void onCreate() 
	{
		super.onCreate();
		Log.i("Service","onCreate My Dear Service");
		AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);  
		alarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), (long) (0.5*1000), pi);     //check every second
	}
	
	@Override
    public void onDestroy() {
        Log.i("Service", "onDestroy My Dear Service");
        super.onDestroy();
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
		alarm.cancel(pi);
    }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
