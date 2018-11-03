package com.example.timetable;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ModuleAdapter extends ArrayAdapter<Module> 
{
	private final int resourceID;
	private ActivityModuleList activity;
	private RelativeLayout layout;
	private List<TextView> textviewList = new ArrayList<TextView>();
	private List<Button> buttonList = new ArrayList<Button>();
	private String fontStyle;
	private String fontColor;
	private String bgColor;
	
	public ModuleAdapter(Context context,int textViewResourceID,List<Module> objects,ActivityModuleList activity)
	{
		super(context,textViewResourceID,objects);
		resourceID = textViewResourceID;
		this.activity = activity;
	}
	
	@Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
		//initTimeAdvanceMap();
		final Module module = (Module)getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceID, null);
		layout = (RelativeLayout)view.findViewById(R.id.layout_item);
		TextView tv_lst_moduleCode = (TextView)view.findViewById(R.id.tv_lst_moduleCode);
		TextView tv_lst_choice = (TextView)view.findViewById(R.id.tv_lst_choice);
		TextView tv_lst_dayOfWeek = (TextView)view.findViewById(R.id.tv_lst_dayOfWeek);
		TextView tv_lst_startTime = (TextView)view.findViewById(R.id.tv_lst_startTime);
		TextView tv_lst_location = (TextView)view.findViewById(R.id.tv_lst_location);
		ImageView iv_delete = (ImageView)view.findViewById(R.id.iv_delete);
		final Button bt_setTime = (Button)view.findViewById(R.id.bt_setTime);
		
		tv_lst_moduleCode.setText(module.getModuleCode());
		tv_lst_choice.setText(String.valueOf(module.getChoice().charAt(0)));
		tv_lst_dayOfWeek.setText(module.getDayOfWeek().substring(0, 2));
		tv_lst_startTime.setText(module.getStartTime());
		tv_lst_location.setText(module.getLocation());
		initList(bt_setTime,tv_lst_moduleCode,tv_lst_choice,tv_lst_dayOfWeek,tv_lst_startTime,tv_lst_location);
		setStyle();
		
		if(module.getAlarm() != null)
			bt_setTime.setText(module.getAlarm().toString() + " min in advance");
		OnClickListener lst_setTime = new OnClickListener()
		{
			int timeChoice = 0;
			final String[] items = { "15 min in advance","10 min in advance","5 min in advance","0 min in advance","Close Notification" };
			@Override
			public void onClick(View arg0) 
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setTitle("Choose The Notify Time");
				builder.setSingleChoiceItems(items,0,new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int choice)
					{
						timeChoice = choice;
					}
				});
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
			    {
			        @Override
			        public void onClick(DialogInterface dialog, int choice)
			        {
			            if (timeChoice == 0)
			            	setAlarm(module, 15, bt_setTime, "15 min in advance");
			            else if(timeChoice == 1)
			            	setAlarm(module, 10, bt_setTime, "10 min in advance");
			            else if(timeChoice == 2)
			            	setAlarm(module, 5, bt_setTime, "5 min in advance");
			            else if(timeChoice == 3)
			            	setAlarm(module, 0, bt_setTime, "0 min in advance");
			            else if(timeChoice == 4)
			            {
			            	removeAlarm(module);
			            	bt_setTime.setText("Notification Time");
			            }
			        }
			    });
				builder.show();
			}
		};
		bt_setTime.setOnClickListener(lst_setTime);
		OnClickListener lst_delete = new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
				builder.setTitle("Delete Module");
				builder.setIcon(R.id.iv_delete);
				builder.setMessage("Do you want to delete this module information?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		        {
		            @Override
		            public void onClick(DialogInterface dialog, int which)
		            {
		            	if(module.getAlarm() != null)
		            		removeAlarm(module);
		            	activity.deleteModule(module);
		            	dialog.dismiss();
		            	activity.initModuleList();
		            	activity.adapter.notifyDataSetChanged();
		            }
		        });
				builder.setNegativeButton("No",null);
				builder.show();
			}
		};
		iv_delete.setOnClickListener(lst_delete);
		return view;
	}
	
	public String getTimeStamp(String timeOrigin,int advance)
	{
		int hour = Integer.parseInt(timeOrigin.substring(0,2));
		int min = Integer.parseInt(timeOrigin.substring(3,5));
		int timeInMin = hour * 60 + min;
		String timeStamp = String.valueOf(timeInMin - advance);
		return timeStamp;
	}
	
	public void setAlarm(Module module, int advance, Button bt_setTime, String s)
	{
		if(module.getAlarm() != null)
			removeAlarm(module);
		SharedPreferences sharedPreferences = activity.getSharedPreferences("time_data",0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		module.setAlarm(advance);
		editor.putString(module.getDayOfWeek() + getTimeStamp(module.getStartTime(),advance), SharedPreferenceUtil.objectToString(module));
		editor.apply();
		bt_setTime.setText(s);
		String key = module.getDayOfWeek() + getTimeStamp(module.getStartTime(),advance);
		Toast.makeText(getContext(), key,Toast.LENGTH_SHORT).show();
		//if(sharedPreferences.getString(key, null).equals(null))
			//Toast.makeText(getContext(), "√ª¥Ê…œ", Toast.LENGTH_SHORT).show();
		//else
			//Toast.makeText(getContext(), "¥Ê…œ", Toast.LENGTH_SHORT).show();
		activity.freshModule(module);
	}
	
	public void removeAlarm(Module module)
	{
		SharedPreferences sharedPreferences = activity.getSharedPreferences("time_data",0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove(module.getDayOfWeek() + module.getAlarm());
		editor.apply();
		module.setAlarm(null);
	}
	
	public void initList(Button bt, TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5)
	{
		textviewList.add(tv1);
		textviewList.add(tv2);
		textviewList.add(tv3);
		textviewList.add(tv4);
		textviewList.add(tv5);
		buttonList.add(bt);
	}
	
	public void setStyle()
	{
		setFontStyle();
		setFontColor();
		setBgStyle();
	}
	
	public void setFontStyle()
	{
		Typeface typeface = Typeface.DEFAULT;
		Object object = SharedPreferenceUtil.get(activity, "style_data", "fontStyle");
		if(object != null)
		{
			String s = (String) object;
			if(s.equals("Perfetto"))
			{
				fontStyle = "fonts/Perfetto.ttf";
				typeface = Typeface.createFromAsset(activity.getAssets(), fontStyle);
			}
			else if(s.equals("Snickles"))
			{
				fontStyle = "fonts/Snickles.ttf";
				typeface = Typeface.createFromAsset(activity.getAssets(), fontStyle);
			}
		}
		for(TextView tv:textviewList)
			tv.setTypeface(typeface);
		for(Button bt:buttonList)
			bt.setTypeface(typeface);
	}
	
	public void setFontColor()
	{
		fontColor = "#000000";
		Object object = SharedPreferenceUtil.get(activity, "style_data", "fontColor");
		if(object != null)
		{
			String s = (String) object;
			if(s.equals("Blue"))
				fontColor = "#6CA6CD";
			else if(s.equals("Green"))
				fontColor = "#6E8B3D";
		}
		for(TextView tv:textviewList)
			tv.setTextColor(Color.parseColor(fontColor));
		for(Button bt:buttonList)
			bt.setTextColor(Color.parseColor(fontColor));
	}
	
	public void setBgStyle()
	{
		bgColor = "#FFFFFF";
		Object object = SharedPreferenceUtil.get(activity, "style_data", "bgColor");
		if(object != null)
		{
			String s = (String) object;
			if(s.equals("Blue"))
				bgColor = "#CAE1FF";
			else if(s.equals("Green"))
				bgColor = "#CAFF70";
		}
		layout.setBackgroundColor(Color.parseColor(bgColor));
	}
}
