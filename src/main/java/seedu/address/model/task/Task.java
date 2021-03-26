package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Task in the planner.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final Title title;
    private final Deadline deadline;
    private final Duration duration;

    // Data fields
    private final Description description;
    private final RecurringSchedule recurringSchedule;
    private final Status status;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, Deadline deadline, Duration duration, RecurringSchedule recurringSchedule,
                Description description, Status status, Set<Tag> tags) {
        // All fields are not null even if its value is blank.
        requireAllNonNull(title, deadline, duration, recurringSchedule, description, status, tags);
        this.title = title;
        this.deadline = deadline;
        this.duration = duration;
        this.recurringSchedule = recurringSchedule;
        this.description = description;
        this.status = status;
        this.tags.addAll(tags);
    }

    public Title getTitle() {
        return title;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public Duration getDuration() {
        return duration;
    }

    public RecurringSchedule getRecurringSchedule() {
        return recurringSchedule;
    }

    public Description getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Replaces the task with a new Task with the set of tags provided.
     */
    public Task setTags(Set<Tag> tagSet) {
        return new Task(title, deadline, duration, recurringSchedule, description, status, tagSet);
    }

    /**
     * Gets a hashmap of each field name and its string value. Insertion order matters for the
     * TaskCardDetails class that invokes this method to dynamically use entries.
     *
     * @return Hashmap of field names and their string values.
     */
    public HashMap<String, String> getFields() {
        HashMap<String, String> optionalFieldMap = new HashMap<>();
        optionalFieldMap.put(Title.FIELD_NAME, title.toString());
        optionalFieldMap.put(Duration.FIELD_NAME, duration.toString());
        optionalFieldMap.put(Status.FIELD_NAME, status.toString());
        optionalFieldMap.put(Deadline.FIELD_NAME, deadline.toString());
        optionalFieldMap.put(Description.FIELD_NAME, description.toString());
        optionalFieldMap.put(RecurringSchedule.FIELD_NAME, recurringSchedule.toString());
        return optionalFieldMap;
    }

    /**
     * Returns true if both tasks have the same title.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getTitle().equals(getTitle());
    }

    public boolean dateOver() {
        return deadline.over();
    }

    public boolean hasExpired() {
        return recurringSchedule.isExpired();
    }

    /**
     * Checks if the Deadline attribute contains any data.
     *
     * @return true if the String of Deadline isEmpty, false otherwise.
     */
    public boolean isDeadlineEmpty() {
        return deadline.isEmptyValue();
    }

    /**
     * Checks if the Duration attribute contains any data.
     *
     * @return true if the String of Duration isEmpty, false otherwise.
     */
    public boolean isDurationEmpty() {
        return duration.isEmptyValue();
    }

    /**
     * Checks if the RecurringSchedule attribute contains any data.
     *
     * @return true if the String of RecurringSchedule isEmpty, false otherwise.
     */
    public boolean isRecurringScheduleEmpty() {
        return recurringSchedule.isEmptyValue();
    }

    /**
     * Returns true if both tasks have the same identity and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getTitle().equals(getTitle())
                && otherTask.getDeadline().equals(getDeadline())
                && otherTask.getDuration().equals(getDuration())
                && otherTask.getRecurringSchedule().equals(getRecurringSchedule())
                && otherTask.getDescription().equals(getDescription())
                && otherTask.getStatus().equals(getStatus())
                && otherTask.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, deadline, duration, recurringSchedule, description, status, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append("; \n").append(Deadline.FIELD_NAME).append(": ")
                .append(getDeadline())
                .append("; \n").append(Duration.FIELD_NAME).append(": ")
                .append(getDuration())
                .append("; \n").append(RecurringSchedule.FIELD_NAME).append(": ")
                .append(getRecurringSchedule())
                .append("; \n").append(Description.FIELD_NAME).append(": ")
                .append(getDescription())
                .append("; \n").append(Status.FIELD_NAME).append(": ")
                .append(getStatus());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; \n").append(Tag.FIELD_NAME).append("s: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}
