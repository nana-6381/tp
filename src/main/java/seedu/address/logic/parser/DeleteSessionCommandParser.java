package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SESSION_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a DeleteSessionCommand object.
 */
public class DeleteSessionCommandParser implements Parser<DeleteSessionCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a DeleteSessionCommand object.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteSessionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OWNER_INDEX, PREFIX_PET_INDEX, PREFIX_SESSION_INDEX);

    if (argMultimap.getValue(PREFIX_OWNER_INDEX).isEmpty()
                || argMultimap.getValue(PREFIX_PET_INDEX).isEmpty()
                || argMultimap.getValue(PREFIX_SESSION_INDEX).isEmpty()
                || !argMultimap.getPreamble().isEmpty()) {

        throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, DeleteSessionCommand.MESSAGE_USAGE));
    }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_OWNER_INDEX, PREFIX_PET_INDEX, PREFIX_SESSION_INDEX);

        Index ownerIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_OWNER_INDEX).get());
        Index petIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PET_INDEX).get());
        Index sessionIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_SESSION_INDEX).get());

        return new DeleteSessionCommand(ownerIndex, petIndex, sessionIndex);
    }
}