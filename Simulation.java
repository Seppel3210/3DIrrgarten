import GLOOP.*;

public class Simulation {
    private GLTastatur tastatur;
    private GLHimmel himmel;

    //private Steuerung steuerung;

    private Irrgarten irrgarten;
    private Spieler spieler;
    private ScoreAnzeige score;

    public Simulation() {
        tastatur = new GLTastatur();
        //steuerung = new Steuerung();

        irrgarten = new Irrgarten(50, 50, new GLTextur("./Texturen/Ziegel.jpg"), new GLTextur("./Texturen/Rotmarmor.jpg"));
        spieler = new Spieler(irrgarten);
        score = new ScoreAnzeige(spieler.gibKamera());
        irrgarten.setzeScoreAnzeige(score);
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