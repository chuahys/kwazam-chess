import javax.swing.*;

/**
 * The Main class of Kwazam Chess Game.
 * Implement the Model-View-Controller (MVC) and start the game.
 */
public class Main {
    public static void main(String[] args) {
        // Use invokeLater to ensure the program thread-safe
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = Game.getInstance();  // Initialize the game model
                BoardView boardView = new BoardView(); // Initialize the view
                GameController controller = new GameController(game, boardView);  // Initialize the controller and pass the model and view
            }
        });
    }
}
