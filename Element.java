import java.util.ArrayList;

public class Element implements Comparable<Element> {
    protected boolean betretbar;
    private boolean prinzipiellBetretbar;
    private ArrayList<Element> nachbarn = new ArrayList<>();
    protected Irrgarten garten;
    private int row, col;
    private int distanz = Integer.MAX_VALUE;

    public Element(boolean pPrinzipiell, int row, int col, Irrgarten pGarten) {
        prinzipiellBetretbar = pPrinzipiell;
        this.row = row;
        this.col = col;
        garten = pGarten;
    }

    public boolean gibBetretbar() {
        return betretbar;
    }

    public boolean gibPrinzipiellBetretbar() {
        return prinzipiellBetretbar;
    }

    public void sammleNachbarn() {
        Vektor2 pos = new Vektor2(col, row);
        for (Vektor2 r : Vektor2.alleRichtungen()) {
            if (garten.imFeld(pos.summe(r)) && garten.gibElement(pos.summe(r)).gibPrinzipiellBetretbar()) {
                nachbarn.add(garten.gibElement(pos.summe(r)));
            }
        }
    }

    @Override
    public int compareTo(Element boden) {
        return distanz - boden.gibDistanz();
    }

    public int gibDistanz() {
        return distanz;
    }

    public void setzeDistanz(int pDistanz) {
        distanz = pDistanz;
    }
    
    /*public GLTafel gibPlatte() {
       return platte;
    }
    
    public GLVektor gibPosition() {
        return platte.gibPosition();
    }
    */

    public ArrayList<Element> gibNachbarn() {
        return nachbarn;
    }

    public int gibRow() {
        return row;
    }

    public int gibCol() {
        return col;
    }
}
