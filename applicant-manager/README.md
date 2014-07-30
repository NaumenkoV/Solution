#ENTITY: 

###Applicant: 
```java
private String id;
private String name; 
private String surname; 
private int age; 
private String email;


```
###Group: 
```java
private String groupID;
private List<Applicant> applicantsInGroup = new ArrayList<>();
``` 


#SERVICES:
###ApplicantManager:

resource path: ```/applicants```

method: GET   -

    public ResponseEntity<String> getApplicants() 
    - get all applicants 
    
method: POST -

    public ResponseEntity<Applicant> addNewApplicant Applicant applicant) 
    - add new applicant 

resource path: ```/applicants/applicantId```

method: GET -

	public ResponseEntity<List<String>> getApplicantIDs() 
	- get all applicants ID

resource path: ```/applicants/{applicant_id}```

method: GET   - 

    public @ResponseBody Applicant getApplicantById( String applicantId) 
    - get applicant by his ID
method: PUT   - 

    public ResponseEntity<Applicant> editApplicant(Applicant applicant) 
    - edit applicant

resource path: ```applicants/groups/{group_id}```

method: GET    -

    public ResponseEntity<String> getApplicantsByGroupID(String groupID) 
    - get all applicants with specefied ID. 

#24.06.2014
