package edu.ycp.cs481.control;

import edu.ycp.cs481.model.TrainingHistory;
import edu.ycp.cs481.model.User;

import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import edu.ycp.cs481.db.Database;
import edu.ycp.cs481.model.Position;
import edu.ycp.cs481.model.SOP;

public class SystemSnifferController {
	private List<SOP> reqs;

	//private User u; 
	//private Position p;
	private Database db = new Database(); 
	private TrainingHistory t; 
	
	public SystemSnifferController() {
	}
	
	public void initHistory(User u) {
		t = u.getHistory();
	}
	
	//the DB calls the training histories will need
	public void PopulateHistoryThroughUser(User u) {
		t.addAndSortCollection(db.findSOPsByPosition(u.getPosition().getID()));
	}
	
	public void PopulateHistoryThroughPosition(int pos_id) {
		t.addAndSortCollection(db.findSOPsByPosition(pos_id));
	}
	
	public void sortListByPriority(int priority) {
		PriorityQueue q = t.getSopsToDo();
		
		
	}
}