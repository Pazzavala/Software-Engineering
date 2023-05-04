import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ClosedPartyListTest {

    @Test
    void runElectionCPLClearWinner1() {
        AuditFile auditFile = new AuditFile("CPLTestFile1.csv", "3/24/2023");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        ArrayList<Party> winners = cpl.runElection();
        assertEquals(" Reform", winners.get(0).getpName());
    }

    @Test
    void runElectionCPLClearWinner2() {
        AuditFile auditFile = new AuditFile("CPLTestFile2.csv", "3/24/2023");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        ArrayList<Party> winners = cpl.runElection();
        assertEquals(" Republican", winners.get(0).getpName());
        assertEquals(" Independent", winners.get(1).getpName());
//        assertEquals(" Reform", winners.get(2).getpName());
    }

    @Test
    void runElectionCPLClearWinner3() {
        AuditFile auditFile = new AuditFile("CPLTestFile3.csv", "3/24/2023");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        ArrayList<Party> winners = cpl.runElection();
        assertEquals("Democratic", winners.get(0).getpName());
//        assertEquals(" New Wave", winners.get(1).getpName());
//        String name;

//        if (winners.get(1).getpName().equals(" New Wave"))
//            name = " New Wave";
//        else
//            name = "Reform";

//        assertEquals(name, winners.get(1).getpName());
    }

    @Test
    void assignSeats() {
        AuditFile auditFile = new AuditFile("CPLTestFile1.csv", "3/24/2023");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);
        int numBallots = auditFile.getnBallots();
        Ballot[] ballots = auditFile.getBallots();
        int numParties = auditFile.getnParties();
        int[] pBallotCount = new int[numParties];
        Party[] parties = auditFile.getParties();

        int index = 0;

        for(int i = 0; i < numBallots; i++) {   //for loop to search through ballots and retrieve votes
            index = ballots[i].indexOf(1);      //get index of ballot that is 1 -- index of vote
            pBallotCount[index]++;              //increase that index's (parties) votes by 1
        }

        for(int i = 0; i < numParties; i++) {           //sets party object in parties[] at index i
            parties[i].setNumPVotes(pBallotCount[i]);       //to have amount of votes from pBallotCount[] at index i
        }

        Party[] results = cpl.assignSeats(parties);

        ArrayList<Party> winners = new ArrayList<Party>();      //create arraylist for party winners
        for(int i = 0; i < parties.length; i++) {           //loop that goes through all parties and adds them to
            if(parties[i].numPSeats > 0) {                  //winners arraylist if they won a seat
                winners.add(parties[i]);
            }
        }

        assertEquals(" Reform", winners.get(0).getpName());
    }

    @Test
    void remainingSeats() {
        AuditFile auditFile = new AuditFile("CPLTestFile2.csv", "3/24/2023");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);

        cpl.runElection();
        Party[] parties = auditFile.getParties();
        Party[] results = cpl.remainingSeats(parties, 1);

        assertEquals("Democratic", results[0].getpName());
        assertEquals(" Republican", results[1].getpName());
        assertEquals(" New Wave", results[2].getpName());
        assertEquals(" Reform", results[3].getpName());
        assertEquals(" Green", results[4].getpName());
        assertEquals(" Independent", results[5].getpName());
    }

    @Test
    void coinFlip() {
        AuditFile auditFile = new AuditFile("CPLTestFile1.csv", "3/24/2023");
        auditFile.collectFileInfo();
        ClosedPartyList cpl = new ClosedPartyList(auditFile);

        cpl.runElection();
        int index = 0;
        int count = 0;
        for(int i = 0; i < 100; i++) {
            index = cpl.coinFlip(1, 2);

            if(index == 1) {
                count++;
            }
        }

        assertNotEquals(100, count);
    }
}