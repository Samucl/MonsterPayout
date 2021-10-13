package monsterpayout;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import cardgames.Card;
import cardgames.Fast_poker;

public class PokerhandTest {
	
	Fast_poker game = new Fast_poker();
	Card[] cards = new Card[5];
	
	@Test                                               
    @DisplayName("Kuningasvärisuora")   
    void royalFlush() {
		cards[0] = new Card("hertta", 10);
		cards[1] = new Card("hertta", 11);
		cards[2] = new Card("hertta", 12);
		cards[3] = new Card("hertta", 13);
		cards[4] = new Card("hertta", 1);
        assertTrue(game.checkWinnings(cards) == 8);
    }
	
	@Test                                               
    @DisplayName("Värisuora")   
    void straightFlush() {
		cards[0] = new Card("hertta", 6);
		cards[1] = new Card("hertta", 7);
		cards[2] = new Card("hertta", 5);
		cards[3] = new Card("hertta", 3);
		cards[4] = new Card("hertta", 4);
        assertTrue(game.checkWinnings(cards) == 7);
    }
	
	@Test                                               
    @DisplayName("Neljä samaa")   
    void fourOfAKind() {
		cards[0] = new Card("hertta", 6);
		cards[1] = new Card("ruutu", 6);
		cards[2] = new Card("hertta", 5);
		cards[3] = new Card("hertta", 6);
		cards[4] = new Card("risti", 6);
        assertTrue(game.checkWinnings(cards) == 6);
    }
	
	@Test                                               
    @DisplayName("Täyskäsi")   
    void fullHouse() {
		cards[0] = new Card("hertta", 7);
		cards[1] = new Card("ruutu", 7);
		cards[2] = new Card("hertta", 2);
		cards[3] = new Card("hertta", 7);
		cards[4] = new Card("risti", 2);
        assertTrue(game.checkWinnings(cards) == 5);
    }
	
	@Test                                               
    @DisplayName("Väri")   
    void flush() {
		cards[0] = new Card("ruutu", 4);
		cards[1] = new Card("ruutu", 2);
		cards[2] = new Card("ruutu", 2);
		cards[3] = new Card("ruutu", 7);
		cards[4] = new Card("ruutu", 2);
        assertTrue(game.checkWinnings(cards) == 4);
		System.out.println(game.checkWinnings(cards));
    }
	
	@Test                                               
    @DisplayName("Suora")   
    void straight() {
		cards[0] = new Card("hertta", 5);
		cards[1] = new Card("ruutu", 3);
		cards[2] = new Card("hertta", 2);
		cards[3] = new Card("hertta", 4);
		cards[4] = new Card("risti", 6);
        assertTrue(game.checkWinnings(cards) == 3);
    }
	
	@Test                                               
    @DisplayName("Kolmoset")   
    void threeOfKind() {
		cards[0] = new Card("hertta", 5);
		cards[1] = new Card("ruutu", 3);
		cards[2] = new Card("hertta", 5);
		cards[3] = new Card("hertta", 5);
		cards[4] = new Card("risti", 6);
        assertTrue(game.checkWinnings(cards) == 2);
    }
	
	@Test                                               
    @DisplayName("Kaksi paria")   
    void twoPair() {
		cards[0] = new Card("hertta", 4);
		cards[1] = new Card("ruutu", 3);
		cards[2] = new Card("hertta", 2);
		cards[3] = new Card("hertta", 3);
		cards[4] = new Card("risti", 4);
        assertTrue(game.checkWinnings(cards) == 1);
    }
	
	@Test                                               
    @DisplayName("pari")   
    void pair() {
		cards[0] = new Card("hertta", 10);
		cards[1] = new Card("ruutu", 3);
		cards[2] = new Card("hertta", 2);
		cards[3] = new Card("hertta", 4);
		cards[4] = new Card("risti", 10);
        assertTrue(game.checkWinnings(cards) == 0);
    }
	
}