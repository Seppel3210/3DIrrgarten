import GLOOP.Sys;
import net.java.games.input.*;

import java.text.DecimalFormat;

public class Steuerung {
  Controller[] geraet;
  Component[] teil;
  int geraetNummer;
  double[] k;

  //Erstellt ein Objekt zum Abfrage von Eingabegeraeten.
  public Steuerung(){
    ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment();
    geraet = ce.getControllers();
    k = new double[4];
  }

  //Schreibt die Namen und Nummern aller Eingabegeraete auf die Konsole.
  public void schreibeGeraete(){
    for(int i = 0;i < geraet.length; i++){           
      System.out.println("Geraet "+i+" : "+geraet[i].getName());
    }  
  }

  public void kalibiere(int pGeraet) {
    teil = geraet[pGeraet].getComponents();        
    geraet[pGeraet].poll();
    for (int i = 0; i < 4; i++) {
      k[i] = teil[i].getPollData();
    }
  }

  //Liefert den Wert des Kanals "pKanal" der Geraets "pGeraet".
  public double gibKanal(int pGeraet, int pKanal){        
    teil = geraet[pGeraet].getComponents();        
    geraet[pGeraet].poll();
    if (pKanal > 3) {
      return teil[pKanal].getPollData();
    } else {
      return teil[pKanal].getPollData() - k[pKanal];
    }
  }

  //Schreibt die aktuellen Werte aller Kanaele des Geraetes "pGeraet" auf die Konsole.
  public void schreibeKanaele(int pGeraet){
    DecimalFormat f = new DecimalFormat("#0.00"); 
    for (int i=0; i<17; i++) 
      System.out.println("Kanal "+i+" : "+f.format(this.gibKanal(pGeraet, i)));
  }

  //Schreibt in einer Endlosschleife alle Werte des Geraets "pGeraet" auf die Konsole.
  public void dauerTest(int pGeraet){
    DecimalFormat f = new DecimalFormat("#0.00"); 
    while (true){
      for (int i=0; i<17; i++)
        if (this.gibKanal(pGeraet, i) != 0) {  
          System.out.print("K "+i+" : " + f.format(this.gibKanal(pGeraet, i))+"  ");  
          Sys.warte();
      }
      System.out.println();
    }
  }

}
