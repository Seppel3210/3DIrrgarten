import GLOOP.GLKamera;
import GLOOP.GLTafel;
import GLOOP.GLVektor;

public class ScoreAnzeige {
    private String score = "000";
    private int coinCount = 0;

    GLTafel picCoin;
    GLTafel anzeige;
    GLTafel num;

    public ScoreAnzeige(GLKamera pKamera) {
        GLVektor normalKPos = pKamera.gibPosition();
        GLVektor normalKBli = pKamera.gibBlickpunkt();

        //pKamera.aus();

        pKamera.setzePosition(0, 25, -200);
        pKamera.setzeBlickpunkt(0, 25, 0);

        Tafel1Erstellen();
        Tafel2Erstellen();
        Tafel3Erstellen();

        pKamera.setzePosition(normalKPos);
        pKamera.setzeBlickpunkt(normalKBli);

        //pKamera.an();
    }

    private void Tafel1Erstellen() {
        anzeige = new GLTafel(-16.45, 35.7, -180, 3.3, 1.5, "Knopf.png");
        anzeige.setzeAutodrehung(true);
        anzeige.setzeKamerafixierung(true);
    }

    private void Tafel2Erstellen() {
        picCoin = new GLTafel(-16.9, 35.175, -181, 1.5, 1.5, "Coin2.png");
        picCoin.setzeAutodrehung(true);
        picCoin.setzeKamerafixierung(true);
    }

    private void Tafel3Erstellen() {
        num = new GLTafel(-15.2, 35, -181, 1, 1, "leer.png");
        num.setzeAutodrehung(true);
        num.setzeKamerafixierung(true);
        num.setzeText("000", 1);
        num.setzeTextfarbe(0, 0, 0);
    }

    public void addCoin() {
        if (coinCount < 999) {
            coinCount++;
        }
        AktualiCoinCount();
    }

    public void subCoin() {
        if (coinCount > 0) {
            coinCount--;
        }
        AktualiCoinCount();
    }

    private void AktualiCoinCount() {
        if (coinCount < 10) {
            score = "00" + coinCount;
        } else if (coinCount < 100) {
            score = "0" + coinCount;
        } else {
            score = "" + coinCount;
        }
        num.setzeText(score, 1);
    }
}
