package seedu.address.model.game;

import seedu.address.logic.commands.ModeCommand;
import seedu.address.model.task.Task;

// @@author chikchengyao

/**
 * Manages the gamification aspects of the task manager.
 * <p>
 * Varies the amount of XP awarded to the player based on the task and on game settings.
 */
public class GameManager {

    // Stores an instance of the currently chosen game mode.
    private GameMode gameMode;

    public GameManager() {
        this.gameMode = GameMode.getDefaultGameMode();
    }

    public GameManager(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void replace(GameManager other) {
        this.gameMode = other.getGameMode().copy();
    }

    /**
     * Makes a copy of the supplied gameManager.
     */
    public GameManager copy() {
        GameMode copyMode = this.gameMode.copy();
        return new GameManager(copyMode);
    }

    public GameMode getGameMode() {
        return this.gameMode.copy();
    }

    /**
     * Appraises the present XP worth of a particular task.
     * <p>
     * Uncompleted tasks are worth 0XP.
     *
     * @param taskFrom The task to be changed
     * @param taskTo   The changed task
     * @return Returns the XP the supplied task is worth at present
     */
    public int appraiseXpChange(Task taskFrom, Task taskTo) {
        if (taskFrom == null || taskTo == null) {
            throw new NullPointerException();
        }

        return gameMode.appraiseXpChange(taskFrom, taskTo);
    }

    public void setGameMode(String newGameModeName) {
        switch (newGameModeName) {
        case ModeCommand.FLAT_MODE:
            this.gameMode = new FlatMode();
            break;

        case ModeCommand.DECREASING_MODE:
            this.gameMode = new DecreasingMode();
            break;

        case ModeCommand.INCREASING_MODE:
            this.gameMode = new IncreasingMode();
            break;

        case ModeCommand.PRIORITY_MODE:
            this.gameMode = new PriorityMode();
            break;
        default:
            assert false;
        }
    }

    public void setGameMode(String newGameModeName, String newGameDifficultyName) {
        int period;
        int low;
        int high;

        switch (newGameDifficultyName) {
        case ModeCommand.EASY_MODE:
            period = 1;
            low = 40;
            high = 50;
            break;

        case ModeCommand.MEDIUM_MODE:
            period = 3;
            low = 30;
            high = 60;
            break;

        case ModeCommand.HARD_MODE:
            period = 7;
            low = 20;
            high = 70;
            break;

        case ModeCommand.EXTREME_MODE:
            period = 10;
            low = 10;
            high = 80;
            break;

        default:
            assert false;
            period = 7;
            low = 25;
            high = 50;
        }

        switch (newGameModeName) {
        case ModeCommand.FLAT_MODE:
            this.gameMode = new FlatMode(low, high);
            break;

        case ModeCommand.DECREASING_MODE:
            this.gameMode = new DecreasingMode(period, low, high);
            break;

        case ModeCommand.INCREASING_MODE:
            this.gameMode = new IncreasingMode(period, low, high);
            break;

        case ModeCommand.PRIORITY_MODE:
            this.gameMode = new PriorityMode(low / 10, high / 10);
            break;
        default:
            assert false;
        }
    }

    /**
     * Checks if the given game mode name is a valid name.
     */
    public static boolean isValidGameMode(String gameModeName) {
        switch(gameModeName) {
        case ModeCommand.FLAT_MODE:
        case ModeCommand.DECREASING_MODE:
        case ModeCommand.INCREASING_MODE:
        case ModeCommand.PRIORITY_MODE:
            return true;
        default:
            return false;
        }
    }

    /**
     * Checks if the supplied string describes a valid game difficulty.
     *
     * @param gameDifficultyName The proposed game difficulty.
     * @return
     */
    public static boolean isValidGameDifficulty(String gameDifficultyName) {
        if (gameDifficultyName.equals(ModeCommand.EASY_MODE)) {
            return true;
        }

        if (gameDifficultyName.equals(ModeCommand.MEDIUM_MODE)) {
            return true;
        }

        if (gameDifficultyName.equals(ModeCommand.HARD_MODE)) {
            return true;
        }

        if (gameDifficultyName.equals(ModeCommand.EXTREME_MODE)) {
            return true;
        }

        return false;
    }

}
