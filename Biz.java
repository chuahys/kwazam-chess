import java.util.*;

public class Biz extends Piece {
    public Biz(PieceColor color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        // Calculate the move differences
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        // Must follow the L-shape pattern: (2, 1) or (1, 2)
        boolean isLShape = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        if (!isLShape) {
            return false;
        }

        // Check if the target position is occupied by the same color
        Piece targetPiece = board.getPieceAt(endRow, endCol);
        if (targetPiece != null && targetPiece.getColor() == getColor()) {
            return false; // Can't land on a square occupied by an ally
        }

        return true;
    }

    @Override
    public List<int[]> getValidMoves(Board board) {
        List<int[]> validMoves = new ArrayList<>();

        // Define all possible L-shaped moves
        int[][] moves = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] move : moves) {
            int newRow = getRow() + move[0];
            int newCol = getCol() + move[1];

            // Check if the new position is within the board bounds
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 5) {
                // Check if the move is valid
                if (isValidMove(board, getRow(), getCol(), newRow, newCol)) {
                    System.out.println("Biz can move to " + newRow + ", " + newCol);
                    validMoves.add(new int[]{newRow, newCol});
                }
            }
        }
        System.out.println("Valid moves for Biz at " + getRow() + ", " + getCol() + ": " + validMoves);

        return validMoves;
    }

    @Override
    public void transform() {
        // No transformation logic for Biz
    }
}
