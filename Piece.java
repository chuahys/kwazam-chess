/**
 * The Piece class represents abstract class of all the pieces.
 * @author Chuah Yun Shan
 * @author Tan Yun Xuan
 */
public abstract class Piece {
    private PieceColor color; // Color of the piece (BLUE or RED)
    private int row, col; // Position of the piece on the board
    
    /**
     * Constructor to initialize a piece with its color and position.
     */
    public Piece(PieceColor color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    /**
     * Get the color of the piece.
     */
    public PieceColor getColor() {
        return color;
    }
    /**
     * Get the current row position of the piece.
     */
    public int getRow() {
        return row;
    }
    /**
     * Get the current column position of the piece.
     */
    public int getCol() {
        return col;
    }

    /**
     * Update and set the position of the piece on the board.
    */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Get the image path of the specific piece based on flip state.
     */
    public abstract String getImagePath(boolean isFlip); // Pass flip state

    /**
     * Checks if a move is valid for the piece.
     */
    public abstract boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol);

    /**
     * Optional transformation logic (for Tor and Xor pieces only)
     * 
     */
    public abstract void transform();

    /**
     * Get the type of the piece.
     */
    public abstract String getPieceType();

    /**
     * Get the color code for the piece as a string (used for save/load).
     */
    public String getColorCode() {
        if (color == PieceColor.RED) {
            return "R";
        } else if (color == PieceColor.BLUE) {
            return "B";
        }
        return "";
    }
}
