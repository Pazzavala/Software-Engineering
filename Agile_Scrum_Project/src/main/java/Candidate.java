/**
 * The Candidate class represents a candidate running for election.
 * @author Aaron Raines
 * @version 1.0
 * @since 1.0
 */
public class Candidate {

    private final String cName;
    private final Party party;
    private int candidateID;
    private int numVotes;
    private Ballot[] ballots;
    private int round;

    /**
     * Gets the current round of ballot distribution the candidates is on.
     * @return the int round value.
     */
    public int getRound() {
        return round;
    }

    /**
     * Sets the current round of ballot distribution the candidates is on.
     * @param round send the round value.
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Creates a new candidate with the given name and party.
     * @param name The name of the candidate.
     * @param party The party the candidate belongs to.
     */
    public Candidate(String name, Party party) {
        this.cName = name;
        this.party = party;
    }

    /**
     * Returns the name of the candidate.
     * @return The name of the candidate.
     */    
    public String getName() {
        return this.cName;
    }

    /**
     * Returns the ID of the candidate.
     * @return The ID of the candidate.
     */
    public int getCandidateID() {
        return this.candidateID;
    }

    /**
     * Sets the ID of the candidate.
     * @param ID The ID of the candidate.
     */
    public void setCandidateID(int ID) {
        this.candidateID = ID;
    }

    /**
     * Returns the party the candidate belongs to.
     * @return The party the candidate belongs to.
     */
    public Party getParty() {
        return this.party;
    }

    /**
     * Returns the number of votes the candidate has received.
     * @return The number of votes the candidate has received.
     */
    public int getNumVotes() {
        return this.numVotes;
    }

    /**
     * Sets the number of votes the candidate has received.
     * @param votes The number of votes the candidate has received.
     */
    public void setNumVotes(int votes) {
        this.numVotes = votes;
    }

    /**
     * Adds a ballot to the candidate's list of received ballots.
     * @param ballot The ballot to add.
     */    
    public void passBallot(Ballot ballot) {
        if (this.ballots == null) {
            this.ballots = new Ballot[1];
            this.ballots[0] = ballot;
        } else {
            Ballot[] newBallots = new Ballot[this.ballots.length + 1];
            for (int i = 0; i < this.ballots.length; i++) {
                newBallots[i] = this.ballots[i];
            }
            newBallots[this.ballots.length] = ballot;
            this.ballots = newBallots;
        }
    
        setNumVotes(ballots.length);
    }
    
    /**
     * Returns an array of all the ballots the candidate has received.
     * @return An array of all the ballots the candidate has received.
     */
    public Ballot[] getBallots() {
        return this.ballots;
    }

}
