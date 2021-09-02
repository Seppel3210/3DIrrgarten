import GLOOP.GLEntwicklerkamera;
import GLOOP.GLKamera;
import GLOOP.GLLicht;
import GLOOP.GLVektor;

public class Spieler extends AniElement {
    private GLKamera kamera;
    private GLLicht licht;

    private Irrgarten feld;

    private int vor;
    private int rechts;
    private Vektor2 pos;
    private Vektor2 blickrichtung;
    private AlteSpielerPosition altePos;

    public Spieler(Irrgarten pFeld) {
        feld = pFeld;
        pos = new Vektor2(0, 1);
        kamera = new GLEntwicklerkamera();
        GLVektor v = ((Bodenplatte) feld.gibElement(pos)).gibPosition();
        v.addiere(new GLVektor(0, Irrgarten.KANTENLAENGE / 2, 0));
        kamera.setzePosition(v);
        licht = new GLLicht(v);
        blickrichtung = Vektor2.WEST;
        v.addiere(blickrichtung.zuGLVektor());
        kamera.setzeBlickpunkt(v);
        altePos = new AlteSpielerPosition(kamera.gibPosition(), blickrichtung, pos);
    }

    public void bewege(double pVorZurueck, double pLinksRechts) {

    }

    public void schwenke(double pLinksRechts, double pHochRunter) {

    }

    public void hinzurueck(boolean richtung) {
        if (vor == 0 && rechts == 0) {
            if (richtung) {

                Vektor2 neuePos = pos.summe(blickrichtung);
                if (!feld.imFeld(neuePos) || feld.gibElement(neuePos).gibBetretbar()) {
                    altePos.position = kamera.gibPosition();
                    altePos.blickrichtung = blickrichtung;
                    altePos.posImArray = pos;
                    pos = neuePos;
                    vor = (int) Irrgarten.KANTENLAENGE;
                }
            } else {
                Vektor2 neuePos = pos.summe(blickrichtung.produkt(-1));
                if (!feld.imFeld(neuePos) || feld.gibElement(neuePos).gibBetretbar()) {
                    altePos.position = kamera.gibPosition();
                    altePos.blickrichtung = blickrichtung;
                    altePos.posImArray = pos;
                    pos = neuePos;
                    vor = -(int) Irrgarten.KANTENLAENGE;
                }
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
        int geschwindigkeit = 2;
        int drehGeschwindigkeit = 2;
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
        } else if (!feld.imFeld(pos) || !feld.gibElement(pos).gibBetretbar()) {
            kamera.setzePosition(altePos.position);
            licht.setzePosition(altePos.position);
            kamera.setzeBlickpunkt(altePos.position.gibSumme(altePos.blickrichtung.zuGLVektor()));
            blickrichtung = altePos.blickrichtung;
            pos = altePos.posImArray;
        }
    }
}
