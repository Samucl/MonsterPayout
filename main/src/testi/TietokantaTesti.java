package testi;

import java.nio.charset.StandardCharsets;
import java.sql.*;

import com.google.common.hash.Hashing;

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
			
			String testiSalasana = "asg123";
			String hTestiSalasana = Hashing.sha256().hashString(testiSalasana, StandardCharsets.UTF_8).toString();
			
			//SQL syöttö kutsu, tehdään Kayttaja tauluun uusi rivi
			String query = "INSERT INTO Kayttaja (Kayttajanimi, Salasana, Tilinumero, Sahkoposti, Firstname, Lastname) "
					+ "values ('Testikäyttäjä', SHA2('"+testiSalasana+"',256), 'FI20 40 8950 1253 1250 20', 'testi@testi.fi', 'Mikko', 'Suomalainen')";
			
			stmt.executeQuery(query);
			
			//Tehdään SQL haku kutsu ja haetaan Testikäyttäjä/käyttäjät
			query = "SELECT KayttajaID, Kayttajanimi, Salasana, Sahkoposti FROM Kayttaja WHERE Kayttajanimi = 'Testikäyttäjä'";
			
			ResultSet rs = stmt.executeQuery(query);
			
			int luku = 0;
			while(rs.next()) {
				System.out.println("Tulos "+luku+" | "
			+"KayttajaID "+rs.getInt("KayttajaID")
			+"    Kayttajanimi "+rs.getString("Kayttajanimi")
			+"    Sahkoposti "+rs.getString("Sahkoposti"));
				String dSalasana = rs.getString("Salasana");
				System.out.println(dSalasana+"\n"+hTestiSalasana);
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
		
		
	}

}
