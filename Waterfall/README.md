How to run the program...

If running with election file:
    1. Add election file into ElectionFiles folder in /Project1/testing/ElectionFiles
    2. Make sure the path name on line 54 is uncommented in AuditFile
    3. Run the main function in Election
    4. View the audit file in the repo-Team5 directory, named after the date of the election
    
If running ClosedPartyListTest:
    1. Make sure election file is in /Project1/testing/CPLtestFiles
    2. Uncomment line 55 in AuditFile to ensure the program reads the correct file
    3. Run any tests in the file and view result

If running InstantRunoffTest:
    1. Make sure election file is in /Project1/testing/IRtestFiles
    2. Uncomment line 56 in AuditFile to ensure the program reads the correct file
    3. Run any tests in the file and view result

If running any other test file:
    1. Make sure election file is in /Project1/testing
    2. Uncomment line 57 in AuditFile to ensure the program reads the correct file
    3. Run any tests in the file and view result



If File not Found error occurs...
    Ensure you have changed the path name to be correct in AuditFile.java, for example
    Line 54 - Running election through Election
    Line 55 - Running ClosedPartyListTest
    Line 56 - Running InstantRunoffTest
    Line 57 - Running any other test files (BallotTest, CandidateTest, AuditFileTest, etc.)


Election Class:
Simply run the main method in this class to run an election given that the file is in the correct folder (see above)

AuditFile Class:
The actual audit file from the election will be stored in the repo-Team5 directory, one just outside of the Project1 folder. It will be named according to the election inputted by the user, for example if the date entered was 05-04-2018, then the audit file would be named '05-04-2018Election.txt'

The path name will need to be changed in order to read in the information from the correct file. The first four lines of collectileInfo() in AuditFile are four examples of this. The first one is for running a normal election file that is placed in ElectionFiles. The next is for running tests in ClosedPartyListTest.java. The next is for running tests in InstantRunoffTest.java. The last is for running tests in AuditFile.java.
There are comments throughout the code to make reading of the class easier for graders. 


ClosedPartyList Class:



InstantRunoff Class:


