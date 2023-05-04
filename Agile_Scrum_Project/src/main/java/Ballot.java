import java.util.ArrayList;

/**
 * A class representing a ballot with a list of integers indicating vote preferences.
 * @author Aaron Raines
 * @version 1.0
 * @since 1.0
 */
public class Ballot {

    private final ArrayList<Integer> ballot;

    /**
     * Constructs a ballot object with a list of integers representing vote preferences.
     * @param ballot a list of integers representing vote preferences
     */
    public Ballot(ArrayList<Integer> ballot) {
        this.ballot = ballot;
    }

    /**
     * Returns the list of integers representing vote preferences.
     * @return the list of integers representing vote preferences
     */
    public ArrayList<Integer> getVotes() {
        return this.ballot;
    }

    /**
     * Returns the index of the given integer in the list of vote preferences.
     * @param num the integer to find the index of in the list of vote preferences
     * @return the index of the given integer in the list of vote preferences, or -1 if the integer is not present in the list
     */
    public int indexOf(int num) {
        return ballot.indexOf(num);
    }

}
