package com.example.timetable;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ActivityModule extends Activity 
{
	private RelativeLayout layout;
	private ScrollView scroll;
	private TextView tv_module_moduleCodeContent;
	private TextView tv_module_moduleNameContent;
	private TextView tv_module_choiceContent;
	private TextView tv_module_dayOfWeekContent;
	private TextView tv_module_startTimeContent;
	private TextView tv_module_endTimeContent;
	private TextView tv_module_locationContent;
	private TextView tv_module_infoContent;
	private Button bt_back;
	private List<TextView> textviewList = new ArrayList<TextView>();
	private List<Button> buttonList = new ArrayList<Button>();
	private String fontStyle;
	private String fontColor;
	private String bgColor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_module);
		Bundle extras = getIntent().getExtras();
		String moduleCode = extras.getString("moduleCode");
		Module module;
		
		layout = (RelativeLayout)this.findViewById(R.id.layout_module);
		scroll = (ScrollView)this.findViewById(R.id.scroll_module);
		tv_module_moduleCodeContent = (TextView)this.findViewById(R.id.tv_module_moduleCodeContent);
		tv_module_moduleNameContent = (TextView)this.findViewById(R.id.tv_module_moduleNameContent);
		tv_module_choiceContent = (TextView)this.findViewById(R.id.tv_module_choiceContent);
		tv_module_dayOfWeekContent = (TextView)this.findViewById(R.id.tv_module_dayOfWeekContent);
		tv_module_startTimeContent = (TextView)this.findViewById(R.id.tv_module_startTimeContent);
		tv_module_endTimeContent = (TextView)this.findViewById(R.id.tv_module_endTimeContent);
		tv_module_locationContent = (TextView)this.findViewById(R.id.tv_module_locationContent);
		tv_module_infoContent = (TextView)this.findViewById(R.id.tv_module_infoContent);
		bt_back = (Button)findViewById(R.id.bt_back);
		initButtonList();
		initTextViewList();
		setStyle();
		
		Object item = SharedPreferenceUtil.get(ActivityModule.this, "module_data", moduleCode);
		if(item != null)
		{
			module = (Module)item;
			tv_module_moduleCodeContent.setText(module.getModuleCode());
			tv_module_moduleNameContent.setText(module.getModuleName());
			tv_module_choiceContent.setText("+" + module.getChoice() + "+");
			tv_module_dayOfWeekContent.setText(module.getDayOfWeek());
			tv_module_startTimeContent.setText(module.getStartTime());
			tv_module_endTimeContent.setText(module.getEndTime());
			tv_module_locationContent.setText(module.getLocation());
			tv_module_infoContent.setText(module.getInfo());
		}
		
		OnClickListener lst_back = new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) {
				finish();
			}
		};
		bt_back.setOnClickListener(lst_back);
	}
	
	public void initButtonList()
	{
		buttonList.add(bt_back);
	}
	
	public void initTextViewList()
	{
		textviewList.add(tv_module_moduleCodeContent);
		textviewList.add(tv_module_moduleNameContent);
		textviewList.add(tv_module_choiceContent);
		textviewList.add(tv_module_dayOfWeekContent);
		textviewList.add(tv_module_startTimeContent);
		textviewList.add(tv_module_endTimeContent);
		textviewList.add(tv_module_locationContent);
		textviewList.add(tv_module_infoContent);
		textviewList.add((TextView)this.findViewById(R.id.tv_module_moduleCode));
		textviewList.add((TextView)this.findViewById(R.id.tv_module_moduleName));
		textviewList.add((TextView)this.findViewById(R.id.tv_module_dayOfWeek));
		textviewList.add((TextView)this.findViewById(R.id.tv_module_startTime));
		textviewList.add((TextView)this.findViewById(R.id.tv_module_endTime));
		textviewList.add((TextView)this.findViewById(R.id.tv_module_location));
		textviewList.add((TextView)this.findViewById(R.id.tv_module_info));
	}
	
	public void setStyle()
	{
		setFontStyle();
		setfontColor();
		setBgStyle();
	}
	
	public void setFontStyle()
	{
		Typeface typeface = Typeface.DEFAULT;
		Object object = SharedPreferenceUtil.get(ActivityModule.this, "style_data", "fontStyle");
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
		for(TextView tv:textviewList)
			tv.setTypeface(typeface);
		for(Button bt:buttonList)
			bt.setTypeface(typeface);
	}
	
	public void setfontColor()
	{
		fontColor = "#000000";
		Object object = SharedPreferenceUtil.get(ActivityModule.this, "style_data", "fontColor");
		if(object != null)
		{
			String s = (String) object;
			if(s.equals("Blue"))
			{
				fontColor = "#6CA6CD";
			}
			else if(s.equals("Green"))
			{
				fontColor = "#6E8B3D";
			}
		}
		for(TextView tv:textviewList)
			tv.setTextColor(Color.parseColor(fontColor));
		for(Button bt:buttonList)
			bt.setTextColor(Color.parseColor(fontColor));
	}
	
	public void setBgStyle()
	{
		bgColor = "#FFFFFF";
		Object object = SharedPreferenceUtil.get(ActivityModule.this, "style_data", "bgColor");
		if(object != null)
		{
			String s = (String) object;
			if(s.equals("Blue"))
			{
				bgColor = "#CAE1FF";
			}
			else if(s.equals("Green"))
			{
				bgColor = "#CAFF70";
			}
		}
		layout.setBackgroundColor(Color.parseColor(bgColor));
		scroll.setBackgroundColor(Color.parseColor(bgColor));
	}
}
