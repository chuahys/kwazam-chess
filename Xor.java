/**
 * Xor class is a subclass of the Piece class.
 * The Xor moves in a diagonally.
 * After 2 turns, it will turn into Tor.
 * @author Tan Yun Xuan
 */

 // Constructor
public class Xor extends Piece {
    public Xor(PieceColor color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public String getImagePath(boolean isFlip) {
        String pieceName = "Xor";
        String colorName = getColor() == PieceColor.BLUE ? "b" : "r";

        // Xor does not have flipped images
        return "resources/img/" + colorName + pieceName + ".png";
    }
    
    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        // Must move diagonally (row and column must change by the same amount)
        if (Math.abs(endRow - startRow) != Math.abs(endCol - startCol)) {
            return false;
        }

        // Determine direction of movement (diagonal)
        int rowDir = Integer.compare(endRow, startRow);
        int colDir = Integer.compare(endCol, startCol);

        // Check all squares in the path to ensure they are empty
        int currRow = startRow + rowDir;
        int currCol = startCol + colDir;

        while (currRow != endRow || currCol != endCol) {
            if (board.getPieceAt(currRow, currCol) != null) {
                return false; // Path is blocked
            }
            currRow += rowDir;
            currCol += colDir;
        }

        // Check if the target square is occupied by an ally
        Piece tgtPiece = board.getPieceAt(endRow, endCol);
        return tgtPiece == null || tgtPiece.getColor() != getColor();
    }


    // Replace this piece with a Tor piece every two rounds.
    @Override
    public void transform(Board board) {
        Tor transPiece = new Tor(getColor(), getRow(), getCol());
        board.setPieceAt(transPiece, getRow(), getCol());
        System.out.println(this.getColor() + " Xor at (" + getRow() + ", " + getCol() + ") transformed into Tor.");
    }
    
    // Return the name of the PieceType as a String
    @Override
    public String getPieceType() {
        return PieceType.XOR.name();
    }
}
