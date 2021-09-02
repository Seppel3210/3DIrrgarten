import GLOOP.GLTextur;
import GLOOP.GLVektor;
import GLOOP.GLWuerfel;

public class Block extends Element {
    private GLWuerfel wuerfel;

    public Block(double pX, double pY, double pZ, double seite, int row, int col, Irrgarten garten) {
        super(false, row, col, garten);
        wuerfel = new GLWuerfel(pX, pY, pZ, seite);
        betretbar = false;
    }

    public Block(double pX, double pY, double pZ, double seite, GLTextur pTex, int row, int col, Irrgarten garten) {
        super(false, row, col, garten);
        wuerfel = new GLWuerfel(pX, pY, pZ, seite, pTex);
        betretbar = false;
    }

    public Block(GLVektor pPos, double seite, int row, int col, Irrgarten garten) {
        super(false, row, col, garten);
        wuerfel = new GLWuerfel(pPos, seite);
        betretbar = false;
    }

    public Block(GLVektor pPos, double seite, GLTextur pTex, int row, int col, Irrgarten garten) {
        super(false, row, col, garten);
        wuerfel = new GLWuerfel(pPos, seite, pTex);
        betretbar = false;
    }

    public void loesche() {
        wuerfel.loesche();
    }

    public Bodenplatte ersetzeBp(GLTextur pTex) {
        Bodenplatte bp = new Bodenplatte(wuerfel.gibX(), 0, wuerfel.gibZ(), Irrgarten.KANTENLAENGE, pTex, gibRow(), gibCol(), garten);
        wuerfel.loesche();
        return bp;
    }

    public Bodenplatte ersetzeBp() {
        Bodenplatte bp = new Bodenplatte(wuerfel.gibX(), 0, wuerfel.gibZ(), Irrgarten.KANTENLAENGE, gibRow(), gibCol(), garten);
        wuerfel.loesche();
        return bp;
    }

    public AniWand ersetzeAw() {
        AniWand aw = new AniWand(wuerfel.gibPosition(), gibRow(), gibCol(), garten);
        wuerfel.loesche();
        return aw;
    }
}
