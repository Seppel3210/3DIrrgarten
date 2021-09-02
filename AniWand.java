import GLOOP.*;

public class AniWand extends AniElement {

    private GLWuerfel aniWand;
    private boolean betretbar = false;
    private int counter = 500;
    private boolean unten;

    private GLTextur tex;

    public AniWand(double pX, double pY, double pZ) {
        tex = new GLTextur("./Texturen/Rotleder.jpg");
        aniWand = new GLWuerfel(pX, pY, pZ, 50, tex);
        unten = false;
    }

    public AniWand(GLVektor pos) {
        tex = new GLTextur("./Texturen/Rotleder.jpg");
        aniWand = new GLWuerfel(pos, 50, tex);
        unten = false;
    }

    public boolean gibBetretbar() {
        return betretbar;
    }

    public void animiere() {
        if (counter > 0) {
            if (unten) {
                aniWand.verschiebe(0,0.1,0);
                counter--;
            } else {
                aniWand.verschiebe(0,-0.1,0);
                counter--;
            }
        } else {
            if(counter > -2000) {
                //wartezeit
                counter--;
            } else {
                counter = 500;
                unten = !unten;
            }
        }   
    }


}