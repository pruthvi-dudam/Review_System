package product.review.sys;

import java.util.UUID;

public class User {
	
	public static final String KEY_USER_ID = "userId";
	public static final String KEY_USER_NAME = "userName";
	
	private String userId;
	private String userName;
	
	// Constructor
	public User() {
		this.userName = "";
		this.userId = "";
	}
	
	// Constructor
	public User(String userName) {
		this.userName = userName;
		this.userId = UUID.randomUUID().toString();
	}
	
	// Constructor
	public User(String username, String userId) {
		this.userName = username;
		this.userId = userId;
	}
	
	// Getter and Setter for User id
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	// Getter and Setter for User name
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String v) {
		this.userName = v;
	}
	
	/*
	 * Converts user object to string
	 */
	public String toString() {
		return "{user_name : " + this.userName + ", user_id : " + this.userId + "}";
	}
	
	/*
	 *  Handles the process of creating new user
	 */
	public static void createNewUser() {
		
		final String mes = "Enter unique user name ? ";
		Logger.output(mes);
		
		String input = Logger.input().trim();
		if (input.length() > 0) {
			
			// Check if provided username is unique
			if (MongoDbHandler.getInstance().isUsernameUnique(input)) {
				Logger.output("User data stored successfully.");
				return;
			}
			else {
				Logger.output("Username already exists in database.");
				return;
			}
		}
		else {
			Logger.output("Invalid username, try again");
		}
	}
}
