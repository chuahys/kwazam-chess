public class Tor extends Piece {
    private int turns;

    public Tor(PieceColor color, Position position) {
        super(color, position);
        this.turns = 0; // Initialize turn counter
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        // Tor can move orthogonally (horizontally or vertically)
        if (position.getRow() == newPosition.getRow()) {
            int columnStart = Math.min(position.getColumn(), newPosition.getColumn()) + 1;
            int columnEnd = Math.max(position.getColumn(), newPosition.getColumn());
            for (int column = columnStart; column < columnEnd; column++) {
                if (board[position.getRow()][column] != null) {
                    return false; // There's a piece in the way
                }
            }
        } else if (position.getColumn() == newPosition.getColumn()) {
            int rowStart = Math.min(position.getRow(), newPosition.getRow()) + 1;
            int rowEnd = Math.max(position.getRow(), newPosition.getRow());
            for (int row = rowStart; row < rowEnd; row++) {
                if (board[row][position.getColumn()] != null) {
                    return false; // There's a piece in the way
                }
            }
        } else {
            return false; // Invalid move (Tor can only move orthogonally)
        }

        // Check the destination square for capturing
        Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
        if (destinationPiece == null || destinationPiece.getColor() != this.getColor()) {
            return true; // Valid move or capture
        }

        return false; // Same color piece in the way
    }

    @Override
    public void transformIfNecessary() {
        turns++;
        if (turns == 2) {
            // Transform to Xor
            System.out.println("Tor transforms into Xor");
            // In a real game, you'd update the board with a new Xor piece
        }
    }
}
