package com.example.timetable;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class WidgetReceiver extends AppWidgetProvider 
{
	@Override
    public void onDisabled(Context context) 
	{
        super.onDisabled(context);
        Intent stopUpdateIntent = new Intent(context, UpdateWidgetService.class);
        context.stopService(stopUpdateIntent);
    }

	@Override
    public void onEnabled(Context context) 
	{
        super.onEnabled(context);
        Intent startUpdateIntent = new Intent(context, UpdateWidgetService.class);
        context.startService(startUpdateIntent);
    }
	
	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) 
	{
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
	
	@Override
    public void onReceive(Context context, Intent intent) 
	{
        super.onReceive(context, intent);
        Intent startUpdateIntent = new Intent(context, UpdateWidgetService.class);
        context.startService(startUpdateIntent);
    }
}
