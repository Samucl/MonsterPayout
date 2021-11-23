import java.util.Locale;
import java.util.ResourceBundle;

import model.Session;

public class ForTesting {

	public static void main(String[] args) {
		/*
		 * Lokalisoinnin testausta
		 */
		Locale locale = new Locale("en", "US");
		/*
		 * Haetaan resurssit lang-pakkauksesta (Humaa ei etuliitettä properties!)
		 */
		ResourceBundle texts = ResourceBundle.getBundle("lang.language",locale);
		System.out.println(texts.getString("games.button"));
		System.out.println(texts.getString("login"));
		
		/*
		 * Yritetään vaihtaa johonkin muuhun jolloin resurssin ei pitäisi vaihtua:
		 * miksi valtsee fi_FI päätteisen tiedoston?
		 * Johtuu, ehkä siitä, että järjestelmän oletus on fi_FI ja sille
		 * löytyy tiedosto
		 */
		locale = new Locale("se","SE");
		texts = ResourceBundle.getBundle("lang.language",locale);
		System.out.println(texts.getString("login"));
		
		/*
		 * Vaihdetaan kieleksi suomi ja maaksi Suomi "fi" "FI"
		 */
		locale = new Locale("fi","FI");
		texts = ResourceBundle.getBundle("lang.language",locale);
		System.out.println(texts.getString("login"));
		
		/*
		 * Vaihdetaan kieleksi englanti ja maaksi Yhdysvallat "en" "US"
		 */
		locale = new Locale("en","US");
		texts = ResourceBundle.getBundle("lang.language",locale);
		System.out.println(texts.getString("login"));
		
		Session.initialization();

	}

}
