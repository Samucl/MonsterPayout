package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.scene.image.Image;

public class Session {
	private Session instance = null;
	
	/*
	 * Tänne luokkaan tallennetaan "istunnon" tiedot, kuten tilaushistoria
	 */
	private static Order[] orders;
	
	private static Image[] avatarImages;
	
	private static List<Properties> languageProperties;
	
	private static Properties userProperties = new Properties();
	
	private static ResourceBundle languageBundle;
	
	private static Locale userLocale;
	
	private static String userPropertiesPath = "./src/main/resources/properties/user.properties";
	
	private Session() {
	}
	
	public static void initialization() {
		loadAvatarImages();
		loadUserProperties();
		loadLanguageBundle();
		loadLanguages();
	}
	
	public Session getInstace() {
		if(instance == null)
			instance = new Session();
		return instance;
	}
	
	public static void loadLanguageBundle() {
		/*
		 * Käytetään user.properties tiedoston language ja country avaimia
		 * Locale:n luomisessa ja sen avulla haetaan oikea kieli bundle
		 */
		userLocale = new Locale(userProperties.getProperty("language"),userProperties.getProperty("country"));
		languageBundle = ResourceBundle.getBundle("lang.language",userLocale);
	}
	
	public static ResourceBundle getLanguageBundle() {
		if(languageBundle == null)
			initialization();
		return languageBundle;
	}
	
	private static void loadUserProperties() {
		try {
			userProperties.load(new FileInputStream(userPropertiesPath));
			/*
			 * Jos tähän tarvittavat avaimet puuttuvat tiedostosta, luodaan ne
			 */
			if(userProperties.getProperty("language")==null||userProperties.getProperty("country")==null) {
				Locale locale = new Locale("en","US");
				if(userProperties.getProperty("language")==null)
					userProperties.setProperty("language", locale.getLanguage());
				if(userProperties.getProperty("country")==null)
					userProperties.setProperty("country", locale.getCountry());
				
				userProperties.store(new FileOutputStream(userPropertiesPath), "missing keys added");
			}
			System.out.println(userProperties.getProperty("language")+userProperties.getProperty("country"));
		} catch (FileNotFoundException e) {
			createUserProperties();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void createUserProperties() {
		/*
		 * Jos user.properties tiedosto jostain syystä puuttuu, niin luodaan uusi
		 */
		File userFile = new File(userPropertiesPath);
		userFile.getParentFile().mkdir();
		try {
			userFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Locale locale = new Locale("en","US");
			userProperties.load(new FileInputStream(userPropertiesPath));
			
			userProperties.setProperty("language", locale.getLanguage());
			userProperties.setProperty("country", locale.getCountry());
			
			userProperties.store(new FileOutputStream(userPropertiesPath), "new user.properties file created");
			System.out.println(userProperties.getProperty("language")+userProperties.getProperty("country"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setOrders(Order[] orders) {
		Session.orders = orders;
	}
	
	public static Order[] getOrders() {
		if(orders == null)
			initialization();
		return orders;
	}
	
	public static Image getAvatar(int index) {
		if(avatarImages == null)
			initialization();
		return avatarImages[index];
	}
	
	public static void loadLanguages() {
		try {
			File path = new File("./src/main/java/lang/");
			File[] allFiles = path.listFiles();
			List<Properties> properties = new ArrayList<Properties>();
			for(File f : allFiles) {
				Properties property = new Properties();
				try {
					property.load(new FileInputStream(f.getPath()));
					System.out.println(
							property.getProperty("info.language")+
							property.getProperty("info.country")+
							property.getProperty("info.name")
							);
					/*
					 * Katsotaan, että tiedostosta löytyy nämä 3 avainta, jolloin 
					 * se lasketaan sovelluksen kielitiedostoksi
					 */
					if(property.getProperty("info.language")!=null
							&&property.getProperty("info.country")!=null
							&&property.getProperty("info.name")!=null
							)
						properties.add(property);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			languageProperties = properties;
			System.out.println("Kielitiedostoja löytyi: "+properties.size());
		} catch(Exception e) {
			System.err.println("Kielitiedostoja ei voitu ladata");
		}
		
	}
	
	public static List<Properties> getLanguages(){
		return languageProperties;
	}
	
	public static void loadAvatarImages() {
		//File path = new File("./src/main/resources/avatars");
		File path = new File("./src/main/resources/avatars");
		System.out.println(path);
		File[] allAvatarFiles = path.listFiles();
		/*
		 * Lajitellaan sijainnin tiedostot aakkos järjestykseen
		 */
		if(allAvatarFiles!=null) {
			Arrays.sort(allAvatarFiles);
			for(File file : allAvatarFiles) {
			    System.out.println(file);
			}
			System.out.println("Löytyi "+allAvatarFiles.length+" kuvaa");
			
			avatarImages = new Image[allAvatarFiles.length];
			
			for(int i = 0; i < allAvatarFiles.length; i++) {
				try {
					avatarImages[i] = new Image(new FileInputStream(allAvatarFiles[i]));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
