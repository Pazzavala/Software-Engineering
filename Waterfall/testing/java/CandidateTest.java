import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CandidateTest {

    @Test
    public void testGettersAndSetters() {
        Party party = new Party("Test Party");
        Candidate candidate = new Candidate("Test Candidate", party);
        candidate.setCandidateID(123);
        candidate.setNumVotes(456);
        assertEquals("Test Candidate", candidate.getName());
        assertEquals(party, candidate.getParty());
        assertEquals(123, candidate.getCandidateID());
        assertEquals(456, candidate.getNumVotes());
    }

    @Test
    public void testPassBallot() {
        Party party = new Party("Test Party");
        Candidate candidate = new Candidate("Test Candidate", party);
        Ballot ballot1 = new Ballot(new ArrayList<Integer>(Arrays.asList(1, 2, 3)));
        Ballot ballot2 = new Ballot(new ArrayList<Integer>(Arrays.asList(4, 5, 6)));
        candidate.passBallot(ballot1);
        candidate.passBallot(ballot2);
        Ballot[] ballots = candidate.getBallots();
        assertEquals(2, ballots.length);
        assertIterableEquals(Arrays.asList(1, 2, 3), ballots[0].getVotes());
        assertIterableEquals(Arrays.asList(4, 5, 6), ballots[1].getVotes());
}
}