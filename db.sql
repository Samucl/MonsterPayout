CREATE DATABASE kasino;
USE kasino;

CREATE TABLE Tili
(
  TiliID INT NOT NULL AUTO_INCREMENT,
  KolikkoSaldo FLOAT NOT NULL,
  KrediittiSaldo FLOAT NOT NULL,
  PRIMARY KEY (TiliID)
);

CREATE TABLE Tuote
(
  Tuotenumero INT NOT NULL AUTO_INCREMENT,
  Kuvaus VARCHAR(100) NOT NULL,
  Hinta INT NOT NULL,
  KrediittienMaara FLOAT NOT NULL,
  Alennuskerroin INT NOT NULL,
  KolikoidenMaara FLOAT NOT NULL,
  Myynnissa TINYINT NOT NULL,
  PRIMARY KEY (Tuotenumero)
);

INSERT INTO Tuote (Kuvaus, Hinta, KrediittienMaara, Alennuskerroin, KolikoidenMaara, Myynnissa) VALUES ("Lucky Loot", 9.99,  10, 1, 40, 1);
INSERT INTO Tuote (Kuvaus, Hinta, KrediittienMaara, Alennuskerroin, KolikoidenMaara, Myynnissa) VALUES ("High Roller Pack", 24.99,  30, 1, 100, 1);
INSERT INTO Tuote (Kuvaus, Hinta, KrediittienMaara, Alennuskerroin, KolikoidenMaara, Myynnissa) VALUES ("Royal Treasury", 49.99,  60, 1, 230, 1);
INSERT INTO Tuote (Kuvaus, Hinta, KrediittienMaara, Alennuskerroin, KolikoidenMaara, Myynnissa) VALUES ("Golden Horde", 99.99,  130, 1, 520, 1);
INSERT INTO Tuote (Kuvaus, Hinta, KrediittienMaara, Alennuskerroin, KolikoidenMaara, Myynnissa) VALUES ("Fortune Foundry", 499.99,  600, 1, 2200, 1);

CREATE TABLE Peli
(
  Tyyppi VARCHAR(50) NOT NULL,
  PelinNimi VARCHAR(50) NOT NULL,
  PRIMARY KEY (PelinNimi)
);

INSERT INTO Peli (PelinNimi, Tyyppi) VALUES ("MoneyRain", "ARCADE");
INSERT INTO Peli (PelinNimi, Tyyppi) VALUES ("Slalom Madness", "ARCADE");

CREATE TABLE Kayttaja
(
  Kayttajanimi VARCHAR(50) NOT NULL,
  Salasana VARCHAR(500) NOT NULL,
  KayttajaID INT NOT NULL AUTO_INCREMENT,
  Tilinumero VARCHAR(50) DEFAULT NULL NULL,
  Sahkoposti VARCHAR(50) NOT NULL,
  Firstname VARCHAR(50) NOT NULL,
  Lastname VARCHAR(50) NOT NULL,
  Last_login DATE DEFAULT NULL,
  Login_streak INT DEFAULT NULL,
  Status TINYINT DEFAULT NULL,
  XP INT DEFAULT NULL,
  TiliID INT NOT NULL,
  PRIMARY KEY (KayttajaID),
  FOREIGN KEY (TiliID) REFERENCES Tili(TiliID)
);

INSERT INTO Tili (KolikkoSaldo, KrediittiSaldo) VALUES (0,0);
INSERT INTO Kayttaja (Kayttajanimi, Salasana, Sahkoposti, TiliID , Firstname, Lastname, Status) VALUES ("admin", SHA2("admin",256), "admin", 1, "admin", "admin", 1);

CREATE TABLE Tilaus
(
  TilausID INT NOT NULL AUTO_INCREMENT,
  Summa FLOAT NOT NULL,
  Paivamaara DATE NOT NULL,
  KrediittienMaara FLOAT NOT NULL NULL,
  KolikoidenMaara FLOAT NOT NULL NULL,
  Tuotekuvaus VARCHAR(100) NOT NULL,
  KayttajaID INT NOT NULL,
  PRIMARY KEY (TilausID),
  FOREIGN KEY (KayttajaID) REFERENCES Kayttaja(KayttajaID)
);

CREATE TABLE Sisaltaa
(
  Tuotenumero INT NOT NULL,
  TilausID INT NOT NULL,
  PRIMARY KEY (Tuotenumero, TilausID),
  FOREIGN KEY (Tuotenumero) REFERENCES Tuote(Tuotenumero),
  FOREIGN KEY (TilausID) REFERENCES Tilaus(TilausID)
);

CREATE TABLE Saavuttaa
(
  HighScore INT NOT NULL NULL,
  AikaScore INT NOT NULL NULL,
  KayttajaID INT NOT NULL,
  PelinNimi VARCHAR(50) NOT NULL,
  PRIMARY KEY (KayttajaID, PelinNimi),
  FOREIGN KEY (KayttajaID) REFERENCES Kayttaja(KayttajaID),
  FOREIGN KEY (PelinNimi) REFERENCES Peli(PelinNimi)
);

CREATE TABLE Pelaa
(
  Panos INT NOT NULL,
  Voitto INT NOT NULL,
  Paivamaara INT NOT NULL,
  VoittoKrediitit FLOAT NOT NULL,
  PelinNimi VARCHAR(50) NOT NULL,
  KayttajaID INT NOT NULL,
  PRIMARY KEY (PelinNimi, KayttajaID),
  FOREIGN KEY (PelinNimi) REFERENCES Peli(PelinNimi),
  FOREIGN KEY (KayttajaID) REFERENCES Kayttaja(KayttajaID)
);

CREATE TABLE Leaderboards
(
  HighScore INT NOT NULL,
  PelinNimi VARCHAR(50) NOT NULL,
  KayttajaID INT NOT NULL,
  FOREIGN KEY (PelinNimi, KayttajaID) REFERENCES Pelaa(PelinNimi, KayttajaID)
);