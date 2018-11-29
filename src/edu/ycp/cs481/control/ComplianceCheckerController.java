package edu.ycp.cs481.control;

import edu.ycp.cs481.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.ArrayList;
import edu.ycp.cs481.control.UserController;
import edu.ycp.cs481.db.Database;
import edu.ycp.cs481.control.SOPController;
import edu.ycp.cs481.model.Position;
import edu.ycp.cs481.model.SOP;

public class ComplianceCheckerController {
	private Database db = new Database();
	private static User u; 
	private static Position p;
	private static PositionController pc = new PositionController(); 
	private static UserController uc = new UserController();
	private static SOPController sc = new SOPController(); 

	public ComplianceCheckerController() {

	}

	public void setAndShowToDoList(Position p) {
		List<SOP> displayList = p.getIncompleteSOPs();
		for(SOP s: displayList) {
			System.out.println(s.getID() + " | " + s.getTitle() + " | " + s.getDescription());
		}
	}

	public void setAndshowDoneList(Position p) {
		List<SOP> displayList = p.getCompletedSOPs();

		for(SOP s: displayList) {
			System.out.println(s.getID() + " | " + s.getTitle() + " | " + s.getDescription());
		}
	}

	public boolean checkIfToDoIsEmpty(Position p) {
		//set the initial condition to false and change it if need be
		boolean areGaps = false;

		if(p.getIncompleteSOPs().isEmpty()) {
			System.out.println("There is no incomplete SOPs");
			areGaps = false; 
		} else {
			areGaps = true;
			System.out.println("There are incomplete SOPs");
		}

		return areGaps;
	}

	//time to do the actual testing and checking of peoples training histories  
	public void SniffDeeply() {
		List<User> systemUsers = uc.searchForUsers(-1, -1, false, "", false, "", false, "", 0, -1);

		if(systemUsers.isEmpty()) {
			System.out.println("There was an error searching for users.");
		}
		else {
			for(User u: systemUsers) {
				Position p = u.getPosition();
				if(checkIfToDoIsEmpty(p)) {
					setAndshowDoneList(p);
				}
				else {
					Messenger.send(u.getEmail(), "Incomplete Training", u.getFirstName() + ", you have incomplete SOPs in your training"
							+ "	history, please complete these as soon as possible. A message will also be sent to your manager. Have a great day.");


					//Display the List
					setAndShowToDoList(p); 
					//send the users manager an email 
					//TODO: get the users manager 

				}

			}
		}
	}
}
