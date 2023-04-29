import java.util.ArrayList;
import java.util.Random;

/** Class that represents an Instant Runoff election.
 * @author Maria Zavala
 * @version 1.0
 * @since 1.0
 */
public class InstantRunoff {
    private AuditFile electionFile;
    private ArrayList<Candidate> droppedCandidates = new ArrayList<Candidate>();
    private Ballot[] ballots = null;
    private Candidate[] candidates = null;
    private int numCandidates = 0;
    private int numBallots = 0;
    private Candidate winner;
    private int round = 0;

    /** Constructor creates a InstantRunoff object and initializes
     * its ballots and candidates.
     * @param electionFile this is where we gather all the information of election files.
     */
    public InstantRunoff(AuditFile electionFile) {
        this.electionFile = electionFile;
        this.numBallots = electionFile.getnBallots();
        this.numCandidates = electionFile.getnCandidates();
        this.candidates = electionFile.getCandidates();
        this.ballots = electionFile.getBallots();
    }

    /** This method runs the Instant Runoff Algorithm.
     * @return the candidate object that has been determined as winner.
     */
    public Candidate runElection() {

        ArrayList<Candidate> lowestCandidates = new ArrayList<Candidate>();
        ArrayList<Candidate> validCandidates = new ArrayList<>();
        Candidate lowestCandidate;

        boolean haveWinner = false;
        boolean winnerTie = false;
        boolean loserTie = false;

        ++round;
        // Distribute Votes round 1
        for (Ballot ballot : ballots) {
            distributeBallot(ballot, round);
        }
        int i = 0;
        for (Candidate candidate: candidates) {
            if (candidate.getNumVotes() != 0) {
                validCandidates.add(candidate);
            } else {
                droppedCandidates.add(candidate);
            }
            candidate.setCandidateID(i++);
        }

        for (Candidate candidate: validCandidates) {
            candidate.setRound(round);
            System.out.println(candidate.getName() + " " + candidate.getNumVotes());
        }

        do {
            int maxVotes = 0, numVotes = 0, minVotes = numBallots;

            for (Candidate candidate : validCandidates) {
                numVotes = candidate.getNumVotes(); // Must set amount of votes in pass ballot

//              System.out.println(candidate.getName() + " " + numVotes);   See the distribution of votes in each round

                if (numVotes > maxVotes) {
                    maxVotes = numVotes;
                    winner = candidate;
                    winnerTie = false;  // Reset to false since we have now found a candidate with max votes
                } else if (numVotes == maxVotes) { // Tie in voted candidates for winner
                    winnerTie = true;
                }

                if (numVotes < minVotes) {  // Only add the single lowest candidate found.
                    minVotes = numVotes;
                    if (!lowestCandidates.isEmpty()) // If empty simply add to candidates
                        lowestCandidates.clear();   // remove previous that was in there and add new lowest
                    lowestCandidates.add(candidate);
                    loserTie = false;               // Reset to false since we have now found a candidate with min votes
                } else if (numVotes == minVotes) {
                    loserTie = true;
                    lowestCandidates.add(candidate); // Does this work as in adding only lowest tied candidates
                }
            }

            // If there is a tie for a winner: Redistribute votes of the lowest candidate.
            if (winnerTie) {
                // If there is a tie between the lowest candidates to drop.
                if (loserTie) {
                    lowestCandidate = coinFlip(lowestCandidates);
                } else {
                    lowestCandidate = lowestCandidates.get(0);
                    System.out.println(lowestCandidate.getName());
                }
                if (lowestCandidate.getNumVotes() != 0) {
                    int candRound = lowestCandidate.getRound();
                    Candidate out = dropCandidate(lowestCandidate, ++candRound);
                    lowestCandidate.setRound(candRound);
                    droppedCandidates.add(out);
                }

                validCandidates.remove(lowestCandidate);
                lowestCandidates.remove(lowestCandidate);

            } else {
                haveWinner = true;
            }
            round++;
        } while (!haveWinner);

        electionFile.setDroppedCandidates(droppedCandidates);
        return winner;
    }

    /** Method that drops a candidate with the lowest amount of votes.
     * @param candidate the candidate object who is being dropped.
     * @return returns the candidate that has been dropped.
     */
    public Candidate dropCandidate(Candidate candidate, int round) {
        Ballot[] cBallots = candidate.getBallots();
        int ogRound = round;
        for (Ballot ballot: cBallots) {
            for (Candidate dc: droppedCandidates) {
                if (ballot.indexOf(round) == dc.getCandidateID()) {
                    round++;
                }
            }
            distributeBallot(ballot, round);
            round = ogRound;
        }

        return candidate;
    }

    /** Method that redistributes ballot according to their choice and by round.
     * @param ballot is the ballot which will be redistributed.
     * @param round the current round of vote distribution.
     */
    public void distributeBallot(Ballot ballot, int round) {
        int idx = ballot.indexOf(round);
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

    /**
     * Allows user of InstantRunoff to get the list of dropped candidates.
     * @return ArrayList of dropped candidates.
     */
    public ArrayList<Candidate> getDroppedCandidates() {
        return droppedCandidates;
    }
}