import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class InstantRunoffTest {

    @Test
    void runElectionClearWinner1() {
        AuditFile auditFile = new AuditFile("IRTestFile1.txt", "3/20/2023");
        auditFile.collectFileInfo();
        InstantRunoff ir = new InstantRunoff(auditFile);
        Candidate winner = ir.runElection();
        assertEquals("Rosen", winner.getName());
    }

    @Test
    void runElectionClearWinner2() {
        AuditFile auditFile = new AuditFile("IRTestFile2.txt", "3/20/2023");
        auditFile.collectFileInfo();
        InstantRunoff ir = new InstantRunoff(auditFile);
        Candidate winner = ir.runElection();
        assertEquals("Kleinberg", winner.getName());
    }

    @Test
    void runElectionClearWinner3() {
        AuditFile auditFile = new AuditFile("IRTestFile3.txt", "3/20/2023");
        auditFile.collectFileInfo();
        InstantRunoff ir = new InstantRunoff(auditFile);
        Candidate winner = ir.runElection();
        assertEquals("Royce", winner.getName());
    }

    // Many tied lowest candidates
    @Test
    void runElectionTieLowest1() {
        AuditFile auditFile = new AuditFile("IRTieTestFile3.txt", "3/20/2023");
        auditFile.collectFileInfo();
        InstantRunoff IR = new InstantRunoff(auditFile);
        Candidate winner = IR.runElection();
        assertEquals("Kleinberg", winner.getName());
    }

    @Test
    void runElectionTieLowest2() {
        AuditFile auditFile = new AuditFile("IRTieTestFile4.txt", "3/20/2023");
        auditFile.collectFileInfo();
        InstantRunoff IR = new InstantRunoff(auditFile);
        Candidate winner = IR.runElection();
        String name;

        if (winner.getName().equals("Kleinberg"))
            name = "Kleinberg";
        else
            name = "Rosen";

        assertEquals(name, winner.getName());
    }

    @Test
    void runElectionRedistributeWinner1() {
        AuditFile auditFile = new AuditFile("IRTieTestFile1.txt", "3/20/2023");
        auditFile.collectFileInfo();
        InstantRunoff IR = new InstantRunoff(auditFile);
        Candidate winner = IR.runElection();
        assertEquals("Kleinberg", winner.getName());
    }

    @Test
    void runElectionRedistributeWinner2() {
        AuditFile auditFile = new AuditFile("IRTieTestFile2.txt", "3/20/2023");
        auditFile.collectFileInfo();
        InstantRunoff IR = new InstantRunoff(auditFile);
        Candidate winner = IR.runElection();
        assertEquals("Chou", winner.getName());
    }

    @Test
    void runElectionRedistributeWinner3() {
        AuditFile auditFile = new AuditFile("IRTieTestFile6.txt", "3/20/2023");
        auditFile.collectFileInfo();
        InstantRunoff IR = new InstantRunoff(auditFile);
        Candidate winner = IR.runElection();
        assertEquals("Chou", winner.getName());
    }

    @Test
    void runElectionTieWinners1() {
        AuditFile auditFile = new AuditFile("IRTieTestFile5.txt", "3/20/2023");
        auditFile.collectFileInfo();
        InstantRunoff IR = new InstantRunoff(auditFile);
        Candidate winner = IR.runElection();

        assertNotEquals("Royce" , winner.getName());
        assertNotEquals("Rosen" , winner.getName());
    }

    @Test
    void dropCandidate1() {
        AuditFile auditFile = new AuditFile("IRTieTestFile1.txt", "3/23/2023");
        auditFile.collectFileInfo();
        InstantRunoff ir = new InstantRunoff(auditFile);

        ir.runElection();
        String droppedCandidates = ir.getDroppedCandidates().get(0).getName();

        assertEquals("Rosen", droppedCandidates);
    }

    @Test
    void distributeBallot1() {
        AuditFile auditFile = new AuditFile("CoinFlip.txt", "3/23/2023");
        auditFile.collectFileInfo();
        InstantRunoff ir = new InstantRunoff(auditFile);
        Candidate[] candidates = auditFile.getCandidates();

        Ballot[] beforeDistribution = candidates[0].getBallots();
        ir.distributeBallot(auditFile.getBallots()[0], 1);
        Ballot[] afterDistribution = candidates[0].getBallots();

        assertNotEquals(beforeDistribution, afterDistribution);
    }

    @Test
    void coinFlip1() {
        AuditFile auditFile = new AuditFile("CoinFlip.txt", "3/23/2023");
        auditFile.collectFileInfo();
        InstantRunoff ir = new InstantRunoff(auditFile);

        ArrayList<Candidate> candidatesList = new ArrayList<>(Arrays.asList(auditFile.getCandidates()));

        int count = 0;
        for (int i = 0; i < 100; i++) {
            Candidate candidate = ir.coinFlip(candidatesList);

            if (candidate.getName() == "Rosen") count++;
        }

        assertNotEquals(100, count);
    }

    @Test
    void getDroppedCandidates1() {
        AuditFile auditFile = new AuditFile("IRTieTestFile1.txt", "3/23/2023");
        auditFile.collectFileInfo();
        InstantRunoff ir = new InstantRunoff(auditFile);

        ir.runElection();
        String droppedCandidates = ir.getDroppedCandidates().get(0).getName();

        assertEquals("Rosen", droppedCandidates);
    }
}