package test;
import model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class KayttajaTest {
	/*
	 * VANHENTUNUT TESTI
	 * 
	private User kayttaja = new User(1, "a", "b", "c", "d", "e@email.fi", 1);
	
	
	@Test
	public void testSetteritJaGetterit() {
		kayttaja.setId(666);
		kayttaja.setFirstname("testietunimi");
		kayttaja.setLastname("testisukunimi");
		kayttaja.setEmail("testi@email.fi");
		kayttaja.setPassword("testisalasana");
	
		assertEquals("testietunimi", kayttaja.getFirstname(), "Palauttaa väärän etunimen");
		assertEquals("testisukunimi", kayttaja.getLastname(), "Palauttaa väärän sukunimen");
		assertEquals("testi@email.fi", kayttaja.getEmail(), "Palauttaa väärän emailin");
		assertEquals("testisalasana", kayttaja.getPassword(), "Palauttaa väärän salasanan");
		assertEquals(666, kayttaja.getId(), "Palauttaa väärän Id:n");
	}
	
	@Test
	public void testCount() {
		User uusiKayttaja = new User(2, "q", "w", "e", "r", "t@email.fi", 2);
		assertEquals(2, User.getCount(), "Palauttaa väärän käyttäjämäärän");
	}
	*/
}
