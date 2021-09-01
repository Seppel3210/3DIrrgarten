import GLOOP.GLTextur;
import GLOOP.GLVektor;
import GLOOP.GLWuerfel;

public class Block extends Element {
    private GLWuerfel wuerfel;

    public Block(double pX, double pY, double pZ, double seite) {
        wuerfel = new GLWuerfel(pX, pY, pZ, seite);
        betretbar = false;
    }

    public Block(double pX, double pY, double pZ, double seite, GLTextur pTex) {
        wuerfel = new GLWuerfel(pX, pY, pZ, seite, pTex);
        betretbar = false;
    }

    public Block(GLVektor pPos, double seite) {
        wuerfel = new GLWuerfel(pPos, seite);
        betretbar = false;
    }

    public Block(GLVektor pPos, double seite, GLTextur pTex) {
        wuerfel = new GLWuerfel(pPos, seite, pTex);
        betretbar = false;
    }

    public void loesche() {
        wuerfel.loesche();
    }

    public Bodenplatte ersetzeBp(GLTextur pTex) {
        Bodenplatte bp = new Bodenplatte(wuerfel.gibX(), 0, wuerfel.gibZ(), 100, pTex);
        wuerfel.loesche();
        return bp;
    }

    public Bodenplatte ersetzeBp() {
        Bodenplatte bp = new Bodenplatte(wuerfel.gibX(), 0, wuerfel.gibZ(), 100);
        wuerfel.loesche();
        return bp;
    }

    public AniWand ersetzeAw() {
        AniWand aw = new AniWand(wuerfel.gibPosition());
        wuerfel.loesche();
        return aw;
    }
}
