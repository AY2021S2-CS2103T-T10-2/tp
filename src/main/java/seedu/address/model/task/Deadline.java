package seedu.address.model.task;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents a Task's deadline in the planner.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {
    public static final String FIELD_NAME = "Deadline";

    public static final String MESSAGE_CONSTRAINTS =
            "Deadline should be in the format dd/mm/yyyy and should be "
                    + "a valid date after today eg. 12/08/2021";
    public static final String MESSAGE_CONSTRAINTS_INVALID_DATE =
            "Deadline should not be before today";

    public static final String VALIDATION_REGEX = "^((0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/(19|20)\\d\\d)$";

    private static final Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public final LocalDate value;

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline A valid deadline number.
     */
    public Deadline(String deadline) {
        checkArgument(isValidDeadline(deadline), MESSAGE_CONSTRAINTS);
        value = parseDeadline(deadline);
    }

    /**
     * Returns true if a given string is a valid deadline number.
     */
    public static boolean isValidDeadline(String test) {
        Pattern p = Pattern.compile(VALIDATION_REGEX);
        Matcher m = p.matcher(test);
        boolean validDate = false;
        if (!test.isEmpty() && m.matches()) {
            LocalDate today = LocalDate.now();
            LocalDate parsedDeadline = LocalDate.parse(test,
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            validDate = parsedDeadline.isAfter(today);
        }
        LOGGER.log(Level.INFO, "Checking for Valid Deadline");
        return (m.matches() && validDate) || test.isEmpty();

    }

    /**
     * Returns a deadline in the form of a LocalDate.
     * @param deadline the specified deadline.
     * @return
     */
    public static LocalDate parseDeadline(String deadline) {
        LOGGER.log(Level.INFO, "Parsing Deadline");
        if (deadline.isEmpty()) {
            return null;
        } else {
            LocalDate parsedDeadline = LocalDate.parse(deadline,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return parsedDeadline;
        }
    }

    public LocalDate getDate() {
        LOGGER.log(Level.INFO, "Getting Date");
        return value;
    }

    /**
     * Indicates whether the deadline is already over
     * @return boolean to indicate whether deadline is over
     */
    public boolean over() {
        LOGGER.log(Level.INFO, "Checking if the date is after today");
        LocalDate now = LocalDate.now();
        return now.isAfter(value);
    }

    public boolean isWithinSevenDays(LocalDate currentDate) {
        LocalDate sevenDaysFromNow = currentDate.plusDays(7);

        return value.isBefore(sevenDaysFromNow);
    }

    /**
     * Check if there is a value present.
     *
     * @return true is value is null, false otherwise.
     */
    public boolean isEmptyValue() {
        return value == null;
    }

    @Override
    public String toString() {
        if (value != null) {
            return value.format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && ((value == null && ((Deadline) other).value == null)
                        || value.equals(((Deadline) other).value))); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
