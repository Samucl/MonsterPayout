package model;

public class User {
	private static int id;
	private static String username;
	private static String firstname;
	private static String lastname;
	private static String password;
	private static String email;
	private static int tiliId;
	
	private static int count = 0;
	
	public static void setUserData(int id, String username, String password,String firstname, String lastname, String email, int tiliId) {
		User.id = id;
		User.username = username;
		User.password = password;
		User.firstname = firstname;
		User.lastname = lastname;
		User.email = email;
		User.tiliId = tiliId;
		
		// Laskee pit�� kirjaa k�ytt�jien m��r�st�
		count++; 
		User.id = count;
	}
	
	public String toString() {
		return User.firstname + " " + User.lastname + ", " + User.email + ", " + User.password;
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		User.id = id;
	}

	public static String getFirstname() {
		return firstname;
	}

	public static void setFirstname(String firstName) {
		User.firstname = firstName;
	}

	public static String getLastname() {
		return lastname;
	}

	public static void setLastname(String lastName) {
		User.lastname = lastName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		User.password = password;
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		User.count = count;
	}

	public static String getEmail() {
		return email;
	}

	public static void setEmail(String email) {
		User.email = email;
	}
	
	public static int getTiliId() {
		return tiliId;
	}
	
	public static void setTiliId(int tiliId) {
		User.tiliId = tiliId;
	}
	
	public static void setUsername(String username) {
		User.username = username;
	}
	
	public static String getUsername() {
		return username;
	}
	
	
}
