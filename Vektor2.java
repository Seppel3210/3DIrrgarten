import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vektor2 {
    public int x;
    public int z;
    public static Vektor2 NORD = new Vektor2(0, 1);
    public static Vektor2 SUED = new Vektor2(0, -1);
    public static Vektor2 OST = new Vektor2(1, 0);
    public static Vektor2 WEST = new Vektor2(-1, 0);

    public Vektor2(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Vektor2 summe(Vektor2 other) {
        return new Vektor2(this.x + other.x, this.z + other.z);
    }

    public Vektor2 produkt(int skalar) {
        return new Vektor2(this.x * skalar, this.z * skalar);
    }

    public boolean gleich(Vektor2 position) {
        return this.x == position.x && this.z == position.z;
    }

    public Vektor2 rotiereLinks() {
        return new Vektor2(-z, x);
    }

    public Vektor2 rotiereRechts() {
        return new Vektor2(z, -x);
    }

    public static List<Vektor2> alleRichtungen() {
        List<Vektor2> list = new ArrayList<>();
        list.add(NORD);
        list.add(SUED);
        list.add(OST);
        list.add(WEST);
        return list;
    }

    public static List<Vektor2> zufaelligeRichtungen() {
        List<Vektor2> list = alleRichtungen();
        Collections.shuffle(list);
        return list;
    }

    @Override
    public String toString() {
        return "(" + +x + ", " + z + ")";
    }
}
