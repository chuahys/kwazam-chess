import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {
    private GameController controller;
    private Component[][] buttons;
    private JLabel currentPlayerLabel;
    private JLabel turnCountLabel;

    public GUI(GameController controller) {
        this.controller = controller;
        buttons = new Component[8][5];
        initGUI();
    }

    private void initGUI() {
        setTitle("Kwazam Chess Game");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel boardPanel = new JPanel(new GridLayout(8, 5));
        JPanel rowPanel = new JPanel(new GridLayout(8, 1));
        JPanel colPanel = new JPanel(new GridLayout(1, 5));
        
        rowPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
        colPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        for (int row = 0; row < 8; row++) {
            JLabel rowLabel = new JLabel(String.valueOf(row + 1), SwingConstants.CENTER);
            rowPanel.add(rowLabel);
        }

        for (int col = 0; col < 5; col++) {
            JLabel colLabel = new JLabel(String.valueOf(col + 1), SwingConstants.CENTER);
            colPanel.add(colLabel);
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                final int r = row; // Capture row value
                final int c = col; // Capture column value
                Component button = new Component(row, col);
                button.addActionListener(e -> controller.buttonClick(r, c));
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        mainPanel.add(rowPanel, BorderLayout.WEST);
        mainPanel.add(colPanel, BorderLayout.SOUTH);
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Status panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        currentPlayerLabel = new JLabel();
        turnCountLabel = new JLabel();

        statusPanel.add(currentPlayerLabel, BorderLayout.WEST);
        statusPanel.add(turnCountLabel, BorderLayout.EAST);

        add(statusPanel, BorderLayout.NORTH);
        
        gameMenu();
        refreshBoard();
    }

    public void refreshBoard() {
        Piece[][] board = controller.getGame().getBoard().getBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                Piece piece = board[row][col];
                if (piece != null) {
                    String colorName = piece.getColor() == PieceColor.BLUE ? "b" : "r";
                    String imagePath = "/resources/img/" + colorName + piece.getClass().getSimpleName() + ".png";
                    buttons[row][col].setPieceImage(imagePath);
                } else {
                    buttons[row][col].clearPieceImage();
                }
            }
        }

        // Update status labels
        currentPlayerLabel.setText("Current Player: " + (controller.getGame().getCurrentPlayerColor() == PieceColor.BLUE ? "Blue" : "Red"));
        turnCountLabel.setText("Turn Count: " + controller.getGame().getMoveCount()/2);
    }

    public void highlightValidMove(Position position) {
        List<Position> validMove = controller.getGame().getValidMoveAt(position);
        for (Position move : validMove) {
            buttons[move.getRow()][move.getColumn()].setBackground(Color.GREEN);
        }
    }

    public void clearHighlight() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                buttons[row][col].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    private void gameMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Menu");

        JMenuItem resetItem = new JMenuItem("Restart");
        resetItem.addActionListener(e -> {
            controller.getGame().resetGame();
            refreshBoard();
        });

        gameMenu.add(resetItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }
}