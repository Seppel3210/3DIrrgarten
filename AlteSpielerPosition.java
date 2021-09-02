import GLOOP.GLVektor;

public class AlteSpielerPosition {
    public GLVektor position;
    public Vektor2 blickrichtung;
    public Vektor2 posImArray;

    public AlteSpielerPosition(GLVektor pPosition, Vektor2 pBlickrichtung, Vektor2 pPosImArray) {
        position = pPosition;
        blickrichtung = pBlickrichtung;
        posImArray = pPosImArray;
    }

}
