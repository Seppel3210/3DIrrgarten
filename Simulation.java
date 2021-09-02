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

        GLKamera kamera = new GLKamera();
        kamera.setzePosition(0, 2500, 0);
        kamera.setzeBlickpunkt(0, 0, -10);
        irrgarten = new Irrgarten(50, 50, new GLTextur("./Texturen/Ziegel.jpg"), new GLTextur("./Texturen/Bodenplatte3.png"));
        spieler = new Spieler(irrgarten, kamera);
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
            if (tastatur.istGedrueckt(' ')) {
                spieler.toggleVogelperspektive();
                Sys.warte(500);
            }
            if (tastatur.enter()) {
                irrgarten.findShortestPath(spieler.getZ(), spieler.getX(), irrgarten.ende.z, irrgarten.ende.x);
            }
            if(tastatur.istGedrueckt('c'))
            {
                irrgarten.clearDijkstra();
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