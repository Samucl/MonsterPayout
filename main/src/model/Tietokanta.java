package model;

import java.nio.charset.StandardCharsets;
import java.sql.*;

import com.google.common.hash.Hashing;

public class Tietokanta {
	
	final static String URL = "jdbc:mariadb://10.114.32.22:3306/kasino";
	final static String USERNAME = "remote";
	final static String PASSWORD = "remote";
	
	private static boolean loggedIn = false;
	
	public static void logout() {
		User.logout();
		loggedIn = false;
	}
	
	public static boolean login(String username, String password) {
		/*
		 * Luodaan käyttäjän istunto. Samalla haetaan alustavasti kaikki käyttäjän tilaukset yms.
		 */
		
		try {
			Connection con;
			con = DriverManager.getConnection(
					URL + "?user=" + USERNAME + "&password=" + PASSWORD);
			
			Statement stmt = con.createStatement();
			
			String passwordHash = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
			
			//Tehdään SQL haku kutsu ja haetaan Testikäyttäjä/käyttäjät
			String query = "SELECT KayttajaID, Kayttajanimi, Sahkoposti, Tilinumero, TiliID, Firstname, Lastname, Login_streak, Last_login, Status "
					+ "FROM Kayttaja WHERE Kayttajanimi = '"+ username +"' AND Salasana = '"+ passwordHash +"'";
			
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.next())
				if(rs.getString("Kayttajanimi") != null) {
					int tId = rs.getInt("KayttajaID");
					String tUsername = rs.getString("Kayttajanimi");
					String tFirstname = rs.getString("Firstname");
					String tLastname = rs.getString("Lastname");
					String tEmail = rs.getString("Sahkoposti");
					String tTilinumero = rs.getString("Tilinumero");
					int tTiliId = rs.getInt("TiliID");
					int tLogin_streak = rs.getInt("Login_streak");
					int tStatus = rs.getInt("Status");
					query = "SELECT KolikkoSaldo, KrediittiSaldo FROM Tili "
							+ "WHERE TiliID = " + tTiliId;
					rs = stmt.executeQuery(query);
					if(rs.next()) {
						double tCredits = rs.getDouble("KrediittiSaldo");
						int tCoins = rs.getInt("KolikkoSaldo");
						User.setUserData(tId, tUsername, password,tFirstname, tLastname, tEmail, tTilinumero,tTiliId, tCoins, tCredits, tLogin_streak, tStatus);
						/*
						 * Lisätään Session-luokkaan käyttäjän tekemät tilaukset
						 */
						loggedIn = true;
						Session.setOrders(Tietokanta.getOrders());
						return loggedIn;
					}
					
				}
			
		} catch (SQLException e) {
			do {
				System.err.println("Viesti: "+e.getMessage());
				System.err.println("Virhekoodi: "+e.getErrorCode());
				System.err.println("SQL-tilakoodi: "+e.getSQLState());
			} while (e.getNextException() != null);
		}
		
