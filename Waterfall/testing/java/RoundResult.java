import java.util.Map;
import java.util.HashMap;

public class RoundResult {
    private String candidateName;
    private int votesReceived;
    private int votesAddedOrSubtracted;
    private int numCandidates;
    private int numBallots;
    private String droppedCandidate;
    private String winner;
    private Map<String, Integer> candidateResults;

    public RoundResult(String candidateName, int votesReceived, int votesAddedOrSubtracted) {
        this.candidateName = candidateName;
        this.votesReceived = votesReceived;
        this.votesAddedOrSubtracted = votesAddedOrSubtracted;
        this.candidateResults = new HashMap<>();
    }

    public String getCandidateName() {
        return candidateName;
    }

    public int getVotesReceived() {
        return votesReceived;
    }

    public int getVotesAddedOrSubtracted() {
        return votesAddedOrSubtracted;
    }

    public void setVotesReceived(int votesReceived) {
        this.votesReceived = votesReceived;
    }

    public void setVotesAddedOrSubtracted(int votesAddedOrSubtracted) {
        this.votesAddedOrSubtracted = votesAddedOrSubtracted;
    }

    public int getNumCandidates() {
        return numCandidates;
    }

    public void setNumCandidates(int numCandidates) {
        this.numCandidates = numCandidates;
    }

    public int getNumBallots() {
        return numBallots;
    }

    public void setNumBallots(int numBallots) {
        this.numBallots = numBallots;
    }

    public String getDroppedCandidate() {
        return droppedCandidate;
    }

    public void setDroppedCandidate(String droppedCandidate) {
        this.droppedCandidate = droppedCandidate;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void addCandidateResult(String candidateName, int numVotes) {
        candidateResults.put(candidateName, numVotes);
    }

    public Map<String, Integer> getCandidateResults() {
        return candidateResults;
    }
}
