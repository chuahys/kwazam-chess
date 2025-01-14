import java.io.*;
import java.util.*;
import javax.swing.*;

public interface MenuCommand {
    void execute();
}

/**
 * RestartCommand class handles the restart action for the menu.
 */
class RestartCommand implements MenuCommand {
    private final Game game;
    private final BoardView boardView;

    public RestartCommand(Game game, BoardView boardView) {
        this.game = game;
        this.boardView = boardView;
    }

    @Override
    public void execute() {
        game.resetGame();
        boardView.setBoardFlip(false);
        boardView.refreshBoard();
        boardView.updateMessage("Game restart!");
    }
}

/**
 * SaveCommand class handles the save action for the menu.
 */
class SaveCommand implements MenuCommand {
    private BoardView boardView;
    private Board board;
    private Game game;

    public SaveCommand(BoardView boardView, Board board, Game game) {
        this.boardView = boardView;
        this.board = Board.getInstance();
        this.game = game;
    }

    @Override
    public void execute() {
        try {
            // Ensure the SAVE directory exists
            File saveDir = new File("SAVE");
            // Create the directory if it doesn't exist
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            String filename;

            while (true) {
                filename = JOptionPane.showInputDialog(boardView, "Enter the file name:").toLowerCase();

                if (filename == null) {
                    return;
                }

                if (filename.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(boardView, "Please enter a valid file name", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                if (!filename.endsWith(".txt")) {
                    filename += ".txt";
                }

                File file = new File("SAVE", filename);
                if (file.exists()) {
                    int replaceOption = JOptionPane.showConfirmDialog(
                            boardView,
                            "A file with the same name already exists. Do you want to replace it?",
                            "File Exists",
                            JOptionPane.YES_NO_OPTION);

                    if (replaceOption == JOptionPane.YES_OPTION) {
                        break;
                    }
                } else {
                    break;
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("SAVE", filename)))) {
                writer.write("Game Saved");
                writer.newLine();
                writer.write("Current turn: " + game.getCurrentPlayer());
                writer.newLine();

                // Loop through the board and save each piece
                for (int row = 0; row < board.getHeight(); row++) {
                    for (int col = 0; col < board.getWidth(); col++) {
                        Piece piece = board.getPieceAt(row, col);

                        if (piece != null) {
                            String playerColor = piece.getColor() == PieceColor.RED ? "Red" : "Blue";
                            String pieceType = piece.getPieceType().toString();

                            // Inv?

                            writer.write(playerColor + pieceType);
                        } else {
                            writer.write("NULL");
                        }

                        if (col < board.getWidth() - 1) {
                            writer.write(" ");
                        }
                    }
                    writer.newLine();
                }

                JOptionPane.showMessageDialog(boardView, "Game saved to: " + filename);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(boardView, "Error saving the game.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(boardView, "Error saving the game.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

/**
 * LoadCommand class handles the load action for the menu.
 */
class LoadCommand implements MenuCommand {
    private Board board;
    private BoardView boardView;
    private Game game;

    public LoadCommand(BoardView boardView, Game game) {
        this.board = Board.getInstance(); // Get the singleton instance of the Board
        this.boardView = boardView;
        this.game = game;
    }

    @Override
    public void execute() {
        try {
            // Check if the SAVE directory exists
            File saveDirectory = new File("SAVE");

            if (!saveDirectory.exists() || !saveDirectory.isDirectory()) {
                JOptionPane.showMessageDialog(boardView, "No saved games found", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Let user choose the save file
            JFileChooser fileChooser = new JFileChooser(saveDirectory);
            int option = fileChooser.showOpenDialog(boardView);

            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                // Reset the board to clear old game state before loading new one
                board.resetBoard();

                try (Scanner scanner = new Scanner(file)) {
                    // Read the header line for validation
                    if (!scanner.nextLine().equals("Game Saved")) {
                        throw new IOException("Invalid save file format");
                    }

                    // Load the current player and move count
                    String currentPlayerString = scanner.nextLine().split(": ")[1].trim();
                    PieceColor currentPlayer = PieceColor.valueOf(currentPlayerString.toUpperCase());
                    game.setCurrentPlayer(currentPlayer);

                    int moveCount = Integer.parseInt(scanner.nextLine().split(": ")[1].trim());
                    game.setMoveCount(moveCount);

                    // Load the board state
                    for (int row = 0; row < board.getHeight(); row++) {
                        String line = scanner.nextLine();
                        String[] pieces = line.split(" "); // Split by space

                        for (int col = 0; col < board.getWidth(); col++) {
                            String pieceInfo = pieces[col];

                            if (!pieceInfo.equals("EMPTY")) {
                                char colorCode = pieceInfo.charAt(0);
                                String pieceTypeStr = pieceInfo.substring(1); // Extract the piece type string

                                PieceColor color = colorCode == 'r' ? PieceColor.RED : PieceColor.BLUE;
                                PieceType pieceType = PieceType.valueOf(pieceTypeStr.toUpperCase()); // Convert to
                                                                                                     // PieceType

                                // Create and place the piece on the board
                                Piece loadedPiece = PieceFactory.createPiece(pieceType, color, row, col);
                                board.setPieceAt(loadedPiece, row, col);
                            }
                        }
                    }

                    // Update the view
                    boardView.refreshBoard();
                    JOptionPane.showMessageDialog(boardView, "Game loaded successfully!", "Info",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(boardView, "Error loading game: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(boardView, "Error loading game", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void refresh() {
        boardView.refreshBoard();
    }
}

/**
 * ExitCommand class handles the exit action for the menu.
 */
class ExitCommand implements MenuCommand {
    @Override
    public void execute() {
        // Show a confirmation dialog before exiting
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", 
                                                     "Exit Confirmation", JOptionPane.YES_NO_OPTION);

        // If the user confirms, exit the application
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
        // If the user chooses "No", do nothing and return to the game
    }
}