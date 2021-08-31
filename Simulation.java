import GLOOP.*;
public class Simulation {
    private GLKamera spieler;
    private GLTastatur tastatur;
    private GLHimmel himmel;
    private GLBoden boden;
    private GLLicht licht;

    private Steuerung steuerung;

    private Irrgarten irrgarten;


    public Simulation() {
        Sys.warte(1000);
        himmel = new GLHimmel("./Texturen/Skydome.jpg");
        boden = new GLBoden("./Texturen/Boden.jpg");
        tastatur = new GLTastatur();
        licht = new GLLicht();

        steuerung = new Steuerung();

        spieler = new GLEntwicklerkamera(800, 600);
        spieler.setzePosition(0, 5000, 0);
        spieler.setzeBlickpunkt(0, 0, -100);
        irrgarten = new Irrgarten(100, 100);
    }

    public void starteSimulation() {
        while (!tastatur.esc()) {
            // [...]
            Sys.warte();
        }
        Sys.beenden();
    }

    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.starteSimulation();
    }
}