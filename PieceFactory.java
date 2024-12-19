public class PieceFactory {
    public static Piece createPiece(String type, PieceColor color, Position position) {
        switch (type) {
            case "Ram":
                return new Ram(color, position);
            case "Biz":
                return new Biz(color, position);
            case "Tor":
                return new Tor(color, position);
            case "Xor":
                return new Xor(color, position);
            case "Sau":
                return new Sau(color, position);
            default:
                throw new IllegalArgumentException("Unknown piece type: " + type);
        }
    }
}
