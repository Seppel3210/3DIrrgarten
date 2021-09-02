import GLOOP.*;

public class Coin extends AniElement {
    private GLTextur tex;
    private GLZylinder coin;
    private GLVektor ortVekt;
    private Vektor2 pos;
    private Boolean eingesammelt;

    public Coin(int pX, int pZ) {
        coin = new GLZylinder(0, 25, 0, 12, 5/*,"Gold.jpg"*/);
        coin.setzeFarbe(1, 0.84, 0);
        coin.setzePosition(pX, 25, pZ);
        pos = new Vektor2(pX,pZ);
        coin.setzeSelbstleuchten(1, 0.84, 0);
        ortVekt = new GLVektor(pX, 25, pZ);
    }

    public void animiere() {
        coin.rotiere(0.1,new GLVektor(0, 1, 0),ortVekt);
    }

    public void einsammeln() {
        coin.setzeSichtbarkeit(false);
    }
    
    public Vektor2 gibPos(){
        return pos;
    }
}