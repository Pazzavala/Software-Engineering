import java.util.ArrayList;

public class InstantRunoffDisplay {
    public static void main(String[] args) {
        AuditFile auditFile = new AuditFile("IRTieTestFile1.txt", "3/20/2023");
        //AuditFile auditFile = new AuditFile("IRTestFile1.txt", "3/20/2023");
        auditFile.collectFileInfo();
        InstantRunoff runoff = new InstantRunoff(auditFile);
        Candidate winner = runoff.runElection();
        System.out.println("Winner: " + winner.getName());
        ArrayList<RoundResult> results = runoff.getRoundResults();
        runoff.printResults();
    }
}
