package com.example.timetable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service 
{
	private RemoteViews remoteViews;
	private List<Module> moduleList = new ArrayList<Module>();
	private String content;
	private Timer timer;
    private TimerTask task;

	@Override
    public void onCreate() 
	{
		timer = new Timer();
		task = new TimerTask() 
		{
			@Override
			public void run() 
			{
				initModuleList();
				initContent();
				remoteViews = new RemoteViews(getPackageName(), R.layout.widget_info);
				remoteViews.setTextViewText(R.id.tv_widget_moduleName, content);
				AppWidgetManager awm = AppWidgetManager.getInstance(getApplicationContext());
				ComponentName componentName = new ComponentName(UpdateWidgetService.this, WidgetReceiver.class);
				awm.updateAppWidget(componentName, remoteViews);
			}
		};
		timer.schedule(task, 0, 3*1000);
		super.onCreate();
	}
	
	@Override
    public void onDestroy() 
	{
        super.onDestroy();
        timer.cancel();
        task.cancel();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public void initModuleList()
	{
		moduleList.clear();
		Object object;
		String s;
		List<String> moduleCodeList;
		SharedPreferences sharedPreferences = getSharedPreferences("module_data",Context.MODE_PRIVATE);
		s = sharedPreferences.getString("modulelist", null);
		if(s != null)
		{
			object = SharedPreferenceUtil.stringToObject(s);
			moduleCodeList = (List<String>)object;
			for(String code:moduleCodeList)
			{
				s = sharedPreferences.getString(code, null);
				if(s != null)
				{
					object = SharedPreferenceUtil.stringToObject(s);
					moduleList.add((Module)object);
				}
			}
		}
	}
	
	public void initContent()
	{
		String courseInfo = "";
		for(Module module:moduleList)
		{
			if(module.getDayOfWeek().equals(getSystemDayOfWeek()))
			{
				if(getModuleTime(module.getStartTime()) > getSystemMin())
					courseInfo += (module.getModuleName() + " " + module.getStartTime() + "\n");
			}
		}
		content = courseInfo;
	}
	
	public int getModuleTime(String moduleStartTime)
	{
		int hour = Integer.parseInt(moduleStartTime.substring(0,2));
		int min = Integer.parseInt(moduleStartTime.substring(3,5));
		int timeInMin = hour * 60 + min;
		return timeInMin;
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
	
	public int getSystemMin()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		return (hour * 60 + minute);
	}

}
