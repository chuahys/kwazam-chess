import java.util.*;

public class Game {
    private Board board;
    private PieceColor currentPlayer; // Use PieceColor to track current player
    private int moveCount; // Track the number of moves

    public Game() {
        board = Board.getInstance(); // Get the single instance of Board
        currentPlayer = PieceColor.BLUE; // Start with BLUE player
        moveCount = 0;
        board.setupPieces(); // Initialize pieces for both players
    }

    public Board getBoard() {
        return board;
    }

    public PieceColor getCurrentPlayer() {
        return currentPlayer;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void switchTurn() {
        // Switch the current player color
        currentPlayer = (currentPlayer == PieceColor.BLUE) ? PieceColor.RED : PieceColor.BLUE;
        moveCount++;

        // Transform pieces every two turns
        if (moveCount > 2 && moveCount % 2 == 0) {
            board.transformPiece();
        }
    }

    public List<int[]> getValidMoves(int row, int col) {
        List<int[]> validMoves = new ArrayList<>();

        // Get the piece at the selected position
        Piece piece = board.getPieceAt(row, col);
        if (piece == null || piece.getColor() != currentPlayer) {
            return validMoves; // Return empty list if piece is null or not current player's piece
        }

        // Iterate through all possible board positions
        for (int targetRow = 0; targetRow < 8; targetRow++) {
            for (int targetCol = 0; targetCol < 5; targetCol++) {
                // Check if the move is valid
                if (piece.isValidMove(board, row, col, targetRow, targetCol)) {
                    validMoves.add(new int[] { targetRow, targetCol }); // Add the move to the list
                    System.out.println("Valid move: " + row + ", " + col + " to " + targetRow + ", " + targetCol);
                }
            }
        }

        return validMoves;
    }

     // Move the piece on the board
    public boolean makeMove(int startRow, int startCol, int endRow, int endCol) {
        Piece piece = board.getPieceAt(startRow, startCol);

        // Check if the piece exists and if the move is valid
        if (piece != null && piece.isValidMove(board, startRow, startCol, endRow, endCol)) {
            board.movePiece(startRow, startCol, endRow, endCol);  // Move the piece on the board
            switchTurn();  // Switch the turn after a valid move
            return true;
        }
        return false;
    }

    // (Optional) Reset the game state (useful for restarting)
    public void resetGame() {
        board.setupPieces();  // Reset pieces
        currentPlayer = PieceColor.BLUE;  // Set starting player
        moveCount = 0;  // Reset move count
    }
}
