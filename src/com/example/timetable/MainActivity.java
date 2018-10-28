package com.example.timetable;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static Typeface typePerfetto;
	private Button bt_start;
	private Button bt_close;
	private Spinner sp_fontChoice;
	private List<String> fontsList = new ArrayList<String>();
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bt_start = (Button)findViewById(R.id.bt_start);
		bt_close = (Button)findViewById(R.id.bt_close);
		sp_fontChoice = (Spinner)findViewById(R.id.sp_fontChoice);
		initList();
		adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, android.R.id.text1, fontsList);
		sp_fontChoice.setAdapter(adapter);
		sp_fontChoice.setSelection(0, true);
		
		OnItemSelectedListener lst_sp = new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
			{
				String font = (String)sp_fontChoice.getItemAtPosition(position);
				if(font.equals("Perfetto"))
				{
					//Toast.makeText(MainActivity.this, font, Toast.LENGTH_SHORT).show();
					typePerfetto = Typeface.createFromAsset(getAssets(), "Perfetto.ttf");
					
					//LayoutInflaterCompat.setFactory(LayoutInflater.from(MainActivity.this), new LayoutInflaterFactory() {});
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
			
		};
		sp_fontChoice.setOnItemSelectedListener(lst_sp);
		
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

	public void initList()
	{
		fontsList.add("Regular");
		fontsList.add("Perfetto");
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
