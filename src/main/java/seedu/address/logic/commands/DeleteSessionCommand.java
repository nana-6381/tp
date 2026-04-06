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
 * Deletes a session from a pet.
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

    public DeleteSessionCommand(Index ownerIdx, Index petIdx, Index sessIdx) {
        this.ownerIndex = ownerIdx;
        this.petIndex = petIdx;
        this.sessionIndex = sessIdx;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> personList = model.getFilteredPersonList();

        if (ownerIndex.getZeroBased() >= personList.size()) {
            throw new CommandException("Invalid owner index");
        }

        Person owner = personList.get(ownerIndex.getZeroBased());

        List<Pet> pets = owner.getPetList();

        if (petIndex.getZeroBased() >= pets.size()) {
            throw new CommandException("Invalid pet index");
        }

        Pet pet = pets.get(petIndex.getZeroBased());

        List<Session> sessions = pet.getSessions();

        if (sessionIndex.getZeroBased() >= sessions.size()) {
            throw new CommandException("Invalid session index");
        }

        Session sessionToDelete = sessions.get(sessionIndex.getZeroBased());

        // Remove session
        pet.removeSession(sessionIndex.getZeroBased());

        // Rebuild owner
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
