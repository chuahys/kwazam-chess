import java.util.*;
import javax.swing.*;

/**
 * The GameController class handles interactions between the model and the view.
 * @author Chuah Yun Shan 
 */
public class GameController {
    private Game game; // Model
    private BoardView boardView; // View
    private int selectRow = -1; // Row of selected piece (-1 --> no selection)
    private int selectCol = -1; // Column of selected piece (-1 --> no selection)
    private final Map<String, MenuCommand> commands = new HashMap<>(); // Map of menu commands to handle menu actions

    /**
     * Constructor to initialize the controller with the game model and view.
     */
    public GameController(Game game, BoardView boardView) {
        this.game = game;
        this.boardView = boardView;
        addBoardListener(); // Add listeners for board interactions
        addMenuListener(); // Add listeners for menu interactions
        // Initializes menu commands to specific actions
        commands.put("Restart", new RestartCommand(boardView));
        commands.put("Save", new SaveCommand(boardView));
        commands.put("Load", new LoadCommand(boardView));
        commands.put("Exit", new ExitCommand(boardView));
        commands.put("How to Play?", new InfoCommand());
    }
    
    /**
     * Add action listeners to each button on the game board.
     */
    private void addBoardListener() {
        JButton[][] button = boardView.getButton();
        for (int row = 0; row < button.length; row++) {
            for (int col = 0; col < button[row].length; col++) {
                final int r = row;
                final int c = col;
                button[row][col].addActionListener(e -> handleButtonClick(r, c));
            }
        }
    }
    
    /**
     * Add action listeners to the menu items (Restart, Save, Load, Exit).
     */
    private void addMenuListener() {
        // Access the menu bar from the BoardView
        JMenuBar menuBar = boardView.getJMenuBar();  // Get the menu bar from the JFrame

        // Access the "Menu"
        JMenu menu = menuBar.getMenu(0);

        // Get menu items
        JMenuItem restart = menu.getItem(0);
        JMenuItem save = menu.getItem(1);
        JMenuItem load = menu.getItem(2);
        JMenuItem exit = menu.getItem(3);

        // Add action listeners to these items
        restart.addActionListener(e -> restartGame());
        save.addActionListener(e -> saveGame());
        load.addActionListener(e -> loadGame());
        exit.addActionListener(e -> exitGame());
        exit.addActionListener(e -> infoRule());
    }

    /**
     * Handle click on the board's buttons (pieces).
     */
    private void handleButtonClick(int row, int col) {
        // Clear highlights before any action
        boardView.clearHighlight();

        if (selectRow == -1 && selectCol == -1) {
            // First click: select a piece
            selectPiece(row, col); // Handle piece selection
        } else {
            // Second click: attempt a move
            toMove(row, col); // Handle move attempt
        }
    }
    
    /**
     * Select a piece and call the view to highlight valid moves.
     */
    private void selectPiece(int row, int col) {
        Piece piece = game.getBoard().getPieceAt(row, col);
        if (piece != null && piece.getColor() == game.getCurrentPlayer()) {
            selectRow = row;
            selectCol = col;
            boardView.updateMessage(piece.getClass().getSimpleName() + " is selected.");
            // Get valid moves for the selected piece
            List<int[]> move = game.getValidMoves(row, col);
            // Call to highlight valid moves
            boardView.highlight(move);
        } else {
            boardView.updateMessage("Invalid selection. Select your own piece.");
        }
    }
    
    /**
     * Attempt to move the selected piece.
     */
    private void toMove(int row, int col) {
        if (game.movePiece(selectRow, selectCol, row, col)) {
            // Check if the game has a winner (Sau captured)
            PieceColor winner = game.checkWinner();
            if (winner != null) {
                // If a winner is detected
                boardView.refreshBoard(); // Update the board view
                boardView.showWinner(winner);
                // Restart the game after Sau captured
                game.resetGame(); // Reset the game 
                boardView.setBoardFlip(false); // Reset the board flip state
                boardView.refreshBoard(); // Update the board view
                boardView.updateMessage("Game restart!");
                return; // Exit early since the game has reset
            } else {
                game.getBoard().flipBoard(); // Flip the board if the move is successful 
                boardView.setBoardFlip(!boardView.isBoardFlip()); // Update flip state to insert pieces image correctly
                boardView.refreshBoard(); // Update the board view after the move
                boardView.updateMessage("Move successful!");
            }

        } else {
            boardView.updateMessage("Invalid move. Try again.");
        }
        // Reset selection
        selectRow = -1;
        selectCol = -1;
    }

    /**
     * Execute the Restart command.
     */
    public void restartGame() {
        executeCommand("Restart");
    }

    /**
     * Execute the Save command.
     */
    public void saveGame() {
        executeCommand("Save");
    }

    /**
     * Execute the Load command.
     */
    public void loadGame() {
        executeCommand("Load");
    }

    /**
     * Execute the Exit command.
     */
    public void exitGame() {
        executeCommand("Exit");
    }

    /**
     * Execute the Info command (game rule).
     */
    public void infoRule() {
        executeCommand("How to Play?");
    }

    /**
     * Execute the command with the given string.
     */
    private void executeCommand(String str) {
        MenuCommand cmd = commands.get(str);
        if (cmd != null) {
            cmd.execute();
        } else {
            System.out.println("Command not found: " + str);
        }
    }
}
