/**
 * Tor class is a subclass of the Piece class.
 * The Tor moves in a straight line either horizontally or vertically.
 * After 2 turns, it will turn into Xor.
 */
// Constructor
public class Tor extends Piece {
    public Tor(PieceColor color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public String getImagePath(boolean isFlip) {
        String pieceName = "Tor";
        String colorName = getColor() == PieceColor.BLUE ? "b" : "r";

        // Tor does not have flipped images
        return "resources/img/" + colorName + pieceName + ".png";
    }

    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        // Must move either same row or same column
        if (startRow != endRow && startCol != endCol) {
            return false;
        }

        // Determine direction of movement for rows and columns
        int rowDir = Integer.compare(endRow, startRow); // -1 for up, 1 for down, 0 for no row movement
        int colDir = Integer.compare(endCol, startCol); // -1 for left, 1 for right, 0 for no column movement

        // Check if the path is clear
        int currRow = startRow + rowDir;
        int currCol = startCol + colDir;

        while (currRow != endRow || currCol != endCol) {
            if (board.getPieceAt(currRow, currCol) != null) {
                return false; // Path is blocked
            }
            currRow += rowDir;
            currCol += colDir;
        }

        // Check if the target square is occupied by an ally or empty
        Piece tgtPiece = board.getPieceAt(endRow, endCol);
        return tgtPiece == null || tgtPiece.getColor() != getColor();
    }

    // Replace this piece with an Xor piece every two rounds.
    @Override
    public void transform() {
        Xor transPiece = new Xor(getColor(), getRow(), getCol());
        Board board = Board.getInstance(); // Assuming Board follows Singleton pattern
        board.setPieceAt(transPiece, getRow(), getCol());
        System.out.println(this.getColor() + " Tor at (" + getRow() + ", " + getCol() + ") transformed into Xor.");
    }
    
    // Return the name of the PieceType as a String
    @Override
    public String getPieceType() {
        return PieceType.TOR.name(); 
    }
    
}
