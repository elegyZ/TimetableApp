package com.example.timetable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Base64;

public class SharedPreferenceUtil 
{
	private static String objectToString(Object object) 
	{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String string = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
            objectOutputStream.close();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	private static Object stringToObject(String objectString) 
	{
        byte[] mobileBytes = Base64.decode(objectString.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static void save(Activity activity, String fileKey, String key, Object saveObject) 
	{
        SharedPreferences sharedPreferences = activity.getApplicationContext().getSharedPreferences(fileKey, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String string = objectToString(saveObject);
        editor.putString(key, string);
        editor.apply();
    }

	public static Object get(Activity activity, String fileKey, String key) {
        SharedPreferences sharedPreferences =  activity.getApplicationContext().getSharedPreferences(fileKey, Activity.MODE_PRIVATE);
        String string = sharedPreferences.getString(key, null);
        if (string != null) 
        {
            Object object = stringToObject(string);
            return object;
        } 
        else
            return null;
    }
	
	public static void remove(Activity activity, String fileKey, String key) 
	{
        SharedPreferences sharedPreferences = activity.getApplicationContext().getSharedPreferences(fileKey, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
	
	public static void clear(Activity activity, String fileKey) 
	{
		SharedPreferences sharedPreferences = activity.getApplicationContext().getSharedPreferences(fileKey, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
	}
}
