package model;

import java.io.*;
public class Lue {
/*************************************** Arto Wikla 1998**********

Lukurutiinit kirjaan Ohjelmoinnin perusteet Java-kielell�

LUOKKA on JAVAN VERSIOILLE 1.1.*

Operaatiot:   Lue.rivi()    antaa seuraavan sy�tt�rivin    (String)
              Lue.kluku()     "      "      kokonaisluvun  (int)
              Lue.dluku()     "      "      desimaaliluvun (double)
              Lue.merkki()  antaa seuraavan sy�tt�rivin ensimm�isen
merkin

Operaatiot ovat sitkeit�, ne VAATIVAT kelvollisen sy�tteen!
******************************************************************/

  static BufferedReader stdin =
     new BufferedReader(new InputStreamReader(System.in));

/******************************************************************/

  public static String rivi() {
    String arvo=null;
    boolean ok;
    do {
      try {
        arvo = stdin.readLine();
        ok = true;
      } catch (Exception e) {
        System.out.println("Virhe rivin lukemisessa. Anna uusi!");
        ok = false;
      }
    }
    while (!ok);
    return arvo;
  }
/******************************************************************/

  public static int kluku() {
    int arvo=-1;
    boolean ok;
    do {
      try {
        arvo = Integer.parseInt(stdin.readLine());
        ok = true;
      } catch (Exception e) {
        System.out.println("Kelvoton kokonaisluku. Anna uusi!");
        ok = false;
      }
    }
    while (!ok);
    return arvo;
  }

/*******************************************************************/

  public static double dluku() {
    double arvo=-1;
    boolean ok;
    do {
      try {
        arvo = new Double(stdin.readLine()).doubleValue();
        ok = true;
      } catch (Exception e) {
        System.out.println("Kelvoton desimaaliluku. Anna uusi!");
        ok = false;
      }
    }
    while (!ok);
    return arvo;
  }
/*******************************************************************/

  public static char merkki() {
    String rivi = rivi();
    try {
      return rivi.charAt(0);
    } catch (Exception e) {
      return ' ';
    }
  }
}
