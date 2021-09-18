package model;

public class Kayttaja {
	private int id;
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private String email;
	private int tiliId;
	
	private static int count = 0;
	
	public Kayttaja(String firstName, String lastName, String email, String password, int tiliId) {
		this.firstname = firstName;
		this.lastname = lastName;
		this.email = email;
		this.password = password;
		this.tiliId = tiliId;
		
		// Laskee pit�� kirjaa k�ytt�jien m��r�st�
		count++; 
		this.id = count;
	}
	
	public Kayttaja(int id, String username, String password,String firstname, String lastname, String email, int tiliId) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.tiliId = tiliId;
		
		// Laskee pit�� kirjaa k�ytt�jien m��r�st�
		count++; 
		this.id = count;
	}
	
	public String toString() {
		return firstname + " " + lastname + ", " + email + ", " + password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastName) {
		this.lastname = lastName;
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
	
	public int getTiliId() {
		return tiliId;
	}
	
	public void setTiliId(int tiliId) {
		this.tiliId = tiliId;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
}
