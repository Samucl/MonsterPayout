package slalommadness;

import moneyrain.Item;
import view.MainApplication;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Tietokanta;

public class SlalomMadnessGame extends Application {
	
	private int timeInMillis;
	private int poleCount;
	private long startTime;
	private long finishTime;
	private Timeline tl;
	
	private static final int polesBeforeFinishLine = 10; //Pelin pituus
	private static final int width = 920;
	private static final int height = 780;
	private static final int PLAYER_WIDTH = 36;
	private static final int PLAYER_HEIGHT = 60;
	private static final int boundLeft = PLAYER_WIDTH * 4;
	private static final int boundRight = PLAYER_WIDTH * 4;
	
	private Image skier;
	private Image leftPole;
	private Image rightPole;
	private Image spruce;
	private Image stone1;
	private Image stone2;
	private Image finish;
	
	private int points = 0;
	private int speed = 1;
	private boolean dead = false;
	private boolean finished = false;
	private boolean sprucesOnStartCreated = false;
	
	private double playerXPos = (width/2-PLAYER_WIDTH/2); //Keskelle ruutua
	private double playerYPos = (height/4 - PLAYER_HEIGHT);
	
	private List<Item> items = new ArrayList<>();
	
	private boolean running = false, goFaster = false, goSlower = false, goLeft = false, goRight = false;
	
	
	public SlalomMadnessGame(Stage stage) {
		this.timeInMillis = 0;
		this.poleCount = 0;
		
		try {
			start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}   
	
	
	public void start(Stage stage) throws Exception {
		stage.setTitle("Slalom Madness");
		
		skier = new Image(new FileInputStream("./src/main/resources/slalommadness/skier.png"));
		leftPole = new Image(new FileInputStream("./src/main/resources/slalommadness/leftPole.png"));
		rightPole = new Image(new FileInputStream("./src/main/resources/slalommadness/rightPole.png"));
		spruce = new Image(new FileInputStream("./src/main/resources/slalommadness/spruce.png"));
		stone1 = new Image(new FileInputStream("./src/main/resources/slalommadness/stone1.png"));
		stone2 = new Image(new FileInputStream("./src/main/resources/slalommadness/stone2.png"));
		finish = new Image(new FileInputStream("./src/main/resources/slalommadness/finish.png"));
		
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
		
		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch (e.getCode()) {
					case ENTER: 
						if (!running && !dead && !finished) {
							running = true; 
							startTime = System.currentTimeMillis();
							
						} else if (dead || finished) {
							stage.close();
							toSlalomMadnessMenu();
							
						}	
						break;	
						
					case UP: if (running) {
						goSlower = true;
					} break;
					case DOWN: if (running) {
						goFaster = true;
						speed = 2;
					} break;
					case LEFT: if (running) {
						goLeft = true;
					} break;
					case RIGHT: if (running) {
						goRight = true;
					} break;
					default: break;
				}
			}
		});
		
		canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch (e.getCode()) {
					case UP: if (running) {
						goSlower = false;
						speed = 1;
					} break;
					case DOWN: if (running) {
						goFaster = false;
						speed = 1;
					} break;
					case LEFT: if (running) {
						goLeft = false;
					} break;
					case RIGHT: if (running) {
						goRight = false;
					} break;
					default: break;
				}
			}
		});
		
		stage.setScene(new Scene(new StackPane(canvas)));
		stage.setResizable(false);
		stage.show();
		canvas.requestFocus(); //Tarvitaan, jotta ohjelma reagoisi keyEventeihin
		tl.play(); //run()
	}

	private void run(GraphicsContext gc) {
		
		if (running) {
			
			timeInMillis += speed * 10; //Laskuri jolla seurataan pelissä käytettyä aikaa

			gc.clearRect(0, 0, width, height); //Tyhjennetään näyttö
			gc.drawImage(skier, playerXPos , playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
			
			//Pelaajan liikuttaminen ruudulla
			if (goLeft && !(playerXPos-2 < boundLeft - PLAYER_WIDTH)) { //Liikkumisrajoitus vasemmalla
				gc.clearRect(playerXPos, playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT); //Tyhjennetään pelaajan kokoinen alue pelaajan koordinaateilla
				playerXPos-=2;
				gc.drawImage(skier, playerXPos , playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
			}  
			if (goRight && !(playerXPos+2 > width - boundRight)) { //Liikkumisrajoitus oikealla
				gc.clearRect(playerXPos, playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
				playerXPos+=2;
				gc.drawImage(skier, playerXPos , playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
			}  
			if (goFaster && !(playerYPos-1 > height)) { //Liikkumisrajoitus alhaalla
				if (timeInMillis % 20 == 10) {//Nopeutta ei voida tuplata sillä hetkellä kun aikaa kulunut esim. 10 ms, koska silloin laskuri menisi -> 10-30-50-70-90 jne. eikä saavuttaisi koskaan tasalukua, jonka välein itemit spawnaavat
					timeInMillis+= 10;
				}
				speed = 2;
				if (timeInMillis % 100 == 0) { //Siirretään hahmoa pikselin verran ruudulla vain joka kuudennella kierroksella, jotta hahmo ei liikkuisi liian nopeasti
					playerYPos++;
					gc.clearRect(playerXPos, playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
					gc.drawImage(skier, playerXPos , playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
				}
			}  
			if (goSlower && !(playerYPos-1 < PLAYER_HEIGHT)) { //Liikkumisrajoitus ylhäällä
				speed = 1;
				if (timeInMillis % 20 == 0) //Siirretään hahmoa ruudulla ylös hitaammin kuin objektit liikkuvat (objektit liikkuvat 10 ms välein), jotta ei näytä siltä, että hahmo pysähtyisi kokonaan
					playerYPos--;
				gc.clearRect(playerXPos, playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
				gc.drawImage(skier, playerXPos , playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
			}
			
			//--------------------------------------------------
			//Objektien luonti tietyin aikavälein
			
			if (timeInMillis % 3000 == 0 && !(timeInMillis % 6000 == 0) && poleCount < polesBeforeFinishLine) {
				createPoleLeft();
			}
			
			if (timeInMillis % 6000 == 0 && poleCount < polesBeforeFinishLine) {
				createPoleRight();
			}
			
			if (timeInMillis % 190 == 0 && poleCount < polesBeforeFinishLine) {
				createObject();
			}
			
			if (timeInMillis % 200 == 0) {
				createSprucesOnSides(2);
			}
			
			if (poleCount == polesBeforeFinishLine && timeInMillis == 6000 * polesBeforeFinishLine + 3000) {
				createFinishLine();
			}
			
			//---------------------------------------------------
			
			items.forEach(item -> {
				
				if (item.getName().equals("leftPole")) {
					if (playerYPos-PLAYER_HEIGHT/2 < item.getYPos() && playerYPos-PLAYER_HEIGHT/2 > item.getYPos() - item.getHeight()) { //Jos pelihahmo on kepin kanssa samalla korkeudella
						if (playerXPos+PLAYER_WIDTH <= (item.getXPos())) { //Jos hahmo on kepin vasemmalla puolella
							if (!item.checkCollected()) {
								points++;
								item.isCollected();
							}
						}			
					} 
				} else if (item.getName().equals("rightPole")) {
					if (playerYPos-PLAYER_HEIGHT/2 < item.getYPos() && playerYPos-PLAYER_HEIGHT/2 > item.getYPos() - item.getHeight()) {
						if ((playerXPos) > (item.getXPos() + item.getWidth() / 2)) {
							if (!item.checkCollected()) {
								points++;
								item.isCollected();
							}
						} 
					}
				//Jos pelaaja törmää vaaralliseen objektiin
				} else if (item.isDangerous()) {
					if ((item.getYPos() + item.getHeight() - item.getHeight()/5 <= playerYPos + PLAYER_HEIGHT) 
							&& (item.getYPos() + item.getHeight() >= playerYPos + PLAYER_HEIGHT / 1.5)) {
						if (item.getXPos() + item.getWidth() / 2 <= playerXPos + PLAYER_WIDTH && item.getXPos() + item.getWidth() / 2 >= playerXPos) {
							dead = true;
							running = false;
						}
					}
					
				} else if (item.getName().equals("finish")) {	
					if (playerYPos > item.getYPos() + item.getHeight()) {	
						long time = System.currentTimeMillis();
						finishTime = time - startTime;
						finished = true;
						running = false;
					}
				}
				
				
				gc.clearRect(item.getXPos(), item.getYPos(), item.getWidth(), item.getHeight());
				item.ascend(speed); //Laitetaan item liikkumaan vertikaalisesti
				
				gc.drawImage(skier, playerXPos, playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
				
				if (item.getYPos() < 0) { //Jos item häviää kuvaruudusta niin poistetaan
					item = null;
				}
	
			});
			
			
			//----------------------------------------------------
			
			//Kolmiulotteisuuden tuntua lisäävä koodi
			items.forEach(item -> {
				gc.drawImage(item.getImg(), item.getXPos(), item.getYPos(), item.getWidth(), item.getHeight()); //Piirretään item päällimmäiseksi, eli pelaaja jää esim. kuusen taakse
				
				if (item.getYPos() + item.getHeight() > playerYPos - PLAYER_HEIGHT / 2        //Mutta jos pelaajan koordinaatit ovat tarpeeksi alhaalla itemiin nähden, niin pelaaja piirtyy sen päälle
						&& item.getYPos() + item.getHeight() / 2.7 < playerYPos) {
					gc.drawImage(skier, playerXPos, playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
				}
			});
			//-----------------------------------------------------
			
			gc.setFont(Font.font ("Arial Black", 20));
			gc.fillText("Pisteet: " + points, 320, 30);
			gc.fillText("Aika: " + String.valueOf((System.currentTimeMillis() - startTime) / 1000.0) + " s", 460, 30);
			
		} else if (!running && !finished && !dead) { //Jos peli ei vielä käynnissä 
			
			gc.setFont(Font.font ("Arial Black", 30));
			gc.fillText("Paina  ↵  aloittaaksesi!", 285, 352);
			gc.setFont(Font.font ("Verdana", 16));
			gc.fillText("Ohje:", 440, 413);
			gc.fillText("Liiku nuolinäppäimillä. Väistele esteitä ja yritä päästä maaliin.", 220, 450);
			gc.fillText("Kierrä punaiset kepit vasemmalta ja siniset oikealta.", 220, 482);
			gc.fillText("Jokaisesta ohilasketusta kepistä saat kaksi sekuntia sanktiota.", 220, 512);

			gc.drawImage(skier, playerXPos , playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
			
			if (!sprucesOnStartCreated) {
				createSprucesOnSidesOnStart(60);
				items.forEach(item -> {
					gc.drawImage(item.getImg(), item.getXPos(), item.getYPos(), item.getWidth(), item.getHeight());
				});
			}

		} else if (!running && finished) { //Jos maaliin on tultu
			tl.stop();
			
			gc.setFont(Font.font ("Arial Black", 30));
			
			double score;
			
			if (points < poleCount * 2) { //Jos on tullut ohilaskuja
				int missedPoles = poleCount * 2 - points;
				score = (finishTime / 1000.0 + missedPoles * 2);
				
				gc.setFill(Color.RED);
				gc.fillText("Sanktio: " + missedPoles + " ohilaskua = " + missedPoles * 2 + " s", 250, 280);
				gc.setFill(Color.BLACK);
				gc.fillText("Aika: " + score + " s", 350, 340);
				
			} else {
				score = finishTime / 1000.0;
				gc.fillText("Aika: " + score + " s", 350, 220);
			}
			
			if (Tietokanta.getHighScoreTime("Slalom Madness") == 0 || Tietokanta.getHighScoreTime("Slalom Madness") > score) {
				gc.setFill(Color.GOLD);
				gc.setFont(Font.font ("Arial Black", 24));
				gc.fillText("UUSI ENNÄTYS!", 350, 390);
			}

			Tietokanta.setHighScoreTime(score, "Slalom Madness");
			
			gc.setFill(Color.BLACK);
			gc.setFont(Font.font ("Arial Black", 24));
			gc.fillText("Palaa menuun painamalla  ↵", 294, 730);
			
		} else if (dead) { //Jos pelaaja törmännyt esteeseen
			tl.stop();
			
			gc.setFont(Font.font ("Arial Black", 30));
			gc.fillText("GAME OVER", 356, 240);
			gc.setFont(Font.font ("Arial Black", 24));
			gc.fillText("Palaa menuun painamalla  ↵", 294, 292);
		}
	}

	private void createSprucesOnSides(int number) {
		int xPos = new Random().nextInt(boundLeft - boundLeft/3) - 16; //Randomoitu sijainti x-akselilla
		double sizeMultiplier = new Random().nextDouble() + 1; //Arvotaan objektin "kokokerroin"
		int spruceWidth = (int) (sizeMultiplier * 0.618 * PLAYER_HEIGHT);
		int spruceHeight = (int) (sizeMultiplier * PLAYER_HEIGHT);
		Item spruceItem = new Item("spruce", spruce, spruceWidth, spruceHeight, xPos, height, true, false);
		items.add(spruceItem);
		
		int xPos2 = new Random().nextInt(boundRight - boundRight/3) + width - boundRight; //Randomoitu sijainti x-akselilla
		double sizeMultiplier2 = new Random().nextDouble() + 1; //Arvotaan objektin "kokokerroin"
		int spruceWidth2 = (int) (sizeMultiplier2 * 0.618 * PLAYER_HEIGHT);
		int spruceHeight2 = (int) (sizeMultiplier2 * PLAYER_HEIGHT);
		Item spruceItem2 = new Item("spruce", spruce, spruceWidth2, spruceHeight2, xPos2, height, true, false);
		items.add(spruceItem2);
		
	}

	private void createSprucesOnSidesOnStart(int number) {
		for (int i = 0 ; i < number/2 ; i++) {
			int xPos = new Random().nextInt(boundLeft - boundLeft/3) - 16; //Randomoitu sijainti x-akselilla
			double sizeMultiplier = new Random().nextDouble() + 1; //Arvotaan objektin "kokokerroin"
			int spruceWidth = (int) (sizeMultiplier * 0.618 * PLAYER_HEIGHT);
			int spruceHeight = (int) (sizeMultiplier * PLAYER_HEIGHT);
			int yPos = new Random().nextInt(height - spruceHeight);
			Item spruceItem = new Item("spruce", spruce, spruceWidth, spruceHeight, xPos, yPos, true, false);
			items.add(spruceItem);
		}
		
		for (int i = 0 ; i < number/2 ; i++) {
			int xPos = new Random().nextInt(boundRight - boundRight/3) + width - boundRight; //Randomoitu sijainti x-akselilla
			double sizeMultiplier = new Random().nextDouble() + 1; //Arvotaan objektin "kokokerroin"
			int spruceWidth = (int) (sizeMultiplier * 0.618 * PLAYER_HEIGHT);
			int spruceHeight = (int) (sizeMultiplier * PLAYER_HEIGHT);
			int yPos = new Random().nextInt(height - spruceHeight);
			Item spruceItem = new Item("spruce", spruce, spruceWidth, spruceHeight, xPos, yPos, true, false);
			items.add(spruceItem);
		} 
		
		sprucesOnStartCreated = true;
	}

	//Vasemman kepin luonti
	public void createPoleLeft() {
		int xPos = new Random().nextInt((width / 3)) + (boundLeft + PLAYER_WIDTH); //Randomoitu sijainti x-akselilla
		int poleWidth = (int) PLAYER_WIDTH / 2;
		int poleHeight = (int) PLAYER_HEIGHT;
		Item item = new Item("leftPole", leftPole, poleWidth, poleHeight, xPos, height, false, false);
		items.add(item);
	}
	
	//Oikean kepin luonti
	public void createPoleRight() {
		int xPos = new Random().nextInt((width / 3)) + (width - (width/3 + boundLeft + PLAYER_WIDTH)); //Randomoitu sijainti x-akselilla
		int poleWidth = (int) PLAYER_WIDTH / 2;
		int poleHeight = (int) PLAYER_HEIGHT;
		Item item = new Item("rightPole", rightPole, poleWidth, poleHeight, xPos, height, false, false);
		items.add(item);
		poleCount++;
	}
	
	//Maalin luonti
	private void createFinishLine() {
		int lineWidth = (int) width/2;
		int lineHeight = (int) PLAYER_HEIGHT * 3;
		int xPos = width / 4;
		Item item = new Item("finish", finish, lineWidth, lineHeight, xPos, height, false, false);
		items.add(item);
	}
	
	//Objektin arvonta ja luonti
	public void createObject() {
		
		if (new Random().nextInt(3) == 1) {
			switch (new Random().nextInt(3)) {
				case 0: //Kuusi
					int xPos = new Random().nextInt(width - boundRight - boundLeft) + boundLeft; //Randomoitu sijainti x-akselilla
					double sizeMultiplier = new Random().nextDouble() + 1; //Arvotaan objektin "kokokerroin"
					int spruceWidth = (int) (sizeMultiplier * 0.618 * PLAYER_HEIGHT);
					int spruceHeight = (int) (sizeMultiplier * PLAYER_HEIGHT);
					Item spruceItem = new Item("spruce", spruce, spruceWidth, spruceHeight, xPos, height, true, false);
					items.add(spruceItem);
					break;
				case 1: //Kivi
					int xPos2 = new Random().nextInt(width - boundRight - boundLeft) + boundLeft; //Randomoitu sijainti x-akselilla
					double sizeMultiplier2 = new Random().nextDouble() + 0.5; //Arvotaan objektin "kokokerroin"
					int stone1Width = (int) (sizeMultiplier2 * PLAYER_HEIGHT / 1.5);
					int stone1Height = (int) (sizeMultiplier2 * PLAYER_HEIGHT / 1.5);
					Item stoneItem = new Item("stone1", stone1, stone1Width, stone1Height, xPos2, height, true, false);
					items.add(stoneItem);
					break;
				case 2: //Toinen kivi
					int xPos3 = new Random().nextInt(width - boundRight - boundLeft) + boundLeft; //Randomoitu sijainti x-akselilla
					double sizeMultiplier3 = new Random().nextDouble() + 0.5; //Arvotaan objektin "kokokerroin"
					int stone2Width = (int) (sizeMultiplier3 * PLAYER_HEIGHT / 1.5);
					int stone2Height = (int) (sizeMultiplier3 * PLAYER_HEIGHT / 1.5);
					Item stoneItem2 = new Item("stone2", stone2, stone2Width, stone2Height, xPos3, height, true, false);
					items.add(stoneItem2);
					break;
			}
		}

	}
	
	public void toSlalomMadnessMenu() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("SlalomMadnessMenuView.fxml"));
            AnchorPane slalomMenuView = (AnchorPane) loader.load();
            Scene slalomMenuScene = new Scene(slalomMenuView);
			Stage window = new Stage();
			window.setOnCloseRequest(evt -> {
				if(tl != null) {
					tl.stop();//Pysäyttää pelin timelinen jos suljetaan ikkuna kesken pelin.
					tl = null;
				}
			});
			window.setScene(slalomMenuScene);
			window.setResizable(false);
			window.show();
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
	}
	
	public Timeline getTl() {
		return tl;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}