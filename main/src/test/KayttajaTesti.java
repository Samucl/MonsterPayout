package test;
import java.util.Scanner;
import model.Kayttaja;

public class KayttajaTesti {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);  
	    System.out.println("Sy�t� etunimi");
	    String firstName = scanner.nextLine();
	    
	    System.out.println("Sy�t� sukunimi");
	    String lastName = scanner.nextLine();
	    
	    System.out.println("s�hk�postiosoite");
	    String email = scanner.nextLine();
	    
	    int tiliId = 69;
	    
	    String password1;
	    String password2;
	    while (true) {
		    System.out.println("Sy�t� salasana");
		    password1 = scanner.nextLine();
		    
		    System.out.println("Sy�t� salasana uudelleen");
		    password2 = scanner.nextLine();
		    
		    if (!password1.equals(password2)) {
		    	System.out.println("Salasanat eiv�t t�sm��, yrit� uudelleen");
		    } else {
		    	break;
		    }
	    }
	    try {
	    	Kayttaja user = new Kayttaja(firstName, lastName, email, password1, tiliId);
	    	System.out.println(user);
	    } catch (Exception e) {
	    	System.out.println("Antamissa sy�tteiss� on virhe.");
	    }
	}
}
