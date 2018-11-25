package edu.ycp.cs481.model;

import java.util.ArrayList;

public class Position{
	private int ID;
	private String title, description;
	private int priority;
	private ArrayList<SOP> requirements;
	
	public int getID(){
		return ID;
	}

	public void setID(int ID){
		this.ID = ID;
	}

	public String getTitle(){
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getDescription(){
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}
	
	public int getPriority(){
		return priority;
	}

	public void setPriority(int priority){
		this.priority = priority;
	}
	
	public ArrayList<SOP> getRequirements(){
		return requirements;
	}

	public void setRequirements(ArrayList<SOP> requirements){
		this.requirements = requirements;
	}

	public ArrayList<SOP> getCompletedSOPs(){
		ArrayList<SOP> completedSOPs = new ArrayList<SOP>();
		for(SOP s: requirements){
			if(s.isComplete()){
				completedSOPs.add(s);
			}
		}

		return completedSOPs;
	}

	public ArrayList<SOP> getIncompleteSOPs(){
		ArrayList<SOP> IncompleteSOPs = new ArrayList<SOP>();
		for(SOP s: requirements){
			if(!s.isComplete()){
				IncompleteSOPs.add(s);
			}
		}

		return IncompleteSOPs;
	}
}
