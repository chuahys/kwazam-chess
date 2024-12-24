import java.util.*;

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
    public List<int[]> getValidMoves(Board board) {
        List<int[]> validMoves = new ArrayList<>();

        // Check all 8 possible directions (vertical, horizontal, and diagonal)
        int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1}, // Top-left, top, top-right
            {0, -1}, {0, 1},             // Left, right
            {1, -1}, {1, 0}, {1, 1}      // Bottom-left, bottom, bottom-right
        };

        for (int[] direction : directions) {
            int newRow = getRow() + direction[0];
            int newCol = getCol() + direction[1];

            // Check if the new position is within bounds
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 5) {
                Piece piece = board.getPieceAt(newRow, newCol);
                // Add the move if the square is empty or occupied by an opponent
                if (piece == null || piece.getColor() != getColor()) {
                    validMoves.add(new int[]{newRow, newCol});
                }
            }
        }

        return validMoves;
    }

    @Override
    public void transform() {
        // The Sau does not transform, so no implementation needed here
    }
}
