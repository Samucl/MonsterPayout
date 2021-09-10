import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);  
	    System.out.println("Syötä etunimi");
	    String firstName = scanner.nextLine();
	    
	    System.out.println("Syötä sukunimi");
	    String lastName = scanner.nextLine();
	    
	    System.out.println("sähköpostiosoite");
	    String email = scanner.nextLine();
	    
	    String password1;
	    String password2;
	    while (true) {
		    System.out.println("Syötä salasana");
		    password1 = scanner.nextLine();
		    
		    System.out.println("Syötä salasana uudelleen");
		    password2 = scanner.nextLine();
		    
		    if !(password1.equals(password2)) {
		    	System.out.println("Salasanat eivät täsmää, yritä uudelleen");
		    } else {
		    	break;
		    }
	    }
	    try () {
	    	User user = new User(firstName, lastName, email, password1);
	    	System.out.println(user);
	    } catch (exception e) {
	    	System.out.println("Antamissa syötteissä on virhe.")
	    }
	}
}
