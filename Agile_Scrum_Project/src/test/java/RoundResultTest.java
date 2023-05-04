import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RoundResultTest {

    @Test
    void gettersAndSetters() {
        RoundResult rr = new RoundResult();
        rr.setDroppedCandidate("Rosen");
        assertEquals("Rosen", rr.getDroppedCandidate());

        Map <String, Integer> expected = new HashMap<>();
        expected.put("Rosen", 40);
        rr.setCandidateResult("Rosen", 40);
        Map <String, Integer> actual = rr.getCandidateResults();

        assertEquals(expected, actual);
    }
}