public class Ram extends Piece {
    private boolean movingUp; // Tracks the current direction of the Ram

    public Ram(PieceColor color, int row, int col) {
        super(color, row, col);
        this.movingUp = true; // Initially moving upward
    }

    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        // The Ram moves one step forward (vertically)
        if (movingUp) {
            // Move one step upward (decrease row by 1) and stay in the same column
            if (endRow != startRow - 1 || endCol != startCol) {
                return false; // Invalid if not exactly one step upward in the same column
            }
        } else {
            // Move one step downward (increase row by 1) and stay in the same column
            if (endRow != startRow + 1 || endCol != startCol) {
                return false; // Invalid if not exactly one step downward in the same column
            }
        }

        // Check if the target square is empty or occupied by an opponent
        Piece targetPiece = board.getPieceAt(endRow, endCol);
        if (targetPiece == null || targetPiece.getColor() != getColor()) {
            return true; // Valid if empty or occupied by an opponent
        }

        return false; // Invalid if occupied by an ally
    }
    
    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        // Update direction if the Ram reaches the end of the board
        if (row == 0) {
            movingUp = false; // Change direction to downward
        } else if (row == Board.getInstance().getHeight() - 1) {
            movingUp = true; // Change direction to upward
        }
    }

    @Override
    public void transform() {
        // No transformation for Ram, it just moves forward
    }

    @Override
    public String getPieceType() {
        return PieceType.RAM.name(); // Return the name of the PieceType as a String
    }
    
}