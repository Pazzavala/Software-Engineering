import java.util.HashMap;
import java.util.Map;

/**
 * The RoundResult class represents the results of a single round of an election.
 * Each instance of this class contains information about the number of votes received by each candidate,
 * as well as the candidate who was dropped from the election (if any) and the winner of the election (if there is one).
 */
public class RoundResult {
    private String candidateName;
    private int votesReceived;
    private int votesAddedOrSubtracted;
    private int numCandidates;
    private int numBallots;
    private int round;
    private String droppedCandidate;
    private String winner;
    private final Map<String, Integer> candidateResults = new HashMap<>();

    /**
     * Creates a new RoundResult object with the specified round number.
     * @param round the round number of the election
     */
    public RoundResult(int round) {
        this.round = round;
    }

    /**
     * Sets the name of the candidate associated with this RoundResult object.
     * @param name the name of the candidate
     */
    public void setCandidateName(String name) {
        this.candidateName = name;
    }

    /**
     * Returns the name of the candidate associated with this RoundResult object.
     * @return the name of the candidate
     */
    public String getCandidateName() {
        return candidateName;
    }

    /**
     * Sets the number of votes received by the candidate associated with this RoundResult object.
     * @param votesReceived the number of votes received
     */
    public void setVotesReceived(int votesReceived) {
        this.votesReceived = votesReceived;
    }

    /**
     * Returns the number of votes received by the candidate associated with this RoundResult object.
     * @return the number of votes received
     */
    public int getVotesReceived() {
        return votesReceived;
    }

    /**
     * Sets the number of votes added or subtracted from the candidate associated with this RoundResult object.
     * @param votesAddedOrSubtracted the number of votes added or subtracted
     */
    public void setVotesAddedOrSubtracted(int votesAddedOrSubtracted) {
        this.votesAddedOrSubtracted = votesAddedOrSubtracted;
    }

    /**
     * Returns the number of votes added or subtracted from the candidate associated with this RoundResult object.
     * @return the number of votes added or subtracted
     */
    public int getVotesAddedOrSubtracted() {
        return votesAddedOrSubtracted;
    }

    /**
     * Sets the total number of candidates in the election associated with this RoundResult object.
     * @param numCandidates the total number of candidates
     */
    public void setNumCandidates(int numCandidates) {
        this.numCandidates = numCandidates;
    }

    /**
     * Returns the total number of candidates in the election associated with this RoundResult object.
     * @return the total number of candidates
     */
    public int getNumCandidates() {
        return numCandidates;
    }

    /**
     * Sets the total number of ballots in the election associated with this RoundResult object.
     * @param numBallots the total number of ballots
     */
    public void setNumBallots(int numBallots) {
        this.numBallots = numBallots;
    }

    /**
     * Returns the total number of ballots in the election associated with this RoundResult object.
     * @return the total number of ballots
    */
    public int getNumBallots() {
        return numBallots;
    }

    /**
     * Sets the name of the candidate who was dropped from the election.
    * @param droppedCandidate the name of the candidate who was dropped
    */
    public void setDroppedCandidate(String droppedCandidate) {
        this.droppedCandidate = droppedCandidate;
    }

    /**
     * Returns the name of the candidate who was dropped from the election.
     * @return the name of the candidate who was dropped
     */
    public String getDroppedCandidate() {
        return droppedCandidate;
    }

    /**
     * Sets the name of the winning candidate.
     * @param winner the name of the winning candidate
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }

    /**
     * Returns the name of the winning candidate.
     * @return the name of the winning candidate
     */
    public String getWinner() {
        return winner;
    }
    
    /**
     * Adds the number of votes received by a candidate to the candidate results map.
     * @param candidateName the name of the candidate
     * @param numVotes the number of votes received by the candidate
     */
    public void addCandidateResult(String candidateName, int numVotes) {
        this.candidateResults.put(candidateName, numVotes);
    }

    /**
     * Returns a map containing the number of votes received by each candidate in this round.
     * @return a map containing the number of votes received by each candidate
     */
    public Map<String, Integer> getCandidateResults() {
        return candidateResults;
    }
}