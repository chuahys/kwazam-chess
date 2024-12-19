import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        GameController controller = new GameController(game);
        GUI gui = new GUI(controller);
        controller.setView(gui);
        SwingUtilities.invokeLater(() -> gui.setVisible(true));
    }
}