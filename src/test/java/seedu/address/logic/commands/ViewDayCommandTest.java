package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.DANIEL;
import static seedu.address.testutil.TypicalTasks.GEORGE;
import static seedu.address.testutil.TypicalTasks.getTypicalPlanner;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Date;
import seedu.address.model.task.predicates.TaskOnDatePredicate;

class ViewDayCommandTest {
    private Model model = new ModelManager(getTypicalPlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPlanner(), new UserPrefs());

    @Test
    public void execute_viewDayWithTasks_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 2);
        Date date = new Date("27/05/2021");
        TaskOnDatePredicate predicate = new TaskOnDatePredicate(date);
        ViewDayCommand command = new ViewDayCommand(predicate, date.getDate());
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, GEORGE), expectedModel.getFilteredTaskList());
    }

    @Test
    public void execute_viewDayWithoutTasks_noTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        Date date = new Date("24/04/2021");
        TaskOnDatePredicate predicate = new TaskOnDatePredicate(date);
        ViewDayCommand command = new ViewDayCommand(predicate, date.getDate());
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredTaskList());
    }
}