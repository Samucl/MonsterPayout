package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Tietokanta;

class DatabaseTest {

	@BeforeEach                                         
    void setUp() {
    }

    @Test                                               
    @DisplayName("Tietokantaan pitäisi saada yhteys")   
    void testConnection() {
        assertTrue(Tietokanta.testConnection());
    }

}
