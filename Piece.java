public abstract class Piece {
    protected Position position;
    protected PieceColor color;

    public Piece(PieceColor color, Position position) {
        this.color = color;
        this.position = position;
    }

    public PieceColor getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    // Abstract method to be implemented by subclasses for movement validation
    public abstract boolean isValidMove(Position newPosition, Piece[][] board);

    // Abstract method for pieces that need transformation logic to implement it
    public void transformIfNecessary() {
        // Default implementation does nothing (only implemented in Tor/Xor)
    }
}
