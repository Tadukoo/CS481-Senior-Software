package edu.ycp.cs481.control;

import edu.ycp.cs481.db.DBFormat;
import edu.ycp.cs481.db.Database;
import edu.ycp.cs481.model.EnumPermission;
import edu.ycp.cs481.model.User;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.*;

public class UserController{
	private Database db = new Database();
	
	public String hashPassword(String password){
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public boolean authenticate(String password, String hashedPass){
		return BCrypt.checkpw(password, hashedPass);
	}

	public Integer insertUser(boolean passNeedsHashed, String email, String password, String firstName, String lastName, 
			boolean lockedOut, boolean isArchived, int positionID){
		if(passNeedsHashed){
			password = hashPassword(password);
		}
		
		int defaultRole = 0;
		
		try {
			defaultRole = db.executeQuery("Checking Quarantine User doesn't exist", 
					"select default_role from Position where position_id = " + positionID, DBFormat.getIntResFormat()).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return db.insertAndGetID("User", "user_id",
				new String[]{"email", "password", "first_name", "last_name", "locked_out", "archive_flag",
						"position_id", "role_id"},
				new String[]{email, password, firstName, lastName, String.valueOf(lockedOut), String.valueOf(isArchived),
						String.valueOf(positionID), String.valueOf(defaultRole)});
	}
	
	public boolean findQuarantineUser(String email){
		try{
			return db.executeQuery("Checking Quarantine User doesn't exist", 
					"select * from Quarantine where email = '" + email + "'", DBFormat.getCheckResFormat());
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public void insertQuarantineUser(String email, String password, String firstName, String lastName) {
	    String verificationString = generateString();
	    
		// Hash the password. We assure this is only called once by only hashing the password
		// in insertUser if it's being called with a positionID different than 2
		password = hashPassword(password);
		
		// Verify user doesn't exist in the table
		if(findQuarantineUser(email)){
			retrySendEmail(email);
		} else {
			db.insert("Quarantine", 
					new String[] {"email", "password", "first_name", "last_name", "verification"}, 
					new String[] {email, password, firstName, lastName, hashPassword(verificationString)});
			
			// Send email with messenger
			Messenger.send(email, "CTM Verification Pin", "Please visit the following URL to verify your account: <br><br>"
					+ "<a href=\"http://localhost:8081/CS481-Senior-Software/verify_email?"
					+ "email=" + email
					+ "&token=" + verificationString 
					+ "\">Verify Email</a>");
		}
	}
	
	public void deleteQuarantineUser(String email) {
		db.executeUpdate("Deleting Quarantine User", "delete from Quarantine where email = '" + email + "'");
	}
	
	public String retrySendEmail(String email) {
		// Generate a new token
		String token = generateString();

		// Update Quarantine entry to use new token
		db.executeUpdate("Updating Quarantine User", "update Quarantine set verification = '" + hashPassword(token) + "' where email = '" + email + "'");
		
		// Send email with messenger
		Messenger.send(email, "CTM Verification Pin", "Please visit the following URL to verify your account: <br><br>"
				+ "<a href=\"http://localhost:8081/CS481-Senior-Software/verify_email?"
				+ "email=" + email
				+ "&token=" + token 
				+ "\">Verify Email</a>");
		
		// Return verificationString
		return token;
	}
	
	public boolean verifyEmail(String email, String verificationString) {
		boolean verify = false;
		String hashedVerifString = null;
		User user = null;

		System.out.println(email);
		try{
			String name = "Verifying User";
			String sql = "select verification from Quarantine where email = '" + email + "'";
			hashedVerifString = db.executeQuery(name, sql, DBFormat.getStringResFormat()).get(0);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		verify = BCrypt.checkpw(verificationString, hashedVerifString);
		
		if(verify) {
			// Move information from Quarantine -> User
			try {
				String name = "Migrating to User table";
				String sql = "select " + DBFormat.getQuarantinePieces() + " from Quarantine where email = '" + email + "'";
				ArrayList<User> userSearch = db.executeQuery(name, sql, DBFormat.getQuarantineResFormat());
				user = userSearch.get(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// Delete entry in Quarantine
			db.executeUpdate("Deleting Quarantine User", "delete from Quarantine where email = '" + email + "'");
			
			insertUser(false, user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), false, false, 2);
		} 
		
		return verify;
	}

	public ArrayList<User> searchForUsers(int userID, int employeeID, boolean emailPartial, String email, 
			boolean firstNamePartial, String firstName, boolean lastNamePartial, String lastName, int positionID,
			int managerID){
		try{
			ArrayList<String> otherTables = new ArrayList<String>();
			ArrayList<String> junctions = new ArrayList<String>();
			if(managerID != -1){
				otherTables.add("Subordinate");
				junctions.add("Subordinate.subordinate_id = User.user_id");
			}
			ArrayList<User> results = db.doSearch(DBFormat.getUserResFormat(), "User", otherTables, junctions, 
					new String[]{"user_id", "employee_id", "position_id", "manager_id"}, 
					new int[]{userID, employeeID, positionID, managerID}, 
					new boolean[]{emailPartial, firstNamePartial, lastNamePartial}, 
					new String[]{"email", "first_name", "last_name"}, 
					new String[]{email, firstName, lastName});
			if(results.size() == 0 && userID != -1){
				System.out.println("No User found with ID " + userID);
			}else if(results.size() > 1){
				if(userID != -1){
					System.out.println("Multiple Users found with ID " + userID + "! Returning null");
					return null;
				}else if(!emailPartial && (email != null && !email.equalsIgnoreCase(""))){
					System.out.println("Multiple Users found with email " + email + "! Returning null");
					return null;
				}
			}
			return results;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void changeFirstName(int userID, String newFirstName){
		db.executeUpdate("Change User First Name", "update User set first_name = '" + newFirstName + "' where user_id = "
				+ userID);
	}
	
	public void changeLastName(int userID, String newLastName){
		db.executeUpdate("Change User Last Name", "update User set last_name = '" + newLastName + "' where user_id = "
				+ userID);
	}

	public void changeEmail(int userID, String newEmail){
		db.executeUpdate("Change User Email", "update User set email = '" + newEmail + "' where user_id = " + userID);
	}

	public void changePassword(int userID, String newPass){
		db.executeUpdate("Change User Password",
				"update User set password = '" + hashPassword(newPass) + "' where " + "user_id = " + userID);
	}
	
	public void resetPassword(String email, String newPass){
		db.executeUpdate("Delete ResetPassword entry", "delete from ResetPassword where email = '" + email + "'");
		db.executeUpdate("Change User Password",
				"update User set password = '" + hashPassword(newPass) + "' where " + "email = '" + email + "'");
	}
	
	public String generateString() {
		int leftLimit = 65;
		int rightLimit = 90;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(20);
		for (int i = 0; i < 20; i++) {
		    int randomLimitedInt = leftLimit + (int) 
		      (random.nextFloat() * (rightLimit - leftLimit + 1));
		    buffer.append((char) randomLimitedInt);
		}
		String password = buffer.toString();
		return password;
	}
	
	public void resetPasswordEmail(String email) {
		String token = generateString();
		
		// Fresh insert
		if(!duplicateResetRequest(email)) {
			// Send out email with token
			Messenger.send(email, "CTM Password Reset", "Please visit the following URL to reset your password: <br><br> "
					+ "<a href=\"http://localhost:8081/CS481-Senior-Software/reset_password?"
					+ "email=" + email
					+ "&token=" + token 
					+ "\">Reset Password</a>");
			
			// Insert user into the ResetPassword table and generate their pin
			db.insert("ResetPassword", new String[] {"email", "verification"}, 
					new String[] {email, hashPassword(token)});
		} else {
			// Send out email with a new token
			Messenger.send(email, "CTM Password Reset", "Please visit the following URL to reset your password: <br><br> "
					+ "<a href=\"http://localhost:8081/CS481-Senior-Software/reset_password?"
					+ "email=" + email
					+ "&token=" + token 
					+ "\">Reset Password</a>");
			
			// Update ResetPassword with new token
			db.executeUpdate("Generate new ResetPassword token", "update ResetPassword set verification = '" 
					+ hashPassword(token) + "' where email = '" + email + "'");
		}
	}
	
	// Called when there's already a request in the ResetPassword table
	public boolean duplicateResetRequest(String email) {
		boolean duplicate = false;
		
		try {
			String name = "Check if User already requested a reset";
			String sql = "select * from ResetPassword where email = '" + email + "'";
			duplicate = db.executeQuery(name, sql, DBFormat.getCheckResFormat());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return duplicate;
	}
	
	public boolean verifyResetPasswordToken(String email, String token) {
		String hashedToken = "";
		
		try {
			String name = "Get Hashed Token from ResetPassword";
			String sql = "select verification from ResetPassword where email = '" + email + "'";
			hashedToken = db.executeQuery(name, sql, DBFormat.getStringResFormat()).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return authenticate(token, hashedToken);
	}
	
	public boolean hasPermission(int userID, EnumPermission perm){
		try{
			String name = "Get User's role_id";
			String sql = "select role_id from User where user_id = " + userID;
			int roleID = db.executeQuery(name, sql, DBFormat.getIntResFormat()).get(0);
			
			String name2 = "Permission check for permission " + perm.getPerm();
			String sql2 = "select * from RolePermission where role_id = " + roleID + 
															" and perm_id = " + perm.getID();
			return db.executeQuery(name2, sql2, DBFormat.getCheckResFormat());
		}catch(SQLException e){
			e.printStackTrace();
		} 
		return false;
	}
	
	public void changeRole(int userID, int roleID){
		db.executeUpdate("Change user's role", "update User set role_id = " + roleID + " where user_id = " + userID);
	}
	
	public boolean hasSubordinate(int managerID, int userID){
		try{
			String name = "";
			String sql = "select * from Subordinate where manager_id = " + managerID + 
												 " and subordinate_id = " + userID;
			return db.executeQuery(name, sql, DBFormat.getCheckResFormat());
		}catch(SQLException e){
			e.printStackTrace();
		} 
		return false;
	}

	public boolean SOPisCompleted(int userID, int sopID){
		try{
			String name = "";
			String sql = "select * from CompletedSOP where user_id = " + userID + 
												 " and sop_id = " + sopID;
			return db.executeQuery(name, sql, DBFormat.getCheckResFormat());
		}catch(SQLException e){
			e.printStackTrace();
		} 
		return false;
	}
	
	public boolean isLockedOut(int userID){
		try{
			String name = "";
			String sql = "select * from User where user_id = " + userID + 
												 " and locked_out = 1";
			return db.executeQuery(name, sql, DBFormat.getCheckResFormat());
		}catch(SQLException e){
			e.printStackTrace();
		} 
		return false;
	}
	
	public ArrayList<User> getManagersOfUser(int userID){
		try{
			return db.executeQuery("Get Managers of User",
				"select " + DBFormat.getUserPieces() + " from Subordinate, User " + "where manager_id = " + userID,
				DBFormat.getUserResFormat());
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void lockout(int userID){
		db.executeUpdate("Lockout User with ID " + userID, "update User set locked_out = true where user_id = " + userID);
	}

	public void overturnLockout(int userID) {
		db.executeUpdate("Overturn lockout on User with ID " + userID, "update User set locked_out = false where user_id = " + userID);
	}
	
	public void archiveUser(int userID){
		db.executeUpdate("Archive User with ID " + userID, "update User set archive_flag = true where user_id = " + userID);
	}

	public void unarchiveUser(int userID){
		db.executeUpdate("Unarchive User with ID " + userID, "update User set archive_flag = false where user_id = " + userID);
	}

	public void changePosition(int userID, int positionID){
		db.executeUpdate(
				"Change User Position",
				"update User set position_id = " + positionID + " where user_id = " + userID);
	}
	
	public void changeEmployeeID(int userID, int employeeID){
		db.executeUpdate(
				"Change User " + userID + "'s employee_id to " + employeeID,
				"update User set employee_id = " + employeeID + " where user_id = " + userID);
	}
	
	public static void logout(HttpServletRequest req){
		req.getSession().removeAttribute("user_id");
	}
	
	// InsertSubordinate
	public void addSubordinate(int manager_id, int subordinate_id){
		db.insert("Subordinate", new String[] {"manager_id", "subordinate_id"},
				new String[] {String.valueOf(manager_id), String.valueOf(subordinate_id)});
	}
	
	// DeleteSubordinate
	public void removeSubordinate(int manager_id, int subordinate_id){
		db.executeUpdate("Remove subordinate with ID " + subordinate_id, "delete from Subordinate where manager_id = " + 
				manager_id + " and subordinate_id = " + subordinate_id);
	}
	
	public boolean isClockedIn(int userID){
		try{
			String name = "Is user clocked in";
			String sql = "select * from UnresolvedClockIn where user_id = " + userID;
			boolean result = db.executeQuery(name, sql, DBFormat.getCheckResFormat());
			return result;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public void clockIn(int userID){
		if(!isClockedIn(userID)){
			db.insert("UnresolvedClockIn",
					new String[]{"user_id"},
					new String[]{String.valueOf(userID)});
		}else
			System.out.println("This employee is already clocked in");
	}
	
	public void clockOut(int userID){
		Timestamp in = null, out = null;
		long hours = 0;
		
		// Start out confirming user is clocked in, get their clock in time		
		if(isClockedIn(userID)){
			try {						
				in = db.executeQuery("Fetching Clock In time", 
						"select time from UnresolvedClockIn where user_id = " + userID, DBFormat.getTimeResFormat()).get(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		// Insert completed shift using clock in time, clock out time auto generates
			db.insert("CompletedShift",
					new String[]{"user_id", "time_in"},
					new String[]{String.valueOf(userID), in.toString()});
		// Pull the newly inserted CompletedShift to find out the clock in time	
			try {
				out = db.executeQuery("Fetching Clock Out time", 
						"select time_out from CompletedShift where user_id = " + userID + " order by time_out desc", 
						DBFormat.getTimeResFormat()).get(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		// Calculate hours and insert our hours
			hours = (out.getTime() - in.getTime()) / 3600000; // getTime returns milliseconds, there are 3.6e+6 milliseconds per hour
			db.executeUpdate("Updating hours", 
					"update CompletedShift set hours = " + (int)hours + " where user_id = " + userID + 
					" and time_out = '" + out.toString() + "'");
		// Remove unresolvedclockin entry
			db.executeUpdate("Removing Unresolved ClockIn", "delete from UnresolvedClockIn where user_id = " + userID);
			
		}else
			System.out.println("This employee is not clocked in yet");
	}
	
	public void assignSOP(int userID, int sopID) {
		db.insert("UserSOP",
				new String[] {"user_id", "sop_id"},
				new String[] {String.valueOf(userID), String.valueOf(sopID)});
	}
}