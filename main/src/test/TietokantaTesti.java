package test;

import java.nio.charset.StandardCharsets;
import java.sql.*;

import com.google.common.hash.Hashing;

import model.User;
import model.Lue;
import model.Tietokanta;
import model.User;

//T�M� LUOKKA PITÄÄ MYÖHEMMIN MUOKATA NIIN ETTEI KÄYTTÄJÄ PUOLELLA SUORAAN PÄÄSTÄ MUUTTAMAAN TIETOKANNAN TIETOAJA
//VAAN VÄLIKÄTENÄ OILISI PARHAASSA TILANTEESSA PALVELIMELLA PYÖRIVÄ OHJELMA JOKA OTTAA PARAMETRIT VASTAAN (nimi salasana yms)

public class TietokantaTesti {
	final static String URL = "jdbc:mariadb://10.114.32.22:3306/kasino";
	final static String USERNAME = "remote";
	final static String PASSWORD = "remote";
	
	public static void main(String[] args) {
		char select;
		do {
			System.out.println("Tietokanta testi\n"
					+ "---------------------------------\n"
					+ "1. Kirjautumis testi\n"
					+ "2. Yleinen testi\n"
					+ "3. Käyttäjänimen tarkistus\n"
					+ "4. Rekisteröinti\n"
					+ "5. lopeta\n\n");
			select = Lue.merkki();
			switch (select) {
            case '1':
                loginTesti();
                break;
            case '2':
                yleinenTesti();
                break;
            case '3':
            	kayttajanimiTesti();
                break;
            case '4':
            	kayttajanLuontiTesti();
            	break;
            case '5':
            	break;
            }
		} while (select != '5');
	}
	
	private static void kayttajanLuontiTesti() {
		System.out.println("Syötä käyttäjätunnus: ");
		String username = new String(Lue.rivi());
		System.out.println("Syötä salasana: ");
		String password = new String(Lue.rivi());
		System.out.println("Syötä sähköpostiosoite: ");
		String email = new String(Lue.rivi());
		System.out.println("Syötä etunimi: ");
		String firstname = new String(Lue.rivi());
		System.out.println("Syötä sukunimi: ");
		String lastname = new String(Lue.rivi());
		
		/*
		 * Varmistetaan ensin Tietokanta-luokan checkUsername metodilla 
		 * onko luotavan käyttäjän nimi vapaana. Jos on niin siirrytään 
		 * if-lauseen sisälle.
		 */
		if(Tietokanta.checkUsername(username))
			if(Tietokanta.register(username, password, email, firstname, lastname)) {
				System.out.println("Uuden käyttäjän luonti onnistui!");
			} else {
				System.out.println("Jotain meni pieleen uutta käyttäjää luodessa.");
			}
		else {
			System.out.println("Käyttäjätunnus on jo käytössä");
		}
	}
	
	private static void kayttajanimiTesti() {
		System.out.println("Syötä käyttäjätunnus: ");
		String username = new String(Lue.rivi());
		
		if(Tietokanta.checkUsername(username))
			System.out.println("Käyttäjätunnus on vapaa");
		else {
			System.out.println("Käyttäjätunnus on jo käytössä");
		}
	}
	
	private static void loginTesti() {
		System.out.println("Syötä käyttäjätunnus: ");
		String username = new String(Lue.rivi());
		System.out.println("Syötä salasana: ");
		String password = new String(Lue.rivi());
		Tietokanta.login(username, password);
		if(Tietokanta.isLogged()) {
			System.out.println("Tervetuloa takaisin "+User.getFirstname()+" "+User.getLastname());
		} else {
			System.out.println("Käyttäjätunnus tai salasana väärä");
		}
		
		/*
		 * VANHAA KOODIA
		 * 
		Kayttaja kayttaja = Tietokanta.login(username, password);
		
		if(kayttaja == null)
			System.out.println("Käyttäjätunnus tai salasana väärä");
		else {
			System.out.println("Tervetuloa takaisin "+kayttaja.getFirstname()+" "+kayttaja.getLastname());
		}
		char select;
		do {
			System.out.println("Pelaaminen\n"
					+ "---------------------------------\n"
					+ "1. Pyöräytä kolikkopeliä\n"
					+ "2. lopeta\n\n");
			select = Lue.merkki();
			switch (select) {
            case '1':
                System.out.println("Panoksen koko: ");
                int balance = Lue.kluku();
                int returnValue = Tietokanta.decreaseKolikkoBalance(kayttaja, balance);
                System.out.println("Tilin kolikko saldoa vähennettiin "+returnValue+" verran.");
                break;
            case '2':
                break;
            case '3':
                break;
            case '4':
            	break;
            case '5':
            	break;
            }
		} while (select != '2');
		*/
	}
	
	private static void yleinenTesti() {
		Connection con;
		try {
			con = DriverManager.getConnection(
					URL + "?user=" + USERNAME + "&password=" + PASSWORD);
			
			Statement stmt = con.createStatement();
			
			String testiSalasana = "asg123";
			String hTestiSalasana = Hashing.sha256().hashString(testiSalasana, StandardCharsets.UTF_8).toString();
			
			//SQL syöttökutsu, tehdään Kayttaja tauluun uusi rivi
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
