import java.util.*;

/**
 * Game class represents the main logic of this chess game.
 * It tracks the game state, including the board, player, and moves.
 */
public class Game {
    private static Game instance; // Singleton instance of Game
    private Board board;
    private PieceColor currentPlayer; // Track the current player
    private int moveCount; // Track the number of moves

    /**
     * Private constructor for Game.
     */
    private Game() {
        board = new Board(); // create board
        this.currentPlayer = PieceColor.BLUE; // Start with BLUE player
        moveCount = 0;
        board.setupPieces(); // Initialize pieces for both players
    }

     /**
     * Singleton method to ensure only one single instance of the Game.
     * @author Tan Yun Xuan
     */
     public static Game getInstance() {
         if (instance == null) {
             instance = new Game();
         }
         return instance;
     }

    /**
     * Get the game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Set the current player (BLUE or RED).
     */
    public void setCurrentPlayer(PieceColor currentPlayer) {
        this.currentPlayer = currentPlayer; // Use `this` to access instance variable
    }

    /**
     * Get the current player (BLUE or RED).
     */
    public PieceColor getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Set the move count.
     */
    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }
    
    /**
     * Get the move count (number of moves made).
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Switch the turn to the next player and increment the move count.
     * Transforms pieces every two turns (4 moves).
     * @author Tan Yun Xuan
     * @author Chuah Yun Shan
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == PieceColor.BLUE) ? PieceColor.RED : PieceColor.BLUE;
        moveCount++;

        // Transform pieces every two turns (4 moves)
        if (moveCount % 4 == 0) {
            transformPiece();
        }
    }

    /**
     * Get a list of valid moves as (row, col) coordinates for a piece.
     * @author Chuah Yun Shan
     */
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

    /**
     * Attempt to move a piece from current position to new position.
     * @author Chuah Yun Shan
     */
    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
        Piece piece = board.getPieceAt(startRow, startCol);

        // Check if the piece exists and if the move is valid
        if (piece != null && piece.isValidMove(board, startRow, startCol, endRow, endCol)) {
            board.setPieceAt(piece, endRow, endCol); // Move the piece to the new position
            board.setPieceAt(null, startRow, startCol); // Clear the old position
            piece.setPosition(endRow, endCol); // Update the piece's internal position
            switchPlayer(); // Switch the player turn after a valid move
            return true; //  Return true if the move is valid and successful
        }
        return false;
    }
    
    /**
     * Transform Tor and Xor pieces on the board after 4 moves.
     *  @author Chuah Yun Shan
     */
    public void transformPiece() {
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                Piece piece = board.getPieceAt(row, col);
                // Transform only Tor and Xor pieces
                if (piece instanceof Tor || piece instanceof Xor) {
                    piece.transform(board); // Call the transform method of the piece
                }
            }
        }
        // Notify observers about the transformation
        board.notifyTransform();
    }

     /**
     * Check if there is a winner in the game.
     * @author Chuah Yun Shan
     */
     public PieceColor checkWinner() {
         boolean bSau = true;
         boolean rSau = true;

         // Iterate over all rows and columns of the board
         for (int row = 0; row < board.getHeight(); row++) {
             for (int col = 0; col < board.getWidth(); col++) {
                 Piece piece = board.getPieceAt(row, col);

                 if (piece instanceof Sau) {
                     // If the Sau piece is found, check its color
                     if (piece.getColor() == PieceColor.BLUE) {
                         bSau = false; // Blue's Sau is still on the board
                     } else if (piece.getColor() == PieceColor.RED) {
                         rSau = false; // Red's Sau is still on the board
                     }
                 }
             }
         }
         // Return color of the winner
         // If Blue's Sau is captured, Red wins
         if (bSau) {
             return PieceColor.RED;
         }
         // If Red's Sau is captured, Blue wins
         if (rSau) {
             return PieceColor.BLUE;
         }
         // No winner yet
         return null;
     }

    /**
     * Reset the game to its initial state.
     * @author Chuah Yun Shan
     */
    public void resetGame() {
        currentPlayer = PieceColor.BLUE; // Reset starting player
        moveCount = 0; // Reset move count
        board.resetBoard(); // Reset the board
        board.setupPieces(); // Set initial positions
    }
}