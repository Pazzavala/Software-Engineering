import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PartyTest {

    @Test
    public void testGettersAndSetters() {
        Party party = new Party("Test Party");
        Candidate candidate1 = new Candidate("Test Candidate 1", party);
        Candidate candidate2 = new Candidate("Test Candidate 2", party);
        Candidate[] candidates = {candidate1, candidate2};
        Ballot ballot1 = new Ballot(new ArrayList<Integer>(Arrays.asList(1, 2, 3)));
        Ballot ballot2 = new Ballot(new ArrayList<Integer>(Arrays.asList(4, 5, 6)));  
        Ballot[] ballots = {ballot1, ballot2};
        party.setCandidates(candidates);
        party.setpBallots(ballots);
        party.setNumPVotes(100);
        party.setNumPSeats(2);
        party.setRemainingVotes(50);
        assertEquals("Test Party", party.getpName());
        assertArrayEquals(candidates, party.getCandidates());
        assertArrayEquals(ballots, party.getpBallots());
        assertEquals(100, party.getNumPVotes());
        assertEquals(2, party.getNumPSeats());
        assertEquals(50, party.getRemainingVotes());
    }
}