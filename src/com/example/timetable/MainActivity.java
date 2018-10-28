package com.example.timetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button bt_start = (Button)findViewById(R.id.bt_start);
		final Button bt_close = (Button)findViewById(R.id.bt_close);
		
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
