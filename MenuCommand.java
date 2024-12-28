public interface MenuCommand {
    void execute();
}

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
        boardView.setBoardFlip(!boardView.isBoardFlip());
        boardView.refreshBoard();
        boardView.updateMessage("Game restart!");
    }
}

class SaveCommand implements MenuCommand {
    @Override
    public void execute() {
        // Add save game logic
    }
}

class LoadCommand implements MenuCommand {
    @Override
    public void execute() {
        // Add load game logic
    }
}

class ExitCommand implements MenuCommand {
    @Override
    public void execute() {
        System.exit(0);
    }
}