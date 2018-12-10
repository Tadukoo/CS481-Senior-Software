package edu.ycp.cs481.control;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ycp.cs481.db.DBFormat;
import edu.ycp.cs481.db.Database;
import edu.ycp.cs481.model.SOP;

public class SOPController{
	private Database db = new Database();

	public Integer insertSOP(String title, String description, int priority, int version, int authorID,  boolean isArchived){
		return db.insertAndGetID("SOP", "sop_id", 
				new String[]{"title", "description", "priority", "version", "author_id", "archive_flag"}, 
				new String[]{title, description, String.valueOf(priority), String.valueOf(version), 
						String.valueOf(authorID), String.valueOf(isArchived)});
	}
	
	public ArrayList<SOP> searchForSOPs(int sopID, boolean titlePartial, String title, boolean descPartial, 
			String description, int priority, int version, int authorID, int userID, int posID){
		try{
			ArrayList<String> otherTables = new ArrayList<String>();
			ArrayList<String> junctions = new ArrayList<String>();
			if(userID != -1){
				otherTables.add("PositionSOP");
				otherTables.add("UserSOP");
				otherTables.add("User");
				junctions.add("((PositionSOP.position_id = User.position_id and PositionSOP.sop_id = SOP.sop_id) or "
						+ "(UserSOP.user_id = User.user_id and UserSOP.sop_id = SOP.sop_id))");
			}
			if(posID != -1){
				otherTables.add("PositionSOP");
				junctions.add("PositionSOP.sop_id = SOP.sop_id");
			}
			ArrayList<SOP> results = db.doSearch(DBFormat.getSopResFormat(), "SOP", otherTables, junctions, 
					new String[]{"sop_id", "priority", "version", "author_id", "User.user_id", "position_id"}, 
					new int[]{sopID, priority, version, authorID, userID, posID}, 
					new boolean[]{titlePartial, descPartial}, 
					new String[]{"title", "description"}, 
					new String[]{title, description});
			if(sopID != -1){
				if(results.size() == 0){
					System.out.println("No SOP found with ID " + sopID);
				}else if(results.size() > 1){
					System.out.println("Multiple SOPs found with ID " + sopID + "! Returning null");
					return null;
				}
			}
			return results;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public void archiveSOP(int sopID){
		db.executeUpdate("Archive SOP with ID " + sopID, "update SOP set archive_flag = true where sop_id = " + sopID);
	}

	public void unarchiveSOP(int sopID){
		db.executeUpdate("Unarchive SOP with ID " + sopID, "update SOP set archive_flag = false where sop_id = " + sopID);
	}
	
	public void reversionSOP(int sopID, int version){
		SOP s = searchForSOPs(sopID, false, null, false, null, -1, -1, -1, -1, -1).get(0);
		
		archiveSOP(sopID);
		insertSOP(s.getTitle(), s.getDescription(), s.getPriority(), version, s.getAuthorID(), false);
	}
	
	public void changeTitle(int id, String newTitle){
		db.executeUpdate("Change SOP with id " + id + " to Title " + newTitle, "update SOP set title = '" + newTitle +
				"' where sop_id = " + id);
	}
	
	public void changeDescription(int id, String newDesc){
		db.executeUpdate("Change SOP with id " + id + " to Description " + newDesc, "update SOP set description = '" + newDesc +
				"' where sop_id = " + id);
	}
	
	public void changePriority(int id, int priority){
		db.executeUpdate("Change SOP with id " + id + " to Priority " + priority, "update SOP set priority = " + priority +
				" where sop_id = " + id);
	}
	
	public void changeVersion(int id, int newVersion){
		db.executeUpdate("Change SOP with id " + id + " to Version " + newVersion, "update SOP set version = " + newVersion +
				" where sop_id = " + id);
	}
	
	public void insertCompletedSOP(int user_id, int sop_id){
		db.insert("CompletedSOP", new String[] {"user_id", "sop_id"},
				new String[] {String.valueOf(user_id), String.valueOf(sop_id)});
	}
}