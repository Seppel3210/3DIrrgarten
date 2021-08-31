import java.util.List;

public class VertagtePosition {
    public Vektor2 position;
    public Vektor2 richtung;

    public VertagtePosition(Vektor2 position, Vektor2 richtung) {
        this.position = position;
        this.richtung = richtung;
    }

    public Vektor2 neuePosition() {
        return position.summe(richtung);
    }
}
