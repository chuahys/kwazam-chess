/**
 * Represents the color of a piece in the game. (Blue or Red)
 * @author Chuah Yun Shan
 */
public enum PieceColor {
    BLUE, RED;

    /**
     * Return "Blue" if the color is BLUE, "Red" if the color is RED.
     */
    @Override
    public String toString() {
        return this == BLUE ? "Blue" : "Red";
    }
}