### **Audit File Collector**

```java
Class Name: AuditFile
    
AuditFile(String name, String date) {
    this.name = name
	this.date = date
	audtiFile = Create a file with the name set to fileName + electionDate
}
    
void collectFileInfo() {
	eletionType = first line in file
        
	if (electionType == IR) {
        read/set numCandidate = line 2
		parse line 3 read/create/set candidates
		
		read/set numBallots = line 4
		for i = line 5 to numBallots {
            create ballot
			add to ballots
        } 
    } else {
        read/set numParties = line 2
		parse line 3 read/create/set parties
		
		for i = line 4 to numberof parties 
            for each name on each line
            	read/create/set candidates
	
		read/set numSeats = line number of parties + 4
		read/set numBallots = line number of parties + 5
                
		for i = line number of parties + 6 to numBallots {
            create ballot
			add to ballots
        } 
    }
}
    
String getElecType( ) {
    return this.electionType
}

void setElecType(String election) {
    this.electionType = election
}

String getDate( ) {
    return this.electionDate
}

void setDate(String date) {
    this.electionDate = date
}

Ballot[] getBallots( ) {
    return this.ballots
}

void setElecType(Ballot[] ballots) {
    this.ballots = ballots
}

Candidate[] getCandidates( ) {
    return this.candidates
}

void setCandidates(Candidate[] cadidates) {
    this.candidates = candidates
}

Party[] getParties( ) {
    return this.parties
}

void setParies(Party[] parties) {
    this.parties = parties
}

int getnBallots( ) {
    return this.numBallots
}
```

```java
void setnBallots(int ballots) {
    this.numBallots = ballots
}

int getnSeats( ) {
    return this.numSeats
}

void setnSeats(int seats) {
    this.numSeats = seats
}

Candidate[] getDCandidates( ) {
    return this.droppedCandidates
}

void setDCandidates(Candidate[] dCandidates) {
    this.droppedCandidates = dCandidates
}

Candidate[] getWinners( ) {
    return this.winners
}

void setWinners(Candidate[] winners) {
    this.winners
}

```

### **Ballot**

```java
Ballot(int[] vote) {
	this.votes = votes
}
```



### **Candidate**

```java
Class Name: Candidate

Candidate(String name, Party party) {
    this.cName = name
    this.party = party
}

String getName( ) {
    return this.cName
}

int getCandidateID() {
    return this.candidateID
}

void setCandiateID(int ID) {
 	this.candidateID = ID   
}
```

```java


Party getParty( ) {
    return this.party
}

int getNumVotes( ) {
    return numVotes
}

void setNumVotes(int nVotes) {
    this.numVotes = nVotes
}

void passBallot(Ballot ballot) {
    Add ballot to cBallot array
}
```

### **Closed Party List**

```java
Class Name: ClosedPartyList

CPL(AuditFile electionFile) {
	this.numballots = electionFile.getnBallots()
	this.numParties = electionFile.getnParties()
	this.numSeats = electionFile.getnSeats()
	this.parties = electionfile.getParties()
	this.ballots = electionFile.getBallots()
}
    
void runElection() {
    for each ballot in ballots {
        get party choice match to party
        party.passBallot(ballot);
    }
    
    Determine quota = total number of ballots / total number of seats
	
	for each party in parties {
		int numPSeats = party.getVotes() / quota
		int remainder = party.getVotes() % quota
            
		party.setSeats(numPSeats)
		party.setReaminingVotes(remainder)
            
		if party.getRemainingVotes() for two or more parties are equal...there is a tie {
        	coinFlip(Party[] parties)
    	}
    }
    
    assignSeats(Party[] parties)
   
}

void assignSeats(Party[] parties) {
    
	for each party in parties {
        for each candidate and i < party.getSeats() {
            add candidate to winners
        }
    }
    
    electionFile.setWinners(winners)
}

Party coinFlip(Party[] parties) {
		randomly select party that gets a seat
}
```

### **Election**

```java
Class Name: Election
    
void main(String[] args) {
    if args is equal to 0 then
        Prompt user to [enter the file name]
        set fileName = input 
    else 
        Set fileName form args[0]
    
	Prompt user [enter the date of election]
    electionDate = Save the inpu t
    
	auditFile = AuiditFile(fileName, date)
	auditFile.collectFileInfo()
	
	if auditFile.getElectionType() == IR {
		ir = IR(auditFile)
		ir.runElection()   
    }
     else {
		cpl = CPL(auditFile)
         cpl.runElection()
     }

}

```

### **Instant Runoff**

```java
Class Name: InstantRunoff

IR(AuditFile electionFile) {
	this.numballots = electionFile.getnBallots()
	this.numCandiates = electionFile.getnParties()
	this.candidates = electionfile.getcandidates()
	this.ballots = electionFile.getBallots()
}
  
void runElection() {
   for each ballot in ballots {
        int ID = get number 1 choice and set that index
        candiates[ID].passBallot(ballot);
    }
    
    for each candidiate {
      compare number of votes recieved and set max votes as winner
      
        if there is a tie then {
          check for lowest candidate
          if there is a tie between lowest voted {
            do a coin flip to detremine candidate to drop
          } else {
            drop the lowest voted candidate
          }
        }
    }
}

void dropCandidate(Candidate candidate) {
    for each ballot in candidate {
			Redistribute Votes of candidate
    }
  
  	set candidate as a dropped canidate 
}

void redistributeVotes(Ballot ballot, int round) {
		look at ballot and select round number from ballot
		add this ballot to candidate who corresponds to round vote
}

Candidate coinFlip(Candidate[] candidates) {
  	generate random number
    randomly select candidate from candidates according to random number
}
```

### **Party**

```java
Class Name: Party
    
Party(String name) {
    this.pName = name
}

Party(String name, Candidate[] candidates) {
    this.pName = name
	this.candidates = candidates
}

String getName( ) {
    return this.pName
}

Candidate[] getCandidates( ) {
    return this.candidates
}

int getVotes( ) {
    return this.numPVotes
}
```

```java

void setVotes(int votes) {
    this.numPVotes = votes
}

int getSeats( ) {
    return this.numPSeats
}

void setSeats(int seats) {
    this.numPSeats = seats
}

int getRemainVotes( ) {
    return this.remaningVotes
}

void setRemainVotes(int remainder) {
    this.remaningVotes = remainder
}

void passBallot(Ballot ballot) {
    add ballot to pBallots
    increment count of numPVotes
} 
```

