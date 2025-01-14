import java.util.*;
import javax.swing.*;

/**
 * The GameController class handles interactions between the model and the view.
 * @author Chuah Yun Shan 
 */
public class GameController {
    private Game game; // Model
    private Board board;
    private BoardView boardView; // View
    private int selectedRow = -1; // Row of selected piece (-1 --> no selection)
    private int selectedCol = -1; // Column of selected piece (-1 --> no selection)
    private final Map<String, MenuCommand> commands = new HashMap<>(); // Map of menu commands to handle menu actions

    /**
     * Constructor to initialize the controller with the game model and view.
     */
    public GameController(Game game, BoardView boardView) {
        this.game = game;
        this.boardView = boardView;
        addBoardListener(); // Add listeners for board interactions
        addMenuListener(); // Add listeners for menu interactions
        initCommand(); // Initialize the menu commands
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
        // Get menu items from the view (BoardView)
        JMenu menu = boardView.getMenuBar().getMenu(0);

        // Get individual menu items
        JMenuItem restartItem = menu.getItem(0);
        JMenuItem saveItem = menu.getItem(1);
        JMenuItem loadItem = menu.getItem(2);
        JMenuItem exitItem = menu.getItem(3);

        // Add action listeners to these items
        restartItem.addActionListener(e -> restartGame());
        saveItem.addActionListener(e -> saveGame());
        loadItem.addActionListener(e -> loadGame());
        exitItem.addActionListener(e -> exitGame());
    }

    /**
     * Handles clicks on the board's buttons (pieces).
     */
    private void handleButtonClick(int row, int col) {
        // Clear highlights before any action
        boardView.clearHighlight();

        if (selectedRow == -1 && selectedCol == -1) {
            // First click: select a piece
            selectPiece(row, col); // Handle piece selection
        } else {
            // Second click: attempt a move
            toMove(row, col); // Handle move attempt
        }
    }
    
    /**
     * Selects a piece and highlights its valid moves.
     */
    private void selectPiece(int row, int col) {
        Piece piece = game.getBoard().getPieceAt(row, col);
        if (piece != null && piece.getColor() == game.getCurrentPlayer()) {
            selectedRow = row;
            selectedCol = col;
            boardView.updateMessage(piece.getClass().getSimpleName() + " is selected.");
            // Get valid moves for the selected piece
            List<int[]> validMoves = game.getValidMoves(row, col);
            // Call to highlight valid moves
            boardView.highlightValidMoves(validMoves);
        } else {
            boardView.updateMessage("Invalid selection. Select your own piece.");
        }
    }
    
    /**
     * Attempt to move the selected piece.
     */
    private void toMove(int row, int col) {
        if (game.movePiece(selectedRow, selectedCol, row, col)) {
            // Check if the game has a winner (Sau captured)
            PieceColor winner = game.checkWinner();
            if (winner != null) {
                // If a winner is detected
                boardView.refreshBoard(); // Update the board view
                boardView.showWinnerMessage(winner);
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
        selectedRow = -1;
        selectedCol = -1;
    }
        
    /**
     * Initializes menu commands and maps them to specific actions.
     */
    private void initCommand() {
        commands.put("Restart", new RestartCommand(game, boardView));
        commands.put("Save", new SaveCommand(boardView, board, game));
        commands.put("Load", new LoadCommand(boardView, game));
        commands.put("Exit", new ExitCommand());
    }

    /**
     * Executes the Restart command.
     */
    public void restartGame() {
        executeCommand("Restart");
    }

    /**
     * Executes the Save command.
     */
    public void saveGame() {
        executeCommand("Save");
    }

    /**
     * Executes the Load command.
     */
    public void loadGame() {
        executeCommand("Load");
    }

    /**
     * Executes the Exit command.
     */
    public void exitGame() {
        executeCommand("Exit");
    }

    /**
     * Executes the command with the given string.
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
