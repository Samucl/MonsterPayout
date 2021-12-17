package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;

import javafx.scene.image.Image;

/**
 * Tähän luokkaan tallennetaan "istunnon" tiedot, kuten tilaushistoria
 */
public class Session {
	private Session instance = null;

	private static Order[] orders;

	private static Image[] avatarImages;

	private static List<Properties> languageProperties;

	private static Properties userProperties = new Properties();

	private static ResourceBundle languageBundle;

	private static Locale userLocale;

	private static String userPropertiesPath = "properties/user.properties";
	
	//private static File propertiesPath = getUserPropertiesDirectory();
	private static String userPropertiesFileName = "user.properties";
	//private static File avatarsPath = new File(monsterpayout.app.Launch.class.getClassLoader().getResource("avatars").getFile());
	private static InputStream avatarsPath = monsterpayout.app.Launch.class.getClassLoader().getResourceAsStream("avatars");
	private static String avatarsPathString = "avatars/";
	
	private static String[] langSettings = {"en","US"};
	
	private Session() {
	}
	
	public static InputStream getFile(String file) {
		return Session.class.getClassLoader().getResourceAsStream(file);
	}

	public static void initialization() {
		try{loadAvatarImages();} catch(Exception e) {}
		//loadUserProperties();
		loadLanguageBundle();
		//loadLanguages();
	}

	public Session getInstace() {
		if(instance == null)
			instance = new Session();
		return instance;
	}

	public static ResourceBundle changeToLanguage(String lang, String country) {
		languageBundle = ResourceBundle.getBundle("lang.language", new Locale(lang,country));
		//saveLanguage();
		//loadUserProperties();
		//loadLanguageBundle();
		return languageBundle;
	}

	public static void setLanguageBundle(ResourceBundle lB) {
		languageBundle = lB;
		//saveLanguage();
	}

	/*
	private static void saveLanguage() {
		try {
			userProperties.load(getUserPropertiesStream());
			//Jos tähän tarvittavat avaimet puuttuvat tiedostosta, luodaan ne
			if(userProperties.getProperty("language")==null||userProperties.getProperty("country")==null) {
				Locale locale = new Locale("en","US");
				if(userProperties.getProperty("language")==null)
					userProperties.setProperty("language", languageBundle.getString("info.language"));
				if(userProperties.getProperty("country")==null)
					userProperties.setProperty("country", languageBundle.getString("info.country"));

				userProperties.store(new FileOutputStream(getUserPropertiesFile()), "missing keys added");
			} else {
				userProperties.setProperty("language", languageBundle.getString("info.language"));
				userProperties.setProperty("country", languageBundle.getString("info.country"));
			}
			userProperties.store(new FileOutputStream(getUserPropertiesFile()), "language changed");
			System.out.println(userProperties.getProperty("language")+userProperties.getProperty("country"));
		} catch (FileNotFoundException e) {
			createUserProperties();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/

	public static void loadLanguageBundle() {
		/*
		 * Käytetään user.properties tiedoston language ja country avaimia
		 * Locale:n luomisessa ja sen avulla haetaan oikea kieli bundle
		 */
		//userLocale = new Locale(userProperties.getProperty("language"),userProperties.getProperty("country"));
		userLocale = new Locale(langSettings[0],langSettings[1]);
		languageBundle = ResourceBundle.getBundle("lang.language",userLocale);
	}

	public static ResourceBundle getLanguageBundle() {
		if(languageBundle == null)
			initialization();
		return languageBundle;
	}
	
	private static InputStream getUserPropertiesStream() {
		InputStream stream = monsterpayout.app.Launch.class.getResourceAsStream(userPropertiesPath);
		return stream;
	}
	
	private static File getUserPropertiesDirectory() {
		File directory = new File(monsterpayout.app.Launch.class.getClassLoader().getResource("properties").getFile());
		if(!directory.exists())
			directory.mkdir();
		return directory;
	}
	
	/*
	private static File getUserPropertiesFile() throws FileNotFoundException {
		//Luetellaan kaikki tiedostot resources/properties kansiosta
		File[] file = propertiesPath.listFiles();
		File propertyToLoad = null;
		//Jos sieltä löytyy tiedosto(ja), etsitään haluama user.properties tiedosto
		if(file!=null) {
			for(File f : file) {
				if(f.getName().equals(userPropertiesFileName)) {
					propertyToLoad = f;
					break;
				}
			}
		}
		if(propertyToLoad==null)
			throw new FileNotFoundException("Couldn't find file: "+userPropertiesFileName);
		return propertyToLoad;
	}
	*/

	/*
	private static void loadUserProperties() {
		try {
			userProperties.load(new FileInputStream(getUserPropertiesFile()));
			
			if(userProperties.getProperty("language")==null||userProperties.getProperty("country")==null) {
				Locale locale = new Locale("en","US");
				if(userProperties.getProperty("language")==null)
					userProperties.setProperty("language", locale.getLanguage());
				if(userProperties.getProperty("country")==null)
					userProperties.setProperty("country", locale.getCountry());

				userProperties.store(new FileOutputStream(getUserPropertiesFile()), "missing keys added");
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
		File userFile = new File(propertiesPath, userPropertiesFileName);
		userFile.getParentFile().mkdir();
		try {
			userFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Locale locale = new Locale("en","US");
			userProperties.load(new FileInputStream(getUserPropertiesFile()));

			userProperties.setProperty("language", locale.getLanguage());
			userProperties.setProperty("country", locale.getCountry());

			userProperties.store(new FileOutputStream(getUserPropertiesFile()), "new user.properties file created");
			System.out.println(userProperties.getProperty("language")+userProperties.getProperty("country"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/

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
			List<Properties> properties = new ArrayList<>();
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

	public static void loadAvatarImages() throws IOException{
		//File path = new File("./src/main/resources/avatars");
		//File path = new File("./src/main/resources/avatars");
		//File path = new File(Session.class.getClassLoader().getResource("avatars").getFile());
		InputStreamReader isr = new InputStreamReader(avatarsPath);
		BufferedReader br = new BufferedReader(isr);
		List<String> files = new ArrayList<String>();
		for(String name = br.readLine(); name !=null; name = br.readLine()) {
			files.add(name);
		}
		if(files.size()>0) {
			avatarImages = new Image[files.size()];
			for(String s : files) {
				System.out.println(s);
			}
			for(int i = 0; i < files.size(); i++) {
				avatarImages[i] = new Image(getFile(avatarsPathString+files.get(i)));
			}
		}
		/*
		File[] allAvatarFiles = avatarsPath.listFiles();
		//Lajitellaan sijainnin tiedostot aakkos järjestykseen
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
		*/

	}

	public static NumberFormat getNumberFormatter() {
		return NumberFormat.getInstance(userLocale);
	}

}
