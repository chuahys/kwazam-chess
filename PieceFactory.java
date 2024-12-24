public class PieceFactory {
    // Create a new piece based on the given type, color, and position (row, col)
    public static Piece createPiece(String type, PieceColor color, int row, int col) {
        switch (type) {
            case "Ram":
                return new Ram(color, row, col);
            case "Biz":
                return new Biz(color, row, col);
            case "Tor":
                return new Tor(color, row, col);
            case "Xor":
                return new Xor(color, row, col);
            case "Sau":
                return new Sau(color, row, col);
            default:
                throw new IllegalArgumentException("Unknown piece type: " + type);
        }
    }
}
