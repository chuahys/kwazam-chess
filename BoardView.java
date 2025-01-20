import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * BoardView class is responsible for displaying the game board.
 * @author Chuah Yun Shan
 */
public class BoardView extends JFrame implements BoardObserver {
    private Board board;
    private Game game;
    private JButton[][] button;
    private JLabel playerLabel;
    private JLabel countLabel;
    private JLabel msgLabel;
    private boolean isFlip = false;  // Flag to track if the board is flipped
    private int height;
    private int width;

    /**
     * Constructor for BoardView.
     */
    public BoardView() {
        this.game = Game.getInstance(); // Get the single instance of Game
        this.board = game.getBoard(); // Get the current board from the game
        this.height = board.getHeight();
        this.width = board.getWidth();

        // Initialize the frame
        setTitle("Kwazam Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true); // Enable resizing
        setSize(500, 800); // Set initial size
        setLocationRelativeTo(null); // Center the window on the screen

        // Set the layout for the frame
        setLayout(new BorderLayout());

        // Add panels to the frame
        add(infoPanel(), BorderLayout.NORTH); // Add info panel at the top
        add(boardPanel(), BorderLayout.CENTER); // Add board panel in the center
        add(messagePanel(), BorderLayout.SOUTH); // Add message panel at the bottom

        // Add menu bar to the frame
        setJMenuBar(createMenu());

        // Make the frame visible
        setVisible(true);
    }

    /**
     * Create panel that displays the player and move count information.
     */
    private JPanel infoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        playerLabel = new JLabel("Player: " + game.getCurrentPlayer().toString());
        countLabel = new JLabel("Move Count: " + game.getMoveCount());

        infoPanel.add(playerLabel);
        infoPanel.add(countLabel);

        return infoPanel;
    }

    /**
     * Create panel that represents the chessboard with all the buttons.
     */
    private JPanel boardPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(height, width));
        button = new JButton[height][width];

        // Initialize buttons for each square and set up the chessboard
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(60, 60));
                btn.setBackground(Color.LIGHT_GRAY);
                button[row][col] = btn;
                boardPanel.add(btn);

                // Set piece icon if there's a piece on this square
                Piece piece = board.getPieceAt(row, col);
                if (piece != null) {
                    String imgPath = piece.getImagePath(isFlip);
                    ImageIcon icon = new ImageIcon(imgPath);
                    btn.setIcon(icon);
                }
            }
        }
        return boardPanel;
    }

    /**
     * Create the panel for displaying messages.
     */
    private JPanel messagePanel() {
        JPanel msgPanel = new JPanel(new BorderLayout());
        msgLabel = new JLabel("Welcome to Kwazam Chess!", SwingConstants.CENTER);
        msgPanel.add(msgLabel, BorderLayout.CENTER);
        return msgPanel;
    }

    /**
     * Create a menu bar with menu items for the game.
     */
    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Create Menu
        JMenu menu = new JMenu("Menu");
        JMenuItem restart = new JMenuItem("Restart");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem exit = new JMenuItem("Exit");

        // Add menu items to the menu
        menu.add(restart);
        menu.add(save);
        menu.add(load);
        menu.add(exit);

        // Add the menu to the menu bar
        menuBar.add(menu);

        return menuBar;
    }

    /**
     * Update the player and move count labels.
     */
    public void updateInfo() {
        playerLabel.setText("Player: " + game.getCurrentPlayer().toString());
        countLabel.setText("Move Count: " + game.getMoveCount());
    }

    /**
     * Update the message label with a custom message.
     */
    public void updateMessage(String str) {
        msgLabel.setText(str); // Update the message
    }
       
    /**
     * Refresh the board view by updating the images and labels.
     */
    @Override
    // Override from BoardObserver class
    public void refreshBoard() {
        // Update the labels first
        updateInfo();

        // Update the board buttons
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece != null) {
                    String imagePath = piece.getImagePath(isFlip); // Get the image path from pieces
                    ImageIcon pieceIcon = new ImageIcon(imagePath); // Create piece icon
                    button[row][col].setIcon(pieceIcon); // Set the piece image on the button
                } else {
                    button[row][col].setIcon(null); // Clear icon if no piece
                }
                button[row][col].setBackground(Color.LIGHT_GRAY); // Reset button background
            }
        }
        repaint(); // Redraw the panel to update the view
    }
    
    /**
     * Highlight valid moves, set the background color to green.
     */
    public void highlight(List<int[]> move) {
        // Iterate the list of valid moves
        for (int[] m : move) {
            int row = m[0]; // Extract the row index
            int col = m[1]; // Extract the column index
            button[row][col].setBackground(Color.GREEN); // Highlight valid move buttons
        }
    }

    /**
    * Clear highlight on all buttons (resets background color).
    */
    public void clearHighlight() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                button[row][col].setBackground(Color.LIGHT_GRAY); // Reset the background color
            }
        }
    }

    /**
     * Set the board's flip state (whether the board is flipped or not).
     */
    public void setBoardFlip(boolean flip) {
        this.isFlip = flip;
    }

    /**
     * Get the current flip state of the board.
     */ 
    public boolean isBoardFlip() {
        return isFlip;
    }

    /**
     * Return the 2D array of buttons representing the board squares.
     */
    JButton[][] getButton() {
        return button;
    }

    /**
     * Display a winner message in a dialog box when the game ends.
     */
    public void showWinner(PieceColor winner) {
        String s = (winner == PieceColor.BLUE) ? "Blue wins! Sau captured!" : "Red wins! Sau captured!";
        String title = "Game Over!"; // Title of the dialog
        JOptionPane.showMessageDialog(null, s, title, JOptionPane.INFORMATION_MESSAGE);
    }
}