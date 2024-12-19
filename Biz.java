public class Biz extends Piece {
  public Biz(PieceColor color, Position position) {
      super(color, position);
  }

  @Override
  public boolean isValidMove(Position newPosition, Piece[][] board) {
      if (newPosition.equals(this.position)) {
          return false;
      }

      int rowDiff = Math.abs(this.position.getRow() - newPosition.getRow());
      int colDiff = Math.abs(this.position.getColumn() - newPosition.getColumn());

      // L-shape move (3x2 or 2x3) and skips over pieces
      return (rowDiff == 3 && colDiff == 2) || (rowDiff == 2 && colDiff == 3);
  }
}