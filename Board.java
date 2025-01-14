import java.util.*;

/**
 * The Board class represents the game board and manages the pieces on it.
 */
public class Board {
    private static Board instance; // The single instance of Board
    private static final int HEIGHT = 8; // fixed rows
    private static final int WIDTH = 5; // fixed columns
    private Piece[][] board; // 2D array for pieces
    private List<BoardObserver> observer = new ArrayList<>(); // List of observers for the board

    /**
     * Private constructor for Board.
     */
    private Board() {
        board = new Piece[HEIGHT][WIDTH]; // Initialize an 8x5 board
    }

    /**
     * Singleton pattern to ensure only one instance of the Board class.
     * @author Chuah Yun Shan
     */
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board(); // Create the instance if it doesn't exist
        }
        return instance;
    }

    /**
     * Get the number of rows of the board.
     */
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * Get the number of columns of the board.
     */
    public int getWidth() {
        return WIDTH;
    }

    /**
     * Get the piece at a specific position on the board.
     */
    public Piece getPieceAt(int row, int col) {
        if (row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH) {
            return null; // Return null for out-of-bounds indices
        }
        return board[row][col];
    }

    /**
     * Place a piece at a specific position on the board.
     */
    public void setPieceAt(Piece piece, int row, int col) {
        if (row >= 0 && row < HEIGHT && col >= 0 && col < WIDTH) {
            board[row][col] = piece;
        }
    }

    /**
     * Add an observer to the board.
     */
    public void addObserver(BoardObserver ob) {
        observer.add(ob);
    }
    
    /**
     * Remove an observer from the board.
     */
    public void removeObserver(BoardObserver ob) {
        observer.remove(ob);
    }

    /**
     * Notify all observers about a piece transformation on the board.
     */
    public void notifyTransform() {
        for (BoardObserver ob : observer) {
            ob.refreshBoard(); // Notify observers to refresh the board
        }
    }

    /**
     * Set up the initial positions of all pieces on the board.
     * @author Tan Yun Xuan
     */
    public void setupPieces() {
        // Setup Blue pieces (bottom of the board)
        for (int col = 0; col < WIDTH; col++) {
            board[6][col] = PieceFactory.createPiece(PieceType.RAM, PieceColor.BLUE, 6, col);
        }
        board[7][0] = PieceFactory.createPiece(PieceType.XOR, PieceColor.BLUE, 7, 0);
        board[7][1] = PieceFactory.createPiece(PieceType.BIZ, PieceColor.BLUE, 7, 1);
        board[7][2] = PieceFactory.createPiece(PieceType.SAU, PieceColor.BLUE, 7, 2);
        board[7][3] = PieceFactory.createPiece(PieceType.BIZ, PieceColor.BLUE, 7, 3);
        board[7][4] = PieceFactory.createPiece(PieceType.TOR, PieceColor.BLUE, 7, 4);

        // Setup Red pieces (top of the board)
        for (int col = 0; col < WIDTH; col++) {
            board[1][col] = PieceFactory.createPiece(PieceType.RAM, PieceColor.RED, 1, col);
        }
        board[0][0] = PieceFactory.createPiece(PieceType.TOR, PieceColor.RED, 0, 0);
        board[0][1] = PieceFactory.createPiece(PieceType.BIZ, PieceColor.RED, 0, 1);
        board[0][2] = PieceFactory.createPiece(PieceType.SAU, PieceColor.RED, 0, 2);
        board[0][3] = PieceFactory.createPiece(PieceType.BIZ, PieceColor.RED, 0, 3);
        board[0][4] = PieceFactory.createPiece(PieceType.XOR, PieceColor.RED, 0, 4);
    }
    
    /** 
     * Flip the board (reverse positions of the pieces)
     * @author Chuah Yun Shan
     */
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

        // Replace the board with the flipped board using setPieceAt()
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                setPieceAt(tempBoard[row][col], row, col);
            }
        }
    }

    /**
     * Reset the board (clear all pieces).
     * @author Chuah Yun Shan
     */
    public void resetBoard() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                board[row][col] = null;
            }
        }
    }
}