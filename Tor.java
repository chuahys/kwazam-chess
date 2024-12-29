public class Tor extends Piece {
    public Tor(PieceColor color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        // Must move orthogonally (either same row or same column)
        if (startRow != endRow && startCol != endCol) {
            return false;
        }

        // Determine direction of movement
        int rowDirection = Integer.compare(endRow, startRow); // -1 for up, 1 for down, 0 for no row movement
        int colDirection = Integer.compare(endCol, startCol); // -1 for left, 1 for right, 0 for no column movement

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
        // Replace this piece with an Xor piece
        Xor transformPiece = new Xor(getColor(), getRow(), getCol());
        Board board = Board.getInstance(); // Assuming Board follows Singleton pattern
        board.setPieceAt(transformPiece, getRow(), getCol());
        System.out.println(this.getColor() + " Tor at (" + getRow() + ", " + getCol() + ") transformed into Xor.");
    }
    
    @Override
    public String getPieceType() {
        return PieceType.TOR.name(); // Return the name of the PieceType as a String
    }
    
}
