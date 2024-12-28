public enum PieceColor {
    BLUE, RED;

    @Override
    public String toString() {
        return this == BLUE ? "Blue" : "Red";
    }
}