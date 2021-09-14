package testi;

import java.sql.*;

//TÄMÄ LUOKKA PITÄÄ MYÖHEMMIN MUOKATA NIIN ETTEI KÄYTTÄJÄ PUOLELLA SUORAAN PÄÄSTÄ MUUTTAMAAN TIETOKANNAN TIETOAJA
//VAAN VÄLIKÄTENÄ OILISI PARHAASSA TILANTEESSA PALVELIMELLA PYÖRIVÄ OHJELMA JOKA OTTAA PARAMETRIT VASTAAN (nimi salasana yms)

public class tietokantaTesti {
	public static void main(String[] args) {
	
		Connection con;
		
		final String URL = "jdbc:mariadb://10.114.32.22:3306/kasino";
		final String USERNAME = "r12";
		final String PASSWORD = "r12";
		
		try {
			con = DriverManager.getConnection(
					URL + "?user=" + USERNAME + "&password=" + PASSWORD);
		} catch (SQLException e) {
			do {
				System.err.println("Viesti: "+e.getMessage());
				System.err.println("Virhekoodi: "+e.getErrorCode());
				System.err.println("SQL-tilakoodi: "+e.getSQLState());
			} while (e.getNextException() != null);
		}
		
	}

}
