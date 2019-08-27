Instructions on how to install, run, and test our project submission
CS451 Checkers Project
Jake Carfagno, Matthew Horger, Preet Patel, John Zlotek

\************************
INSTALLATION INSTRUCTIONS
************************\

To install the following project, please open up your interactive development environment of your choice. We recommend IntelliJ because the .idea files will allow for ease of project setup.
To import the project into Eclipse, you will have issues with some of our dependencies using Gradle.

Once you import the project in Intellij, you will have a couple of options to install the code on your system. First, you can build the server.jar using the class Server.java located in src/main/java/tbc/server as the target entry point. This will allow you to spin up the process management tool used to facilitate the game.

However, before you export the .jar, please make sure your /src/main/java/tbc/util/Constants.java file has the correct network information you will be using to play the game. For example, if you are trying the program locally, the host should be localhost. If you are playing across a network, then this hostname will be the target address for the physical server you host the .jar on. For our purposes, testing locally we recommend localhost as the host, but we understand that some tests can not be achieved using localhost. 

Once you export the server.jar, you have two options to run a client process to connect to the server. You can export the Main class in /src/main/java/tbc/client in order to connect to the server. Please note that whatever server you put for your Constants.java file MUST be the same for the server and client.
The second option is running the program locally within IntelliJ. To do this, simply press SHIFT+F10, which will build the project and run the client.

\*************************
RUNTIME INSTRUCTIONS
*************************\

For running the program, most of your interaction will be in the client.jar or in the windows produced by the debug session started in the IntelliJ. If you click create lobby, you will start a lobby that will be waiting for another player. We recommend that you spawn two client.jar processes so that one is able to start the lobby with the create button, and another is able to join using their respective buttons. It should be noted that joining a lobby before one is created will cause a deadlock when sometime tries to create a lobby, so you should avoid this for the usage of the program. However, you can create up to 5 lobbies using our program at once, and have 10 clients running in multiple synchronous threads.

Once the game board appears, you will begin normal play of Checkers. Play until someone wins, as we did not implement our terminate and heartbeat function to check for awarding a winner on a disconnect due to some time constraints. We also had issues with lobby menus and choosing them with synchronous threads, so that feature was also left out for processing game information.

Otherwise, standard Checkers rule applies to our program.

\************************
FURTHER INFORMATION
************************\


All supporting documents for our upload, including screenshots of code coverage analysis, static analysis, selected test results, and release notes have been provided in the /docs/finalSubmission folder. We are unable to test a good portion of the code due to unfamiliarity with Swing and synchronous tasks. If this class was on Java, and we were instructed further than what we learned in CS350, we would have strived for 100% code coverage. We also chose to submit our previous documents in order to show the progression of the project, and have a culmination of all our work. In conclusion, we think we wanted to implement many features that necessarility were not in the scope of this class project, and it hurt our development lifecycle. However, we were still able to produce a main product that achieves the stated requirements as per the assignment page. 