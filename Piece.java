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

    // Abstract method to check if a move is valid for this piece
    public abstract boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol);

    // Optional transformation logic (for Tor and Xor pieces)
    public abstract void transform();


    public String getColorCode() {
        if (color == PieceColor.RED) {
            return "R";
        } else if (color == PieceColor.BLUE) {
            return "B";
        }
        return "";
    }

    // To return the piece type
    public abstract String getPieceType();

}
