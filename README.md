# Launch instructions
run 'gradlew tomcatRunWar' from the root of the project


#Additional Quesitons

* How well does your note-searching-api scale or not scale? How would you make your search more efficient?
	I would say it does not scale very well depending on the application.  The wildcard matching will get slow with a large number of records.  If required, I would use some kind of full text search if the entire message had to be in the index.  I would also explore using tags or word clouds to simplify the search without using full wildcard matching. 
* How would you add security to your API?
	If it is an internal api, I might only secure traffic with SSL between the required servers.  In a more scalable or public server sitation, I would secure using standard servlet security through the web xml.  Since we are in a service space, some kind of header based proven technology like OWASP.  Other considerations would be if the authentication needs to happen at a system or user level.  In most production environments, I would prefer to delegate the gateway security to a piece of middleware.
* What features should we add to this API next?
	I would start with deletion of records.  After that I would consider more boundary conditions and what errors were appropriate for the requirements.
* How would you test the API?
	I would start with using the same build script created here to run an isolated integration test in another project.  I would load up a set number of records, retrieve them, search, etc.  I would likely implement the delete function soon to make the testing more complete feeling(especially for tests being run against persistent environments) 
