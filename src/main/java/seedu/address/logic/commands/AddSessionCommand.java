package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AddSessionCommand extends Command {

    public static final String COMMAND_WORD = "addsessions";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a session. "
            + "Parameters: "
            + PREFIX_OWNER_INDEX + "OWNER_INDEX "
            + PREFIX_PET_INDEX + "PET_INDEX "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_OWNER_INDEX + "1 "
            + PREFIX_PET_INDEX + "1 "
            + PREFIX_START_TIME + "2026-03-25 10:00 "
            + PREFIX_END_TIME + "2026-03-25 11:00";

    public static final String MESSAGE_SUCCESS = "New session added";

    private final Index ownerIndex;
    private final Index petIndex;
    private final String startTime;
    private final String endTime;

    public AddSessionCommand(Index ownerIndex, Index petIndex, String startTime, String endTime) {
        requireNonNull(ownerIndex);
        requireNonNull(petIndex);
        requireNonNull(startTime);
        requireNonNull(endTime);
        this.ownerIndex = ownerIndex;
        this.petIndex = petIndex;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddSessionCommand)) {
            return false;
        }
        AddSessionCommand otherCommand = (AddSessionCommand) other;
        return ownerIndex.equals(otherCommand.ownerIndex)
                && petIndex.equals(otherCommand.petIndex)
                && startTime.equals(otherCommand.startTime)
                && endTime.equals(otherCommand.endTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("ownerIndex", ownerIndex)
                .add("petIndex", petIndex)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .toString();
    }
}