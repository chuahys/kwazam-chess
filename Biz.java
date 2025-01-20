/**
 * Biz class is a subclass of the Piece class.
 * It moves in L-shape and can skip over other pieces.
 * @author Lee Kar Yen
 */
public class Biz extends Piece {
    public Biz(PieceColor color, int row, int col) {
        super(color, row, col);
    }

     @Override
    public String getImagePath(boolean isFlip) {
        String pieceName = "Biz";
        String colorName = getColor() == PieceColor.BLUE ? "b" : "r";

        // return the img cuz Biz does not have flipped images
        return "resources/img/" + colorName + pieceName + ".png";
    }

    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        // Calculate the move differences
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        // Must follow the Lshape pattern (2,1) or (1,2)
        boolean isLShape = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        if (!isLShape) {
            return false; //invalid if not an Lshape move
        }

        // Check if the target position 
        Piece targetPiece = board.getPieceAt(endRow, endCol);
        // Check if the target position is occupied by an ally piece
        if (targetPiece != null && targetPiece.getColor() == getColor()) {
            return false; // Can't land on a square occupied by an ally
        }

        return true;
    }

    @Override
    public void transform(Board board) {
        // No transformation logic for Biz
    }

    @Override
    public String getPieceType() {
        return PieceType.BIZ.name(); // Return the name of the PieceType as a String
    }
}
