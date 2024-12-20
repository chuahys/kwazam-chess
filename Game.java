import java.util.*;

public class Game {
    private static Game instance;
    private Board board;
    private boolean blueTurn = true; // Blue starts the game
    private int moveCount; // Count player moves

    private Game() {
        this.board = new Board();
        moveCount = 0;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public Board getBoard() {
        return this.board;
    }

    public void nextTurn() {
        moveCount++;
        if (moveCount % 2 == 0) {
            transformPieces();
        }
        blueTurn = !blueTurn;
        board.flipBoard();
    }

    private void transformPieces() {
        for (Piece[] row : board.getBoard()) {
            for (Piece piece : row) {
                if (piece != null) {
                    piece.transformIfNecessary();
                }
            }
        }
    }

    public void resetGame() {
        this.board = new Board();
        this.blueTurn = true;
        this.moveCount = 0;
    }


    public PieceColor getCurrentPlayerColor() {
        return blueTurn ? PieceColor.BLUE : PieceColor.RED;
    }

    public int getMoveCount() {
        return moveCount;
    }

    private Position selectedPosition;

    public boolean isPieceSelected() {
        return selectedPosition != null;
    }

    public boolean handleButtonSelection(int row, int col) {
        if (selectedPosition == null) {
            Piece selectedPiece = board.getPiece(row, col);
            if (selectedPiece != null
                    && selectedPiece.getColor() == (blueTurn ? PieceColor.BLUE : PieceColor.RED)) {
                selectedPosition = new Position(row, col);
                return false;
            }
        } else {
            boolean moveMade = makeMove(selectedPosition, new Position(row, col));
            selectedPosition = null;
            if (moveMade) {
                nextTurn(); // Ensure nextTurn is called after a move is made
            }        
            return moveMade;
        }
        return false;
    }

    public boolean makeMove(Position start, Position end) {
        Piece movingPiece = board.getPiece(start.getRow(), start.getColumn());
        if (movingPiece == null || movingPiece.getColor() != (blueTurn ? PieceColor.BLUE : PieceColor.RED)) {
            return false;
        }

        if (movingPiece.isValidMove(end, board.getBoard())) {
            board.movePiece(start, end);
            System.out.println("Current Player: " + (blueTurn ? "Blue" : "Red"));
            System.out.println(movingPiece.getClass().getSimpleName() +
              " moves from (" + (start.getRow()+1) + ", " + (start.getColumn()+1) + ") to (" + (end.getRow()+1) + ", " + (end.getColumn()+1) + ")");
            return true;
        }
        return false;
    }

    public List<Position> getValidMoveAt(Position position) {
        Piece selectedPiece = board.getPiece(position.getRow(), position.getColumn());
        if (selectedPiece == null) {
            return new ArrayList<>();
        }

        List<Position> validMove = new ArrayList<>();
        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; col < board.getBoard()[row].length; col++) {
                Position newPosition = new Position(row, col);
                if (selectedPiece.isValidMove(newPosition, board.getBoard())) {
                    validMove.add(newPosition);
                }
            }
        }
        return validMove;
    }

    // private Position findSauPosition(PieceColor color) {
    //     for (int row = 0; row < board.getBoard().length; row++) {
    //         for (int col = 0; col < board.getBoard()[row].length; col++) {
    //             Piece piece = board.getPiece(row, col);
    //             if (piece instanceof Sau && piece.getColor() == color) {
    //                 return new Position(row, col);
    //             }
    //         }
    //     }
    //     throw new RuntimeException("Sau not found, which should never happen.");
    // }

    // public boolean isInCheck(PieceColor SauColor) {
    //     Position SauPosition = findSauPosition(SauColor);
    //     for (int row = 0; row < board.getBoard().length; row++) {
    //         for (int col = 0; col < board.getBoard()[row].length; col++) {
    //             Piece piece = board.getPiece(row, col);
    //             if (piece != null && piece.getColor() != SauColor) {
    //                 if (piece.isValidMove(SauPosition, board.getBoard())) {
    //                     return true;
    //                 }
    //             }
    //         }
    //     }
    //     return false;
    // }
    
    // public boolean isCheckmate(PieceColor SauColor) {
    //     if (!isInCheck(SauColor)) {
    //         return false;
    //     }

    //     Position SauPosition = findSauPosition(SauColor);
    //     Sau Sau = (Sau) board.getPiece(SauPosition.getRow(), SauPosition.getColumn());

    //     for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
    //         for (int colOffset = -1; colOffset <= 1; colOffset++) {
    //             if (rowOffset == 0 && colOffset == 0) {
    //                 continue;
    //             }
    //             Position newPosition = new Position(SauPosition.getRow() + rowOffset,
    //                     SauPosition.getColumn() + colOffset);

    //             if (isPositionOnBoard(newPosition) && Sau.isValidMove(newPosition, board.getBoard())
    //                     && !wouldBeInCheckAfterMove(SauColor, SauPosition, newPosition)) {
    //                 return false;
    //             }
    //         }
    //     }
    //     return true;
    // }

    // private boolean isPositionOnBoard(Position position) {
    //     return position.getRow() >= 0 && position.getRow() < board.getBoard().length &&
    //             position.getColumn() >= 0 && position.getColumn() < board.getBoard()[0].length;
    // }

    // private boolean wouldBeInCheckAfterMove(PieceColor SauColor, Position from, Position to) {
    //     Piece temp = board.getPiece(to.getRow(), to.getColumn());
    //     board.setPiece(to.getRow(), to.getColumn(), board.getPiece(from.getRow(), from.getColumn()));
    //     board.setPiece(from.getRow(), from.getColumn(), null);

    //     boolean inCheck = isInCheck(SauColor);

    //     board.setPiece(from.getRow(), from.getColumn(), board.getPiece(to.getRow(), to.getColumn()));
    //     board.setPiece(to.getRow(), to.getColumn(), temp);

    //     return inCheck;
    // }



    
    // public List<Position> getLegalMovesForPieceAt(Position position) {
    //     Piece selectedPiece = board.getPiece(position.getRow(), position.getColumn());
    //     if (selectedPiece == null)
    //         return new ArrayList<>();

    //     List<Position> validMove = new ArrayList<>();
    //     switch (selectedPiece.getClass().getSimpleName()) {
    //         case "Ram":
    //             addRamMoves(position, selectedPiece.getColor(), validMove);
    //             break;
    //         case "Tor":
    //             addLineMoves(position, new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }, validMove);
    //             break;
    //         case "Biz":
    //             addSingleMoves(position, new int[][] { { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, 2 }, { -1, 2 },
    //                     { 1, -2 }, { -1, -2 } }, validMove);
    //             break;
    //         case "Xor":
    //             addLineMoves(position, new int[][] { { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } }, validMove);
    //             break;
    //         case "Sau":
    //             addSingleMoves(position, new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { -1, -1 },
    //                     { 1, -1 }, { -1, 1 } }, validMove);
    //             break;
    //     }
    //     return validMove;
    // }

    // private void addLineMoves(Position position, int[][] directions, List<Position> validMove) {
    //     for (int[] d : directions) {
    //         Position newPos = new Position(position.getRow() + d[0], position.getColumn() + d[1]);
    //         while (isPositionOnBoard(newPos)) {
    //             if (board.getPiece(newPos.getRow(), newPos.getColumn()) == null) {
    //                 validMove.add(new Position(newPos.getRow(), newPos.getColumn()));
    //                 newPos = new Position(newPos.getRow() + d[0], newPos.getColumn() + d[1]);
    //             } else {
    //                 if (board.getPiece(newPos.getRow(), newPos.getColumn()).getColor() != board
    //                         .getPiece(position.getRow(), position.getColumn()).getColor()) {
    //                     validMove.add(newPos);
    //                 }
    //                 break;
    //             }
    //         }
    //     }
    // }

    // private void addSingleMoves(Position position, int[][] moves, List<Position> validMove) {
    //     for (int[] move : moves) {
    //         Position newPos = new Position(position.getRow() + move[0], position.getColumn() + move[1]);
    //         if (isPositionOnBoard(newPos) && (board.getPiece(newPos.getRow(), newPos.getColumn()) == null ||
    //                 board.getPiece(newPos.getRow(), newPos.getColumn()).getColor() != board
    //                         .getPiece(position.getRow(), position.getColumn()).getColor())) {
    //             validMove.add(newPos);
    //         }
    //     }
    // }

    // private void addRamMoves(Position position, PieceColor color, List<Position> validMove) {
    //     int direction = color == PieceColor.BLUE ? -1 : 1;
    //     Position newPos = new Position(position.getRow() + direction, position.getColumn());
    //     if (isPositionOnBoard(newPos) && board.getPiece(newPos.getRow(), newPos.getColumn()) == null) {
    //         validMove.add(newPos);
    //     }

    //     if ((color == PieceColor.BLUE && position.getRow() == 6)
    //             || (color == PieceColor.RED && position.getRow() == 1)) {
    //         newPos = new Position(position.getRow() + 2 * direction, position.getColumn());
    //         Position intermediatePos = new Position(position.getRow() + direction, position.getColumn());
    //         if (isPositionOnBoard(newPos) && board.getPiece(newPos.getRow(), newPos.getColumn()) == null
    //                 && board.getPiece(intermediatePos.getRow(), intermediatePos.getColumn()) == null) {
    //             validMove.add(newPos);
    //         }
    //     }

    //     int[] captureCols = { position.getColumn() - 1, position.getColumn() + 1 };
    //     for (int col : captureCols) {
    //         newPos = new Position(position.getRow() + direction, col);
    //         if (isPositionOnBoard(newPos) && board.getPiece(newPos.getRow(), newPos.getColumn()) != null &&
    //                 board.getPiece(newPos.getRow(), newPos.getColumn()).getColor() != color) {
    //             validMove.add(newPos);
    //         }
    //     }
    // }
}