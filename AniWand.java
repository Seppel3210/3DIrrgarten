import GLOOP.GLTextur;
import GLOOP.GLVektor;
import GLOOP.GLWuerfel;

public class AniWand extends AniElement {

    private GLWuerfel aniWand;
    private boolean betretbar = false;
    private int counter = 490;
    private boolean unten;

    private GLTextur tex;

    public AniWand(double pX, double pY, double pZ, int row, int col, Irrgarten garten) {
        super(true, row, col, garten);
        tex = new GLTextur("./Texturen/Rotleder.jpg");
        aniWand = new GLWuerfel(pX, pY, pZ, 50, tex);
        unten = false;
    }

    public AniWand(GLVektor pos, int row, int col, Irrgarten garten) {
        super(true, row, col, garten);
        tex = new GLTextur("./Texturen/Rotleder.jpg");
        aniWand = new GLWuerfel(pos, 50, tex);
        unten = false;
    }

    public GLWuerfel gibWuerfel() {
        return aniWand;
    }

    public boolean gibBetretbar() {
        return betretbar;
    }

    public void animiere() {
        if (counter > 0) {
            if (unten) {
                aniWand.verschiebe(0, 0.1, 0);
                counter--;
            } else {
                aniWand.verschiebe(0, -0.1, 0);
                counter--;
            }
        } else {
            if (counter > -2000) {
                //wartezeit
                if (!unten) {
                    betretbar = true;
                }
                counter--;
            } else {
                counter = 490;
                if (!unten) {
                    betretbar = false;
                }
                unten = !unten;
            }
        }
    }


}