import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Start the game on the Event Dispatch Thread for thread-safety with Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = new Game();  // Initialize the game model
                BoardView boardView = new BoardView(game);  // Pass the Game object to the view constructor
                GameController controller = new GameController(game, boardView);  // Initialize the controller

                // Set up JFrame
                JFrame frame = new JFrame("Kwazam Chess Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 800);  // Set window size (adjust as needed)
                frame.setResizable(true); // Enable resizing
                frame.add(boardView); // Add the game view to the JFrame
                frame.setJMenuBar(boardView.getMenuBar(controller)); // Set the menu bar
                frame.setLocationRelativeTo(null);  // Center the window
                frame.setVisible(true);  // Show the window
            }
        });
    }
}
