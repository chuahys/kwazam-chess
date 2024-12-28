public class Sau extends Piece {

    public Sau(PieceColor color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        // The Sau can move one step in any direction (vertically, horizontally, or diagonally)
        if (Math.abs(endRow - startRow) <= 1 && Math.abs(endCol - startCol) <= 1) {
            // Check if the target square is occupied by an ally
            Piece targetPiece = board.getPieceAt(endRow, endCol);
            return targetPiece == null || targetPiece.getColor() != getColor();
        }
        return false;
    }

    @Override
    public void transform() {
        // The Sau does not transform, so no implementation needed here
    }
}