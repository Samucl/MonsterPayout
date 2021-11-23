package monsterpayout;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Database;
import model.User;

public class DatabaseTest {
	
	@BeforeEach 
    void init() {
		//Rekisteröidään uusi testikäyttäjä
		if(Database.checkUsername("testikäyttäjä123"))
			Database.deleteTestUser();
		Database.register("testikäyttäjä123", "testi", "testi123@gmail.com", "Testaaja", "Testaa");
    }

    @AfterEach
    void teardown() {
    	if(Database.checkUsername("testikäyttäjä123"))
			Database.deleteTestUser();
    }
    
	
	@Test                                               
    @DisplayName("Kirjaudutaan testikäyttäjälle")   
    void loginTest() {
        assertTrue(Database.login("testikäyttäjä123", "testi"));
    }
	
	@Test                                               
    @DisplayName("Muutetaan testikäyttäjän profiilia")   
    void profileEditingTest() {
		//Kirjaudutaan sisään
		Database.login("testikäyttäjä123", "testi");
		User.setLastname("UusiSukunimi");
        assertTrue(Database.saveProfileChanges());
    }
	
	@Test
	@DisplayName("Lisätään/vähennetään testikäyttäjän kolikkosaldoa")
	void alterCoinBalanceTest() {
		Database.login("testikäyttäjä123", "testi");
		int coins = User.getCoins();
		Database.increaseCoinBalance(100);
		System.out.println(User.getCoins());
		assertTrue(User.getCoins() == 100 + coins);
		Database.decreaseCoinBalance(80);
		assertTrue(User.getCoins() == 20 + coins);
	}
	
	@Test
	@DisplayName("Lisätään/vähennetään testikäyttäjän krediittisaldoa")
	void alterCreditBalanceTest() {
		Database.login("testikäyttäjä123", "testi");
		double credits = User.getCredits();
		Database.increaseCreditBalance(200);
		assertTrue(User.getCredits() == 200 + credits);
		
		Database.decreaseCreditBalance(50);
		assertTrue(User.getCredits() == 150 + credits);
	}
	
	

	
}
