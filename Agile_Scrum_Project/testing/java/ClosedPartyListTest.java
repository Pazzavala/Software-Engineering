import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class ClosedPartyListTest {

    @Test
    void runElectionCPLClearWinner1() {
        AuditFile auditFile = new AuditFile("CPLTestFile1.csv", "3/24/2023");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        ArrayList<Party> winners = cpl.runElection();
        assertEquals("Reform", winners.get(0).getpName());
    }

    @Test
    void runElectionCPLClearWinner2() {
        AuditFile auditFile = new AuditFile("CPLTestFile2.csv", "3/24/2023");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        ArrayList<Party> pWinners = cpl.runElection();

        // Testing Party names match
        String[] expectedPWinners = {"Republican", "Reform", "Independent"};

        int i = 0;
        for (Party pWinner : pWinners) {
            assertEquals(expectedPWinners[i++], pWinner.getpName());
        }

        // Testing Candidate Names Match
        List<String> expectedCWinner = new ArrayList<>(List.of("Green", "McClure", "Peters"));
        List<String> actualCWinners = new ArrayList<>();

        for (i = 0; i < pWinners.size(); i++) {
            List<String> temp = pWinners.get(i).getWinners();
            actualCWinners.addAll(temp);
        }

        assertEquals(expectedCWinner, actualCWinners);
    }

    @Test
    void runElectionCPLClearWinner3() { // Rand choice works
        AuditFile auditFile = new AuditFile("CPLTestFile3.csv", "3/24/2023");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        ArrayList<Party> winners = cpl.runElection();

        assertEquals(2, winners.size());

        assertEquals("Democratic", winners.get(0).getpName());
        // assert that at least New wave, Reform, Green, Independent get picked randomly
        String randParty = winners.get(1).getpName();
        if (randParty.equals("New Wave")) {
            assertEquals("New Wave", randParty);
        } else if (randParty.equals("Reform")) {
            assertEquals("Reform", randParty);
        } else if (randParty.equals("Green")) {
            assertEquals("Green", randParty);
        } else {
            assertEquals("Independent", randParty);
        }
    }

    @Test
    void ClearWinners4() {
        AuditFile auditFile = new AuditFile("CPLTestFile4.csv", "01/01/2000");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        ArrayList<Party> pWinners = cpl.runElection();

        // Testing Party names match
        String[] expectedPWinners = {"Democratic", "Republican"};
        int i = 0;
        for (Party pWinner : pWinners) {
            assertEquals(expectedPWinners[i++], pWinner.getpName());
        }

        // Testing Candidate Names Match
        List<String> expectedCWinner = new ArrayList<>(List.of("Foster", "Green", "Xu"));
        List<String> actualCWinners = new ArrayList<>();

        for (i = 0; i < pWinners.size(); i++) {
            List<String> temp = pWinners.get(i).getWinners();
            actualCWinners.addAll(temp);
        }

        assertEquals(expectedCWinner, actualCWinners);
    }

    @Test
    void RemainingSeatsWinners5() {
        AuditFile auditFile = new AuditFile("CPLTestFile5.txt", "01/01/2000");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        ArrayList<Party> pWinners = cpl.runElection();

        // Testing Party names match
        String[] expectedPWinners = {"Democratic", "Republican"};
        int i = 0;
        for (Party pWinner : pWinners) {
            assertEquals(expectedPWinners[i++], pWinner.getpName());
        }

        // Testing Candidate Names Match
        List<String> expectedCWinner = new ArrayList<>(List.of("Foster", "Green", "Xu"));
        List<String> actualCWinners = new ArrayList<>();

        for (i = 0; i < pWinners.size(); i++) {
            List<String> temp = pWinners.get(i).getWinners();
            actualCWinners.addAll(temp);
        }

        assertEquals(expectedCWinner, actualCWinners);
    }

    @Test
    void CoinFlipWinners6() {
        AuditFile auditFile = new AuditFile("CPLTestFile6.csv", "01/01/2000");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        ArrayList<Party> pWinners = cpl.runElection();

        // Testing Party names match
        String[] expectedPWinners = {"Democratic", "Republican"};
        int i = 0;
        for (Party pWinner : pWinners) {
            assertEquals(expectedPWinners[i++], pWinner.getpName());
        }

        // Testing Candidate Names Match
        List<String> expectedCWinner;
        if (pWinners.get(0).getWinners().size() == 2) {
            expectedCWinner = new ArrayList<>(List.of("Foster", "Volz", "Green"));
        } else {
            expectedCWinner = new ArrayList<>(List.of("Foster", "Green", "Xu"));
        }

        List<String> actualCWinners = new ArrayList<>();
        for (i = 0; i < pWinners.size(); i++) {
            List<String> temp = pWinners.get(i).getWinners();
            actualCWinners.addAll(temp);
        }

        assertEquals(expectedCWinner, actualCWinners);
    }

    @Test
    void moreSeatsThanCand() {
        AuditFile auditFile = new AuditFile("CPLTestFile7.csv", "01/01/2000");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        ArrayList<Party> pWinners = cpl.runElection();

        // Testing Party names match
        String[] expectedPWinners = {"Democratic", "Republican"};
        int i = 0;
        for (Party pWinner : pWinners) {
            assertEquals(expectedPWinners[i++], pWinner.getpName());
        }

        // Testing Candidate Names Match
        List<String> expectedCWinner = new ArrayList<>(List.of("Foster", "Volz", "Green", "Xu"));
        List<String> actualCWinners = new ArrayList<>();
        for (i = 0; i < pWinners.size(); i++) {
            List<String> temp = pWinners.get(i).getWinners();
            actualCWinners.addAll(temp);
        }

        assertEquals(expectedCWinner, actualCWinners);
    }

    @Test
    void tiedWinners8() {
        AuditFile auditFile = new AuditFile("CPLTestFile8.csv", "3/24/2023");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        ArrayList<Party> pWinners = cpl.runElection();


    }

    @Test
    void assignSeats() {
        AuditFile auditFile = new AuditFile("CPLTestFile1.csv", "3/24/2023");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        Party[] parties = auditFile.getParties();

        for (Party party: parties) {
            cpl.assignSeats(party, 1);
            assertEquals(1, party.getNumPSeats());
        }
    }

    // Make Tests for calcSeats and calcRemainderSeats

    @Test
    void coinFlip() {
        AuditFile auditFile = new AuditFile("CPLTestFile2.csv", "3/24/2023");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);

        ArrayList<Party> parties = cpl.runElection();

        int count = 0;
        for(int i = 0; i < 100; i++) {
            Party party = cpl.coinFlip(parties);

            if(party.getpName().equals("Reform")) {
                count++;
            }
        }

        assertNotEquals(100, count);
    }
}