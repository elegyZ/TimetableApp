package com.example.timetable;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public RelativeLayout layout;
	private Button bt_start;
	private Button bt_close;
	private Spinner sp_fontChoice;
	private Spinner sp_bgcolorChoice;
	private Spinner sp_fontcolorChoice;
	private List<TextView> textviewList = new ArrayList<TextView>();
	private List<Button> buttonList = new ArrayList<Button>();
	private String fontStyle;
	private String fontColor;
	private String bgColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout = (RelativeLayout)findViewById(R.id.layout_main);
		bt_start = (Button)findViewById(R.id.bt_start);
		bt_close = (Button)findViewById(R.id.bt_close);
		sp_fontChoice = (Spinner)findViewById(R.id.sp_fontChoice);
		sp_fontcolorChoice = (Spinner)findViewById(R.id.sp_fontcolorChoice);
		sp_bgcolorChoice = (Spinner)findViewById(R.id.sp_bgcolorChoice);
		initTextViewList();
		initButtonList();
		setStyle();
		
		OnItemSelectedListener lst_font = new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
			{
				fontStyle = sp_fontChoice.getItemAtPosition(position).toString();
				SharedPreferenceUtil.save(MainActivity.this, "style_data", "fontStyle", fontStyle);
				setFontStyle();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Toast.makeText(MainActivity.this, "nothing", Toast.LENGTH_SHORT).show();
			}
		};
		sp_fontChoice.setOnItemSelectedListener(lst_font);
		
		OnItemSelectedListener lst_fontColor = new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
			{
				fontColor = sp_fontcolorChoice.getItemAtPosition(position).toString();
				SharedPreferenceUtil.save(MainActivity.this, "style_data", "fontColor", fontColor);
				setFontColor();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Toast.makeText(MainActivity.this, "nothing", Toast.LENGTH_SHORT).show();
			}
		};
		sp_fontcolorChoice.setOnItemSelectedListener(lst_fontColor);
		
		OnItemSelectedListener lst_bgColor = new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
			{
				bgColor = (String)sp_bgcolorChoice.getItemAtPosition(position);
				SharedPreferenceUtil.save(MainActivity.this, "style_data", "bgColor", bgColor);
				setBgStyle();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Toast.makeText(MainActivity.this, "nothing", Toast.LENGTH_SHORT).show();
			}
		};
		sp_bgcolorChoice.setOnItemSelectedListener(lst_bgColor);
		
		OnClickListener lst_start = new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(MainActivity.this,ActivityModuleList.class);
				startActivity(intent);
			}
		};
		
		OnClickListener lst_close = new OnClickListener()
		{
			@Override
			public void onClick(View arg0) {
				finish();
			}
		};
		bt_start.setOnClickListener(lst_start);
		bt_close.setOnClickListener(lst_close);
	}
	
	public void initButtonList()
	{
		buttonList.add(bt_start);
		buttonList.add(bt_close);
	}
	
	public void initTextViewList()
	{
		textviewList.add((TextView)findViewById(R.id.tv_fontChoice));
		textviewList.add((TextView)findViewById(R.id.tv_fontcolorChoice));
		textviewList.add((TextView)findViewById(R.id.tv_bgcolorChoice));
	}
	
	public void setStyle()
	{
		setFontStyle();
		setFontColor();
		setBgStyle();
	}
	
	public void setFontStyle()
	{
		int position = 0;
		Typeface typeface = Typeface.DEFAULT;
		Object object = SharedPreferenceUtil.get(MainActivity.this, "style_data", "fontStyle");
		if(object != null)
		{
			String s = (String) object;
			if(s.equals("Perfetto"))
			{
				fontStyle = "fonts/Perfetto.ttf";
				typeface = Typeface.createFromAsset(getAssets(), fontStyle);
				position = 1;
			}
			else if(s.equals("Snickles"))
			{
				fontStyle = "fonts/Snickles.ttf";
				typeface = Typeface.createFromAsset(getAssets(), fontStyle);
				position = 2;
			}
		}
		for(TextView tv:textviewList)
			tv.setTypeface(typeface);
		for(Button bt:buttonList)
			bt.setTypeface(typeface);
		sp_fontChoice.setSelection(position, true);
	}
	
	public void setFontColor()
	{
		int position = 0;
		fontColor = "#000000";
		Object object = SharedPreferenceUtil.get(MainActivity.this, "style_data", "fontColor");
		if(object != null)
		{
			String s = (String) object;
			if(s.equals("Blue"))
			{
				fontColor = "#6CA6CD";
				position = 1;
			}
			else if(s.equals("Green"))
			{
				fontColor = "#6E8B3D";
				position = 2;
			}
		}
		for(TextView tv:textviewList)
			tv.setTextColor(Color.parseColor(fontColor));
		for(Button bt:buttonList)
			bt.setTextColor(Color.parseColor(fontColor));
		sp_fontcolorChoice.setSelection(position, true);
	}
	
	public void setBgStyle()
	{
		int position = 0;
		bgColor = "#FFFFFF";
		Object object = SharedPreferenceUtil.get(MainActivity.this, "style_data", "bgColor");
		if(object != null)
		{
			String s = (String) object;
			if(s.equals("Blue"))
			{
				bgColor = "#CAE1FF";
				position = 1;
			}
			else if(s.equals("Green"))
			{
				bgColor = "#CAFF70";
				position = 2;
			}
		}
		layout.setBackgroundColor(Color.parseColor(bgColor));
		sp_bgcolorChoice.setSelection(position, true);
	}
	
	public void fresh()
	{
		Intent intent=new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();//close
		overridePendingTransition(0, 0);
		Toast.makeText(getBaseContext(), "fresh", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
