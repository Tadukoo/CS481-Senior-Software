package DBpersist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import DBpersist.IDatabase;
import DBpersist.DBUtil;
import DBpersist.PersistenceException;
import DBpersist.DerbyDatabase.Transaction;
import model.Position;
import model.SOP;
import model.User;



public class DerbyDatabase implements IDatabase {

	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}

	public interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 100;
	
	//**********************************************************************************************
	//**********************************************************************************************
	//methods/querries 
	
	
	
	
	//**********************************************************************************************
	//**********************************************************************************************
	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}

	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();

		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;

			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}

			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}

			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");

		// Set autocommit to false to allow multiple the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);

		return conn;
	}
	
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				
				//
				List<User> userList;
				List<SOP> sopList;
				List<Position> positionList;
				
				try {
					System.out.println("Init userlist");
					userList = InitialData.getUsers();
					 
				}
				
				catch(IOException e) {
					throw new SQLException("Couldn't read the initial data");
				}
				
				
				//the create tables lists
				PreparedStatement insertUsers = null;
				
				try {
					//set up the users list to be imported 
					System.out.println("Preparing user insertion");
					insertUsers = conn.prepareStatement("insert into user ()");
					//actually do the insert 
					
					for (User u : userList) {
						insertUsers.setString(1, u.getEmail()); 
						insertUsers.setString(2, u.getPassword());
					}
					
					//verify and execute 
					System.out.println("inserting users");
					insertUsers.executeBatch();
					System.out.println("Users table populated");
					
					
					
					
					
					return true;
				}
				finally {
					DBUtil.closeQuietly(insertUsers);
				}
			}	
		});		
	}
}
