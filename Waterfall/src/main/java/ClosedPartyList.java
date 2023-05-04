import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;



/** Class that represents an Closed Party List election.
 * @author Ethan Johnson
 * @version 1.0
 * @since 1.0
 */

public class ClosedPartyList {
    private AuditFile electionFile;
    private Ballot[] ballots;
    private Candidate[] candidates;
    private Party[] parties;
    private int numBallots;
    private int numCandidates;
    private int numParties;
    private int numVotes;
    private int numSeats;

    /** Constructor creates a ClosedPartyList object and initializes
     * its ballots, parties, and candidates.
     * @param electionFile this is where we gather all the information of election files.
     */
    public ClosedPartyList(AuditFile electionFile) {
        this.ballots = electionFile.getBallots();
        this.candidates = electionFile.getCandidates();
        this.parties = electionFile.getParties();
        this.numBallots = electionFile.getnBallots();
        this.numCandidates = electionFile.getnCandidates();
        this.numParties = electionFile.getnParties();
        this.numVotes = electionFile.getnBallots();
        this.numSeats = electionFile.getnSeats();
    }

    /** This method runs Closed party list algorithm
     * @return candidate object who is the winner
     */
    public ArrayList<Party> runElection() {
        int[] pBallotCount = new int[numParties];       //array with each index associated to party to store votes for parties
        int index = 0;

        for(int i = 0; i < numBallots; i++) {   //for loop to search through ballots and retrieve votes
            index = ballots[i].indexOf(1);      //get index of ballot that is 1 -- index of vote
            pBallotCount[index]++;              //increase that index's (parties) votes by 1
        }

        for(int i = 0; i < numParties; i++) {           //sets party object in parties[] at index i
            parties[i].setNumPVotes(pBallotCount[i]);       //to have amount of votes from pBallotCount[] at index i
        }

        parties = assignSeats(parties);     //call function to assign seats to parties based off votes

        ArrayList<Party> winners = new ArrayList<Party>();      //create arraylist for party winners
        for(int i = 0; i < parties.length; i++) {           //loop that goes through all parties and adds them to
            if(parties[i].numPSeats > 0) {                  //winners arraylist if they won a seat
                winners.add(parties[i]);
            }
        }

        return winners;
    }

    /** This method assigns seats to parties based off votes
     * @param parties is the array of Party objects
     * @return Party array with updated seats 
     */
    public Party[] assignSeats(Party[] parties) {
        int quota = numVotes/numSeats;

        int curPvotes = 0;
        for(int i = 0; i < numParties; i++) {

            if(numSeats < 1) {      //check if all seats have been filled before continuing in loop
                if(i == 0){     //check if no seats left and we have not started the loop, this means there was an error in the ballot file
                    System.out.println("There was an Error in the ballots, the number of seats given to us as 0");
                }
                break;
            }
            curPvotes = parties[i].getNumPVotes();
            parties[i].setNumPSeats(Math.floorDiv(curPvotes,quota));    //first allocation of seats to party based off quota
            parties[i].setRemainingVotes(curPvotes%quota);          //sets remaining votes for second allocation of seats
            numSeats = numSeats - Math.floorDiv(curPvotes,quota);   //adjust number of remaining seats after each assignment
        }

        if(numSeats > 0) {              //if there are still seats remianing, 
            parties = remainingSeats(parties, numSeats);    //call function remainingSeats for second allocation of seats
        }

        return parties;
    }

    /** This method is for the second allocation of seats if there are seats remaining
     * @param parties is the array of Party objects
     * @param numSeats is the number of seats still to be filled
     * @return Party array with updated seats 
     */
    public Party[] remainingSeats(Party[] parties, int numSeats) {
        Party[] sortedParties = Arrays.copyOf(parties, parties.length);     //creates copy of parties array to be sorted

        Arrays.sort(sortedParties, new Comparator<Party>() {
            public int compare(Party p1, Party p2) {
                return Integer.compare(p2.getRemainingVotes(), p1.getRemainingVotes());     //sorts array by descending order of remaining votes
            }
        });

        for(int i = 0; i < numSeats; i++) {
            if (i+1 < numParties && sortedParties[i].getNumPVotes() == parties[i+1].getNumPVotes() && numSeats == 1) {
                i = coinFlip(i, i+1);       //BUG: checks for a tie but only would work for a tie between 2 parties--still doesn't work--don't know why
            }
            sortedParties[i].setNumPSeats(sortedParties[i].getNumPSeats() + 1);     //second allocation of seats

            numSeats = numSeats - 1;    //adjust number of seats after allocation

            if(i == numParties - 1) {       //if there are more seats remaining than parties, this will restart the loop
                i = 0;
            }
        }
        return parties;     //returns
    }

    /**
     * This method is used when there is a tie bewtween parties. It has a bug and currently is not working as intedned/\.
     * @param x A party index
     * @param y The next party index
     * @return Random party out of the two party indices
     */
    public int coinFlip(int x, int y) {
        Random random = new Random();
        int result = -1;
        for (int i = 0; i < 1000; i++) {
            result = random.nextBoolean() ? x : y;  //runs randomizer 1000 times to truly make it random 
        }
        return result;
    }

}