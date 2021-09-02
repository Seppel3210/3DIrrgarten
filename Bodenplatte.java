import GLOOP.GLTafel;
import GLOOP.GLTextur;
import GLOOP.GLVektor;

public class Bodenplatte extends Element {
    private GLTafel platte;

    public Bodenplatte(double pX, double pY, double pZ, double laenge) {
        platte = new GLTafel(pX, pY, pZ, laenge, laenge);
        betretbar = true;
        platte.drehe(90, 0, 0, platte.gibPosition());
    }

    public Bodenplatte(double pX, double pY, double pZ, double laenge, GLTextur pTex) {
        platte = new GLTafel(pX, pY, pZ, laenge, laenge, pTex);
        betretbar = true;
        platte.drehe(90, 0, 0, platte.gibPosition());
    }

    public Bodenplatte(GLVektor pPos, double laenge) {
        platte = new GLTafel(pPos, laenge, laenge);
        betretbar = true;
        platte.drehe(90, 0, 0, platte.gibPosition());
    }

    public Bodenplatte(GLVektor pPos, double laenge, GLTextur pTex) {
        platte = new GLTafel(pPos, laenge, laenge, pTex);
        betretbar = true;
        platte.drehe(90, 0, 0, platte.gibPosition());
    }

    public GLVektor gibPosition() {
        return platte.gibPosition();
    }
}
