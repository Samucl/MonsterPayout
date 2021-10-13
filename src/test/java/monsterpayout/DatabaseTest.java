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
        
    }

    @AfterEach
    void teardown() {
    }
	
	@Test                                               
    @DisplayName("Kirjaudutaan testikäyttäjälle")   
    void loginTest() {
		//Rekisteröidään uusi testikäyttäjä
		if(Tietokanta.checkUsername("testikäyttäjä123"))
			Tietokanta.deleteTestUser();
		Tietokanta.register("testikäyttäjä123", "testi", "testi123@gmail.com", "Testaaja", "Testaa");
        assertTrue(Tietokanta.login("testikäyttäjä123", "testi"));
    }
	
	@Test                                               
    @DisplayName("Muutetaan testikäyttäjän profiilia")   
    void profileEditingTest() {
		//Rekisteröidään uusi testikäyttäjä
		if(Tietokanta.checkUsername("testikäyttäjä123"))
			Tietokanta.deleteTestUser();
		Tietokanta.register("testikäyttäjä123", "testi", "testi123@gmail.com", "Testaaja", "Testaa");
		//Kirjaudutaan sisään
		Tietokanta.login("testikäyttäjä123", "testi");
		User.setLastname("UusiSukunimi");
        assertTrue(Tietokanta.saveProfileChanges());
    }
}
