import GLOOP.*;

public class Simulation {
    private GLTastatur tastatur;
    private GLHimmel himmel;
    private GLLicht licht;

    //private Steuerung steuerung;

    private Irrgarten irrgarten;
    private Spieler spieler;
    //private GLKamera kamera;
    private Coin testcoin;

    public Simulation() {
    /*kamera = new GLEntwicklerkamera();
    kamera.setzePosition(0,5000,0);
    kamera.setzeBlickpunkt(0,0,-100);
    himmel = new GLHimmel("./Texturen/Skydome.jpg");*/
        tastatur = new GLTastatur();
        licht = new GLLicht();
        //steuerung = new Steuerung();

        irrgarten = new Irrgarten(50, 50, new GLTextur("./Texturen/Ziegel.jpg"), new GLTextur("./Texturen/Rotmarmor.jpg"));
        spieler = new Spieler(irrgarten);
        testcoin = new Coin(2000, 2000);
    }

    public void starteSimulation() {
        while (!tastatur.esc()) {

            if (tastatur.oben()) {
                spieler.hinzurueck(true);
            }
            if (tastatur.unten()) {
                spieler.hinzurueck(false);
            }
            if (tastatur.rechts()) {
                spieler.linksrechts(true);
            }
            if (tastatur.links()) {
                spieler.linksrechts(false);
            }
            spieler.animiere();
            irrgarten.animiereElemente();
            Sys.warte();
        }
        Sys.beenden();
    }

    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.starteSimulation();
    }
}