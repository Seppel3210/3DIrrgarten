import GLOOP.GLObjekt;
import GLOOP.GLWuerfel;
import GLOOP.Sys;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Irrgarten {
    public static double KANTENLAENGE = 80;
    private final GLObjekt[][] felder;
    private Vektor2 letztesRechts = null;

    public Irrgarten(int tiefe, int breite) {
        felder = new GLObjekt[tiefe][breite];
        for (int z = 0; z < tiefe; z++) {
            for (int x = 0; x < breite; x++) {
                double xOffset = breite * KANTENLAENGE / 2;
                double zOffset = tiefe * KANTENLAENGE / 2;
                felder[z][x] = new GLWuerfel(x * KANTENLAENGE - xOffset, KANTENLAENGE / 2, z * KANTENLAENGE - zOffset, KANTENLAENGE);
            }
        }
        generieren(1, 1);
    }

    private int breite() {
        return felder[0].length;
    }

    private int tiefe() {
        return felder.length;
    }

    private void generieren(int x, int z) {
        entferne(new Vektor2(0, 1));
        ArrayDeque<VertagtePosition> positionQueue = new ArrayDeque<>();
        entferne(new Vektor2(x, z));
        for (Vektor2 richtung : Vektor2.zufaelligeRichtungen()) {
            positionQueue.push(new VertagtePosition(new Vektor2(x, z), richtung));
        }
        while (!positionQueue.isEmpty()) {
            VertagtePosition vertagt = positionQueue.poll();
            if (!istSackgasse(vertagt.position, vertagt.richtung)) {
                Vektor2 neuePosition = vertagt.neuePosition();
                entferne(neuePosition);
                if (neuePosition.x == breite() - 2) {
                    letztesRechts = neuePosition;
                }
                //Sys.warte(40);
                for (Vektor2 richtung : Vektor2.zufaelligeRichtungen()) {
                    // 0 => komplette Breitensuche
                    // 1 => komplette Tiefensuche
                    if (Math.random() < 1.0) {
                        positionQueue.addFirst(new VertagtePosition(neuePosition, richtung));
                    } else {
                        positionQueue.addLast(new VertagtePosition(neuePosition, richtung));
                    }
                }
            }
        }
        entferne(letztesRechts.summe(new Vektor2(1, 0)));
    }

    private boolean istSackgasse(Vektor2 position, Vektor2 richtung) {
        Vektor2 neuePosition = position.summe(richtung);
        List<Vektor2> ueberpruefeRichtungen = new ArrayList<>();
        ueberpruefeRichtungen.add(new Vektor2(0, 0));
        ueberpruefeRichtungen.add(richtung);
        ueberpruefeRichtungen.add(richtung.rotiereLinks());
        ueberpruefeRichtungen.add(richtung.rotiereRechts());
        ueberpruefeRichtungen.add(richtung.rotiereLinks().summe(richtung)); // Diagonal
        ueberpruefeRichtungen.add(richtung.rotiereRechts().summe(richtung)); // Diagonal
        for (Vektor2 r : ueberpruefeRichtungen) {
            if (!imFeld(neuePosition.summe(r)) || !besetzt(neuePosition.summe(r))) {
                return true;
            }
        }
        return false;
    }

    private boolean besetzt(Vektor2 position) {
        return felder[position.z][position.x] != null;
    }

    private void entferne(Vektor2 position) {
        int x = position.x;
        int z = position.z;
        if (imFeld(position)) {
            if (felder[z][x] != null) {
                felder[z][x].loesche();
                felder[z][x] = null;
            }
        }
    }

    private boolean imFeld(Vektor2 position) {
        return position.x >= 0 && position.x < breite() && position.z >= 0 && position.z < tiefe();
    }
}
