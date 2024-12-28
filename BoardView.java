import java.awt.*;
import java.util.List;
import javax.swing.*;

public class BoardView extends JPanel implements BoardObserver {
    private Board board;
    private Game game;
    private JButton[][] buttons;
    private JLabel playerLabel;
    private JLabel countLabel;
    private JLabel messageLabel;
    private boolean isFlip = false;  // Flag to track if the board is flipped
    private String IMAGE_PATH = "resources/img/"; // Base path for images
    private int height;
    private int width;

    public BoardView(Game game) {
        this.game = game;
        this.board = game.getBoard(); // Get the current board from the game
        this.height = board.getHeight();
        this.width = board.getWidth();
        setLayout(new BorderLayout());

        // Create and add the Player Label and Move Count Label at the top
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        playerLabel = new JLabel("Player: " + game.getCurrentPlayer().toString());
        countLabel = new JLabel("Move Count: " + game.getMoveCount());

        infoPanel.add(playerLabel);
        infoPanel.add(countLabel);
        
        add(infoPanel, BorderLayout.NORTH);

        // Add the chessboard in the center
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
                    String imagePath = getPieceImagePath(piece);
                    ImageIcon pieceIcon = new ImageIcon(imagePath);
                    button.setIcon(pieceIcon);
                }
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        // Add a message label at the bottom
        messageLabel = new JLabel("Welcome to Kwazam Chess!", SwingConstants.CENTER);
        add(messageLabel, BorderLayout.SOUTH);  // Add message box to the bottom of the board view
    }

    private String getPieceImagePath(Piece piece) {
        String pieceName = piece.getClass().getSimpleName();
        String colorName= piece.getColor() == PieceColor.BLUE ? "b" : "r";
        // If the board has been flipped, we need to rotate the image for Ram and Sau
        if (pieceName.equals("Ram") || pieceName.equals("Sau")) {
            // Rotate the image if the board is flipped
            if (isFlip) {
                return IMAGE_PATH + colorName + pieceName + "_flip.png";
            }
        }
        return IMAGE_PATH + colorName + pieceName + ".png";
    }

    private void updatePlayerLabel() {
        playerLabel.setText("Player: " + game.getCurrentPlayer().toString());
    }

    private void updateMoveCountLabel() {
        countLabel.setText("Move Count: " + game.getMoveCount());
    }

    public void updateLabels() {
        updatePlayerLabel();
        updateMoveCountLabel();
    }

    public void updateMessage(String message) {
        messageLabel.setText(message); // Update the message
    }
        
    // BoardObserver class to update the board view
    @Override
    public void refreshBoard() {
        // Update the labels first
        updateLabels();

        // Update the board buttons
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece != null) {
                    String imagePath = getPieceImagePath(piece);
                    ImageIcon pieceIcon = new ImageIcon(imagePath);
                    buttons[row][col].setIcon(pieceIcon);  // Set the piece image on the button
                } else {
                    buttons[row][col].setIcon(null);  // Clear icon if no piece
                }
                buttons[row][col].setBackground(Color.LIGHT_GRAY);  // Reset button background
            }
        }

        repaint(); // Redraw the panel to update the view
    }

    public void highlightValidMoves(List<int[]> validMoves) {
        for (int[] move : validMoves) {
            int row = move[0];
            int col = move[1];
            buttons[row][col].setBackground(Color.GREEN); // Highlight valid moves
        }
    }

    public void clearHighlight() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                buttons[row][col].setBackground(Color.LIGHT_GRAY); // Reset the background color
            }
        }
    }

    // Method to set the board flipped state
    public void setBoardFlip(boolean flip) {
        this.isFlip = flip;
        refreshBoard(); // Update the board view after flipping
    }

    public boolean isBoardFlip() {
        return isFlip;
    }

    JButton[][] getButtons() {
        return buttons;
    }

    public JMenuBar getMenuBar(GameController controller) {
        JMenuBar menuBar = new JMenuBar();

        // Create Game Menu
        JMenu gameMenu = new JMenu("Menu");
        JMenuItem restartItem = new JMenuItem("Restart");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Add action listeners
        restartItem.addActionListener(e -> controller.restartGame());
        saveItem.addActionListener(e -> controller.saveGame());
        loadItem.addActionListener(e -> controller.loadGame());
        exitItem.addActionListener(e -> controller.exitGame());

        // Add menu items to the menu
        gameMenu.add(restartItem);
        gameMenu.add(saveItem);
        gameMenu.add(loadItem);
        gameMenu.add(exitItem);

        // Add the menu to the menu bar
        menuBar.add(gameMenu);

        return menuBar;
    }
}
