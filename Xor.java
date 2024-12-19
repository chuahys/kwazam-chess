public class Xor extends Piece {
    private int turns;

    public Xor(PieceColor color, Position position) {
        super(color, position);
        this.turns = 0; // Initialize turn counter
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        // Xor can move diagonally
        int rowDifference = Math.abs(position.getRow() - newPosition.getRow());
        int colDifference = Math.abs(position.getColumn() - newPosition.getColumn());

        if (rowDifference == colDifference) {
            int rowDirection = (newPosition.getRow() > position.getRow()) ? 1 : -1;
            int colDirection = (newPosition.getColumn() > position.getColumn()) ? 1 : -1;

            int row = position.getRow() + rowDirection;
            int col = position.getColumn() + colDirection;

            while (row != newPosition.getRow() && col != newPosition.getColumn()) {
                if (board[row][col] != null) {
                    return false; // There's a piece in the way
                }
                row += rowDirection;
                col += colDirection;
            }

            // Check the destination square for capturing
            Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
            if (destinationPiece == null || destinationPiece.getColor() != this.getColor()) {
                return true; // Valid move or capture
            }
        }
        return false; // Invalid move (Xor can only move diagonally)
    }

    @Override
    public void transformIfNecessary() {
        turns++;
        if (turns == 2) {
            // Transform to Tor
            System.out.println("Xor transforms into Tor");
            // In a real game, you'd update the board with a new Tor piece
        }
    }
}
