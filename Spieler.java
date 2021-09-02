import GLOOP.GLKamera;
import GLOOP.GLLicht;
import GLOOP.GLVektor;
import GLOOP.Sys;

public class Spieler extends AniElement {
    private GLKamera kamera;
    private GLLicht licht;

    private Irrgarten feld;

    private int vor;
    private int rechts;
    private boolean vogelperspektive = false;
    private Vektor2 pos;
    private Vektor2 blickrichtung;
    private AlteSpielerPosition altePos;

    public Spieler(Irrgarten pFeld, GLKamera kamera) {
        super(false, 0, 0, null);
        feld = pFeld;
        pos = new Vektor2(0, 1);
        this.kamera = kamera;
        blickrichtung = Vektor2.OST;
        setzeKameraPosition();
        licht = new GLLicht(kamera.gibPosition());
        altePos = new AlteSpielerPosition(kamera.gibPosition(), blickrichtung, pos);
        toggleVogelperspektive();
    }

    public Vektor2 gibPosition() {
        return pos;
    }

    public GLKamera gibKamera() {
        return kamera;
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
                    feld.coinPruef(pos);
                }
            } else {
                Vektor2 neuePos = pos.summe(blickrichtung.produkt(-1));
                if (!feld.imFeld(neuePos) || feld.gibElement(neuePos).gibBetretbar()) {
                    altePos.position = kamera.gibPosition();
                    altePos.blickrichtung = blickrichtung;
                    altePos.posImArray = pos;
                    pos = neuePos;
                    vor = -(int) Irrgarten.KANTENLAENGE;
                    feld.coinPruef(pos);
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
        } else if (feld.imFeld(pos) && !feld.gibElement(pos).gibBetretbar()) {
            kamera.setzePosition(altePos.position);
            licht.setzePosition(altePos.position);
            kamera.setzeBlickpunkt(altePos.position.gibSumme(altePos.blickrichtung.zuGLVektor()));
            blickrichtung = altePos.blickrichtung;
            pos = altePos.posImArray;
        }
    }

    private void setzeKameraPosition() {
        GLVektor v = ((Bodenplatte) feld.gibElement(pos)).gibPosition();
        v.addiere(new GLVektor(0, Irrgarten.KANTENLAENGE / 2, 0));
        kamera.setzePosition(v);
        v.addiere(blickrichtung.zuGLVektor());
        kamera.setzeBlickpunkt(v);
    }

    public void toggleVogelperspektive() {
        if (vogelperspektive) {
            vogelperspektive = false;
            GLVektor v = ((Bodenplatte) feld.gibElement(pos)).gibPosition();
            v.addiere(new GLVektor(0, Irrgarten.KANTENLAENGE / 2, 0));
            kamerafahrt(v, v.gibSumme(blickrichtung.zuGLVektor()));
        } else {
            vogelperspektive = true;
            kamerafahrt(new GLVektor(0, 2500, -200), new GLVektor(0, 0, 0));
        }
    }

    public int getX() {
        return pos.x;
    }

    public int getZ() {
        return pos.z;
    }

    private void kamerafahrt(GLVektor neuePosition, GLVektor neuerBlickpunkt) {
        GLVektor altePosition = kamera.gibPosition();

        GLVektor positionRichtung = neuePosition.gibDifferenz(altePosition);
        double skalar = 0.0;
        kamera.setzeBlickpunkt(new GLVektor(0, 0, 0));
        while (skalar < 1) {
            skalar += 0.01;
            kamera.setzePosition(altePosition.gibSumme(positionRichtung.gibVielfaches(skalar)));
            //kamera.setzeBlickpunkt(altePosition.gibSumme(blickpunktRichtung.gibVielfaches(skalar)));
            Sys.warte(10);
        }
        kamera.setzeBlickpunkt(neuerBlickpunkt);
    }
}
