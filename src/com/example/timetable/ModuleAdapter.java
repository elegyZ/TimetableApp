package com.example.timetable;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ModuleAdapter extends ArrayAdapter<Module> 
{
	private final int resourceID;
	
	public ModuleAdapter(Context context,int textViewResourceID,List<Module> objects)
	{
		super(context,textViewResourceID,objects);
		resourceID = textViewResourceID;
	}
	
	@Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
		Module module = (Module)getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceID, null);
		TextView tv_lst_moduleCode = (TextView)view.findViewById(R.id.tv_lst_moduleCode);
		TextView tv_lst_choice = (TextView)view.findViewById(R.id.tv_lst_choice);
		TextView tv_lst_dayOfWeek = (TextView)view.findViewById(R.id.tv_lst_dayOfWeek);
		TextView tv_lst_startTime = (TextView)view.findViewById(R.id.tv_lst_startTime);
		TextView tv_lst_location = (TextView)view.findViewById(R.id.tv_lst_location);
		ImageView iv_delete = (ImageView)view.findViewById(R.id.iv_delete);
		tv_lst_moduleCode.setText(module.getModuleCode());
		tv_lst_choice.setText(String.valueOf(module.getChoice().charAt(0)));
		tv_lst_dayOfWeek.setText(module.getDayOfWeek().substring(0, 2));
		tv_lst_startTime.setText(module.getStartTime());
		tv_lst_location.setText(module.getLocation());
		OnClickListener lst_delete = new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				Toast.makeText(getContext(), "delete", Toast.LENGTH_SHORT).show();
			}
		};
		iv_delete.setOnClickListener(lst_delete);
		return view;
	}
}
