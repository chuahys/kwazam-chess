import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameController {
    private final Game game; // Model
    private final BoardView boardView; // View
    
    private int selectedRow = -1; // Row of selected piece
    private int selectedCol = -1; // Column of selected piece

    public GameController(Game game, BoardView boardView) {
        this.game = game;
        this.boardView = boardView;
        addBoardListener();
    }

    private void addBoardListener() {
        JButton[][] buttons = boardView.getButtons();
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                final int r = row;
                final int c = col;
                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClick(r, c);
                    }
                });
            }
        }
    }

    private void handleButtonClick(int row, int col) {
        // Clear highlights before any action
        boardView.clearHighlight();

        if (selectedRow == -1 && selectedCol == -1) {
            // First click: select a piece
            Piece piece = game.getBoard().getPieceAt(row, col);
            System.out.println("Selected piece at row " + row + ", col " + col);

            if (piece != null && piece.getColor() == game.getCurrentPlayer()) {
                selectedRow = row;
                selectedCol = col;
                // Get valid moves for the selected piece
                List<int[]> validMoves = game.getValidMoves(row, col);
                // Call to highlight valid moves
                boardView.highlightValidMoves(validMoves);
            }
        } else {
            // Second click: attempt a move
            boolean moveResult = game.makeMove(selectedRow, selectedCol, row, col);
            if (moveResult) {
                // Flip the board and update the view if the move is successful
                game.getBoard().flipBoard();
                boardView.setBoardFlip(!boardView.isBoardFlip());  // Update flip state to insert pieces image correctly
                boardView.refreshBoard();
                boardView.updateLabels();
            }
            // Reset selection
            selectedRow = -1;
            selectedCol = -1;
        }
    }
}