		return loggedIn = false;
	}
	
	public static Product[] getProducts() {
		try {
			Connection con;
			con = DriverManager.getConnection(
					URL + "?user=" + USERNAME + "&password=" + PASSWORD);
			
			Statement stmt = con.createStatement();
			
			
			//SQL syöttökutsu, tehdään Kayttaja tauluun uusi rivi
			String query = "SELECT * FROM Tuote";
			
			ResultSet rs = stmt.executeQuery(query);
			int size = 0;
			if (rs.last()) {
			  size = rs.getRow();
			  rs.beforeFirst();
			  Product[] products = new Product[size];
			  while(rs.next()) {
				  System.out.println(rs.getRow()+"/"+size);
				  System.out.println("Tuotenumero: "+rs.getInt("Tuotenumero"));
				  System.out.println("Kuvaus: "+rs.getString("Kuvaus"));
				  System.out.println("Hinta: "+rs.getDouble("Hinta"));
				  System.out.println("Krediittien määrä: "+rs.getDouble("KrediittienMaara"));
				  System.out.println("Alennuskerroin: "+rs.getDouble("Alennuskerroin"));
				  products[rs.getRow()-1] = new Product(
						  rs.getInt("Tuotenumero"),
						  rs.getString("Kuvaus"),
						  rs.getDouble("Hinta"),
						  rs.getDouble("KrediittienMaara"),
						  rs.getDouble("Alennuskerroin"));
				}
			  return products;
			}
			
			
		} catch (SQLException e) {
			do {
				System.err.println("Viesti: "+e.getMessage());
				System.err.println("Virhekoodi: "+e.getErrorCode());
				System.err.println("SQL-tilakoodi: "+e.getSQLState());
			} while (e.getNextException() != null);
		}
		return null;
	}
	
	public static Order[] getOrders() {
		if (Tietokanta.isLogged() && User.getUsername() != null && User.getPassword() != null) {
			try {
				Connection con;
				con = DriverManager.getConnection(
						URL + "?user=" + USERNAME + "&password=" + PASSWORD);
				
				Statement stmt = con.createStatement();
				
				
				//SQL syöttökutsu, tehdään Kayttaja tauluun uusi rivi
				String query = "SELECT KayttajaID FROM Kayttaja WHERE Kayttajanimi = '"
									+ User.getUsername() +"' AND Salasana = SHA2('"+ User.getPassword() +"',256)";
						
						
						
						//"SELECT * FROM Tilaus WHERE KayttajaID = " + kayttajaID;
				
				ResultSet rs = stmt.executeQuery(query);
				if(rs.next()) {
					String kayttajaID = rs.getString("KayttajaID");
					query = "SELECT * FROM Tilaus WHERE KayttajaID = " + kayttajaID;
					
					rs = stmt.executeQuery(query);
					int size = 0;
					if (rs.last()) {
					  size = rs.getRow();
					  rs.beforeFirst();
					  Order[] orders = new Order[size];
					  while(rs.next()) {
						  orders[rs.getRow()-1] = new Order(
								  rs.getInt("TilausID"),
								  rs.getString("Paivamaara"),
								  rs.getString("Tuotekuvaus"),
								  rs.getDouble("Summa"),
								  rs.getDouble("KrediittienMaara"));
						}
					  return orders;
					}
				}
				
				
				
			} catch (SQLException e) {
				do {
					System.err.println("Viesti: "+e.getMessage());
					System.err.println("Virhekoodi: "+e.getErrorCode());
					System.err.println("SQL-tilakoodi: "+e.getSQLState());
				} while (e.getNextException() != null);
			}
		}
		return null;
	}
	
	public static boolean register(String username, String password, String email, String firstname, String lastname) {
		/*
		 * Jos rekisteröinti onnistuu palauttaa metodi boolean arvon true, muuten false
		 */
		
		try {
			Connection con;
			con = DriverManager.getConnection(
					URL + "?user=" + USERNAME + "&password=" + PASSWORD);
			
			Statement stmt = con.createStatement();
			
			/*
			 * Jos käyttäjänimi on vapaa siirrytään if-lausekkeen sisään.
			 */
			if(checkUsername(username)) {
				/*
				 * Ennen uuden käyttäjän luomista luodaan uusi tili, 
				 * joka linkitetään uuteen käyttäjään.
				 */
				String query = "INSERT INTO Tili (KolikkoSaldo, KrediittiSaldo) "
						+ "VALUES (0,0)";
				/*
				 * Tehdään uuden tilin SQL-kutsu if lauseen sisällä. Jos 
				 * uuden tilin luonti onnistuu palauttaa executeUpdate uusien 
				 * rivien määrän. Jos luonti epäonnistuu uusia rivejä on 0
				 */
				
				if(stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS) > 0) {
					ResultSet rs = stmt.getGeneratedKeys();
					if(rs.next()) {
						int tiliID = rs.getInt(1);
						System.out.println(tiliID);
						query = "INSERT INTO Kayttaja (Kayttajanimi, Salasana, TiliID, Sahkoposti, Firstname, Lastname) "
								+ "values ('"+username+"', SHA2('"+password+"',256),'"+ tiliID +"', '"+email+"', '"+firstname+"', '"+lastname+"')";
						/*
						 * Ylempää if-lauseen perjaatetta jatkaen lähetetään käyttäjän luonnin SQL-kutsu 
						 * uuden if-lauseen sisällä ja jos käyttäjän luonti onnistuu palauttaa 
						 * executeUpdate metodi 0 suuremman luvun
						 */
						if(stmt.executeUpdate(query) > 0)
							return true;
					}
				}
			}
			
		} catch (SQLException e) {
			do {
				System.err.println("Viesti: "+e.getMessage());
				System.err.println("Virhekoodi: "+e.getErrorCode());
				System.err.println("SQL-tilakoodi: "+e.getSQLState());
			} while (e.getNextException() != null);
		}
		
		return false;
	}
	
	public static boolean checkUsername(String username) {
		/*
		 * Metodi palauttaa boolean arvon false jos käyttäjänimi on jo käytössä.
		 * True tarkoittaa ettei käyttäjänimellä ole vielä tehty käyttäjää.
		 */
		try {
			Connection con;
			con = DriverManager.getConnection(
					URL + "?user=" + USERNAME + "&password=" + PASSWORD);
			
			Statement stmt = con.createStatement();
			
			
			//SQL syöttökutsu, tehdään Kayttaja tauluun uusi rivi
			String query = "SELECT * FROM Kayttaja WHERE Kayttajanimi LIKE '"+username+"' LIMIT 1";
			
			stmt.executeQuery(query);
			
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.next())
				return false;
			
		} catch (SQLException e) {
			do {
				System.err.println("Viesti: "+e.getMessage());
				System.err.println("Virhekoodi: "+e.getErrorCode());
				System.err.println("SQL-tilakoodi: "+e.getSQLState());
			} while (e.getNextException() != null);
		}
		return true;
	}
	
	public static int decreaseKolikkoBalance(int amount) {
		
		/*
		 * Metodi ottaa parametreina Kayttaja-luokan joka sisältää 
		 * käyttäjän tiedot, jolloin voidaan varmistaa uudestaan, että 
		 * käyttäjällä on oikeat kirjautumis tunnukset ennen tiliin 
		 * käsiksi pääsyä.
		 * 
		 * Jos tililtä onnistutaan vähentämään pyydetyn määrän saldoa palauttaa 
		 * metodi tämän saldon määrän. Muuten 0.
		 */
		
		/*
		 * Jos vähennettävä saldo on 0 tai pienempi niin 
		 * lopetetaan metodin suorittaminen tähän ja palautetaan arvo 0
		 */
		if(amount <= 0)
			return 0;
		
		if(Tietokanta.isLogged() && User.getUsername() != null && User.getPassword() != null) {
			try {
				Connection con;
				con = DriverManager.getConnection(
						URL + "?user=" + USERNAME + "&password=" + PASSWORD);
				
				Statement stmt = con.createStatement();
				
				//Tehdään SQL haku kutsu ja haetaan Testikäyttäjä/käyttäjät
				String query = "SELECT TiliID "
						+ "FROM Kayttaja WHERE Kayttajanimi = '"+ User.getUsername() +"' AND Salasana = SHA2('"+ User.getPassword() +"',256)";

				ResultSet rs = stmt.executeQuery(query);
				
				/*
				 * Jos löytyy seuraava tulosjoukko on tietokannasta löytynyt käyttäjä
				 */
				if(rs.next()) {
					int tiliID = rs.getInt("TiliID");
					query = "SELECT KolikkoSaldo FROM Tili "
							+ "WHERE TiliID = "+tiliID;
					rs = stmt.executeQuery(query);
					/*
					 * Jos löytyy seuraava tulosjoukko löytyy tietokannasta käyttäjän tili
					 */
					if(rs.next()) {
						int saldo = rs.getInt("KolikkoSaldo");
						/*
						 * Verrataan käyttäjän tilin saldoa vähennettävään määrään. 
						 * Jos käyttäjän saldo riittää niin vähennetään tietokannasta 
						 * amount-muuttujan verran kolikko saldoa
						 */
						if(saldo >= amount) {
							query = "UPDATE Tili "
									+ "SET KolikkoSaldo = KolikkoSaldo - "+amount
											+ " WHERE TiliID = "+tiliID;
							int updatedRows = stmt.executeUpdate(query);
							query = "SELECT KolikkoSaldo FROM Tili "
									+ "WHERE TiliID = "+tiliID;
							rs = stmt.executeQuery(query);
							if(rs.next())
								User.setCoins(rs.getInt("KolikkoSaldo"));
							
							/*
							 * Jos SQL-kutsu muokkasi vähintään 1-riviä, 
							 * niin saldon vähennys kutsu on onnistunut.
							 */
							if(updatedRows > 0)
								return amount;
						}
					}
				}
				
			} catch (SQLException e) {
				do {
					System.err.println("Viesti: "+e.getMessage());
					System.err.println("Virhekoodi: "+e.getErrorCode());
					System.err.println("SQL-tilakoodi: "+e.getSQLState());
				} while (e.getNextException() != null);
			}
		}
		return 0;
	}
	
	public static boolean buyProduct(Product product) {
		/*
		 * Toteutetaan käyttäjän tekemä tilaus ja samalla päivitetään Session-luokkaan tilaukset
		 */
		if (Tietokanta.isLogged() && User.getUsername() != null && User.getPassword() != null) {
			
			try {
				Connection con = DriverManager.getConnection(
						URL + "?user=" + USERNAME + "&password=" + PASSWORD);
				
				Statement stmt = con.createStatement();
				
				String query = "SELECT TiliID, KayttajaID "
						+ "FROM Kayttaja WHERE Kayttajanimi = '"+ User.getUsername() +"' AND Salasana = SHA2('"+ User.getPassword() +"',256)";

				ResultSet rs = stmt.executeQuery(query);
				
				/*
				 * Jos löytyy seuraava tulosjoukko on tietokannasta löytynyt käyttäjä
				 */
				if(rs.next()) {
					int tiliID = rs.getInt("TiliID");
					int kayttajaID = rs.getInt("KayttajaID");
					// Lisätään saldoa
					query = "UPDATE Tili "
							+ "SET KrediittiSaldo = KrediittiSaldo + " + product.getCreditAmount()
									+ " WHERE TiliID = " + tiliID;
					int updatedRows = stmt.executeUpdate(query); // Tallennetaan palautusta varten päivitettyjen alkioiden lkm (1 jos onnistui, 0 jos ei)
					
					query = "INSERT INTO Tilaus (Summa, Paivamaara, KayttajaID, Tuotekuvaus, KrediittienMaara) VALUES "
							+ "("+product.getPrice()+","
									+ "current_timestamp(),"
							+kayttajaID+",'"
									+product.getDescription()+"',"
							+product.getCreditAmount()+")";
					
					stmt.executeQuery(query);
					
					query = "SELECT KrediittiSaldo FROM Tili "
							+ "WHERE TiliID = "+tiliID;
					rs = stmt.executeQuery(query);
					if(rs.next())
						User.setCredits(rs.getDouble("KrediittiSaldo"));
					
					Session.setOrders(Tietokanta.getOrders());
					
					return true;
				}
				
			} catch (SQLException e) {
				do {
					System.err.println("Viesti: "+e.getMessage());
					System.err.println("Virhekoodi: "+e.getErrorCode());
					System.err.println("SQL-tilakoodi: "+e.getSQLState());
				} while (e.getNextException() != null);
			}
		}
		return false;
	}
	
	public static int increaseCreditBalance(double amount) {
		
		if (Tietokanta.isLogged() && User.getUsername() != null && User.getPassword() != null) {
			
			try {
				Connection con = DriverManager.getConnection(
						URL + "?user=" + USERNAME + "&password=" + PASSWORD);
				
				Statement stmt = con.createStatement();
				
				String query = "SELECT TiliID "
						+ "FROM Kayttaja WHERE Kayttajanimi = '"+ User.getUsername() +"' AND Salasana = SHA2('"+ User.getPassword() +"',256)";

				ResultSet rs = stmt.executeQuery(query);
				
				/*
				 * Jos löytyy seuraava tulosjoukko on tietokannasta löytynyt käyttäjä
				 */
				if(rs.next()) {
					int tiliID = rs.getInt("TiliID");
					// Lisätään saldoa
					query = "UPDATE Tili "
							+ "SET KrediittiSaldo = KrediittiSaldo + " + amount
									+ " WHERE TiliID = " + tiliID;
					int updatedRows = stmt.executeUpdate(query); // Tallennetaan palautusta varten päivitettyjen alkioiden lkm (1 jos onnistui, 0 jos ei)
					
					query = "SELECT KrediittiSaldo FROM Tili "
							+ "WHERE TiliID = "+tiliID;
					rs = stmt.executeQuery(query);
					if(rs.next())
						User.setCredits(rs.getDouble("KrediittiSaldo"));
					
					return updatedRows;
				}
				
			} catch (SQLException e) {
				do {
					System.err.println("Viesti: "+e.getMessage());
					System.err.println("Virhekoodi: "+e.getErrorCode());
					System.err.println("SQL-tilakoodi: "+e.getSQLState());
				} while (e.getNextException() != null);
			}
		}
		return 0;
		
	}
	
	public static boolean createProduct(Product product) {
		if(Tietokanta.isLogged() && User.getUsername() != null && User.getPassword() != null) {
			try {
				Connection con = DriverManager.getConnection(
						URL + "?user=" + USERNAME + "&password=" + PASSWORD);
				
				Statement stmt = con.createStatement();
				
				String query = "SELECT Status "
						+ "FROM Kayttaja WHERE Kayttajanimi = '"+ User.getUsername() +"' AND Salasana = SHA2('"+ User.getPassword() +"',256)";

				ResultSet rs = stmt.executeQuery(query);
				
				/*
				 * Jos löytyy seuraava tulosjoukko on tietokannasta löytynyt käyttäjä statuksella 1 (admin)
				 */
				if(rs.next()) {
					if(rs.getInt("Status")==1) {
						query = "INSERT INTO Tuote (Kuvaus, Hinta, KrediittienMaara, Alennuskerroin)"
								+ "values('"+product.getDescription()+"',"+product.getPrice()+","+product.getCreditAmount()+","+product.getSaleMultiplier()+")";
						rs = stmt.executeQuery(query);
						/*
						 * Jos löytyy seuraava tulosjoukko on tietokantaan lisätty onnistuneesti
						 */
						if(rs.next()) {
							return true;
								
						}
					}
					
				}
				
			} catch (SQLException e) {
				do {
					System.err.println("Viesti: "+e.getMessage());
					System.err.println("Virhekoodi: "+e.getErrorCode());
					System.err.println("SQL-tilakoodi: "+e.getSQLState());
				} while (e.getNextException() != null);
			}
		}
		return false;
	}
	
	public static boolean editProduct(Product product) {
		if(Tietokanta.isLogged() && User.getUsername() != null && User.getPassword() != null) {
			try {
				Connection con = DriverManager.getConnection(
						URL + "?user=" + USERNAME + "&password=" + PASSWORD);
				
				Statement stmt = con.createStatement();
				
				String query = "SELECT Status "
						+ "FROM Kayttaja WHERE Kayttajanimi = '"+ User.getUsername() +"' AND Salasana = SHA2('"+ User.getPassword() +"',256)";

				ResultSet rs = stmt.executeQuery(query);
				
				/*
				 * Jos löytyy seuraava tulosjoukko on tietokannasta löytynyt käyttäjä statuksella 1 (admin)
				 */
				if(rs.next()) {
					if(rs.getInt("Status")==1) {
						query = "UPDATE Tuote SET "
								+ "Kuvaus = '"+product.getDescription()+"', "
								+ "Hinta = "+product.getPrice()+", "
								+ "KrediittienMaara = "+product.getCreditAmount()+", "
								+ "Alennuskerroin = "+product.getSaleMultiplier()+" "
								+ "WHERE Tuotenumero = "+product.getId();
						
						int updatedRows = stmt.executeUpdate(query); // Tallennetaan palautusta varten päivitettyjen alkioiden lkm (1 jos onnistui, 0 jos ei)

						/*
						 * Jos on muokattu tietokannassa useampaa kuin yhtä riviä niin on päivitys onnistunut
						 */
						if(updatedRows > 0) {
							return true;
								
						}
					}
				}
				
			} catch (SQLException e) {
				do {
					System.err.println("Viesti: "+e.getMessage());
					System.err.println("Virhekoodi: "+e.getErrorCode());
					System.err.println("SQL-tilakoodi: "+e.getSQLState());
				} while (e.getNextException() != null);
			}
		}
		return false;
	}
	
	public static boolean deleteProduct(int productNumber) {
		if(Tietokanta.isLogged() && User.getUsername() != null && User.getPassword() != null) {
			try {
				Connection con = DriverManager.getConnection(
						URL + "?user=" + USERNAME + "&password=" + PASSWORD);
				
				Statement stmt = con.createStatement();
				
				String query = "SELECT Status "
						+ "FROM Kayttaja WHERE Kayttajanimi = '"+ User.getUsername() +"' AND Salasana = SHA2('"+ User.getPassword() +"',256)";

				ResultSet rs = stmt.executeQuery(query);
				
				/*
				 * Jos löytyy seuraava tulosjoukko on tietokannasta löytynyt käyttäjä statuksella 1 (admin)
				 */
				if(rs.next()) {
					if(rs.getInt("Status")==1) {
						query = "DELETE FROM Tuote WHERE Tuotenumero = "+productNumber;
						int updatedRows = stmt.executeUpdate(query); // Tallennetaan palautusta varten päivitettyjen alkioiden lkm (1 jos onnistui, 0 jos ei)

						/*
						 * Jos on muokattu tietokannassa useampaa kuin yhtä riviä niin on päivitys onnistunut
						 */
						if(updatedRows > 0) {
							return true;
								
						}
					}
					
				}
				
			} catch (SQLException e) {
				do {
					System.err.println("Viesti: "+e.getMessage());
					System.err.println("Virhekoodi: "+e.getErrorCode());
					System.err.println("SQL-tilakoodi: "+e.getSQLState());
				} while (e.getNextException() != null);
			}
		}
		return false;
	}
	
	public static boolean saveProfileChanges() {
		if(Tietokanta.isLogged() && User.getUsername() != null && User.getPassword() != null) {
			try {
				Connection con = DriverManager.getConnection(
						URL + "?user=" + USERNAME + "&password=" + PASSWORD);
				
				Statement stmt = con.createStatement();
				
				String query = "SELECT KayttajaID "
						+ "FROM Kayttaja WHERE Kayttajanimi = '"+ User.getUsername() +"' AND Salasana = SHA2('"+ User.getPassword() +"',256)";

				ResultSet rs = stmt.executeQuery(query);
				
				/*
				 * Jos löytyy seuraava tulosjoukko on tietokannasta löytynyt käyttäjän id
				 */
				if(rs.next()) {
					int kayttajaID = rs.getInt("KayttajaID");
					System.out.println(kayttajaID);
						query = "UPDATE Kayttaja SET "+ "Firstname = '" + User.getFirstname()+"', "
										+ "Lastname = '" + User.getLastname()+"', "
												+ "Sahkoposti = '" + User.getEmail()+"', "
														+ "Tilinumero = '" + User.getAccountNumber()
														+"' WHERE KayttajaID = " + kayttajaID;
						int updatedRows = stmt.executeUpdate(query); // Tallennetaan palautusta varten päivitettyjen alkioiden lkm (1 jos onnistui, 0 jos ei)

						/*
						 * Jos on muokattu tietokannassa useampaa kuin yhtä riviä niin on päivitys onnistunut
						 */
						if(updatedRows > 0) {
							return true;
						}
					
				}
				
			} catch (SQLException e) {
				do {
					System.err.println("Viesti: "+e.getMessage());
					System.err.println("Virhekoodi: "+e.getErrorCode());
					System.err.println("SQL-tilakoodi: "+e.getSQLState());
				} while (e.getNextException() != null);
			}
		}
		return false;
	}
	
	public static boolean isLogged() {
		/*
		 * Tarkistetaan onko käyttäjä kirjautunut sisään
		 */
		return loggedIn;
	}
	
}
