import GLOOP.GLKamera;
import GLOOP.GLLicht;
import GLOOP.GLSchwenkkamera;
import GLOOP.GLVektor;

public class Spieler extends AniElement {
    private GLKamera kamera;
    private GLLicht licht;

    private Irrgarten feld;

    private int vor;
    private int rechts;
    private Vektor2 pos;
    private Vektor2 blickrichtung;

    public Spieler(Irrgarten pFeld) {
        feld = pFeld;
        pos = new Vektor2(0, 0);
        /*for (int x = feld.breite() / 2; x < feld.breite(); x++) {
            for (int z = feld.tiefe() / 2; z < feld.tiefe(); z++) {
                Vektor2 neuePos = new Vektor2(x, z);
                if (feld.gibElement(neuePos).gibBetretbar()) {
                    pos = neuePos;

                    break;
                }
            }
        }*/
        pos = new Vektor2(1, 1);
        kamera = new GLSchwenkkamera();
        GLVektor v = ((Bodenplatte) feld.gibElement(pos)).gibPosition();
        kamera.setzePosition(v);
        licht = new GLLicht(v);
        blickrichtung = Vektor2.SUED;
        v.addiere(blickrichtung.zuGLVektor());
        kamera.setzeBlickpunkt(v);
        System.out.println(pos.x + " " + pos.z);
    }

    public void bewege(double pVorZurueck, double pLinksRechts) {

    }

    public void schwenke(double pLinksRechts, double pHochRunter) {

    }

    public void hinzurueck(boolean richtung) {
        if (vor == 0 && rechts == 0) {
            if (richtung) {

                Vektor2 neuePos = pos.summe(blickrichtung);
                if (feld.gibElement(neuePos).gibBetretbar()) {
                    pos = neuePos;
                    vor = (int) Irrgarten.KANTENLAENGE;
                }
                System.out.println(pos.x + " " + pos.z);
            } else {
                Vektor2 neuePos = pos.summe(blickrichtung.produkt(-1));
                if (feld.gibElement(neuePos).gibBetretbar()) {
                    pos = neuePos;
                    vor = -(int) Irrgarten.KANTENLAENGE;
                }
                System.out.println(pos.x + " " + pos.z);
            }
        }
    }

    public void linksrechts(boolean richtung) {
        if (vor == 0 && rechts == 0) {
            if (richtung) {
                rechts = 90;
                blickrichtung = blickrichtung.rotiereRechts();
            } else {
                rechts = -90;
                blickrichtung = blickrichtung.rotiereLinks();
            }
        }
    }

    @Override
    public void animiere() {
        int geschwindigkeit = 5;
        int drehGeschwindigkeit = 5;
        if (vor > 0) {
            kamera.verschiebe(blickrichtung.zuGLVektor().gibVielfaches(geschwindigkeit));
            licht.verschiebe(blickrichtung.zuGLVektor().gibVielfaches(geschwindigkeit));
            vor -= geschwindigkeit;
        } else if (vor < 0) {
            kamera.verschiebe(blickrichtung.produkt(-1).zuGLVektor().gibVielfaches(geschwindigkeit));
            licht.verschiebe(blickrichtung.produkt(-1).zuGLVektor().gibVielfaches(geschwindigkeit));
            vor += geschwindigkeit;
        } else if (rechts > 0) {
            kamera.rotiere(-drehGeschwindigkeit, new GLVektor(0, 1, 0), kamera.gibPosition());
            rechts -= drehGeschwindigkeit;
        } else if (rechts < 0) {
            kamera.rotiere(drehGeschwindigkeit, new GLVektor(0, 1, 0), kamera.gibPosition());
            rechts += drehGeschwindigkeit;
        }
    }
}
