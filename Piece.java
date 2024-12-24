import java.util.List;

public abstract class Piece {
    private PieceColor color; // Color of the piece (RED or BLUE)
    protected int row, col;   // Position of the piece on the board

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
        if (row < 0 || row >= 8 || col < 0 || col >= 5) {
            throw new IllegalArgumentException("Invalid position on the board");
        }
        this.row = row;
        this.col = col;
    }

    // Abstract method to check if a move is valid for this piece
    public abstract boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol);

    // Abstract method to get valid moves for a specific piece
    public abstract List<int[]> getValidMoves(Board board);

    // Optional transformation logic (e.g., piece promotion or upgrade)
    public abstract void transform();
}
