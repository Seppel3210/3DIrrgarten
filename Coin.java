import GLOOP.*;

public class Coin extends AniElement {
    private GLTextur tex;
    private GLZylinder coin;
    private GLVektor ortVekt;
    private Boolean eingesammelt;

    public Coin(int pX, int pZ) {
        coin = new GLZylinder(0, 75, 0, 12, 5/*,"Gold.jpg"*/);
        coin.setzeFarbe(1, 0.84, 0);
        coin.setzePosition(pX, 75, pZ);
        coin.setzeSelbstleuchten(1, 0.84, 0);
        ortVekt = new GLVektor(pX, 100, pZ);
    }

    public void animiere() {
        coin.rotiere(0.001, ortVekt, new GLVektor(0, 1, 0));
    }

    public void einsammeln() {
        coin.setzeSichtbarkeit(false);
    }
}