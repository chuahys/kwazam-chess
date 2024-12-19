public class Ram extends Piece {
    public Ram(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        int forwardDirection = color == PieceColor.BLUE ? -1 : 1;
        int rowDiff = (newPosition.getRow() - position.getRow()) * forwardDirection;
        int colDiff = newPosition.getColumn() - position.getColumn();

        // Forward move
        if (colDiff == 0 && rowDiff == 1 && board[newPosition.getRow()][newPosition.getColumn()] == null) {
            return true; // Move forward one square
        }

        return false;
    }
}