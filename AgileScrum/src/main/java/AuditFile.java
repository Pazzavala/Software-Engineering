import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class is responsible for storing all information from the election needed for the audit file and the election classes.
 * @author Camden Fessler
 * @version 1.0
 * @since 1.0
 */
public class AuditFile {
    private boolean runElection = false;
    private String name;
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
     * @param name Name of the audit file
     * @param date the date of creation
     */
    public AuditFile(String name, String date) {
        this.name = name;
        electionType = "";
        electionDate = date;
        candidates = null;
        ballots = null;
        parties = null;
        numCandidates = 0;
        numParties = 0;
        numBallots = 0;
        numSeats = 0;
        droppedCandidates = null;
        winners = null;
    }

    /**
     * This function is responsible for reading through the election file and processing all the information from it.
     * It will store all information in the variables from this class for later use by the election classes and the audit file.
     */
    public void collectFileInfo() {
        try {
            if (this.runElection) {
                name = System.getProperty("user.dir") + "/Project2/testing/" + name; //ONLY WORKS IN ELECTION MAIN
            } else {
                name = System.getProperty("user.dir") + "/testing/" + name; // WORKS for testing all PO/CPL/IR/AuditFile
            }

            System.out.println(name);
            Scanner s = new Scanner(new File(name));
            int header = 0;
            int ballotIndex = 0;
            while (s.hasNextLine()) { //read file until last line reached
                String line = s.nextLine();
                if (header == 0) { //collecting header info
                    electionType = line;
                    switch (line) {
                        case "IR": { //IR election
                            //get election type
//                            electionType = line;

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
                                } else {
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
                            for (Candidate candidate : candidates) {
                                System.out.print(candidate.getName() + "," + candidate.getParty().getpName() + " ");
                            }
                            System.out.println("\nnumBallots: " + numBallots);
                            continue;
                        }
                        case "CPL": { //CPL election
                            //get election type
//                            electionType = line;

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
                            for (Party party : parties) {
                                System.out.print(party.getpName() + ": ");
                                for (int j = 0; j < party.getCandidates().length; j++) {
                                    System.out.print(party.getCandidates()[j].getName() + ", ");
                                }
                            }
                            System.out.println("\nnumSeats: " + numSeats + "  numBallots: " + numBallots);
                            continue;
                        }
                        case "PO": {
                            //get number of candidates
//                            electionType = line;
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
                                } else {
                                    Party p = new Party(c[2]);
                                    Candidate cand = new Candidate(c[1], p);
                                    candidates[j] = cand;
                                }
                            }

                            //get number of ballots
                            line = s.nextLine();
                            numBallots = Integer.parseInt(line);
                            ballots = new Ballot[numBallots];
                            continue;
                        }
                    }
                }

                //collecting ballots
                String[] values;
                if (!electionType.equals("IR")) {
                    values = new String[numParties];
                }

                values = line.split(",", -1);
                int i;
                for (i = 0; i < values.length; i++) {
                    if (values[i].equals("")) {values[i] = "0";}
                }

                ArrayList<Integer> b = new ArrayList<>();
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

    public int collectMultipleFileInfo(ArrayList<String> inputNames) {
        try {
            String prefix = System.getProperty("user.dir") + "/Project2/testing/";

            int ballotIndex = 0;
            for (int i = 0; i < inputNames.size(); i++) {
                name = prefix + inputNames.get(i);
                File file = new File(name);
                if (!file.exists()) {        //check if file exists
                    System.out.println("File does not exist.");
                    return -1;
                }
                Scanner s = new Scanner(file);
                boolean header = true;
                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    if (header) {
                        switch (line) {
                            case "IR":
                            case "PO":
                                if (i == 0) {
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
                                        } else {
                                            Party p = new Party(c[2]);
                                            Candidate cand = new Candidate(c[1], p);
                                            candidates[j] = cand;
                                        }
                                    }

                                    //get number of ballots
                                    line = s.nextLine();
                                    numBallots = Integer.parseInt(line);

                                    ballots = new Ballot[numBallots];
                                } else {
                                    line = s.nextLine();
                                    line = s.nextLine();
                                    line = s.nextLine();
                                    numBallots += Integer.parseInt(line);

                                    Ballot[] newBallots = new Ballot[numBallots];
                                    System.arraycopy(ballots, 0, newBallots, 0, ballots.length);
                                    ballots = newBallots;
                                }
                                break;
                            case "CPL":
                                if (i == 0) {
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
                                    for (int x = 0; x < numParties; x++) {
                                        line = s.nextLine();
                                        String[] cand = line.split(",", -1);
                                        Candidate[] cList = new Candidate[cand.length];
                                        for (int j = 0; j < cand.length; j++) {
                                            Candidate c = new Candidate(cand[j], parties[x]);
                                            cn++;
                                            cList[j] = c;
                                        }
                                        parties[x].setCandidates(cList);
                                    }
                                    numCandidates = cn;

                                    //get number of seats
                                    line = s.nextLine();
                                    numSeats = Integer.parseInt(line);

                                    //get number of ballots
                                    line = s.nextLine();
                                    numBallots = Integer.parseInt(line);

                                    ballots = new Ballot[numBallots];
                                } else {
                                    line = s.nextLine();
                                    line = s.nextLine();
                                    for (int x = 0; x < numParties; x++) {
                                        line = s.nextLine();
                                    }
                                    line = s.nextLine();
                                    line = s.nextLine();
                                    numBallots += Integer.parseInt(line);

                                    Ballot[] newBallots = new Ballot[numBallots];
                                    System.arraycopy(ballots, 0, newBallots, 0, ballots.length);
                                    ballots = newBallots;
                                }
                                break;
                        }
                        header = false;
                        continue;
                    }

                    //collecting ballots
                    String[] values = line.split(",", -1);
                    int j;
                    for (j = 0; j < values.length; j++) {
                        if (values[j].equals("")) {values[j] = "0";}
                    }

                    ArrayList<Integer> b = new ArrayList<Integer>();
                    for (j = 0; j < values.length; j++) {
                        b.add(Integer.parseInt(values[j]));
                    }

                    ballots[ballotIndex] = new Ballot(b);

//                    for (int c = 0; c < values.length; c++) {
//                        System.out.print(b.get(c) + ", ");
//                    }
//                    System.out.println();
                    ballotIndex++;                
                }
                s.close();
            }
            return 0;
        }
        catch (FileNotFoundException e) {
            System.out.println("File does not exist"); 
        }
        return -1;
    }

    public void setRunElection(boolean runElection) {
        this.runElection = runElection;
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
