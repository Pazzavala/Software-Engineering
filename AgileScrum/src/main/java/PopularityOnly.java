import java.util.ArrayList;
import java.util.Random;

/** Class that represents a Popularity Only election.
 * @author Maria Zavala
 * @version 1.0
 * @since 1.0
 */
public class PopularityOnly {
    private final Ballot[] ballots;
    private final Candidate[] candidates;

    /** Constructor creates a PopularityOnly object and initializes
     * its ballots and candidates.
     * @param electionFile this is where we gather all the information of election files.
     */
    public PopularityOnly(AuditFile electionFile) {
        this.candidates = electionFile.getCandidates();
        this.ballots = electionFile.getBallots();
    }

    /** This method runs the Popularity Only Algorithm.
     * @return the candidate object that has been determined as winner.
     */
    public Candidate runElection() {
        ArrayList<Candidate> tiedWinners = new ArrayList<>();
        Candidate winner;
        boolean winnerTie = false;

        // Distribute all ballots
        for (Ballot ballot : ballots)
            distributeBallot(ballot);

        int maxVotes = 0, numVotes;
        // Find candidate with max number of votes
        for (Candidate candidate: candidates) {
            numVotes = candidate.getNumVotes();
            // Set winner if it has the majority of votes
            if (numVotes > maxVotes) {
                maxVotes = numVotes;
                tiedWinners.clear();
                tiedWinners.add(candidate);
                winnerTie = false;
            } // Tied candidates for winner
            else if (numVotes == maxVotes) {
                winnerTie = true;
                tiedWinners.add(candidate);
            }
        }
        // Coin flip if there is a tie
        if (winnerTie)
            winner = coinFlip(tiedWinners);
        else
            winner = tiedWinners.get(0);

        return winner;
    }

    /** Method that redistributes ballot according to their choice and by round.
     * @param ballot is the ballot which will be redistributed.
     */
    public void distributeBallot(Ballot ballot) {
        int idx = ballot.indexOf(1);
        candidates[idx].passBallot(ballot);
    }

    /** Generates a random number to fairly pick a candidate at random.
     * @param candidates the list of candidates to pick from.
     * @return the candidate that is picked by the random number.
     */
    public Candidate coinFlip(ArrayList<Candidate> candidates) {
        Random random = new Random();
        int amount = candidates.size();
        int rand = random.nextInt(amount);
        return candidates.get(rand);
    }
}