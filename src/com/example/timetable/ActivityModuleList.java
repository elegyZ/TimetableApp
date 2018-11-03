package com.example.timetable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class ActivityModuleList extends Activity 
{
	private RelativeLayout layout;
	private List<String> moduleCodeList = new ArrayList<String>();
	private List<Module> moduleList = new ArrayList<Module>();
	private ListView listview;
	ModuleAdapter adapter;
	private Button bt_add;
	private Button bt_switch;
	private Intent intent_setTime;
	private int flag;
	private List<Button> buttonList = new ArrayList<Button>();
	private String fontStyle;
	private String fontColor;
	private String bgColor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		initModuleList();
		layout = (RelativeLayout)findViewById(R.id.layout_list);
		listview = (ListView)findViewById(R.id.lst_module);
		adapter = new ModuleAdapter(this,R.layout.list_item,moduleList,ActivityModuleList.this);
		listview.setAdapter(adapter);
		bt_add = (Button)findViewById(R.id.bt_add);
		bt_switch = (Button)findViewById(R.id.bt_switch);
		intent_setTime = new Intent(ActivityModuleList.this,TimeNotificationService.class);
		flag = getFlag();
		if(flag % 2 == 1)
			bt_switch.setText("Notification Open");
		initButtonList();
		setStyle();
		
		OnClickListener lst_switch = new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				setFlag();
				if(flag % 2 == 1)
				{
					startService(intent_setTime);
					Toast.makeText(ActivityModuleList.this, "You have OPENED notifications which announce you that certain course is about to start.", Toast.LENGTH_SHORT).show();
					bt_switch.setText("Notification Open");
				}
				else
				{
					stopService(intent_setTime);
					Toast.makeText(ActivityModuleList.this, "You have CLOSED notifications", Toast.LENGTH_SHORT).show();
					bt_switch.setText("Notification Close");
				}
			}
		};
		bt_switch.setOnClickListener(lst_switch);
		
		OnItemClickListener item_lst = new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				String moduleCode = moduleCodeList.get(position);
				Intent modulePage = new Intent(ActivityModuleList.this, ActivityModule.class);
				modulePage.putExtra("moduleCode", moduleCode);
				startActivity(modulePage);
			}
			
		};
		listview.setOnItemClickListener(item_lst);

		OnClickListener lst_add = new OnClickListener() 
		{
			//after add should change the alarm intent************
			@Override
			public void onClick(View arg0) 
			{   
				AlertDialog.Builder builder = new AlertDialog.Builder(ActivityModuleList.this);
                builder.setIcon(R.drawable.add);
                builder.setTitle("Module Information");
                final View view = LayoutInflater.from(ActivityModuleList.this).inflate(R.layout.dialog_addmodule, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show(); 
                
                final EditText et_dialog_moduleCode = (EditText)view.findViewById(R.id.et_dialog_moduleCode);
                final EditText et_dialog_moduleName = (EditText)view.findViewById(R.id.et_dialog_moduleName);
                final RadioGroup radioGroup1 = (RadioGroup)view.findViewById(R.id.radioGroup1);
                final Spinner sp_weekdays = (Spinner)view.findViewById(R.id.sp_weekdays);
                final EditText et_dialog_location = (EditText)view.findViewById(R.id.et_dialog_location);
                final EditText et_dialog_info = (EditText)view.findViewById(R.id.et_dialog_info);
                final Button bt_startTime = (Button)view.findViewById(R.id.bt_startTime);
                final Button bt_endTime = (Button)view.findViewById(R.id.bt_endTime);
                Button bt_ok = (Button)view.findViewById(R.id.bt_ok);
                
                OnClickListener lst_startTime = new OnClickListener()
                {
					@Override
					public void onClick(View arg0) 
					{	
		                new TimePickerDialog(ActivityModuleList.this,
		                		TimePickerDialog.THEME_HOLO_LIGHT,
		                        new TimePickerDialog.OnTimeSetListener() {
		                            @Override
		                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		                            	String H = new DecimalFormat("00").format(hourOfDay);
		                            	String M = new DecimalFormat("00").format(minute);
		                            	bt_startTime.setText(H + ":" + M);
		                            }
		                        },
		                        0, 0, true).show();
					}
                };                
                OnClickListener lst_endTime = new OnClickListener()
                {
					@Override
					public void onClick(View arg0) 
					{
						new TimePickerDialog(ActivityModuleList.this,
		                		TimePickerDialog.THEME_HOLO_LIGHT,
		                        new TimePickerDialog.OnTimeSetListener() {
		                            @Override
		                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		                            	String H = new DecimalFormat("00").format(hourOfDay);
		                            	String M = new DecimalFormat("00").format(minute);
		                            	bt_endTime.setText(H + ":" + M);
		                            }
		                        },
		                        0, 0, true).show();
					}
                };                
                OnClickListener lst_ok = new OnClickListener()
                {
					@Override
					public void onClick(View arg0) 
					{
						String moduleCode = et_dialog_moduleCode.getText().toString();
						String moduleName = et_dialog_moduleName.getText().toString();
						String choice = (radioGroup1.getCheckedRadioButtonId() == R.id.rbt_lecture) ? "Lecture" : "Practical";
						String dayOfWeek = sp_weekdays.getSelectedItem().toString();
						String startTime = bt_startTime.getText().toString();
						String endTime = bt_endTime.getText().toString();
						String location = et_dialog_location.getText().toString();
						String info = et_dialog_info.getText().toString();
						if(moduleCode.equals(""))
							Toast.makeText(getBaseContext(), "Please Enter Module Code!", Toast.LENGTH_SHORT).show();
						else if(moduleName.equals(""))
							Toast.makeText(getBaseContext(), "Please Enter Module Name!", Toast.LENGTH_SHORT).show();
						else if(dayOfWeek.equals(""))
							Toast.makeText(getBaseContext(), "Please Enter Day Of Week!", Toast.LENGTH_SHORT).show();
						else if(startTime.equals("Start Time"))
							Toast.makeText(getBaseContext(), "Please Enter Start Time!", Toast.LENGTH_SHORT).show();
						else if(endTime.equals("End Time"))
							Toast.makeText(getBaseContext(), "Please Enter End Time!", Toast.LENGTH_SHORT).show();
						else if(location.equals(""))
							Toast.makeText(getBaseContext(), "Please Enter Class Location!", Toast.LENGTH_SHORT).show();
						else
						{
							Module module = new Module(moduleCode, moduleName, choice, dayOfWeek, startTime, endTime, location, info);
							addModule(module);
							dialog.dismiss();
							initModuleList();
							adapter.notifyDataSetChanged();
						}
					}
                };
                bt_startTime.setOnClickListener(lst_startTime);
                bt_endTime.setOnClickListener(lst_endTime);
                bt_ok.setOnClickListener(lst_ok);
			}
			
		};
		bt_add.setOnClickListener(lst_add);
	}
	
	public void initModuleList()
	{
		moduleCodeList.clear();
		moduleList.clear();
		Object object = SharedPreferenceUtil.get(ActivityModuleList.this, "module_data", "modulelist");
		if(object != null)
		{	
			moduleCodeList = (List<String>) object;
			for(String code:moduleCodeList)
			{
				Object item = SharedPreferenceUtil.get(ActivityModuleList.this, "module_data", code);
				if(item != null)
				{
					Module module = (Module)item;
					moduleList.add(module);
				}
			}
		}
	}
	
	public void addModule(Module module)
	{
		moduleCodeList.add(module.getModuleCode());
		SharedPreferenceUtil.save(ActivityModuleList.this, "module_data", "modulelist", moduleCodeList);
		SharedPreferenceUtil.save(ActivityModuleList.this, "module_data", module.getModuleCode(), module);
	}
	
	public void freshModule(Module module)
	{
		SharedPreferenceUtil.save(ActivityModuleList.this, "module_data", module.getModuleCode(), module);
	}
	
	public void deleteModule(Module module)
	{
		moduleCodeList.remove(module.getModuleCode());
		SharedPreferenceUtil.save(ActivityModuleList.this, "module_data", "modulelist", moduleCodeList);
		SharedPreferenceUtil.remove(ActivityModuleList.this, "module_data", module.getModuleCode());
	}
	
	public int getFlag()
	{
		SharedPreferences sharedPreferences = getSharedPreferences("time_data",0);
		return sharedPreferences.getInt("flag", 0);
	}
	
	public void setFlag()
	{
		SharedPreferences sharedPreferences = getSharedPreferences("time_data",0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt("flag", flag += 1);
		editor.apply();
	}

	public void initButtonList()
	{
		buttonList.add(bt_add);
		buttonList.add(bt_switch);
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
		Object object = SharedPreferenceUtil.get(ActivityModuleList.this, "style_data", "fontStyle");
		if(object != null)
		{
			String s = (String) object;
			if(s.equals("Perfetto"))
			{
				fontStyle = "fonts/Perfetto.ttf";
				typeface = Typeface.createFromAsset(getAssets(), fontStyle);
			}
			else if(s.equals("Snickles"))
			{
				fontStyle = "fonts/Snickles.ttf";
				typeface = Typeface.createFromAsset(getAssets(), fontStyle);
			}
		}
		for(Button bt:buttonList)
			bt.setTypeface(typeface);
	}
	
	public void setFontColor()
	{
		fontColor = "#000000";
		Object object = SharedPreferenceUtil.get(ActivityModuleList.this, "style_data", "fontColor");
		if(object != null)
		{
			String s = (String) object;
			if(s.equals("Blue"))
				fontColor = "#6CA6CD";
			else if(s.equals("Green"))
				fontColor = "#6E8B3D";
		}
		for(Button bt:buttonList)
			bt.setTextColor(Color.parseColor(fontColor));
	}
	
	public void setBgStyle()
	{
		bgColor = "#FFFFFF";
		Object object = SharedPreferenceUtil.get(ActivityModuleList.this, "style_data", "bgColor");
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
