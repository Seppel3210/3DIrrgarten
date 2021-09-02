import GLOOP.GLKamera;
import GLOOP.GLTafel;
import GLOOP.GLVektor;

public class ScoreAnzeige {
    private String score = "000";
    private int coinCount = 0;

    GLTafel picCoin;
    GLTafel anzeige;

    public ScoreAnzeige(GLKamera pKamera) {
        GLVektor normalKPos = pKamera.gibPosition();
        GLVektor normalKBli = pKamera.gibBlickpunkt();

        //pKamera.aus();

        pKamera.setzePosition(0, 25, -200);
        pKamera.setzeBlickpunkt(0, 25, 0);

        Tafel1Erstellen();
        Tafel2Erstellen();

        pKamera.setzePosition(normalKPos);
        pKamera.setzeBlickpunkt(normalKBli);

        //pKamera.an();
    }

    private void Tafel1Erstellen() {
        picCoin = new GLTafel(-177, 131.5, -1, 12, 13, "Coin2.png");
        picCoin.setzeAutodrehung(true);
        picCoin.setzeKamerafixierung(true);
    }

    private void Tafel2Erstellen() {
        anzeige = new GLTafel(-164, 132, 0, 12, 13, "Knopf.png");
        anzeige.setzeAutodrehung(true);
        anzeige.setzeKamerafixierung(true);
        anzeige.setzeText(score, 10);
        anzeige.setzeTextfarbe(0, 0, 0);
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
        anzeige.setzeText(score, 10);
    }
}
