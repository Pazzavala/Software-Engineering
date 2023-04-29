import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Class that is responsible for running the election by collecting the file name and the election date.
 * @author Camden Fessler
 * @version 1.0
 * @since 1.0
 */
public class Election {
    private static String electionDate;
    private static AuditFile auditFile;

    /**
     * This function is responsible for collecting the filename and election date and running the election.
     * @param args
     */
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        String electionType = "";
        System.out.println("Please input the name of the election file or input 'done' if you have input all election files\nWARNING: file names must not contain spaces: ");
        String name = myObj.nextLine();
        ArrayList<String> inputFiles = new ArrayList<String>();
        boolean firstInput = true;
        while (!name.equals("done")) {
            if (firstInput) {
                System.out.print("Please enter the date of the election (mm-dd-yyyy): ");
                electionDate = myObj.nextLine();
                System.out.print("Please enter the type of election you are running (IR, CPL, or PO): ");
                electionType = myObj.nextLine();
                firstInput = !firstInput;
            }
            inputFiles.add(name);    
            System.out.println("Please input the name of the election file or input 'done' if you have input all election files\nWARNING: file names must not contain spaces: ");
            name = myObj.nextLine();
        }

        if (inputFiles.size() == 0) {return;}
        auditFile = new AuditFile(inputFiles.get(0), electionDate);
        int res = auditFile.collectMultipleFileInfo(inputFiles, electionType);
        if (res != 0) {
            System.out.println("Error occurred reading the input files, please try again.");
            return;
        }

        Candidate winner = null;
        ArrayList<Party> winners = null;
        if (electionType.equals("IR")) {
            InstantRunoff ir = new InstantRunoff(auditFile);
            winner = ir.runElection();
        }
        else if (electionType.equals("CPL")) {
            ClosedPartyList cpl = new ClosedPartyList(auditFile);

            winners = cpl.runElection();

            for (Party party: winners) {
                System.out.println(party.getpName());
            }

        }
        else if (electionType.equals("PO")) {
            PopularityOnly po = new PopularityOnly(auditFile);
            winner = po.runElection();

            System.out.println(winner.getName());
            return;
        }

        String filename = electionDate + "AuditFile.txt";
        File file = new File(filename);

        try {
            // create a new file
            if (file.createNewFile()) {
                System.out.println("File created successfully.");
            } else {
                System.out.println("File already exists.");
            }

            // write to the file
            FileWriter writer = new FileWriter(file);
            writer.write("Election Type: " + electionType);
            writer.write("\nNumber of Candidates: " + auditFile.getnCandidates());

            if (electionType.equals("IR")) {
                writer.write("\nCandidates: ");
                for (int i = 0; i < auditFile.getCandidates().length; i++) {
                    writer.write(auditFile.getCandidates()[i].getName() + " " + auditFile.getCandidates()[i].getParty().getpName() + "   ");
                }
                writer.write("\nNumber of Ballots: " + auditFile.getnBallots());
                for (int i = 0; i < auditFile.getBallots().length; i++) {
                    writer.write("\nBallot " + i + ": ");
                    for (int j = 0; j < auditFile.getBallots()[i].getVotes().size(); j++) {
                        writer.write(auditFile.getBallots()[i].getVotes().get(j) + " ");
                    }
                }
                writer.write("\nDropped Candidates: ");
                if (auditFile.getDCandidates() != null) {
                    for (int i = 0; i < auditFile.getDCandidates().size(); i++) {
                        writer.write(auditFile.getDCandidates().get(i).getName() + " ");
                    }
                }
                writer.write("\nWinner(s): ");
                writer.write(winner.getName());

            }
            else if (electionType.equals("CPL")) {
                writer.write("\nParties: \n");
                for (int i = 0; i < auditFile.getParties().length; i++) {
                    writer.write(" " + auditFile.getParties()[i].getpName() + " (Seats Awarded:" + auditFile.getParties()[i].getNumPSeats() + ")\n");
                }
                writer.write("\nCandidates: \n");
                for (int i = 0; i < auditFile.getParties().length; i++) {
                    writer.write(" " + auditFile.getParties()[i].getpName() + ": ");
                    for (int j = 0; j < auditFile.getParties()[i].getCandidates().length; j++) {
                        writer.write(auditFile.getParties()[i].getCandidates()[j].getName() + " ");
                    }
                    writer.write("\n");
                }
                writer.write("\nNumber of Ballots: " + auditFile.getnBallots());
                for (int i = 0; i < auditFile.getBallots().length; i++) {
                    writer.write("\nBallot " + i + ": ");
                    for (int j = 0; j < auditFile.getBallots()[i].getVotes().size(); j++) {
                        writer.write(auditFile.getBallots()[i].getVotes().get(j) + " ");
                    }
                }
                writer.write("\n\nNumber of Seats: " + auditFile.getnSeats());
                writer.write("\n\nWinner(s): \n");
                for (int i = 0; i < winners.size(); i++) {
                    writer.write(" " + winners.get(i).getpName() + " (");
                    for (int j = 0; j < winners.get(i).getNumPSeats(); j++) {
                        if (j == winners.get(i).getNumPSeats() - 1) {
                            writer.write(winners.get(i).getCandidates()[j].getName());
                        }
                        else {
                            writer.write(winners.get(i).getCandidates()[j].getName() + ",");
                        }
                    }
                    writer.write(")\n");
                }

            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
