import java.util.*;
import javax.swing.JOptionPane;

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

    public void switchPlayer() {
        // Switch the current player color
        currentPlayer = (currentPlayer == PieceColor.BLUE) ? PieceColor.RED : PieceColor.BLUE;
        moveCount++;

        // Transform pieces every two turns (4 moves)
        if (moveCount % 4 == 0) {
            transformPiece();
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
        for (int targetRow = 0; targetRow < board.getHeight(); targetRow++) {
            for (int targetCol = 0; targetCol < board.getWidth(); targetCol++) {
                // Check if the move is valid
                if (piece.isValidMove(board, row, col, targetRow, targetCol)) {
                    validMoves.add(new int[] { targetRow, targetCol }); // Add the move to the list
                }
            }
        }

        return validMoves;
    }
    
    // Move the piece on the board
    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
        Piece piece = board.getPieceAt(startRow, startCol);

        // Check if the piece exists and if the move is valid
        if (piece != null && piece.isValidMove(board, startRow, startCol, endRow, endCol)) {
            board.setPieceAt(piece, endRow, endCol); // Move the piece to the new position
            board.setPieceAt(null, startRow, startCol); // Clear the old position
            piece.setPosition(endRow, endCol); // Update the piece's internal position
            switchPlayer(); // Switch the turn after a valid move
            return true;
        }
        return false;
    }
    
    // Method to transform Tor and Xor pieces on the board
    public void transformPiece() {
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                Piece piece = board.getPieceAt(row, col);

                // Transform only Tor and Xor pieces
                if (piece instanceof Tor || piece instanceof Xor) {
                    piece.transform(); // Call the transform method of the piece
                }
            }
        }
        // Notify observers about the transformation
        board.notifyTransform();
    }

    public boolean checkForWinner() {
        // Check if either Sau is captured by checking the board state
        boolean blueCapture = true;
        boolean redCapture = true;

        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece instanceof Sau) {
                    if (piece.getColor() == PieceColor.BLUE) {
                        blueCapture = false; // Blue Sau is still on the board
                    } else if (piece.getColor() == PieceColor.RED) {
                        redCapture = false; // Red Sau is still on the board
                    }
                }
            }
        }

        if (blueCapture) {
            // Red wins
            JOptionPane.showMessageDialog(null, "Red wins! Sau captured!");
            //restartGame(); // Restart the game after the winner is 
            return true;
        } else if (redCapture) {
            // Blue wins
            JOptionPane.showMessageDialog(null, "Blue wins! Sau captured!");
            //restartGame(); // Restart the game after the winner is declared
            return true;
        }
        return false; // No winner yet
    }
    
    // Reset the game state
    public void resetGame() {
        this.currentPlayer = PieceColor.BLUE;  // Set starting player
        this.moveCount = 0; // Reset move count
        board.resetBoard(); // Reset the board
        board.setupPieces(); // Place pieces on the board in the starting positions
    }
}
