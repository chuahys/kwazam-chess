/**
 * Ram class is a subclass of the Piece class.
 * The Ram moves one square forward in its current direction (either upwards or downwards).
 */
public class Ram extends Piece {
    private boolean movingUp; // Tracks the current direction of the Ram

    /**
     * Constructor to initialize the Ram piece with its color and position.
     */
    public Ram(PieceColor color, int row, int col) {
        super(color, row, col);
        this.movingUp = true; // Initially moving upward
    }

    /**
     * Returns the image path for the Ram piece, based on the flip state and direction of movement.
     * @author Chuah Yun Shan
     */
    @Override
    public String getImagePath(boolean isFlip) {
        String pieceName = "Ram";
        String colorName = getColor() == PieceColor.BLUE ? "b" : "r";

        // Logic for Blue Pieces
        if (getColor() == PieceColor.BLUE) {
            if (isFlip) {
                if (movingUp) {
                    // Board is flipped, blue moves up with flipped image (arrow pointing down)
                    return "resources/img/" + colorName + pieceName + "_flip.png";
                } else {
                    return "resources/img/" + colorName + pieceName + ".png";
                }
            } else {
                // Board is not flipped, blue moves up with original image (arrow pointing up)
                if (movingUp) {
                    return "resources/img/" + colorName + pieceName + ".png";
                } else {
                    return "resources/img/" + colorName + pieceName + "_flip.png";
                }
            }
        } else { // Red Pieces
            if (isFlip) {
                // Board is flipped, red moves up with flipped image (arrow pointing up)
                if (movingUp) {
                    return "resources/img/" + colorName + pieceName + "_flip.png";
                } else {
                    return "resources/img/" + colorName + pieceName + ".png";
                }
            } else {
                // Board is not flipped, red moves up with original image (arrow pointing down)
                if (movingUp) {
                    return "resources/img/" + colorName + pieceName + ".png";
                } else {
                    return "resources/img/" + colorName + pieceName + "_flip.png";
                }
            }
        }
    }
    
    /**
     * Validates if the Ram can make a move to the given position.
     * The Ram can move one square in its current direction (up or down).
     * @author Chuah Yun Shan
     */
    @Override
    public boolean isValidMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        // The Ram moves one step forward (vertically)
        if (movingUp) {
            // Move one step upward (decrease row by 1) and stay in the same column
            if (endRow != startRow - 1 || endCol != startCol) {
                return false; // Invalid if not exactly one step upward in the same column
            }
        } else {
            // Move one step downward (increase row by 1) and stay in the same column
            if (endRow != startRow + 1 || endCol != startCol) {
                return false; // Invalid if not exactly one step downward in the same column
            }
        }

        // Check if the target square is empty or occupied by an opponent
        Piece targetPiece = board.getPieceAt(endRow, endCol);
        if (targetPiece == null || targetPiece.getColor() != getColor()) {
            return true; // Valid if empty or occupied by an opponent
        }

        return false; // Invalid if occupied by an ally
    }
    
    /**
     * Updates the position of the Ram on the board 
     * and adjusts the direction of movement (when the Ram reaches the end of the board).
     * @author Chuah Yun Shan
     */
    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        // Update direction if the Ram reaches the top of the board
        if (row == 0) {
            movingUp = false; // Change direction to downward
        } else if (row == Board.getInstance().getHeight() - 1) {
            movingUp = true; // Change direction to upward
        }
    }

    /**
     * The Ram does not transform into another piece, so this method is empty.
     */
    @Override
    public void transform() {
        // No transformation for Ram, it just moves forward
    }

    /**
     * Returns the type of the Ram piece.
     * @author Tan Yun Xuan
     */
    @Override
    public String getPieceType() {
        return PieceType.RAM.name(); // Return the name of the PieceType as a String
    }

     /**
     * Returns the direction of the Ram piece.
     * @return "up" if moving up, "down" if moving down
     */
    public String getDirection() {
        return movingUp ? "up" : "down"; // Return the current direction
    }

    /**
     * Set the direction of the Ram piece.
     * @param direction "up" or "down"
     */
    public void setDirection(String direction) {
        if ("up".equals(direction)) {
            movingUp = true;
        } else if ("down".equals(direction)) {
            movingUp = false;
        }
    }
}