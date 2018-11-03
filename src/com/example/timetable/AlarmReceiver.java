package com.example.timetable;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver 
{
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		String timeStamp = getSystemDayOfWeek() + getSystemMin();
		SharedPreferences sharedPreferences = context.getSharedPreferences("time_data",0);
		String string = sharedPreferences.getString(timeStamp, null);
		if(string != null && isRightSec())
		{
			Object object = SharedPreferenceUtil.stringToObject(string);
			Module module = (Module) object;
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification.Builder(context)
					.setSmallIcon(R.drawable.ic_launcher)
					.setContentTitle("Class Coming Soon!")
	                .setContentText(module.getModuleName() + " will start " + timeAdvance(module) + " minutes later!")
	                .build();
			notificationManager.notify((int)System.currentTimeMillis(), notification);
			Log.i("Notification","Thank God It Works!!!!!!!");
		}
		Log.i("Notification",timeStamp);
	}
	
	public String getSystemDayOfWeek()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		switch (calendar.get(Calendar.DAY_OF_WEEK)) 
		{
        case Calendar.SUNDAY:
            return "Sunday";
        case Calendar.MONDAY:
        	return "Monday";
        case Calendar.TUESDAY:
        	return "Tuesday";
        case Calendar.WEDNESDAY:
        	return "Wednesday";
        case Calendar.THURSDAY:
        	return "Thursday";
        case Calendar.FRIDAY:
        	return "Friday";
        case Calendar.SATURDAY:
        	return "Saturday";
        default:
        	return null;
		}
	}
	
	public String getSystemMin()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		return String.valueOf(hour * 60 + minute);
	}
	
	public boolean isRightSec()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int second = calendar.get(Calendar.SECOND);
		return second == 0;
	}
	
	public int timeAdvance(Module module)
	{
		int hour = Integer.parseInt(module.getStartTime().substring(0,2));
		int min = Integer.parseInt(module.getStartTime().substring(3,5));
		int timeInMin = hour * 60 + min;
		return timeInMin - Integer.parseInt(getSystemMin());
	}

}
