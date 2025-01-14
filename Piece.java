// Piece.java (Abstract Class)
public abstract class Piece {
    private PieceColor color; // Color of the piece (BLUE or RED)
    private int row, col; // Position of the piece on the board
    
    public Piece(PieceColor color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public PieceColor getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Abstract method to get the image path
    public abstract String getImagePath(boolean isFlip); // Pass flip state

    // Abstract method to check if a move is valid for this piece
    public abstract boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol);

    // Optional transformation logic (for Tor and Xor pieces only)
    public abstract void transform();

    // To return the piece type
    public abstract String getPieceType();

    public String getColorCode() {
        if (color == PieceColor.RED) {
            return "R";
        } else if (color == PieceColor.BLUE) {
            return "B";
        }
        return "";
    }
}
