/**
 * This class handles user interactions and updates the view.
 */

public class GameController {
    private Game game;
    private GUI view;

    /**
     * Constructs a GameController with the specified game.
     * 
     * @param game the game to be controlled
     */

    public GameController() {
        this.game = Game.getInstance();
        this.view = new GUI(this);
        //this.game.addObserver(view);
    }

    public Game getGame() {
        return game;
    }

    public void buttonClick(int row, int col) {
        boolean moveResult = game.handleButtonSelection(row, col);
        view.clearHighlight();
        if (moveResult) {
            //game.nextTurn();
            view.refreshBoard();
            //checkGameState();
            //checkGameOver();
        } else if (game.isPieceSelected()) {
            view.highlightValidMove(new Position(row, col));
        }
        view.refreshBoard();
    }

    public void start() {
        view.setVisible(true);
    }

    // private void checkGameState() {
    //     PieceColor currentPlayer = game.getCurrentPlayerColor();
    //     boolean inCheck = game.isInCheck(currentPlayer);

    //     if (inCheck) {
    //         JOptionPane.showMessageDialog(view, currentPlayer + " is in check!");
    //     }
    // }

    // private void checkGameOver() {
    //     if (game.isCheckmate(game.getCurrentPlayerColor())) {
    //         int response = JOptionPane.showConfirmDialog(view, "Checkmate! Would you like to play again?", "Game Over",
    //                 JOptionPane.YES_NO_OPTION);
    //         if (response == JOptionPane.YES_OPTION) {
    //             game.resetGame();
    //             view.refreshBoard();
    //         } else {
    //             System.exit(0);
    //         }
    //     }
    // }
}