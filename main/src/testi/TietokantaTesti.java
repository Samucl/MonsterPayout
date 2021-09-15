package testi;

import java.sql.*;

//TÄMÄ LUOKKA PITÄÄ MYÖHEMMIN MUOKATA NIIN ETTEI KÄYTTÄJÄ PUOLELLA SUORAAN PÄÄSTÄ MUUTTAMAAN TIETOKANNAN TIETOAJA
//VAAN VÄLIKÄTENÄ OILISI PARHAASSA TILANTEESSA PALVELIMELLA PYÖRIVÄ OHJELMA JOKA OTTAA PARAMETRIT VASTAAN (nimi salasana yms)

public class TietokantaTesti {
	public static void main(String[] args) {
	
		Connection con;
		
		//final String URL = "jdbc:mariadb://10.114.32.22:3306/kasino";
		final String URL = "jdbc:mariadb://10.114.32.22:3306/kasino";
		final String USERNAME = "remote";
		final String PASSWORD = "remote";
		
		try {
			con = DriverManager.getConnection(
					URL + "?user=" + USERNAME + "&password=" + PASSWORD);
			
			Statement stmt = con.createStatement();
			
			//SQL syöttö kutsu, tehdään Kayttaja tauluun uusi rivi
			String query = "INSERT INTO Kayttaja (Kayttajanimi, Salasana, Tilinumero, Sahkoposti, Firstname, Lastname) "
					+ "values ('Testikäyttäjä', 123, 'FI20 40 8950 1253 1250 20', 'testi@testi.fi', 'Mikko', 'Suomalainen')";
			
			stmt.executeQuery(query);
			
			//Tehdään SQL haku kutsu ja haetaan Testikäyttäjä/käyttäjät
			query = "SELECT KayttajaID, Kayttajanimi, Sahkoposti FROM Kayttaja WHERE Kayttajanimi = 'Testikäyttäjä'";
			
			ResultSet rs = stmt.executeQuery(query);
			
			int luku = 0;
			while(rs.next()) {
				System.out.println("Tulos "+luku+" | "
			+"KayttajaID "+rs.getInt("KayttajaID")
			+"    Kayttajanimi "+rs.getString("Kayttajanimi")
			+"    Sahkoposti "+rs.getString("Sahkoposti"));
			}
			
			//Poistetaan Kayttaja taulusta mahdolliset "testikäyttäjät"
			query = "DELETE FROM Kayttaja WHERE Kayttajanimi = 'Testikäyttäjä'";
			
			stmt.execute(query);
			
			System.out.println("Testikäyttäjä(t) poistettiin tietokannasta.");
			
			
		} catch (SQLException e) {
			do {
				System.err.println("Viesti: "+e.getMessage());
				System.err.println("Virhekoodi: "+e.getErrorCode());
				System.err.println("SQL-tilakoodi: "+e.getSQLState());
			} while (e.getNextException() != null);
		}
		System.out.println("onnistui");
		
		
	}

}
