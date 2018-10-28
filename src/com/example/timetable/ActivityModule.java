package com.example.timetable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityModule extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_module);
		Bundle extras = getIntent().getExtras();
		String moduleCode = extras.getString("moduleCode");
		Module module;
		
		TextView tv_module_moduleCodeContent = (TextView)this.findViewById(R.id.tv_module_moduleCodeContent);
		TextView tv_module_moduleNameContent = (TextView)this.findViewById(R.id.tv_module_moduleNameContent);
		TextView tv_module_choiceContent = (TextView)this.findViewById(R.id.tv_module_choiceContent);
		TextView tv_module_dayOfWeekContent = (TextView)this.findViewById(R.id.tv_module_dayOfWeekContent);
		TextView tv_module_startTimeContent = (TextView)this.findViewById(R.id.tv_module_startTimeContent);
		TextView tv_module_endTimeContent = (TextView)this.findViewById(R.id.tv_module_endTimeContent);
		TextView tv_module_locationContent = (TextView)this.findViewById(R.id.tv_module_locationContent);
		TextView tv_module_infoContent = (TextView)this.findViewById(R.id.tv_module_infoContent);
		
		Object item = SharedPreferenceUtil.get(ActivityModule.this, "module_data", moduleCode);
		if(item != null)
		{
			module = (Module)item;
			tv_module_moduleCodeContent.setText(module.getModuleCode());
			tv_module_moduleNameContent.setText(module.getModuleName());
			tv_module_choiceContent.setText("#" + module.getChoice() + "#");
			tv_module_dayOfWeekContent.setText(module.getDayOfWeek());
			tv_module_startTimeContent.setText(module.getStartTime());
			tv_module_endTimeContent.setText(module.getEndTime());
			tv_module_locationContent.setText(module.getLocation());
			tv_module_infoContent.setText(module.getInfo());
		}
		
		Button bt_back = (Button)findViewById(R.id.bt_back);
		OnClickListener lst_back = new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) {
				finish();
			}
		};
		bt_back.setOnClickListener(lst_back);
	}
}
