import GLOOP.GLTextur;
import GLOOP.GLVektor;
import GLOOP.GLZylinder;

public class Coin extends AniElement {
    private GLTextur tex;
    private GLZylinder coin;
    private GLVektor ortVekt;
    private Vektor2 pos;
    private Boolean eingesammelt;

    public Coin(int pX, int pZ, GLTextur pTex) {
        super(false, 0, 0, null);
        coin = new GLZylinder(0, 25, 0, 12, 5, pTex);
        coin.setzePosition(pX, 25, pZ);
        pos = new Vektor2(pX, pZ);
        coin.setzeSelbstleuchten(1, 0.84, 0);
        ortVekt = new GLVektor(pX, 25, pZ);
    }

    public void animiere() {
        coin.rotiere(0.2, new GLVektor(0, 1, 0), ortVekt);
    }

    public void einsammeln() {
        coin.setzeSichtbarkeit(false);
    }

    public Vektor2 gibPos() {
        return pos;
    }
}