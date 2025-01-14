import java.util.*;
import javax.swing.*;

public class GameController {
    private Game game; // Model
    private Board board;
    private BoardView boardView; // View
    private int selectedRow = -1; // Row of selected piece
    private int selectedCol = -1; // Column of selected piece
    private final Map<String, MenuCommand> commands = new HashMap<>();

    public GameController(Game game, BoardView boardView) {
        this.game = game;
        this.boardView = boardView;
        addBoardListener();
        addMenuListener();
        initCommand();
    }

    private void addBoardListener() {
        JButton[][] buttons = boardView.getButtons();
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                final int r = row;
                final int c = col;
                buttons[row][col].addActionListener(e-> handleButtonClick(r, c));
            }
        }
    }

    private void addMenuListener() {
        // Get menu items from the view (BoardView)
        JMenu gameMenu = boardView.getMenuBar().getMenu(0);
        
        // Get individual menu items
        JMenuItem restartItem = gameMenu.getItem(0);
        JMenuItem saveItem = gameMenu.getItem(1);
        JMenuItem loadItem = gameMenu.getItem(2);
        JMenuItem exitItem = gameMenu.getItem(3);

        // Add action listeners to these items
        restartItem.addActionListener(e -> restartGame());
        saveItem.addActionListener(e -> saveGame());
        loadItem.addActionListener(e -> loadGame());
        exitItem.addActionListener(e -> exitGame());
    }

    private void handleButtonClick(int row, int col) {
        // Clear highlights before any action
        boardView.clearHighlight();

        if (selectedRow == -1 && selectedCol == -1) {
            // First click: select a piece
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

        } else {
            // Second click: attempt a move
            boolean moveSuccess = game.movePiece(selectedRow, selectedCol, row, col);
            if (moveSuccess) {
                // Check if the game has a winner (Sau captured)
                PieceColor winner = game.checkWinner();
                if (winner!=null) {
                    // If a winner is detected
                    boardView.refreshBoard(); // Update the board view
                    boardView.showWinnerMessage(winner);
                    // Restart the game
                    game.resetGame(); // Update the board view after reset
                    boardView.setBoardFlip(false); // Reset the board flip state
                    boardView.refreshBoard(); // Update the board view
                    boardView.updateMessage("Game restart!");
                    return; // Exit early since the game has reset
                }

                game.getBoard().flipBoard(); // Flip the board if the move is successful 
                boardView.setBoardFlip(!boardView.isBoardFlip()); // Update flip state to insert pieces image correctly
                boardView.refreshBoard(); // Update the board view after the move
                boardView.updateMessage("Move successful!");
                
            } else {
                boardView.updateMessage("Invalid move. Try again.");
            }
            // Reset selection
            selectedRow = -1;
            selectedCol = -1;
        }
    }
    
    // Initialize commands for the menu items
    private void initCommand() {
        commands.put("Restart", new RestartCommand(game, boardView));
        commands.put("Save", new SaveCommand(boardView, board, game));
        commands.put("Load", new LoadCommand(boardView, game));
        commands.put("Exit", new ExitCommand());
    }
    // Method for handling Restart action
    public void restartGame() {
        executeCommand("Restart");
    }

    // Method for handling Save action
    public void saveGame() {
        executeCommand("Save");
    }

    // Method for handling Load action
    public void loadGame() {
        executeCommand("Load");
    }

    // Method for handling Exit action
    public void exitGame() {
        executeCommand("Exit");
    }

    private void executeCommand(String str) {
        MenuCommand command = commands.get(str);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Command not found: " + str);
        }
    }
}
