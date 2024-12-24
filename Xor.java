import java.util.*;

public class Xor extends Piece {
    private int moveCount; // Track the number of moves to handle transformation

    public Xor(PieceColor color, int row, int col) {
        super(color, row, col);
        this.moveCount = 0;
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
    public List<int[]> getValidMoves(Board board) {
        List<int[]> validMoves = new ArrayList<>();

        // Check all 4 diagonal directions (top-left, top-right, bottom-left, bottom-right)
        int[][] directions = {
            {-1, -1}, {-1, 1}, // Top-left, top-right
            {1, -1}, {1, 1}    // Bottom-left, bottom-right
        };

        for (int[] direction : directions) {
            int newRow = getRow();
            int newCol = getCol();

            // Move in the current direction until the edge of the board or a block
            while (true) {
                newRow += direction[0];
                newCol += direction[1];

                // Stop if out of bounds
                if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 5) {
                    break;
                }

                // Stop if the square is occupied by an ally
                Piece piece = board.getPieceAt(newRow, newCol);
                if (piece != null) {
                    if (piece.getColor() == getColor()) {
                        break;
                    }
                    // Add the move if the square is occupied by an opponent
                    validMoves.add(new int[]{newRow, newCol});
                    break;
                }

                // Add the move if the square is empty
                validMoves.add(new int[]{newRow, newCol});
            }
        }

        return validMoves;
    }

    @Override
    public void transform() {
        // Replace this piece with a Tor piece
        Tor transformedPiece = new Tor(getColor(), getRow(), getCol());
        Board board = Board.getInstance();
        board.setPieceAt(getRow(), getCol(), transformedPiece);
        System.out.println("Xor at (" + getRow() + ", " + getCol() + ") transformed into Tor.");
    }
}
