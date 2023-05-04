import java.util.HashMap;
import java.util.Map;

/**
 * The RoundResult class represents the results of a single round of an election.
 * Each instance of this class contains information about the number of votes received by each candidate,
 * as well as the candidate who was dropped from the election (if any) and the winner of the election (if there is one).
 */
public class RoundResult {
    private String droppedCandidate;
    private final Map<String, Integer> candidateResults = new HashMap<>();

    /**
     * Creates a new RoundResult object with the specified round number.
     */
    public RoundResult() {
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
     * Adds the number of votes received by a candidate to the candidate results map.
     * @param candidateName the name of the candidate
     * @param numVotes the number of votes received by the candidate
     */
    public void setCandidateResult(String candidateName, int numVotes) {
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