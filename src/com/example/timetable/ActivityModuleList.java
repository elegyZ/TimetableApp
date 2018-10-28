package com.example.timetable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class ActivityModuleList extends Activity 
{
	private List<String> moduleCodeList = new ArrayList<String>();
	private List<Module> moduleList = new ArrayList<Module>();
	private ModuleAdapter adapter;
	private ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		initModuleList();
		listview = (ListView)findViewById(R.id.lst_module);
		adapter = new ModuleAdapter(this,R.layout.list_item,moduleList);
		listview.setAdapter(adapter);
		Button bt_add = (Button)findViewById(R.id.bt_add);
		
		OnItemClickListener item_lst = new OnItemClickListener ()
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
						if(moduleName.equals(""))
							Toast.makeText(getBaseContext(), "Please Enter Module Name!", Toast.LENGTH_SHORT).show();
						if(dayOfWeek.equals(""))
							Toast.makeText(getBaseContext(), "Please Enter Day Of Week!", Toast.LENGTH_SHORT).show();
						if(startTime.equals("Start Time"))
							Toast.makeText(getBaseContext(), "Please Enter Start Time!", Toast.LENGTH_SHORT).show();
						if(endTime.equals("End Time"))
							Toast.makeText(getBaseContext(), "Please Enter End Time!", Toast.LENGTH_SHORT).show();
						if(location.equals(""))
							Toast.makeText(getBaseContext(), "Please Enter Class Location!", Toast.LENGTH_SHORT).show();
						else
						{
							Module module = new Module(moduleCode, moduleName, choice, dayOfWeek, startTime, endTime, location, info);
							addModule(module);
							dialog.dismiss();
							refresh();        //******
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
	
	protected void refresh() 
	{
		finish();
		onCreate(null); 
	}
	
	public void initModuleList()
	{
		//SharedPreferenceUtil.clear(ActivityModuleList.this, "module_data");
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
	
	public void deleteModule(Module module)
	{
		moduleCodeList.remove(module.getModuleCode());
		if(moduleCodeList.isEmpty())
			SharedPreferenceUtil.remove(ActivityModuleList.this, "module_data", "modulelist");
		else
			SharedPreferenceUtil.save(ActivityModuleList.this, "module_data", "modulelist", moduleCodeList);
		SharedPreferenceUtil.remove(ActivityModuleList.this, "module_data", module.getModuleCode());
	}
}
