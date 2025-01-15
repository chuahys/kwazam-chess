/**
 * Factory design pattern for creating instances of pieces in the game.
 * @author Tan Yun Xuan
 */
public class PieceFactory {
    // Create a new piece based on the given type, color, and position (row, col)
    public static Piece createPiece(PieceType type, PieceColor color, int row, int col) {
        // Determine the type of piece
        switch (type) {
            case RAM:
                return new Ram(color, row, col);
            case BIZ:
                return new Biz(color, row, col);
            case TOR:
                return new Tor(color, row, col);
            case XOR:
                return new Xor(color, row, col);
            case SAU:
                return new Sau(color, row, col);
            default:
                throw new IllegalArgumentException("Unknown piece type: " + type);
        }
    }
}
