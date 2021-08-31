import GLOOP.GLObjekt;
import GLOOP.GLWuerfel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Irrgarten {
    public static double KANTENLAENGE = 200;
    private final GLObjekt[][] felder;

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
        ArrayDeque<Vektor2> queue = new ArrayDeque<>();
        queue.push(new Vektor2(x, z));
        entferne(new Vektor2(x, z));
        while (!queue.isEmpty()) {
            Vektor2 position = queue.pop();
            List<Vektor2> richtungen = Vektor2.zufaelligeRichtungen();
            for (int i = richtungen.size() - 1; i >= 0; i--) {
                if (istSackgasse(position, richtungen.get(i))) {
                    richtungen.remove(i);
                }
            }

            if (richtungen.size() > 0) {
                Vektor2 neuePosition = position.summe(richtungen.remove(0));
                entferne(neuePosition);
                queue.push(neuePosition);
            }
            while (richtungen.size() > 0 && Math.random() < 1.0) {
                Vektor2 neuePosition = position.summe(richtungen.remove(0));
                entferne(neuePosition);
                queue.push(neuePosition);
            }
        }
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
