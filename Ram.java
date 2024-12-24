import java.util.*;

public class Ram extends Piece {
    public Ram(PieceColor color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        // The Ram moves one step forward (vertically).
        // Check if the move is only one row forward
        if (Math.abs(endRow - startRow) == 1 && startCol == endCol) {
            // Ensure the target square is empty or occupied by an opponent's piece
            Piece targetPiece = board.getPieceAt(endRow, endCol);
            return targetPiece == null || targetPiece.getColor() != getColor();
        }
        return false;
    }

    @Override
    public List<int[]> getValidMoves(Board board) {
        List<int[]> validMoves = new ArrayList<>();
        int direction = (getColor() == PieceColor.BLUE) ? 1 : -1; // Set direction based on color

        // Check for one step move in the given direction
        int newRow = getRow() + direction;

        if (newRow >= 0 && newRow < 8 && board.getPieceAt(newRow, getCol()) == null) {
            validMoves.add(new int[] { newRow, getCol() });
        }

        return validMoves;
    }

    @Override
    public void transform() {
        // No transformation for Ram, it just moves forward
    }
}
