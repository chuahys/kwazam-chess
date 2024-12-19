import javax.swing.JOptionPane;

public class GameController {
    private Game game;
    private GUI view;

    public GameController(Game game) {
        this.game = game;
    }

    public void setView(GUI view) {
        this.view = view;
    }

    public Game getGame() {
        return game;
    }

    public void handleUserClick(int row, int col) {
        boolean moveResult = game.handleSquareSelection(row, col);
        view.clearHighlights();
        if (moveResult) {
            view.refreshBoard();
            checkGameState();
            checkGameOver();
        } else if (game.isPieceSelected()) {
            view.highlightLegalMoves(new Position(row, col));
        }
        view.refreshBoard();
    }

    private void checkGameState() {
        PieceColor currentPlayer = game.getCurrentPlayerColor();
        boolean inCheck = game.isInCheck(currentPlayer);

        if (inCheck) {
            JOptionPane.showMessageDialog(view, currentPlayer + " is in check!");
        }
    }

    private void checkGameOver() {
        if (game.isCheckmate(game.getCurrentPlayerColor())) {
            int response = JOptionPane.showConfirmDialog(view, "Checkmate! Would you like to play again?", "Game Over",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                game.resetGame();
                view.refreshBoard();
            } else {
                System.exit(0);
            }
        }
    }
}