package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.pet.Pet;
import seedu.address.model.session.Session;

/**
 * Deletes a specific {@link Session} from a {@link Pet} belonging to a {@link Person}.
 *
 * <p>The command requires three indices:
 * <ul>
 *   <li>Owner index</li>
 *   <li>Pet index (within the owner)</li>
 *   <li>Session index (within the pet)</li>
 * </ul>
 *
 * <p>All indices are 1-based from the user's perspective.
 *
 * @see seedu.address.logic.parser.DeleteSessionCommandParser
 */
public class DeleteSessionCommand extends Command {

    public static final String COMMAND_WORD = "deletesession";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a session from a pet.\n"
            + "Parameters: oi/OWNER_INDEX pi/PET_INDEX si/SESSION_INDEX\n"
            + "Example: " + COMMAND_WORD + " oi/1 pi/1 si/1";

    public static final String MESSAGE_SUCCESS = "Deleted session: %1$s";

    private final Index ownerIndex;
    private final Index petIndex;
    private final Index sessionIndex;

    /**
     * Creates a DeleteSessionCommand to delete a session from a specific pet of an owner.
     *
     * @param ownerIdx index of the owner in the filtered person list
     * @param petIdx index of the pet in the owner's pet list
     * @param sessIdx index of the session in the pet's session list
     */
    public DeleteSessionCommand(Index ownerIdx, Index petIdx, Index sessIdx) {
        this.ownerIndex = ownerIdx;
        this.petIndex = petIdx;
        this.sessionIndex = sessIdx;
    }

    /**
     * Executes the delete session command.
     *
     * <p>The method performs the following steps:
     * <ol>
     *   <li>Validates the owner index</li>
     *   <li>Retrieves the specified pet</li>
     *   <li>Validates the session index</li>
     *   <li>Removes the session from the pet</li>
     *   <li>Reconstructs the owner with the updated pet</li>
     *   <li>Updates the model</li>
     * </ol>
     *
     * @param model the model containing the data
     * @return CommandResult indicating success with the deleted session
     * @throws CommandException if any index is invalid
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> personList = model.getFilteredPersonList();

        // Validate owner index
        if (ownerIndex.getZeroBased() >= personList.size()) {
            throw new CommandException("Invalid owner index");
        }

        Person owner = personList.get(ownerIndex.getZeroBased());

        List<Pet> pets = owner.getPetList();

        // Validate pet index within selected owner
        if (petIndex.getZeroBased() >= pets.size()) {
            throw new CommandException("Invalid pet index");
        }

        Pet pet = pets.get(petIndex.getZeroBased());

        List<Session> sessions = pet.getSessions();

        // Validate session index within selected pet
        if (sessionIndex.getZeroBased() >= sessions.size()) {
            throw new CommandException("Invalid session index");
        }

        Session sessionToDelete = sessions.get(sessionIndex.getZeroBased());

        // Remove session
        pet.removeSession(sessionIndex.getZeroBased());

        // Rebuilds owner's pet set to reflect the updated pet
        // LinkedHashSet preserves any important insertion order
        Set<Pet> updatedPets = new LinkedHashSet<>(owner.getPets());
        updatedPets.remove(pet);
        updatedPets.add(pet);

        Person updatedOwner = new Person(
                owner.getName(),
                owner.getPhone(),
                owner.getEmail(),
                owner.getAddress(),
                owner.getTags(),
                updatedPets
        );

        model.setPerson(owner, updatedOwner);

        return new CommandResult(String.format(MESSAGE_SUCCESS, sessionToDelete));
    }
}
