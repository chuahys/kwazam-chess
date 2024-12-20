public class Board {
    private Piece[][] board;

    public Board() {
        this.board = new Piece[8][5]; // 8x5 chessboard
        setupPieces();
    }

    public void setBoard(Piece[][] newBoard) {
        this.board = newBoard;

    }

    public Piece[][] getBoard() {
        return board;
    }

    public Piece getPiece(int row, int column) {
        return board[row][column];
    }

    public void setPiece(int row, int column, Piece piece) {
        board[row][column] = piece;
        if (piece != null) {
            piece.setPosition(new Position(row, column));
        }
    }

    private void setupPieces() {
        // Setup Blue pieces (bottom of the board)
        for (int col = 0; col < 5; col++) {
            board[6][col] = PieceFactory.createPiece("Ram", PieceColor.BLUE, new Position(6, col));
        }
        board[7][0] = PieceFactory.createPiece("Xor", PieceColor.BLUE, new Position(7, 0));
        board[7][1] = PieceFactory.createPiece("Biz", PieceColor.BLUE, new Position(7, 1));
        board[7][2] = PieceFactory.createPiece("Sau", PieceColor.BLUE, new Position(7, 2));
        board[7][3] = PieceFactory.createPiece("Biz", PieceColor.BLUE, new Position(7, 3));
        board[7][4] = PieceFactory.createPiece("Tor", PieceColor.BLUE, new Position(7, 4));

        // Setup Red pieces (top of the board)
        for (int col = 0; col < 5; col++) {
            board[1][col] = PieceFactory.createPiece("Ram", PieceColor.RED, new Position(1, col));
        }
        board[0][0] = PieceFactory.createPiece("Tor", PieceColor.RED, new Position(0, 0));
        board[0][1] = PieceFactory.createPiece("Biz", PieceColor.RED, new Position(0, 1));
        board[0][2] = PieceFactory.createPiece("Sau", PieceColor.RED, new Position(0, 2));
        board[0][3] = PieceFactory.createPiece("Biz", PieceColor.RED, new Position(0, 3));
        board[0][4] = PieceFactory.createPiece("Xor", PieceColor.RED, new Position(0, 4));
    }

    public void movePiece(Position start, Position end) {
        // Check if there is a piece at the start position and if the move is valid
        if (board[start.getRow()][start.getColumn()] != null &&
                board[start.getRow()][start.getColumn()].isValidMove(end, board)) {

            // Perform the move: place the piece at the end position
            board[end.getRow()][end.getColumn()] = board[start.getRow()][start.getColumn()];

            // Update the piece's position
            board[end.getRow()][end.getColumn()].setPosition(end);

            // Clear the start position
            board[start.getRow()][start.getColumn()] = null;
        }
    }

    public void flipBoard() {
        Piece[][] newBoard = new Piece[8][5];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                Piece piece = board[row][col];
                if (piece != null) {
                    int newRow = 7 - row;
                    int newCol = 4 - col;
                    piece.setPosition(new Position(newRow, newCol));
                    newBoard[newRow][newCol] = piece;
                }
            }
        }
        board = newBoard;
    }
}