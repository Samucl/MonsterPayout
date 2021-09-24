package model;

public class User {
	private static int id;
	private static String username;
	private static String firstname;
	private static String lastname;
	private static String password;
	private static String email;
	private static int coins;
	private static double credits;
	private static int tiliId;
	private static String account_number; //Tilinumero
	private static int login_streak;
	private static int status; // 1 = admin, 0 = normaali käyttäjä
	
	public static void setUserData(int id, String username, String password,String firstname, String lastname, String email, String account_number, int tiliId, int coins, double credits, int login_streak, int status) {
		User.id = id;
		User.username = username;
		User.password = password;
		User.firstname = firstname;
		User.lastname = lastname;
		User.email = email;
		User.account_number = account_number;
		User.tiliId = tiliId;
		User.coins = coins;
		User.credits = credits;
		User.login_streak = login_streak;
		User.status = status;
	}
	
	public static void setCoins(int coins) {
		User.coins = coins;
	}
	public static void setCredits(double credits) {
		User.credits = credits;
	}
	
	public static int getCoins() {
		return User.coins;
	}
	
	public static double getCredits() {
		return User.credits;
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
	
	public static void setAccountNumber(String account_number) {
		User.account_number = account_number;
	}
	
	public static String getAccountNumber() {
		return User.account_number;
	}
	
	public static void setLoginStreak(int login_streak) {
		User.login_streak = login_streak;
	}
	
	public static int getLoginStreak() {
		return User.login_streak;
	}
	
	public static int isAdmin() {
		return status;
	}
	
	public static void logout() {
		id = 0;
		username = null;
		firstname = null;
		lastname = null;
		password = null;
		email = null;
		tiliId = 0;
		account_number = null;
	}
	
	
}
