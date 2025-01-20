/**
 * Ram class is a subclass of the Piece class.
 * It moves one step forward in its current direction (upwards or downwards).
 * @author Chuah Yun Shan
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
     * Adjust direction when the Ram reaches the end of the board.
     * @author Chuah Yun Shan
     */
    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        // Update direction if the Ram reaches the top or bottom of the board
        if (row == 0) {
            movingUp = false; // Change direction to downward
        } else if (row == Game.getInstance().getBoard().getHeight() - 1) {
            movingUp = true; // Change direction to upward
        }
    }
    
    /**
     * Return the image path for the Ram piece, based on the flip state and direction of movement.
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
     * Validate if the Ram can make a move.
     * @author Chuah Yun Shan
     */
    @Override
    public boolean isValidMove(Board board, int startX, int startY, int endX, int endY) {
        // The Ram moves one step forward (vertically)
        if (movingUp) {
            // Move one step upward (decrease row by 1) and stay in the same column
            if (endX != startX - 1 || endY != startY) {
                return false; // Invalid if not exactly one step upward in the same column
            }
        } else {
            // Move one step downward (increase row by 1) and stay in the same column
            if (endX != startX + 1 || endY != startY) {
                return false; // Invalid if not exactly one step downward in the same column
            }
        }

        // Check if the target square is empty or occupied by different color piece (opponent)
        Piece target = board.getPieceAt(endX, endY);
        if (target == null || target.getColor() != getColor()) {
            return true; // Valid if empty or occupied by an opponent
        }

        return false; // Invalid if occupied by same color piece
    }

    /**
     * The Ram does not transform into another piece, so this method is empty.
     */
    @Override
    public void transform(Board board) {
        // No transformation for Ram
    }

    /**
     * Return the type of the Ram piece.
     * @author Tan Yun Xuan
     */
    @Override
    public String getPieceType() {
        return PieceType.RAM.name(); // Return the name of the PieceType as a String
    }

     /**
     * Return the direction of the Ram piece.
     */
    public String getDirection() {
        return movingUp ? "up" : "down"; // Return the current direction
    }

    /**
     * Set the direction of the Ram piece.
     */
    public void setDirection(String str) {
        if ("up".equals(str)) {
            movingUp = true;
        } else if ("down".equals(str)) {
            movingUp = false;
        }
    }
}