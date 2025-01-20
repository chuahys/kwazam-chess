/**
 * Sau class is a subclass of the Piece class.
 * It moves one step in any direction.
 * @author Lee Kar Yen
 */
public class Sau extends Piece {

    public Sau(PieceColor color, int row, int col) {
        super(color, row, col);
    }
    
    @Override
    public String getImagePath(boolean isFlip) {
        String pieceName = "Sau";
        String colorName = getColor() == PieceColor.BLUE ? "b" : "r"; //determine the piece is red or blue side

        if (isFlip) {
            return "resources/img/" + colorName + pieceName + "_flip.png"; //flip if sau is on the other side
        } else {
            return "resources/img/" + colorName + pieceName + ".png";
        }
    }

    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        // The Sau can only move one step in any direction
        if (Math.abs(endRow - startRow) <= 1 && Math.abs(endCol - startCol) <= 1) {
            // Check if the target square is occupied by an ally
            Piece targetPiece = board.getPieceAt(endRow, endCol);
            return targetPiece == null || targetPiece.getColor() != getColor(); //the move is valid if the target is empty or occupied by an opponent's
                                                                                // piece
        }
        return false;
    }

    @Override
    public void transform(Board board) {
        // The Sau does not transform, so no implementation needed here
    }

    @Override
    public String getPieceType() {
        return PieceType.SAU.name(); // Return the name of the PieceType as a String
    }
}
