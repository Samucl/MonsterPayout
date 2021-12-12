package moneyrain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Session;
import view.MainApplication;
import view.MoneyRainDeadViewController;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Luokka sisältää kaiken logiikan MoneyRain peliin
 * @author Samuel Laisaar
 * @version 16.11.2021
 */
public class MoneyRain extends Canvas {
	
	private Stage stage;
	private static Timeline tl;
	private int timeInMillis;
	private int gameTime = 60000;
	private static final int height = 605;
	private static final int width = 720;
	private static final int PLAYER_WIDTH = 85;
	private static final int PLAYER_HEIGHT = 130;
	private static final int playableAreaLeft = 100;
	private static final int playableAreaRight = 600;
	private double playerXPos = width / 2;
	private double playerYPos = height - PLAYER_HEIGHT;
	private Image bg;
	private Image player;
	private Image bill;
	private Image click;
	private Image heart1;
	private Image heart2;
	private Image heart3;
	private Image poison;
	private Image megis;
	private boolean gameStarted = false;
	private int points;
	private int collectedCash;
	private int health = 3;
	private boolean dead = false;
	private List<Item> items = new ArrayList<>();
	private ResourceBundle texts;
	
	public MoneyRain(Stage stage) {
		try {
			texts = Session.getLanguageBundle();
			start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start(Stage stage) throws Exception{
		this.stage = stage;
		this.stage.setTitle("MoneyRain");
		bg = new Image(new FileInputStream("./src/main/resources/moneyrain/bg.jpg"));
		heart1 = new Image(new FileInputStream("./src/main/resources/moneyrain/heart1.png"));
		heart2 = new Image(new FileInputStream("./src/main/resources/moneyrain/heart2.png"));
		heart3 = new Image(new FileInputStream("./src/main/resources/moneyrain/heart3.png"));
		bill = new Image(new FileInputStream("./src/main/resources/moneyrain/bill.png"));
		poison = new Image(new FileInputStream("./src/main/resources/moneyrain/poison.png"));
		megis = new Image(new FileInputStream("./src/main/resources/moneyrain/megis.png"));
		player = new Image(new FileInputStream("./src/main/resources/moneyrain/playerRight.png"));
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		tl = new Timeline(new KeyFrame(Duration.millis(10), e -> {
			try {
				run(gc);
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
		}));
		tl.setCycleCount(Timeline.INDEFINITE);
		
		/*
		 * Pelaajan controlleri
		 */
		canvas.setOnMouseMoved(e -> {
			
			//Pelaajan kuvaa vaihdetaan riippuen siitä liiktaanko vasemmalle tai oikealle
			try {
				if(e.getX() - (PLAYER_WIDTH/2) < playerXPos)
						player = new Image(new FileInputStream("./src/main/resources/moneyrain/playerLeft.png"));
				if(e.getX() - (PLAYER_WIDTH/2) > playerXPos)
						player = new Image(new FileInputStream("./src/main/resources/moneyrain/playerRight.png"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			
			//Asetetaan pelaaja seuraamaan hiirtä ja rajoitukset kuinka pikälle pelaaja voi siirtyä ruudulla
			//PLAYER_WIDTH/2, jotta hiiri olisi pelaaja-kuvan keskellä, eikä pelaaja-kuvan vasemmalla
			if(e.getX() < playableAreaLeft)
				playerXPos = playableAreaLeft - (PLAYER_WIDTH/2);
			else if(e.getX() > playableAreaRight)
				playerXPos = playableAreaRight - (PLAYER_WIDTH/2);
			else
				playerXPos = e.getX() - (PLAYER_WIDTH/2);
			
		});


		/*
		 * Hiiren klikkauksesta tapahtuvat asiat
		 */
		canvas.setOnMouseClicked(e -> {
			if(!gameStarted) {
				gameStarted = true;
			}
			else {
				if(playerXPos < 80 && collectedCash >= 1) {
					switch(collectedCash) { //Annetaan pisteitä sen mukaan kuinka paljon rahaa on kädessä
						case 1:
							points+=1;
							break;
						case 2:
							points+=2;
							break;
						case 3:
							points+=4;
							break;
						case 4:
							points+=7;
							break;
						case 5:
							points+=10;
							break;
					}
					collectedCash--; //Rahat tyhjennetään kädestä autoon
				}
			}
		});
		
		this.stage.setScene(new Scene(new StackPane(canvas)));
		this.stage.show();
		tl.play();
	}
	
	private void run(GraphicsContext gc) throws FileNotFoundException {
		gc.drawImage(bg, 0, 0);
		if(collectedCash == 5) {
			gc.fillText(texts.getString("empty.hands") + "!", 25, height-110);
		}
		gc.setFont(Font.font(25));
		gc.setTextAlign(TextAlignment.LEFT);
		
		//Mitä tapahtuu pelin aikana
		if(gameStarted) {
			timeInMillis += 10; //Laskuri jolla seurataan pelissä käytettyä aikaa
			gameTime -= 10;
			gc.setFill(Color.WHITE);
			gc.drawImage(player, playerXPos, playerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
			gc.setFont(Font.font ("Arial Black", 20));
			gc.fillText(texts.getString("points") + ": " +  points + "    " + texts.getString("cash.in.hand") + ": " + collectedCash + "/5      " + texts.getString("time.left") + ": " + gameTime/1000 + "s", 10, 30);
			
			if(health == 3)
				gc.drawImage(heart3, 630, 10);
			else if(health == 2)
				gc.drawImage(heart2, 630, 10);
			else
				gc.drawImage(heart1, 630, 10);

			//Tehdään asioita kerran per 1s
			if(timeInMillis % 1000 == 0)
				createBill();
			
			//Tehdään asioita kerran per 2s
			if(timeInMillis % 2000 == 0)
				createPoison();
			
			//Tehdään asioita kerran per 20s
			if(timeInMillis % 20000 == 0)
				createMegis();
			
			if(gameTime <= 0)
				dead = true;
			
			items.forEach(item -> {
				
				//Jos item osuu pelaajaan / Box collider
				if(item.getYPos() > playerYPos - item.getHeight() &&
					(item.getXPos() >= playerXPos && item.getXPos() <= PLAYER_WIDTH + playerXPos ||
						item.getWidth() + item.getXPos() >= playerXPos && item.getWidth() + item.getXPos() <= PLAYER_WIDTH + playerXPos)) {
						item.isCollected();
						if(collectedCash < 5 && !item.isDangerous() && !item.isGivesHp()) //Jos item on seteli
							collectedCash++;
						if(item.isDangerous()) { //Jos item on vaarallinen
							health--;
							if(health <= 0) {
								dead = true;
							}
						}
						if(item.isGivesHp() && health <= 2) //Jos item antaa elämän
							health++;
				}
				item.fall(); //Laitetaan item tippumaan
				gc.drawImage(item.getImg(), item.getXPos(), item.getYPos(), item.getWidth(), item.getHeight()); //Tulostetaan item
			});
			
			if(dead) {
				tl.stop();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
				     FXMLLoader loader = new FXMLLoader();
				     loader.setLocation(MainApplication.class.getResource("MoneyRainDeadView.fxml"));
				     AnchorPane moneyraindeadView = (AnchorPane) loader.load();
				     MoneyRainDeadViewController c = loader.getController();
				     c.setPoints(points);
				     Scene moneyraindeadScene = new Scene(moneyraindeadView);
				     stage.setScene(moneyraindeadScene);
				     stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			
			//Poistetaan tippuva item
			items.removeIf(item -> 
				//Jos item tippuu ruudulta pois
				item.getYPos() > height ||
				
				//Jos item osuu pelaajaan kummalta puolelta tahansa
				item.getYPos() > playerYPos - item.getHeight() && (item.getXPos() >= playerXPos && item.getXPos() <= PLAYER_WIDTH + playerXPos && item.checkCollected() || 
				item.getWidth() + item.getXPos() >= playerXPos && item.getWidth() + item.getXPos() <= PLAYER_WIDTH + playerXPos && item.checkCollected())
			);

		}
		//Ennen pelin alkamista suoritettavat rivit
		else {
			click = new Image(new FileInputStream("./src/main/resources/moneyrain/click.png"));
			gc.drawImage(click, 0, 0);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setFill(Color.WHITE);
			gc.setFont(Font.font("Courier", 50));
			gc.fillText(texts.getString("click.to.play"), width/2, height/2);
			gc.setTextAlign(TextAlignment.LEFT);
		}
	}
	
	/**
	 * Luodaan Bill olioita
	 */
	private void createBill() {
		int xPos = new Random().nextInt((playableAreaRight - 69) - playableAreaLeft + 1) + playableAreaLeft;
		Item item = new Item(bill, 69, 33, xPos, 0, false, false);
		items.add(item);
	}
	
	/**
	 * Luodaan Poison olioita
	 */
	private void createPoison() {
		int xPos = new Random().nextInt((playableAreaRight - 69) - playableAreaLeft + 1) + playableAreaLeft;
		Item item = new Item(poison, 24, 48, xPos, 0, true, false);
		items.add(item);
	}
	
	/**
	 * Luodaan Megis olioita
	 */
	private void createMegis() {
		int xPos = new Random().nextInt((playableAreaRight - 69) - playableAreaLeft + 1) + playableAreaLeft;
		Item item = new Item(megis, 25, 57, xPos, 0, false, true);
		items.add(item);
	}
	
	public static Timeline getTl() {
		return tl;
	}
}