# MonsterPayout

Ohjelma on toteutettu Metropolian Tieto- ja viestintätekniikan kurssilla ryhmän 12 kanssa.

## Visio

MonsterPayout on ohjelmisto, joka sisältää kasino- ja arcadepelejä yhdessä virtuaalikasinossa.  Kasinopeleiksi kutsutaan pelejä, joissa ohjelman maksullista valuuttaa (krediittejä) käytetään uhkapelaamiseen. Arcadepelejä voi pelata joko ilmaiseksi tai käyttämällä panoksena ohjelman ilmaista valuuttaa (kolikoita). Pelaajan on mahdollista vaihtaa kolikoita krediitteihin ohjelman sisäisen kaupan avulla. Tämä tekee MonsterPayoutista omanlaisen, antamalla pelaajille mahdollisuuden pelata kasinopelejä ilmaiseksi.

## Kehitysympäristö

Ohjelmointikielenä on Java. Projektityökaluna Apache Maven, jonka standardien mukaisesti tietorakenne on järjestetty. Käyttöliittymien fxml-näkymät on luotu Scene Builder-ohjelmistolla. Tietokannan hallintaan on käytetty Metropolian sisäisessä pilvessä toimivalla  educloud-palvelimella pyörivää MariaDB:tä.

**Asennusohje:**

1.  Tallenna  MonsterPayout-projekti GitLab  repositorystä.
    
2.  Tuo projekti Eclipse-ohjelmointiympäristöön.
    
	a.  Kokoaja versio: JDK 15
 
	b.  Tarvittavat lisäosat Eclipsen  Marketplace:sta:
    
	 - E(fx)clipse

3.  Käyttäessä ohjelmaa tarvitset Metropolian-VPN yhteyden.
  
4.  Ohjelma suoritetaan Launcher.java luokasta (src/main/java/monsterpayout.app).
