package model;

public class OUser {
	private String firstname;
	private String lastname;
	private String username;
	private String loginToken;
	private String tilinumero;
	
	/*
	 * 
	 * 		SETTERIT
	 * 
	 */
	
	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastname = lastName;
	}
	
	public void setUserName(String userName) {
		this.username = userName;
	}
	
	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}
	
	public void setTilinumero(String tilinumero) {
		this.tilinumero = tilinumero;
	}
	
	/*
	 * 
	 * 		GETTERIT
	 * 
	 */
	
	public String getLastName() {
		return lastname;
	}
	
	public String getUserName() {
		return username;
	}
	
	public String getLoginToken() {
		return loginToken;
	}
	
	public String getTilinumero() {
		return tilinumero;
	}
	
	public String getFirstName() {
		return firstname;
	}

}
