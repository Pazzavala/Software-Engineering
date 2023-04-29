/**
 * The Party class represents a political party participating in an election.
 * @author Aaron Raines
 * @version 1.0
 * @since 1.0
 */
public class Party {
    String pName;
    Candidate[] candidates;
    Ballot[] pBallots;
    int numPVotes;
    int numPSeats;
    int remainingVotes;

    /**
     * Constructs a new Party object with the given name.
     * @param name the name of the party
     */
    public Party(String name) {
        pName = name;
    }

    /**
     * Returns the name of the party.
     * @return the name of the party
     */
    public String getpName() {
        return pName;
    }

    /**
     * Returns the array of candidates for the party.
     * @return the array of candidates for the party
     */
    public Candidate[] getCandidates() {
        return candidates;
    }

    /**
     * Sets the array of candidates for the party.
     * @param c the array of candidates for the party
     */
    public void setCandidates(Candidate[] c) {
        this.candidates = c;
    }

    /**
     * Sets the array of ballots cast for the party.
     * @param pBallots the array of ballots cast for the party
     */
    public void setpBallots(Ballot[] pBallots) {
        this.pBallots = pBallots;
    }

    /**
     * Sets the number of proportional votes received by the party.
     * @param numPVotes the number of proportional votes received by the party
     */
    public void setNumPVotes(int numPVotes) {
        this.numPVotes = numPVotes;
    }

    /**
     * Sets the number of seats won by the party.
     * @param numPSeats the number of seats won by the party
     */
    public void setNumPSeats(int numPSeats) {
        this.numPSeats = numPSeats;
    }

    /**
     * Sets the number of remaining votes for the party after the allocation of seats.
     * @param remainingVotes the number of remaining votes for the party
     */
    public void setRemainingVotes(int remainingVotes) {
        this.remainingVotes = remainingVotes;
    }

    /**
     * Returns the array of ballots cast for the party.
     * @return the array of ballots cast for the party
     */
    public Ballot[] getpBallots() {
        return pBallots;
    }

    /**
     * Returns the number of proportional votes received by the party.
     * @return the number of proportional votes received by the party
     */
    public int getNumPVotes() {
        return numPVotes;
    }

    /**
     * Returns the number of seats won by the party.
     * @return the number of seats won by the party
     */
    public int getNumPSeats() {
        return numPSeats;
    }

    /**
     * Returns the number of remaining votes for the party after the allocation of seats.
     * @return the number of remaining votes for the party
     */
    public int getRemainingVotes() {
        return remainingVotes;
    }
}
