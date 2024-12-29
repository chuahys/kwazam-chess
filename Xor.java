public class Xor extends Piece {
    public Xor(PieceColor color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        // Must move diagonally (row and column must change by the same amount)
        if (Math.abs(endRow - startRow) != Math.abs(endCol - startCol)) {
            return false;
        }

        // Determine direction of movement (diagonal)
        int rowDirection = Integer.compare(endRow, startRow);
        int colDirection = Integer.compare(endCol, startCol);

        // Check all squares in the path to ensure they are empty
        int currentRow = startRow + rowDirection;
        int currentCol = startCol + colDirection;

        while (currentRow != endRow || currentCol != endCol) {
            if (board.getPieceAt(currentRow, currentCol) != null) {
                return false; // Path is blocked
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        // Check if the target square is occupied by an ally
        Piece targetPiece = board.getPieceAt(endRow, endCol);
        return targetPiece == null || targetPiece.getColor() != getColor();
    }

    @Override
    public void transform() {
        // Replace this piece with a Tor piece
        Tor transformPiece = new Tor(getColor(), getRow(), getCol());
        Board board = Board.getInstance();
        board.setPieceAt(transformPiece, getRow(), getCol());
        System.out.println(this.getColor() + " Xor at (" + getRow() + ", " + getCol() + ") transformed into Tor.");
    }
    
    @Override
    public String getPieceType() {
        return PieceType.XOR.name(); // Return the name of the PieceType as a String
    }
}
