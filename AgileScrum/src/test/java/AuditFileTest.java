import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class AuditFileTest {
    AuditFile IRauditFile = new AuditFile("IRTestFile.csv", "02/27/2002");
    AuditFile CPLauditFile = new AuditFile("CPLTestFile5.txt", "02/27/2002");

    @Test
    void collectFileInfoTest() {
        IRauditFile.collectFileInfo();
        CPLauditFile.collectFileInfo();
    }
    @Test
    void getElectionTypeTest() {
        IRauditFile.collectFileInfo();
        CPLauditFile.collectFileInfo();
        assertEquals("IR", IRauditFile.getElectionType());
        assertEquals("CPL", CPLauditFile.getElectionType());
    }
    @Test
    void getDateTest() {
        IRauditFile.collectFileInfo();
        assertEquals("02/27/2002", IRauditFile.getDate());
    }
    @Test
    void getBallotsTest() {
        assertNull(IRauditFile.getBallots());
        assertNull(CPLauditFile.getBallots());
    }
    @Test
    void getCandidatesTest() {
        assertNull(IRauditFile.getCandidates());
        IRauditFile.collectFileInfo();
        Candidate[] c = new Candidate[4];
        c[0] = new Candidate("Rosen", new Party("(D)"));
        c[1] = new Candidate("Kleinberg", new Party("(R)"));
        c[2] = new Candidate("Chou", new Party("(I)"));
        c[3] = new Candidate("Royce", new Party("(L)"));
        IRauditFile.setCandidates(c);
        assertEquals(c, IRauditFile.getCandidates());
    }
    @Test
    void getPartiesTest() {
        assertNull(CPLauditFile.getParties());
        CPLauditFile.collectFileInfo();
        Party[] p = new Party[4];
        p[0] = new Party("(D)");
        p[1] = new Party("(R)");
        p[2] = new Party("(I)");
        p[3] = new Party("(L)");
        CPLauditFile.setParties(p);
        assertEquals(p, CPLauditFile.getParties());
    }
    @Test
    void getnBallotsTest() {
        IRauditFile.collectFileInfo();
        CPLauditFile.collectFileInfo();
        assertEquals(18, IRauditFile.getnBallots());
        assertEquals(12, CPLauditFile.getnBallots());
    }
    @Test
    void getnCandidatesTest() {
        IRauditFile.collectFileInfo();
        assertEquals(4, IRauditFile.getnCandidates());
    }
    @Test
    void getnPartiesTest() {
        CPLauditFile.collectFileInfo();
        assertEquals(2, CPLauditFile.getnParties());
    }
    @Test
    void getnSeatsTest() {
        CPLauditFile.collectFileInfo();
        assertEquals(3, CPLauditFile.getnSeats());
    }
    @Test
    void getDroppedCandidatesTest() {
        assertNull(IRauditFile.getDCandidates());
        assertNull(CPLauditFile.getDCandidates());
    }
    @Test
    void getWinnersTest() {
        assertNull(IRauditFile.getWinners());
        assertNull(CPLauditFile.getWinners());
    }
    @Test
    void setElectionTypeTest() {
        IRauditFile.setElectionType("test");
        assertEquals("test", IRauditFile.getElectionType());
    }
    @Test
    void setDateTest() {
        IRauditFile.setDate("01/01/2000");
        assertEquals("01/01/2000", IRauditFile.getDate());
    }
    @Test
    void setBallotsTest() {
        Ballot[] b = new Ballot[0];
        ArrayList<Integer> t1 = new ArrayList<>();
        Ballot b1 = new Ballot(t1);
        IRauditFile.setBallots(b);
        CPLauditFile.setBallots(b);
        assertEquals(b, IRauditFile.getBallots());
        assertEquals(b, CPLauditFile.getBallots());
        b = new Ballot[1];
        b[0] = b1;
        IRauditFile.setBallots(b);
        CPLauditFile.setBallots(b);
        assertEquals(b, IRauditFile.getBallots());
        assertEquals(b, CPLauditFile.getBallots());
    }
    @Test
    void setCandidatesTest() {
        Candidate[] c = new Candidate[0];
        IRauditFile.setCandidates(c);
        assertEquals(c, IRauditFile.getCandidates());
    }
    @Test
    void setPartiesTest() {
        Party[] p = new Party[0];
        CPLauditFile.setParties(p);
        assertEquals(p, CPLauditFile.getParties());
    }
    @Test
    void setnBallotsTest() {
        Ballot[] b = new Ballot[0];
        IRauditFile.setBallots(b);
        CPLauditFile.setBallots(b);
        assertEquals(b, IRauditFile.getBallots());
        assertEquals(b, CPLauditFile.getBallots());
    }
    @Test
    void setnSeatsTest() {
        CPLauditFile.setnSeats(101);
        assertEquals(101, CPLauditFile.getnSeats());
    }
    @Test
    void setDroppedCandidatesTest() {
        ArrayList<Candidate> x = new ArrayList<>();
        IRauditFile.setDroppedCandidates(x);
        assertEquals(x, IRauditFile.getDCandidates());
    }
    @Test
    void setWinnersTest() {
        ArrayList<Candidate> w = new ArrayList<>();
        IRauditFile.setWinners(w);
        CPLauditFile.setWinners(w);
        assertEquals(w, IRauditFile.getWinners());
        assertEquals(w, CPLauditFile.getWinners());
    }
}
