import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

public class BallotTest {
    
    @Test
    public void testGetVotes() {
        ArrayList<Integer> votes = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
        Ballot ballot = new Ballot(votes);
        assertEquals(votes, ballot.getVotes());
    }

    @Test
    public void testIndexOf() {
        ArrayList<Integer> votes = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
        Ballot ballot = new Ballot(votes);
        assertEquals(0, ballot.indexOf(1));
        assertEquals(1, ballot.indexOf(2));
        assertEquals(2, ballot.indexOf(3));
        assertEquals(-1, ballot.indexOf(4));
    }
}