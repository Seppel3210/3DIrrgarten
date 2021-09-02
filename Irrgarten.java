import GLOOP.GLTextur;
import GLOOP.Sys;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Irrgarten {
    public static double KANTENLAENGE = 50;
    private final Element[][] felder;
    private Element[][] sonderElemente;
    GLTextur bodenTex;

    public Irrgarten(int tiefe, int breite) {
        this(tiefe, breite, null, null);
    }

    public Irrgarten(int tiefe, int breite, GLTextur pBlockTex, GLTextur pBodenTex) {
        felder = new Element[tiefe][breite];
        bodenTex = pBodenTex;
        for (int z = 0; z < tiefe; z++) {
            for (int x = 0; x < breite; x++) {
                double xOffset = breite * KANTENLAENGE / 2;
                double zOffset = tiefe * KANTENLAENGE / 2;
                felder[z][x] = new Block(x * KANTENLAENGE - xOffset, KANTENLAENGE / 2, z * KANTENLAENGE - zOffset, KANTENLAENGE, pBlockTex);
            }
        }
        generieren(1, 1);
        elemente(50);
    }

    public void animiereElemente() {
        for (int z = 0; z < tiefe(); z++) {
            for (int x = 0; x < breite(); x++) {
                if (gibElement(x, z) instanceof AniElement) {
                    ((AniElement) gibElement(x, z)).animiere();
                }
            }
        }
    }

    public void elemente(int i) {
        for (; i > 0; i--) {
            int x = (int) (Math.random() * breite() - 2) + 1;
            int z = (int) (Math.random() * tiefe() - 2) + 1;
            if (gibElement(x, z) instanceof Block) {
                setzeElement(x, z, ((Block) gibElement(x, z)).ersetzeAw());
            }
        }
    }

    public int breite() {
        return felder[0].length;
    }

    public int tiefe() {
        return felder.length;
    }

    private void generieren(int x, int z) {
        Vektor2 letztesRechts = null;
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
            /*
            if (Math.random() < 0.1) {
                List<VertagtePosition> zufallsListe = new ArrayList<>(positionQueue);
                Collections.shuffle(zufallsListe);
                positionQueue = new ArrayDeque<>(zufallsListe);
            }
            */
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
            if (!imFeld(neuePosition.summe(r)) || gibElement(neuePosition.summe(r)).gibBetretbar()) {
                return true;
            }
        }
        return false;
    }

    public void entferne(Vektor2 position) {
        int x = position.x;
        int z = position.z;
        if (imFeld(position)) {
            if (felder[z][x] != null && felder[z][x] instanceof Block) {
                if (bodenTex == null) {
                    felder[z][x] = ((Block) felder[z][x]).ersetzeBp();
                } else {
                    felder[z][x] = ((Block) felder[z][x]).ersetzeBp(bodenTex);
                }
            }
        }
    }

    public boolean imFeld(Vektor2 position) {
        return position.x >= 0 && position.x < breite() && position.z >= 0 && position.z < tiefe();
    }

    public Element gibElement(Vektor2 position) {
        return gibElement(position.x, position.z);
    }

    public Element gibElement(int x, int z) {
        return felder[z][x];
    }

    public void setzeElement(Vektor2 position, Element element) {
        setzeElement(position.x, position.z, element);
    }

    public void setzeElement(int x, int z, Element element) {
        felder[z][x] = element;
    }
}
