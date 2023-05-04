import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
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

    /**
     * This function is responsible for collecting the filename and election date and running the election.
     * @param args the file name inputs
     */
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        ArrayList<String> inputFiles = new ArrayList<>();
        boolean firstInput = true;

        // Prompt
        System.out.println("\n|--------------------------------------------------------------------|");
        System.out.println("|\tNOTE: File must not contain spaces & must include the extension  |" +
                           "\n|\t  Valid input file name  --> e.g. TestFile.csv   TestFile2.txt    |" +
                           "\n|\t  Invalid input file name -> e.g. Test  File.csv  or  TesFile    |");
        System.out.println("|--------------------------------------------------------------------|");
        System.out.println("\n\t    Please input the name(s) of the election file(s)" +
                        "\n\telse input 'done' when done inputting all election files\n");

        // While loop to get the file names.
        do {
            System.out.print("Input file name(s) or (done): ");
            String name = myObj.nextLine();
            String[] split = name.split("\\s+");
            inputFiles.addAll(List.of(split));

            if (firstInput) {
                System.out.print("Please enter the date of the election (mm-dd-yyyy): ");
                electionDate = myObj.nextLine();
                firstInput = false;
            }
        } while (!inputFiles.contains("done"));

        inputFiles.remove("done");
        if (inputFiles.size() == 0) {return;}

        AuditFile auditFile = new AuditFile(inputFiles.get(0), electionDate);
        auditFile.setRunElection(true);
        int res = auditFile.collectMultipleFileInfo(inputFiles);
        if (res != 0) {
            System.out.println("Error occurred reading the input files, please try again.");
            return;
        }

        String electionType =  auditFile.getElectionType();
        Candidate winner = null;
        ArrayList<Party> winners = null;

        switch (electionType) {
            case "IR":
                InstantRunoff ir = new InstantRunoff(auditFile);
                winner = ir.runElection();
                ir.printResults();
                break;
            case "CPL":
                ClosedPartyList cpl = new ClosedPartyList(auditFile);
                winners = cpl.runElection();

                for (Party party : winners) {
                    System.out.println(party.getpName());
                }
                break;
            case "PO":
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
                for (Party party : winners) {
                    writer.write(" " + party.getpName() + " (");
                    for (int j = 0; j < party.getNumPSeats(); j++) {
                        if (j == party.getNumPSeats() - 1) {
                            writer.write(party.getCandidates()[j].getName());
                        } else {
                            writer.write(party.getCandidates()[j].getName() + ",");
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
