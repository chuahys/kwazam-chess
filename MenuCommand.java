import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * The MenuCommand class implements the Command design pattern.
 */
public interface MenuCommand {
    void execute();
}

/**
 * RestartCommand class handles the restart action for the menu.
 */
class RestartCommand implements MenuCommand {
    private Game game;
    private BoardView boardView;

    public RestartCommand(BoardView boardView) {
        this.game = Game.getInstance();
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
 * 
 * @author Tan Yun Xuan
 * @author Lee Kar Yen
 * @author Chuah Yun Shan
 */
class SaveCommand implements MenuCommand {
    private BoardView boardView;
    private Board board;
    private Game game;

    public SaveCommand(BoardView boardView) {
        this.boardView = boardView;
        this.board = Board.getInstance();
        this.game = Game.getInstance();
    }

    @Override
    public void execute() {
        try {
            // Ensure the SAVE directory exists
            File save = new File("SAVE");
            // Create the directory if it doesn't exist
            if (!save.exists()) {
                save.mkdirs();
            }

            String name;

            while (true) {
                name = JOptionPane.showInputDialog(boardView, "Enter the file name:").toLowerCase();

                if (name == null) {
                    return;
                }

                if (name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(boardView, "Please enter a valid file name", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                if (!name.endsWith(".txt")) {
                    name += ".txt";
                }

                File file = new File("SAVE", name);
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

            try (BufferedWriter w = new BufferedWriter(new FileWriter(new File("SAVE", name)))) {
                w.write("Game Saved (B-Blue R-Red)");
                w.newLine();
                w.write("Current player: " + game.getCurrentPlayer());
                w.newLine();
                w.write("Move count: " + game.getMoveCount());
                w.newLine();

                // Save the board flip state
                w.write("Board Flip: " + boardView.isBoardFlip());
                w.newLine();

                // Loop through the board and save each piece
                for (int row = 0; row < board.getHeight(); row++) {
                    for (int col = 0; col < board.getWidth(); col++) {
                        Piece piece = board.getPieceAt(row, col);

                        if (piece != null) {
                            String color = piece.getColor() == PieceColor.RED ? "R" : "B";
                            String pieceType = piece.getPieceType().toString();

                            // Save piece data along with its direction for Ram pieces
                            if (piece instanceof Ram) {
                                Ram ram = (Ram) piece;
                                String dir = ram.getDirection(); // Get the direction up or down
                                w.write(color + pieceType + "," + dir); // Save Ram's direction
                            } else {
                                w.write(color + pieceType);
                            }
                        } else {
                            w.write("NULL");
                        }

                        if (col < board.getWidth() - 1) {
                            w.write(" ");
                        }
                    }
                    w.newLine();
                }

                JOptionPane.showMessageDialog(boardView, "Game saved to: " + name);
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
 * 
 * @author Tan Yun Xuan
 * @author Lee Kar Yen
 * @author Chuah Yun Shan
 */
class LoadCommand implements MenuCommand {
    private BoardView boardView;
    private Board board;
    private Game game;

    public LoadCommand(BoardView boardView) {
        this.boardView = boardView;
        this.board = Board.getInstance(); // Get the singleton instance of the Board
        this.game = Game.getInstance();
    }

    @Override
    public void execute() {
        try {
            // Check if the SAVE directory exists
            File save = new File("SAVE");

            if (!save.exists() || !save.isDirectory()) {
                JOptionPane.showMessageDialog(boardView, "No saved games found", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Let user choose the save file
            JFileChooser fc = new JFileChooser(save);
            int option = fc.showOpenDialog(boardView);

            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                // Reset the board to clear old game state before loading new one
                board.resetBoard();

                try (Scanner sc = new Scanner(file)) {
                    // Read the header line for validation
                    if (!sc.nextLine().equals("Game Saved (B-Blue R-Red)")) {
                        throw new IOException("Invalid save file format");
                    }

                    // Load the current player and move count
                    String playerStr = sc.nextLine().split(": ")[1].trim();
                    PieceColor currentPlayer = PieceColor.valueOf(playerStr.toUpperCase());
                    game.setCurrentPlayer(currentPlayer);

                    // Load the move count
                    String countStr = sc.nextLine().split(": ")[1].trim();
                    int moveCount = Integer.parseInt(countStr);
                    game.setMoveCount(moveCount);

                    // Load the board flip state
                    String flipStr = sc.nextLine();
                    boolean bool = flipStr.split(": ")[1].equalsIgnoreCase("true");
                    boardView.setBoardFlip(bool);

                    // Load the board state
                    for (int row = 0; row < board.getHeight(); row++) {
                        String line = sc.nextLine();
                        String[] pieces = line.split(" "); // Split by space

                        for (int col = 0; col < board.getWidth(); col++) {
                            String info = pieces[col];

                            if (info.equals("NULL")) {
                                continue; // No piece at this position
                            }

                            // Split the info to extract color and type
                            String[] data = info.split(",");

                            char ch = data[0].charAt(0); // Extract the color
                            String type = data[0].substring(1); // Extract the piece type
                            String dir = data.length > 1 ? data[1] : null; // If there is a direction, it will be the
                                                                           // second part

                            PieceColor color = (ch == 'r' || ch == 'R') ? PieceColor.RED
                                    : (ch == 'b' || ch == 'B') ? PieceColor.BLUE : null;

                            if (color == null) {
                                throw new IOException("Invalid piece color code: " + ch);
                            }

                            PieceType pieceType;
                            try {
                                pieceType = PieceType.valueOf(type.toUpperCase());
                            } catch (IllegalArgumentException e) {
                                throw new IOException("Invalid piece type: " + type);
                            }

                            // Create and place the piece on the board
                            Piece piece = PieceFactory.createPiece(pieceType, color, row, col);
                            // If the piece is a Ram, load the direction too
                            if (piece instanceof Ram) {
                                ((Ram) piece).setDirection(dir); // Set the direction of the Ram piece
                            }
                            // Place the piece on the board
                            board.setPieceAt(piece, row, col);

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
}

/**
 * ExitCommand class handles the exit action for the menu.
 */
class ExitCommand implements MenuCommand {
    @Override
    public void execute() {
        // Show a confirmation dialog before exiting
        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
                "Exit Confirmation", JOptionPane.YES_NO_OPTION);

        // If the user confirms, exit the application
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
        // If the user chooses "No", do nothing and return to the game
    }
}