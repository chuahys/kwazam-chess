import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * BoardView class is responsible for displaying the game board.
 * It observes the Board and updates the view accordingly.
 * 
 * @author Chuah Yun Shan
 */
public class BoardView extends JPanel implements BoardObserver {
    private Board board;
    private Game game;
    private JButton[][] buttons;
    private JLabel playerLabel;
    private JLabel countLabel;
    private JLabel messageLabel;
    private boolean isFlip = false;  // Flag to track if the board is flipped
    private int height;
    private int width;
    private JFrame frame;
    private JMenuBar menuBar;

    /**
     * Constructor for BoardView. Initializes the JFrame, layout, and components.
     */
    public BoardView(Game game) {
        this.game = Game.getInstance(); // Get the single instance of Game
        this.board = this.game.getBoard(); // Get the current board from the game
        this.height = board.getHeight();
        this.width = board.getWidth();

        // Initialize JFrame
        frame = new JFrame("Kwazam Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true); // Enable resizing
        frame.setSize(500, 800); // Initial frame size

        // Initialize and set up the menu bar
        menuBar = createMenuBar();
        frame.setJMenuBar(menuBar); // Set the menu bar on the JFrame

        // Set up the panel with game content
        setLayout(new BorderLayout());
        frame.add(this); // Add the board view (this panel) to the frame

        // Initialize and add components (player labels, game board, etc.)
        initComponent();

        // Set the frame location at the center of the screen
        frame.setLocationRelativeTo(null); // Center the window

        // Set frame visibility
        frame.setVisible(true);
    }
    
    /**
     * Initializes the components of the board view (information labels, buttons on board, messages label).
     */
    private void initComponent() {
        // Set up the player and move count info panel
        JPanel infoPanel = createInfoPanel();
        add(infoPanel, BorderLayout.NORTH);

        // Set up the chessboard in the center
        JPanel boardPanel = createBoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        // Set up the message panel at the bottom
        createMessagePanel();
    }

/**
     * Creates and returns a JPanel that displays the player and move count info.
     */
    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        playerLabel = new JLabel("Player: " + game.getCurrentPlayer().toString());
        countLabel = new JLabel("Move Count: " + game.getMoveCount());

        infoPanel.add(playerLabel);
        infoPanel.add(countLabel);

        return infoPanel;
    }

    /**
     * Creates and returns a JPanel that represents the chessboard with all the buttons.
     */
    private JPanel createBoardPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(height, width));
        buttons = new JButton[height][width];

        // Initialize buttons for each square and set up the chessboard
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(60, 60));
                button.setBackground(Color.LIGHT_GRAY);
                buttons[row][col] = button;
                boardPanel.add(button);

                // Set piece icon if there's a piece on this square
                Piece piece = board.getPieceAt(row, col);
                if (piece != null) {
                    String imagePath = piece.getImagePath(isFlip);
                    ImageIcon pieceIcon = new ImageIcon(imagePath);
                    button.setIcon(pieceIcon);
                }
            }
        }

        return boardPanel;
    }

    /**
     * Creates and sets up the message label that is displayed at the bottom of the window.
     */
    private void createMessagePanel() {
        messageLabel = new JLabel("Welcome to Kwazam Chess!", SwingConstants.CENTER);
        add(messageLabel, BorderLayout.SOUTH);
    }

    /**
     * Creates a menu bar with menu items for the game.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Create Menu
        JMenu gameMenu = new JMenu("Menu");
        JMenuItem restartItem = new JMenuItem("Restart");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Add menu items to the menu
        gameMenu.add(restartItem);
        gameMenu.add(saveItem);
        gameMenu.add(loadItem);
        gameMenu.add(exitItem);

        // Add the menu to the menu bar
        menuBar.add(gameMenu);

        return menuBar;
    }

    /** 
     * Return the menu bar to the controller
     */
    public JMenuBar getMenuBar() {
        return menuBar;
    }
    
    /**
     * Updates the player and move count labels.
     */
    public void updateLabel() {
        playerLabel.setText("Player: " + game.getCurrentPlayer().toString());
        countLabel.setText("Move Count: " + game.getMoveCount());
    }

    /**
     * Updates the message label with a custom message.
     */
    public void updateMessage(String message) {
        messageLabel.setText(message); // Update the message
    }
       
    /**
     * Refreshes the board view by updating the images and labels.
     */
    @Override
    // Override from BoardObserver class
    public void refreshBoard() {
        // Update the labels first
        updateLabel();

        // Update the board buttons
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece != null) {
                    String imagePath = piece.getImagePath(isFlip); // Get the image path from pieces
                    ImageIcon pieceIcon = new ImageIcon(imagePath); // Create piece icon
                    buttons[row][col].setIcon(pieceIcon); // Set the piece image on the button
                } else {
                    buttons[row][col].setIcon(null); // Clear icon if no piece
                }
                buttons[row][col].setBackground(Color.LIGHT_GRAY); // Reset button background
            }
        }
        repaint(); // Redraw the panel to update the view
    }
    
    /**
     * Highlights valid moves by changing the background color of valid move buttons.
     */
    public void highlightValidMoves(List<int[]> validMoves) {
        for (int[] move : validMoves) {
            int row = move[0];
            int col = move[1];
            buttons[row][col].setBackground(Color.GREEN); // Highlight valid moves
        }
    }

    /**
    * Clears the highlight on all buttons (resets background color).
    */
    public void clearHighlight() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                buttons[row][col].setBackground(Color.LIGHT_GRAY); // Reset the background color
            }
        }
    }

    /**
     * Sets the board's flip state (whether the board is flipped or not).
     */
    public void setBoardFlip(boolean flip) {
        this.isFlip = flip;
    }

    /**
     * Gets the current flip state of the board.
     */ 
    public boolean isBoardFlip() {
        return isFlip;
    }

    /**
     * Returns the 2D array of buttons representing the board squares.
     */
    JButton[][] getButtons() {
        return buttons;
    }

    /**
     * Displays a winner message in a dialog box when the game ends.
     */
    public void showWinnerMessage(PieceColor winner) {
        String message = (winner == PieceColor.BLUE) ? "Blue wins! Sau captured!" : "Red wins! Sau captured!";
        JOptionPane.showMessageDialog(null, message);
    }
}