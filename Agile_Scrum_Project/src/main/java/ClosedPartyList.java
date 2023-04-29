import java.util.ArrayList;
import java.util.Random;

/** Class that represents a Closed Party List election.
 * @author Maria Zavala & Ethan Johnson
 * @version 2.0
 * @since 1.0
 */

public class ClosedPartyList {
    private final Ballot[] ballots;
    private final ArrayList<Party> validParties = new ArrayList<>();
    private final Party[] parties;
    private final int numParties;
    private final int numSeats;
    private final int quota;

    /** Constructor creates a ClosedPartyList object and initializes
     * its ballots, parties, and candidates.
     * @param electionFile this is where we gather all the information of election files.
     */
    public ClosedPartyList(AuditFile electionFile) {
        this.ballots = electionFile.getBallots();
        this.parties = electionFile.getParties();
        this.numParties = electionFile.getnParties();
        this.numSeats = electionFile.getnSeats();
        int numVotes = electionFile.getnBallots();
        this.quota = numVotes /numSeats;
    }

    /** This method runs Closed party list algorithm
     * @return candidate object who is the winner
     */
    public ArrayList<Party> runElection() {
        // Array to count number of votes for each party
        int[] pBallotCount = new int[numParties];
        // Save index where 1 is in ballot and increment each party's ballot count.
        int index;
        for (Ballot ballot: ballots) {
            index = ballot.indexOf(1);
            pBallotCount[index]++;
        }
        // Assign total number of votes to each party.
        index = 0;
        for (Party party: parties) {
            party.setNumPVotes(pBallotCount[index++]);
        }

        for (Party party: parties) {
            if (party.getNumPVotes() != 0) {
                validParties.add(party);
            }
        }
        // Subtract total number of assigned seats
        int totalSeatsRemaining = numSeats;
        for (Party party: parties) {
            totalSeatsRemaining -= calcSeats(party);
        }

        while (totalSeatsRemaining > 0) {
            totalSeatsRemaining -= calcRemainderSeats(validParties);
        }

        ArrayList<Party> winners = new ArrayList<>();

        for (Party party: parties) {
            if (party.getNumPSeats() > 0) {
                winners.add(party);
                party.setWinners();     // Sets names of candidates that get a seat within each party
            }
        }

        return winners;
    }

    public int calcSeats(Party party) {
        int numPSeats;

        party.setNumCandidates();
        int numVotes = party.getNumPVotes();
        numPSeats = Math.floorDiv(numVotes, quota);

        int numCandidates = party.getNumCandidates();
        if (numPSeats >= numCandidates) {
            numPSeats = numCandidates;
            validParties.remove(party);   // Remove party since all candidates have gotten a seat.
        }
        assignSeats(party, numPSeats);
        return numPSeats;
    }

    public int calcRemainderSeats(ArrayList<Party> vParties) {
        int numPSeats;
        ArrayList<Party> maxParties = new ArrayList<>();
        boolean tie = false;

        int maxSeats = 0;
        for (Party party: vParties) {
            party.setNumCandidates();
            int numVotes = party.getNumPVotes();
            int remainPSeats = numVotes % quota;
            int numCandidates = party.getNumCandidates();
            int numSeats = party.getNumPSeats();

            if (remainPSeats >= numCandidates - numSeats) {
                remainPSeats = numCandidates - numSeats;
            }

            if (remainPSeats > maxSeats) {
                maxSeats = remainPSeats;
                maxParties.clear();
                maxParties.add(party);
                tie = false;
            } else if (remainPSeats == maxSeats) {
                maxParties.add(party);
                tie = true;
            }
        }

        Party getSeats;
        if (tie) {
            getSeats = coinFlip(maxParties);
        } else {
            getSeats = maxParties.get(0);
        }

        numPSeats = 1;
        assignSeats(getSeats, numPSeats);     //second allocation of seats
        // Remove from valid candidates as it has already been
        // allocated another seat based on remaining seats
        validParties.remove(getSeats);

        return numPSeats;
    }

    /** This method assigns seats to parties based off votes
     * @param party is the party that will get seats assigned
     * @param numPSeats is the number of seats party gets
     */
    public void assignSeats(Party party, int numPSeats) {
        party.setNumPSeats(party.getNumPSeats() + numPSeats);
    }

    /** This method is used when there is a tie between parties
     * @param ties an array list of ints representing the indices of parties that are tied
     * @return Random int representing the party that won the coin flip
     */
    public Party coinFlip(ArrayList<Party> ties) {
        Random random = new Random();
        int index = 0;
        for (int i = 0; i < 1000; i++) {
            index = random.nextInt(ties.size());  //runs randomizer 1000 times to truly make it random
        }

        return ties.get(index);
    }
}