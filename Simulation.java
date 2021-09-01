import GLOOP.*;
public class Simulation {
    private GLTastatur tastatur;
    private GLHimmel himmel;
    private GLBoden boden;
    private GLLicht licht;


    private Irrgarten irrgarten;
    private Spieler spieler;


    public Simulation() {
        himmel = new GLHimmel("./Texturen/Skydome.jpg");
        boden = new GLBoden("./Texturen/Boden.jpg");
        tastatur = new GLTastatur();
        licht = new GLLicht();

        irrgarten = new Irrgarten(50, 50);
        spieler = new Spieler(irrgarten);
    }

    public void starteSimulation() {
        while (!tastatur.esc()) {
            if(tastatur.oben())
            {
                spieler.hinzurueck(true);
            }
            if(tastatur.unten())
            {
                spieler.hinzurueck(false);
            }
            if(tastatur.rechts())
            {
                spieler.linksrechts(true);
            }
            if(tastatur.links())
            {
                spieler.linksrechts(false);
            }
            spieler.animiere();
            Sys.warte();
        }
        Sys.beenden();
    }

    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.starteSimulation();
    }
}