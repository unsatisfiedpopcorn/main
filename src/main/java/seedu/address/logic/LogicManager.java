package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.TaskManagerParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.achievement.AchievementRecord;
import seedu.address.model.task.Task;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final TaskManagerParser taskManagerParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        taskManagerParser = new TaskManagerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = taskManagerParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public AchievementRecord getAchievementRecord() {
        return model.getAchievementRecord();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return model.getTaskManager();
    }
}
