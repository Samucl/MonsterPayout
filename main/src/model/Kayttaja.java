package model;

public class Kayttaja {
	private int id;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	
	private static int count = 0;
	
	public Kayttaja(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		
		// Laskee pit‰‰ kirjaa k‰ytt‰jien m‰‰r‰st‰
		count++; 
		this.id = count;
	}
	
	public String toString() {
		return firstName + " " + lastName + ", " + email + ", " + password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Kayttaja.count = count;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
