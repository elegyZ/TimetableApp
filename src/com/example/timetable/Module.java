package com.example.timetable;

import java.io.Serializable;

public class Module implements Serializable{

	private static final long serialVersionUID = 1L;
	private String moduleCode;
	private String moduleName;
	private String choice;
	private String dayOfWeek;
	private String startTime;
	private String endTime;
	private String location;
	private String info;
	private Integer alarm;
	
	public Module(String moduleCode,String moduleName, String choice, String dayOfWeek, String startTime, String endTime, String location, String info)
	{
		this.moduleCode = moduleCode;
		this.moduleName = moduleName;
		this.choice = choice;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
		this.info = info;
		this.alarm = null;
	}
	
	public String getModuleCode() {
		return moduleCode;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public String getChoice() {
		return choice;
	}
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setAlarm(Integer alarm)
	{
		this.alarm = alarm;
	}
	
	public Integer getAlarm()
	{
		return alarm;
	}
}


/**Module Code
Full Module Name
Lecture/Practical choice
Day of Week
Time - start time and end time
Location (room #)
Additional info (comments)*/