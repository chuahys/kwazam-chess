import java.util.*;

public class Board {
    private static Board instance; // The single instance of Board
    private static final int HEIGHT = 8;
    private static final int WIDTH = 5;
    private Piece[][] board; // 2D array for pieces
    private List<BoardObserver> observers = new ArrayList<>();


    private Board() {
        board = new Piece[HEIGHT][WIDTH]; // Initialize an 8x5 board
    }

    // Singleton pattern to ensure only one instance of Board
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board(); // Create the instance if it doesn't exist
        }
        return instance;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    // Check if the given position is within bounds
    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < HEIGHT && col >= 0 && col < WIDTH;
    }

    // Method to get the piece at a specific position
    public Piece getPieceAt(int row, int col) {
        if (row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH) {
            return null; // Return null for out-of-bounds indices
        }
        return board[row][col];
    }

    // Method to set a piece at a specific position
    public void setPieceAt(Piece piece, int row, int col) {
        if (isInBounds(row, col)) {
            board[row][col] = piece;
        }
    }

    // Add an observer to the list
    public void addObserver(BoardObserver observer) {
        observers.add(observer);
    }
    
    // Remove an observer from the list
    public void removeObserver(BoardObserver observer) {
        observers.remove(observer);
    }

    // Notify all observers about a piece transformation
    public void notifyTransform() {
        for (BoardObserver observer : observers) {
            observer.refreshBoard(); // Notify observers to refresh the board
        }
    }

    // Add pieces to the board in their initial positions
    public void setupPieces() {
        // Setup Blue pieces (bottom of the board)
        for (int col = 0; col < WIDTH; col++) {
            board[6][col] = PieceFactory.createPiece("Ram", PieceColor.BLUE, 6, col);
        }
        board[7][0] = PieceFactory.createPiece("Xor", PieceColor.BLUE, 7, 0);
        board[7][1] = PieceFactory.createPiece("Biz", PieceColor.BLUE, 7, 1);
        board[7][2] = PieceFactory.createPiece("Sau", PieceColor.BLUE, 7, 2);
        board[7][3] = PieceFactory.createPiece("Biz", PieceColor.BLUE, 7, 3);
        board[7][4] = PieceFactory.createPiece("Tor", PieceColor.BLUE, 7, 4);

        // Setup Red pieces (top of the board)
        for (int col = 0; col < WIDTH; col++) {
            board[1][col] = PieceFactory.createPiece("Ram", PieceColor.RED, 1, col);
        }
        board[0][0] = PieceFactory.createPiece("Tor", PieceColor.RED, 0, 0);
        board[0][1] = PieceFactory.createPiece("Biz", PieceColor.RED, 0, 1);
        board[0][2] = PieceFactory.createPiece("Sau", PieceColor.RED, 0, 2);
        board[0][3] = PieceFactory.createPiece("Biz", PieceColor.RED, 0, 3);
        board[0][4] = PieceFactory.createPiece("Xor", PieceColor.RED, 0, 4);
    }
    
    // Flip the board (reverse positions of the pieces)
    public void flipBoard() {
        Piece[][] tempBoard = new Piece[HEIGHT][WIDTH];

        // Iterate over the board to swap positions
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Piece piece = getPieceAt(row, col);
                if (piece != null) {
                    int flipRow = HEIGHT - 1 - row;  // Flip the row
                    int flipCol = WIDTH - 1 - col;  // Flip the column

                    // Update the piece's internal position
                    piece.setPosition(flipRow, flipCol);

                    // Place the piece on the flipped board
                    tempBoard[flipRow][flipCol] = piece;
                }
            }
        }

        // Replace the board with the flipped board using setPieceAt
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                setPieceAt(tempBoard[row][col], row, col);
            }
        }
    }

    // Reset the board (clear all pieces)
    public void resetBoard() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                board[row][col] = null;
            }
        }
    }
}
