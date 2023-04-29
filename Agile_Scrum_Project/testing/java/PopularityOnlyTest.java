import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PopularityOnlyTest {

    @Test
    void runElectionPOClearWinner1() {
        AuditFile auditFile = new AuditFile("POtestFile1.csv", "3/24/2023");
        auditFile.collectFileInfo();
        PopularityOnly po = new PopularityOnly(auditFile);
        Candidate winner = po.runElection();
        assertEquals("Pike", winner.getName());
    }

    @Test
    void runElectionPOClearWinner2() {
        AuditFile auditFile = new AuditFile("POtestFile2.csv", "3/24/2023");
        auditFile.collectFileInfo();
        PopularityOnly po = new PopularityOnly(auditFile);
        Candidate winner = po.runElection();
        assertEquals("Borg", winner.getName());
    }

    @Test
    void runElectionPOTieWinner() {
        AuditFile auditFile = new AuditFile("POtestFile3.csv", "3/24/2023");
        auditFile.collectFileInfo();
        PopularityOnly po = new PopularityOnly(auditFile);
        Candidate winner = po.runElection();
        String name;

        if (winner.getName().equals("Foster"))
            name = "Foster";
        else
            name = "Borg";

        assertEquals(name, winner.getName());
    }

    @Test
    public void testDistributeBallot() {
        AuditFile auditFile = new AuditFile("POtestFile4.csv", "3/24/2023");
        auditFile.collectFileInfo();
        PopularityOnly po = new PopularityOnly(auditFile);
        Candidate[] candidates = auditFile.getCandidates();

        Ballot[] beforeDistribution = candidates[0].getBallots();
        po.distributeBallot(auditFile.getBallots()[0]);
        Ballot[] afterDistribution = candidates[0].getBallots();

        assertNotEquals(beforeDistribution, afterDistribution);
    }

    @Test
    void coinFlip() {
        AuditFile auditFile = new AuditFile("POtestFile4.csv", "3/24/2023");
        auditFile.collectFileInfo();
        PopularityOnly po = new PopularityOnly(auditFile);

        ArrayList<Candidate> candidates = new ArrayList<>(Arrays.asList(auditFile.getCandidates()));

        int count = 0;
        for(int i = 0; i < 100; i++) {
            Candidate can = po.coinFlip(candidates);

            if(can.getName().equals("Pike")) {
                count++;
            }
        }

        assertNotEquals(100, count);
    }
}
