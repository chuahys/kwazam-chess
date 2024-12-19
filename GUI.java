import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GUI extends JFrame {
    // private final Component[][] squares = new Component[8][5];
    // private final Game game = new Game();

    private GameController controller;
    private Component[][] buttons;

    public GUI(GameController controller) {
        this.controller = controller;
        buttons = new Component[8][5];
        initGUI();
    }

    private void initGUI() {
        setTitle("Kwazam Chess Game");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 5));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                final int r = row; // Capture row value
                final int c = col; // Capture column value
                Component button = new Component(row, col);
                button.addActionListener(e -> controller.handleUserClick(r, c));
                buttons[row][col] = button;
                add(button);
            }
        }

        addGameResetOption();
        refreshBoard();
    }

public void refreshBoard() {
        Piece[][] board = controller.getGame().getBoard().getBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                Piece piece = board[row][col];
                if (piece != null) {
                    String colorName = piece.getColor() == PieceColor.BLUE ? "b" : "r";
                    String imagePath = "/resources/img/" + colorName + piece.getClass().getSimpleName() + ".png";
                    buttons[row][col].setPieceImage(imagePath);
                } else {
                    buttons[row][col].clearPieceImage();
                }
            }
        }
    }

    public void highlightLegalMoves(Position position) {
        List<Position> legalMoves = controller.getGame().getLegalMovesForPieceAt(position);
        for (Position move : legalMoves) {
            buttons[move.getRow()][move.getColumn()].setBackground(Color.GREEN);
        }
    }

    public void clearHighlights() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                buttons[row][col].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    private void addGameResetOption() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem resetItem = new JMenuItem("Reset");
        resetItem.addActionListener(e -> {
            controller.getGame().resetGame();
            refreshBoard();
        });
        gameMenu.add(resetItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }
}

    // public GUI() {
    //     setTitle("Kwazam Chess Game");
    //     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     setLayout(new GridLayout(8, 5));
    //     initializeBoard();
    //     addGameResetOption();
    //     pack();
    //     setVisible(true);
    // }

    // private void initializeBoard() {
    //     for (int row = 0; row < squares.length; row++) {
    //         for (int col = 0; col < squares[row].length; col++) {
    //             final int finalRow = row;
    //             final int finalCol = col;
    //             Component button = new Component(row, col);
    //             button.setBackground(Color.GRAY); // Uniform background color
    //             button.addMouseListener(new MouseAdapter() {
    //                 @Override
    //                 public void mouseClicked(MouseEvent e) {
    //                     handleSquareClick(finalRow, finalCol);
    //                 }
    //             });
    //             add(button);
    //             squares[row][col] = button;
    //         }
    //     }
    //     refreshBoard();
    // }

    // private void refreshBoard() {
    //     Board board = game.getBoard();
    //     for (int row = 0; row < 8; row++) {
    //         for (int col = 0; col < 5; col++) {
    //             Piece piece = board.getPiece(row, col);
    //             if (piece != null) {
    //                 // If using Unicode symbols:
    //                 String symbol = pieceUnicodeMap.get(piece.getClass());
    //                 Color color = (piece.getColor() == PieceColor.BLUE) ? Color.WHITE : Color.BLACK;
    //                 squares[row][col].setPieceSymbol(symbol, color);
    //             } else {
    //                 squares[row][col].clearPieceSymbol();
    //             }
    //         }
    //     }
    // }

    // private void handleSquareClick(int row, int col) {
    //     boolean moveResult = game.handleSquareSelection(row, col);
    //     clearHighlights();
    //     if (moveResult) {
    //         refreshBoard();
    //         checkGameState();
    //         checkGameOver();
    //     } else if (game.isPieceSelected()) {
    //         highlightLegalMoves(new Position(row, col));
    //     }
    //     refreshBoard();
    // }

    // private void checkGameState() {
    //     PieceColor currentPlayer = game.getCurrentPlayerColor();
    //     boolean inCheck = game.isInCheck(currentPlayer);

    //     if (inCheck) {
    //         JOptionPane.showMessageDialog(this, currentPlayer + " is in check!");
    //     }
    // }

    // private void highlightLegalMoves(Position position) {
    //     List<Position> legalMoves = game.getLegalMovesForPieceAt(position);
    //     for (Position move : legalMoves) {
    //         squares[move.getRow()][move.getColumn()].setBackground(Color.GREEN);
    //     }
    // }

    // private void clearHighlights() {
    //     for (int row = 0; row < 8; row++) {
    //         for (int col = 0; col < 5; col++) {
    //             squares[row][col].setBackground(Color.GRAY);
    //         }
    //     }
    // }

    // private void addGameResetOption() {
    //     JMenuBar menuBar = new JMenuBar();
    //     JMenu gameMenu = new JMenu("Game");
    //     JMenuItem resetItem = new JMenuItem("Reset");
    //     resetItem.addActionListener(e -> resetGame());
    //     gameMenu.add(resetItem);
    //     menuBar.add(gameMenu);
    //     setJMenuBar(menuBar);
    // }

    // private void resetGame() {
    //     game.resetGame();
    //     refreshBoard();
    // }

    // private void checkGameOver() {
    //     if (game.isCheckmate(game.getCurrentPlayerColor())) {
    //         int response = JOptionPane.showConfirmDialog(this, "Checkmate! Would you like to play again?", "Game Over",
    //                 JOptionPane.YES_NO_OPTION);
    //         if (response == JOptionPane.YES_OPTION) {
    //             resetGame();
    //         } else {
    //             System.exit(0);
    //         }
    //     }
    // }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(GUI::new);
    // }
// }