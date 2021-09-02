import GLOOP.GLVektor;

public class AlteSpielerPosition {
    public GLVektor position;
    public GLVektor blickpunkt;
    public Vektor2 posImArray;

    public AlteSpielerPosition(GLVektor pPosition, GLVektor pBlickpunkt, Vektor2 pPosImArray) {
        position = pPosition;
        blickpunkt = pBlickpunkt;
        posImArray = pPosImArray;
    }

}
