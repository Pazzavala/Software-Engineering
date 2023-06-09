import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class is responsible for storing all information from the election needed for the audit file and the election classes.
 * @author Camden Fessler
 * @version 1.0
 * @since 1.0
 */
public class AuditFile {
    private String name;
    private File auditFile;
    private String electionType;
    private String electionDate;
    private Ballot[] ballots;
    private Candidate[] candidates;
    private Party[] parties;
    private int numBallots;
    private int numCandidates;
    private int numParties;
    private int numSeats;
    private ArrayList<Candidate> droppedCandidates;
    private ArrayList<Candidate> winners;

    /**
     * This constructor creates an instance of an AuditFile given the file name and the election date.
     * @param name
     * @param date
     */
    public AuditFile(String name, String date) {
        this.name = name;
        auditFile = null;
        electionType = "";
        electionDate = date;
        candidates = null;
        ballots = null;
        parties = null;
        numBallots = -1;
        numSeats = -1;
        droppedCandidates = null;
        winners = null;
    }

    /**
     * This function is responsible for reading through the election file and processing all the information from it.
     * It will store all information in the variables from this class for later use by the election classes and the audit file.
     */
    public void collectFileInfo() {
        try {
//            name = System.getProperty("user.dir") + "/Project1/testing/ElectionFiles/" + name; //ONLY WORKS IN ELECTION MAIN
//            name = System.getProperty("user.dir") + "/testing/CPLtestFiles/" + name; //ONLY WORKS IN test\java FILES FOR CPL
            name = System.getProperty("user.dir") + "/testing/IRtestFiles/" + name; //ONLY WORKS IN test\java FILES FOR IR
//            name = System.getProperty("user.dir") + "/testing/" + name; //ONLY WORKS IN AuditFileTest.java
            System.out.println(name);
            Scanner s = new Scanner(new File(name));
            int header = 0;
            int ballotIndex = 0;
            while (s.hasNextLine()) { //read file until last line reached
                String line = s.nextLine();
                if (header == 0) { //collecting header info
                    if (line.equals("IR")) { //IR election
                        //get election type
                        electionType = line;

                        //get number of candidates
                        line = s.nextLine();
                        numCandidates = Integer.parseInt(line);
                        candidates = new Candidate[numCandidates];

                        //get candidates
                        line = s.nextLine();
                        String[] l = line.split(",");
                        for (int j = 0; j < l.length; j++) {
                            String[] c = l[j].split(" ");
                            if (j == 0) {
                                Party p = new Party(c[1]);
                                Candidate cand = new Candidate(c[0], p);
                                candidates[j] = cand;
                            }
                            else {
                                Party p = new Party(c[2]);
                                Candidate cand = new Candidate(c[1], p);
                                candidates[j] = cand;
                            }
                        }

                        //get nuber of ballots
                        line = s.nextLine();
                        numBallots = Integer.parseInt(line);

                        ballots = new Ballot[numBallots];
                        header++;
                        System.out.println("electionType: " + electionType
                        + "  numCandidates: " + numCandidates + "  candidates: ");
                        for (int i = 0; i < candidates.length; i++) {
                            System.out.print(candidates[i].getName() + "," + candidates[i].getParty().getpName() + " ");
                        }
                        System.out.println("\nnumBallots: " + numBallots);
                        continue;
                    }
                    else if (line.equals("CPL")) { //CPL election
                        //get election type
                        electionType = line;

                        //get number of parties
                        line = s.nextLine();
                        numParties = Integer.parseInt(line);
                        parties = new Party[numParties];

                        //get parties
                        line = s.nextLine();
                        String[] l = line.split(", ");
                        for (int j = 0; j < l.length; j++) {
                            Party p = new Party(l[j]);
                            parties[j] = p;
                        }

                        //get candidates
                        int cn = 0;
                        for (int i = 0; i < numParties; i++) {
                            line = s.nextLine();
                            String[] cand = line.split(",", -1);
                            Candidate[] cList = new Candidate[cand.length];
                            for (int j = 0; j < cand.length; j++) {
                                Candidate c = new Candidate(cand[j], parties[i]);
                                cn++;
                                cList[j] = c;
                            }
                            parties[i].setCandidates(cList);
                        }
                        numCandidates = cn;

                        //get number of seats
                        line = s.nextLine();
                        numSeats = Integer.parseInt(line);

                        //get number of ballots
                        line = s.nextLine();
                        numBallots = Integer.parseInt(line);

                        ballots = new Ballot[numBallots];
                        header++;

                        System.out.println("electionType: " + electionType
                        + "  numParties: " + numParties + "  parties: ");
                        for (int i = 0; i < parties.length; i++) {
                            System.out.print(parties[i].getpName() + ": ");
                            for (int j = 0; j < parties[i].getCandidates().length; j++) {
                                System.out.print(parties[i].getCandidates()[j].getName() + ", ");
                            }
                        }
                        System.out.println("\nnumSeats: " + numSeats + "  numBallots: " + numBallots);
                        continue;
                    }
                }

                //collecting ballots
                String[] values;
                if (electionType.equals("IR")) {values = new String[numCandidates];}
                else {values = new String[numParties];}

                values = line.split(",", -1);
                int i;
                for (i = 0; i < values.length; i++) {
                    if (values[i].equals("")) {values[i] = "0";}
                }

                ArrayList<Integer> b = new ArrayList<Integer>();
                for (i = 0; i < values.length; i++) {
                    b.add(Integer.parseInt(values[i]));
                }

                ballots[ballotIndex] = new Ballot(b);

                for (int c = 0; c < values.length; c++) {
                    System.out.print(b.get(c) + ", ");
                }
                System.out.println();
                ballotIndex++;
            }
            s.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File does not exist");
        }
    }

    
    /**
     * Return election type.
     * @return this is the election type variable
     */
    public String getElectionType() {return electionType;}
    /**
     * Set election type.
     * @param electionType the replacement election type
     */
    public void setElectionType(String electionType) {this.electionType = electionType;}
    /**
     * Return election date.
     * @return the election date variable
     */
    public String getDate() {return electionDate;}
    /**
     * Set election date.
     * @param electionDate the replacement date
     */
    public void setDate(String electionDate) {this.electionDate = electionDate;}
    /**
     * Return ballot list.
     * @return ballot list variable
     */
    public Ballot[] getBallots() {return ballots;}
    /**
     * Set ballot list.
     * @param ballots replacement ballot
     */
    public void setBallots(Ballot[] ballots) {this.ballots = ballots;}
    /**
     * Return candidate list.
     * @return candidate list variable.
     */
    public Candidate[] getCandidates() {return candidates;}
    /**
     * Set candidates.
     * @param candidates replacement candidate list variable.
     */
    public void setCandidates(Candidate[] candidates) {this.candidates = candidates;}
    /**
     * Return party list.
     * @return party list variable
     */
    public Party[] getParties() {return parties;}
    /**
     * Set party list.
     * @param parties replacement party list variable
     */
    public void setParties(Party[] parties) {this.parties = parties;}
    /**
     * Return number of ballots.
     * @return number of ballots variable
     */
    public int getnBallots() {return numBallots;}
    /**
     * Return number of candidates.
     * @return number of candidates variable
     */
    public int getnCandidates() {return numCandidates;}
    /**
     * Return number of parties.
     * @return number of parties variable
     */
    public int getnParties() {return numParties;}
    /**
     * Set number of ballots.
     * @param numBallots replacement number of ballots variable
     */
    public void setnBallots(int numBallots) {this.numBallots = numBallots;}
    /**
     * Return number of seats.
     * @return number of seats variable
     */
    public int getnSeats() {return numSeats;}
    /**
     * Set number of seats.
     * @param numSeats replacement number of seats
     */
    public void setnSeats(int numSeats) {this.numSeats = numSeats;}
    /**
     * Return dropped candidates.
     * @return dropped candidate list variable
     */
    public ArrayList<Candidate> getDCandidates() {return droppedCandidates;}
    /**
     * Set dropped candidates.
     * @param dc replacement dropped candidate list
     */
    public void setDroppedCandidates(ArrayList<Candidate> dc) {droppedCandidates = dc;}
    /**
     * Return winners.
     * @return election winners variable
     */
    public ArrayList<Candidate> getWinners() {return winners;}
    /**
     * Set winners.
     * @param winners replacement election winners list
     */
    public void setWinners(ArrayList<Candidate> winners) {this.winners = winners;}

}
