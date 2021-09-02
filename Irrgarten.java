import GLOOP.GLTextur;
import GLOOP.Sys;

import java.util.*;

public class Irrgarten {
    public static double KANTENLAENGE = 50;
    private Element[][] felder;
    private Element[][] sonderElemente;
    GLTextur bodenTex;
    private ScoreAnzeige score;
    public Vektor2 ende;

    public Irrgarten(int tiefe, int breite) {
        this(tiefe, breite, null, null);
    }

    public Irrgarten(int tiefe, int breite, GLTextur pBlockTex, GLTextur pBodenTex) {
        felder = new Element[tiefe][breite];
        sonderElemente = new Element[breite][tiefe];
        bodenTex = pBodenTex;
        double xOffset = breite * KANTENLAENGE / 2;
        double zOffset = tiefe * KANTENLAENGE / 2;
        for (int z = 0; z < tiefe; z++) {
            for (int x = 0; x < breite; x++) {
                felder[z][x] = new Block(x * KANTENLAENGE - xOffset, KANTENLAENGE / 2, z * KANTENLAENGE - zOffset, KANTENLAENGE, pBlockTex, z, x, this);
            }
        }
        ende = generieren(1, 1);
        elemente(50);
        for (Element[] reihe : felder) {
            for (Element feld : reihe) {
                feld.sammleNachbarn();
            }
        }
    }

    public void coinPruef(Vektor2 pos) {
        //wenn an der Position eine Münze ist verschwindet sie 
        if (imFeld(pos) && sonderElemente[pos.x][pos.z] instanceof Coin) {
            ((Coin) sonderElemente[pos.x][pos.z]).einsammeln();
            sonderElemente[pos.x][pos.z] = null;
            if (score != null) {
                score.addCoin();
            }
        }
    }

    public void setzeScoreAnzeige(ScoreAnzeige pScore) {
        score = pScore;
    }

    public void animiereElemente() {
        //AniElemente im Felder Array
        for (int z = 0; z < tiefe(); z++) {
            for (int x = 0; x < breite(); x++) {
                if (gibElement(x, z) instanceof AniElement) {
                    ((AniElement) gibElement(x, z)).animiere();
                }
            }
        }
        //AniElemente im SonderElemente Array
        for (int z = 0; z < tiefe(); z++) {
            for (int x = 0; x < breite(); x++) {
                if (sonderElemente[x][z] instanceof AniElement) {
                    ((AniElement) sonderElemente[x][z]).animiere();
                }
            }
        }
    }

    public void elemente(int i) {
        for (; i > 0; i--) {
            int x = (int) (Math.random() * (breite() - 2)) + 1;
            int z = (int) (Math.random() * (tiefe() - 2)) + 1;
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

    private Vektor2 generieren(int x, int z) {
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
                    if (Math.random() < 0.8) {
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
        return letztesRechts.summe(new Vektor2(1, 0));
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
    
    public boolean findShortestPath(int sRow, int sCol, int eRow, int eCol) {
        boolean[][] vScore = new boolean[felder[0].length][felder.length];
        for(int i = 0; i < vScore[0].length; i++) {
            for(int j = 0; j < vScore.length; j++) {
                
                vScore[i][j] = false;
            }
        }
        felder[sRow][sCol].setzeDistanz(0);
        
        ArrayList<Element> arrayList = new ArrayList<>();
        arrayList.add((Element)felder[sRow][sCol]);
        HashMap<Element, Element> cameFrom = new HashMap<>();

        while(!arrayList.isEmpty()) {
            Collections.sort(arrayList);
            Element current = arrayList.remove(0);
            if(current.gibRow() == eRow && current.gibCol() == eCol) {
                reconstructPath(cameFrom, felder[sRow][sCol], felder[eRow][eCol]);
                return true;
            }
            vScore[current.gibRow()][current.gibCol()] = true;
            for(Element nachbar: current.gibNachbarn()) {
                if(!vScore[nachbar.gibRow()][nachbar.gibCol()]) {
                    int NDScore = current.gibDistanz() + 1; 
                    if(NDScore < nachbar.gibDistanz()) {
                        nachbar.setzeDistanz(NDScore);
                        arrayList.add(nachbar);
                        cameFrom.put(nachbar, current);
                    }
                }
            }
        }
        //reconstructPath(cameFrom, (Bodenplatte)felder[sRow][sCol], (Bodenplatte)felder[eRow][eCol]);
        return false;
    }
    
    public void sortArrayList(int[][] dScore, ArrayList<Bodenplatte> arrayList) {
        for(int i = arrayList.size() - 1; i >= 1; i--) {
            for(int j = 0; j < i; j++) {
                int dScore1 = dScore[arrayList.get(j).gibRow()][arrayList.get(j).gibCol()];
                int dScore2 = dScore[arrayList.get(j + 1).gibRow()][arrayList.get(j + 1).gibCol()];
                if(dScore1 > dScore2) {
                    Bodenplatte temp = arrayList.get(j);
                    arrayList.set(j, arrayList.get(j + 1));
                    arrayList.set(j + 1, temp);
                }
            }
        }
    }
    
    public ArrayList<Element> reconstructPath(HashMap<Element, Element> cameFrom, Element start, Element end) {
        ArrayList<Element> arrayList = new ArrayList<>();
        arrayList.add(end);
        Element myStart = end;
        while(!myStart.equals(start)) {
            myStart = cameFrom.get(myStart);
            arrayList.add(myStart);
        }
        
        for(int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i) instanceof Bodenplatte)
            {
                ((Bodenplatte)arrayList.get(i)).gibPlatte().setzeSelbstleuchten(0, 0, 0.5);
            }
            else if(arrayList.get(i) instanceof AniWand)
            {
                ((AniWand)arrayList.get(i)).gibWuerfel().setzeFarbe(0, 0, 0.5);
            }
        }
        return arrayList;
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
