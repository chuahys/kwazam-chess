import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = Game.getInstance();  // Initialize the game model
                BoardView boardView = new BoardView(game);  // Pass the Game object to the view constructor
                GameController controller = new GameController(game, boardView);  // Initialize the controller
            }
        });
    }
}
