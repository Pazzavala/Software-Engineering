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
        String name = "";
        if (args.length == 0) {
            System.out.println("Please input the name of the election file: ");
            name = myObj.nextLine();
        }
        else {
            name = args[0];
        }
        System.out.println("Please enter the date of the election (mm-dd-yyyy): ");
        electionDate = myObj.nextLine();

        auditFile = new AuditFile(name, electionDate);
        auditFile.collectFileInfo();

        Candidate winner = null;
        ArrayList<Party> winners = null;
        if (auditFile.getElectionType().equals("IR")) {
            InstantRunoff ir = new InstantRunoff(auditFile);
            winner = ir.runElection();
        }
        else if (auditFile.getElectionType().equals("CPL")) {
            ClosedPartyList cpl = new ClosedPartyList(auditFile);

            winners = cpl.runElection();

            for (Party party: winners) {
                System.out.println(party.getpName());
            }

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
            writer.write("Election Type: " + auditFile.getElectionType());
            writer.write("\nNumber of Candidates: " + auditFile.getnCandidates());
            writer.write("\nCandidates: ");
            if (auditFile.getElectionType().equals("IR")) {
                for (int i = 0; i < auditFile.getCandidates().length; i++) {
                    writer.write(auditFile.getCandidates()[i].getName() + " " + auditFile.getCandidates()[i].getParty().getpName() + "   ");
                }
            }
            else {
                for (int i = 0; i < auditFile.getParties().length; i++) {
                    for (int j = 0; j < auditFile.getParties()[i].getCandidates().length; j++) {
                        writer.write("(" + auditFile.getParties()[i].getpName() + ") " + auditFile.getParties()[i].getCandidates()[j].getName() + ",");
                    }
                    writer.write("  ");
                }
            }
            writer.write("\nParties: ");
            if (auditFile.getElectionType().equals("CPL")) {
                for (int i = 0; i < auditFile.getParties().length; i++) {
                    writer.write(auditFile.getParties()[i].getpName() + " (Seats:" + auditFile.getParties()[i].getNumPSeats() + ")   ");
                }
            }
            writer.write("\nNumber of Ballots: " + auditFile.getnBallots());
            for (int i = 0; i < auditFile.getBallots().length; i++) {
                writer.write("\nBallot " + i + ": ");
                for (int j = 0; j < auditFile.getBallots()[i].getVotes().size(); j++) {
                    writer.write(auditFile.getBallots()[i].getVotes().get(j) + " ");
                }
            }
            writer.write("\nWinner(s): ");
            if (auditFile.getElectionType().equals("IR")) {
                writer.write(winner.getName());
                System.out.println("Winner: " + winner.getName());
            }
            else {
                System.out.print("Winners: ");
                for (int i = 0; i < winners.size(); i++) {
                    writer.write(winners.get(i).getpName() + "(");
                    for (int j = 0; j < winners.get(i).getNumPSeats(); j++) {
                        writer.write(winners.get(i).getCandidates()[j].getName() + ",");
                    }
                    writer.write(")  /  ");
                    System.out.print(winners.get(i).getpName());
                }
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}