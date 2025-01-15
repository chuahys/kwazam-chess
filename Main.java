import javax.swing.*;

/**
 * The Main class of Kwazam Chess Game.
 * Implement the Model-View-Controller (MVC) and starts the game.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = Game.getInstance();  // Initialize the game model
                BoardView boardView = new BoardView(game);  // Pass the Game object to the view constructor
                GameController controller = new GameController(game, boardView);  // Initialize the controller by linking the model and view
            }
        });
    }
}
