#ENTITY: 

###User:
```java
public static final int DEFAULT_USER_AGE = 0;
public static final String DEFAULT_USER_NAME = "";
public static final String DEFAULT_USER_ID = "";
private String id = DEFAULT_USER_ID;
private String name = DEFAULT_USER_NAME;
private String surname; 
private int age = DEFAULT_USER_AGE;
private String phone; 
pricate String email; 


```
###Group: 
```java
private String groupID;
private List<Applicant> applicantsInGroup = new ArrayList<>();
``` 


#SERVICES:
###UserManager:

resource path: ```/users```

method: GET   -	
	
	public ResponseEntity<List<User>> getUsers()
	- returns list of all Users
method: POST - 
             
    public ResponseEntity<User> postNewUser  
    - adds a new User in a list

resource path: ```/users/userId```	
	method:  GET - 
	
	public @ResponseBody ArrayList<String> getAllUsersID() 
    - returns list of all userID

resource path: ```/users/{user_id}```   
	method:  GET  - 
	
	public @ResponseBody User getUserByID(@PathVariable String userID) 
	- returns a User by his userID

method:  PUT -

	public ResponseEntity<User> editUser (@RequestBody User editedUser)
    - edit User by his userID
    
method:  DELETE -

	public ResponseEntity<String> deleteUserByID(@PathVariable String userID) 
	- delete User by his userID

#24.06.2014

