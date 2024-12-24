import java.awt.*;
import java.util.List;
import javax.swing.*;

public class BoardView extends JPanel {
    private JButton[][] buttons;
    private JLabel playerLabel;
    private JLabel countLabel;
    private Board board;
    private Game game;
    private boolean isFlip = false;  // Flag to track if the board is flipped

    public BoardView(Game game) {
        this.game = game;
        this.board = game.getBoard(); // Get the current board from the game
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
        JPanel boardPanel = new JPanel(new GridLayout(8, 5));
        buttons = new JButton[8][5];

        // Initialize buttons for each square and set up the chessboard
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
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
    }

    private String getPieceImagePath(Piece piece) {
        String pieceName = piece.getClass().getSimpleName();
        String colorPrefix = piece.getColor() == PieceColor.RED ? "r" : "b";
        // If the board has been flipped, we need to rotate the image for Ram and Sau
        if (pieceName.equals("Ram") || pieceName.equals("Sau")) {
            // Rotate the image if the board is flipped
            if (isFlip) {
                return "resources/img/" + colorPrefix + pieceName + "_flip.png";
            }
        }
        return "resources/img/" + colorPrefix + pieceName + ".png";
    }

    private void updatePlayerLabel() {
        playerLabel.setText("Player: " + game.getCurrentPlayer().toString());
    }

    private void updateMoveCountLabel() {
        countLabel.setText("Move Count: " + game.getMoveCount());
    }

    public void updateBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece != null) {
                    String imagePath = getPieceImagePath(piece);
                    ImageIcon pieceIcon = new ImageIcon(imagePath);
                    buttons[row][col].setIcon(pieceIcon);
                } else {
                    buttons[row][col].setIcon(null); // Clear the icon if no piece
                }
            }
        }
        this.repaint(); // Redraw the panel
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

    public void refreshBoard() {
        updateBoard();
        updateLabels();
    }

    public void updateLabels() {
        updatePlayerLabel();
        updateMoveCountLabel();
    }

    // Method to set the board flipped state
    public void setBoardFlip(boolean flip) {
        this.isFlip = flip;
    }

    public boolean isBoardFlip() {
        return isFlip;
    }

    JButton[][] getButtons() {
        return buttons;
    }
}
