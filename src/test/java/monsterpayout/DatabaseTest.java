package monsterpayout;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Tietokanta;
import model.User;

public class DatabaseTest {
	
	@BeforeEach 
    void init() {
		//Rekisteröidään uusi testikäyttäjä
		if(Tietokanta.checkUsername("testikäyttäjä123"))
			Tietokanta.deleteTestUser();
		Tietokanta.register("testikäyttäjä123", "testi", "testi123@gmail.com", "Testaaja", "Testaa");
    }

    @AfterEach
    void teardown() {
    	if(Tietokanta.checkUsername("testikäyttäjä123"))
			Tietokanta.deleteTestUser();
    }
    
	
	@Test                                               
    @DisplayName("Kirjaudutaan testikäyttäjälle")   
    void loginTest() {
        assertTrue(Tietokanta.login("testikäyttäjä123", "testi"));
    }
	
	@Test                                               
    @DisplayName("Muutetaan testikäyttäjän profiilia")   
    void profileEditingTest() {
		//Kirjaudutaan sisään
		Tietokanta.login("testikäyttäjä123", "testi");
		User.setLastname("UusiSukunimi");
        assertTrue(Tietokanta.saveProfileChanges());
    }
	
	@Test
	@DisplayName("Lisätään/vähennetään testikäyttäjän kolikkosaldoa")
	void alterCoinBalanceTest() {
		Tietokanta.login("testikäyttäjä123", "testi");
		int coins = User.getCoins();
		Tietokanta.increaseCoinBalance(100);
		System.out.println(User.getCoins());
		assertTrue(User.getCoins() == 100 + coins);
		Tietokanta.decreaseCoinBalance(80);
		assertTrue(User.getCoins() == 20 + coins);
	}
	
	@Test
	@DisplayName("Lisätään/vähennetään testikäyttäjän krediittisaldoa")
	void alterCreditBalanceTest() {
		Tietokanta.login("testikäyttäjä123", "testi");
		double credits = User.getCredits();
		Tietokanta.increaseCreditBalance(200);
		assertTrue(User.getCredits() == 200 + credits);
		
		Tietokanta.decreaseCreditBalance(50);
		assertTrue(User.getCredits() == 150 + credits);
	}
	
}
