import java.util.ArrayList;
import java.util.Random;
import java.util.Map;

/** Class that represents an Instant Runoff election.
 * @author Maria Zavala and Aaron Raines
 * @version 3.0
 * @since 2.0
 */
public class InstantRunoff {
    private final AuditFile electionFile;
    private final ArrayList<Candidate> droppedCandidates = new ArrayList<>();
    private final Ballot[] ballots;
    private final Candidate[] candidates;
    private final int numBallots;
    private Candidate winner;
    private int round = 0;
    private final ArrayList<RoundResult> roundResults = new ArrayList<>();

    /** Constructor creates a InstantRunoff object and initializes
     * its ballots and candidates.
     * @param electionFile this is where we gather all the information of election files.
     */
    public InstantRunoff(AuditFile electionFile) {
        this.electionFile = electionFile;
        this.numBallots = electionFile.getnBallots();
        this.candidates = electionFile.getCandidates();
        this.ballots = electionFile.getBallots();
    }

    /** This method runs the Instant Runoff Algorithm.
     * @return the candidate object that has been determined as winner.
     */
    public Candidate runElection() {

        ArrayList<Candidate> lowestCandidates = new ArrayList<>();
        ArrayList<Candidate> validCandidates = new ArrayList<>();
        Candidate lowestCandidate;
        boolean haveWinner = false;
        boolean winnerTie = false;
        boolean loserTie = false;

        // Round 1 Distribute Votes
        ++round;
        for (Ballot ballot : ballots)
            distributeBallot(ballot, round);
        // Creating a new roundResult
        RoundResult roundResult = new RoundResult();

        int i = 0;
        for (Candidate candidate: candidates) {
            if (candidate.getNumVotes() != 0) {
                validCandidates.add(candidate);
                candidate.setRound(round);
            } else {
                droppedCandidates.add(candidate);
                roundResult.setDroppedCandidate(candidate.getName());
            }
            roundResult.setCandidateResult(candidate.getName(), candidate.getNumVotes());
            candidate.setCandidateID(i++);
        }
        
        do {
            //adding to roundResult
            roundResult = new RoundResult();
            roundResults.add(roundResult);

            int maxVotes = 0, numVotes, minVotes = numBallots;

            for (Candidate candidate : validCandidates) {
                numVotes = candidate.getNumVotes();
                roundResult.setCandidateResult("Candidate " + (candidate.getCandidateID() + 1), numVotes);

                // Set winner if it has the majority of votes
                if (numVotes > maxVotes) {
                    maxVotes = numVotes;
                    winner = candidate;

                    // Check if candidate has received more than 50% of votes
                    winnerTie = maxVotes < (numBallots / 2);
                } // Tied candidates for winner
                else if (numVotes == maxVotes) {
                    winnerTie = true;
                }

                // Only add the single lowest candidate found.
                if (numVotes < minVotes) {
                    minVotes = numVotes;
                    lowestCandidates.clear();   // remove previous candidates
                    lowestCandidates.add(candidate);
                    loserTie = false;
                } //  Tied lowest voted candidates
                else if (numVotes == minVotes) {
                    lowestCandidates.add(candidate);
                    loserTie = true;
                }
            }

            // If there is a tie for a winner: Redistribute votes of the lowest candidate.
            if (winnerTie) {
                // If there is a tie between the lowest candidates to be dropped.
                if (loserTie) {
                    lowestCandidate = coinFlip(lowestCandidates);
                } else {
                    lowestCandidate = lowestCandidates.get(0);
                }
                if (lowestCandidate.getNumVotes() != 0) {
                    int candRound = lowestCandidate.getRound();
                    Candidate out = dropCandidate(lowestCandidate, ++candRound);
                    lowestCandidate.setRound(candRound);
                    droppedCandidates.add(out);
                    roundResult.setDroppedCandidate(out.getName());
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
     * @param round the current round that the algorithm is running.
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

    /** Allows user of InstantRunoff to get the list of dropped candidates.
     * @return ArrayList of dropped candidates.
     */
    public ArrayList<Candidate> getDroppedCandidates() {
        return droppedCandidates;
    }

    /** Prints the results of the Instant Runoff election to the console.
     * Displays the number of votes received by each candidate in each round, 
     * as well as the name of any candidates who have been dropped from the election.
     */
    public void printResults() {
        int numRounds = this.roundResults.size();
        int numCandidates = this.candidates.length;

        // Print header row
        System.out.printf("\n%-20s", " ");
        for (int i = 1; i <= numCandidates; i++) {
            System.out.printf("%-20s", "Candidate " + i);
        }

        System.out.printf("%-20s%n", "Dropped Candidate");

        System.out.printf("%-20s", "Name");
        for (Candidate candidate: candidates) {
            System.out.printf("%-20s", candidate.getName());
        }
        System.out.println();

        // Print results for each round
        for (int i = 0; i < numRounds; i++) {
            RoundResult roundResult = this.roundResults.get(i);
            System.out.printf("%-20s", "Round " + (i + 1));

            Map<String, Integer> candidateResults = roundResult.getCandidateResults(); 

            for (int j = 1; j <= numCandidates; j++) {
                String candidateName = "Candidate " + j;
                int votes = candidateResults.getOrDefault(candidateName, 0);
                int votesAddedOrSubtracted = 0;

                if (i > 0) {
                    RoundResult prevRoundResult = this.roundResults.get(i - 1);
                    Map<String, Integer> prevCandidateResults = prevRoundResult.getCandidateResults();
                    votesAddedOrSubtracted = candidateResults.getOrDefault(candidateName, 0) - prevCandidateResults.getOrDefault(candidateName, 0);
                }
                System.out.printf("%-20s", votes + " (" + (votesAddedOrSubtracted >= 0 ? "+" : "") + votesAddedOrSubtracted + ")");
            }

            String droppedCandidate = roundResult.getDroppedCandidate();
            System.out.printf("%-20s", droppedCandidate != null ? droppedCandidate : "");
            System.out.println();
        }

        // Print the Winner of Election
        System.out.println("______________________________");
        System.out.printf("%-20s", "Winner");
        System.out.printf(this.winner.getName());
        System.out.println();
        System.out.println();
    }
}