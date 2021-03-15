# API Lab Discussion
# SLogo API Design

## Names and NetIDs
Andre Wang (jw542),
Donghan Park (dp239),
Samy Boutouis (sb590),
Felix Jiang (fj32)

### Planning Questions

* What behaviors (methods) should the frontEndTurtle have?
    * Ability to be shown/hidden
    * Ability to rotate by a set amount (either by absolute or relative degree)
    * Ability to move by a set amount in a certain direction (frontEndTurtle may be able to move in all cardinal directions, or only forward--with direction changed by rotation)
    * Ability to toggle drawing (i.e., lift/drop the pen)

* When does parsing need to take place and what does it need to start properly?
    * Parsing takes place when the user starts typing commands in
    * It needs some indication that the program has started
    * Parsing also needs to know the string input that the user types in

* What is the result of parsing and who receives it?
    * The result of parsing is the program now knows what the user wants to do to the frontEndTurtle
    * The part of the program that is responsible for calling the methods that move the frontEndTurtle

* When are errors detected and how are they reported?
    * Errors occur based on unrecognized commands from the user
    * They should be detected by the parser
    * They should report to the command window to let the user know their command did not work

* What do commands know, when do they know it, and how do they get it?
    * How to call the frontEndTurtle
    * They know it when the programmer programs the command, but more importantly they should know it when the user types it in the program
    * Commands know when to be executed, and that is recieved from the user typing

* How is the GUI updated after a command has completed execution?
    * The GUI should update after receiving the response from an API that is called
    * For example, when a user sends a command, the GUI should be continually updated to respond the changes to the frontEndTurtle

* What behaviors does the result of a command need to have to be used by the front end?
    * Result of a command needs to know how to move the frontEndTurtle, where to move it, and what direction the frontEndTurtle is facing

### APIs

#### Backend External API
Returns a big list that represents all the movement steps that correspond to the command the user typed. This is returned each time the user types a command, and the front end can decide how to display this information

```java
public <Collection> getMovementArray(String command){
  // uses the command (code from frontend) to return a list of 
  // all the moves (including coordinates) that the frontEndTurtle makes
}
```

#### Frontend External API
We believe that the backend should have no instance of the frontend for the way we want to design the project, so most of the public methods of the frontend are part of its internal API.
* We did consider another way of passing information between the model and the view. One way is for the view/controller to call a parse method, and then that returns a huge list of movements, and then the frontend starts working on it.
* Another way to achieve this would be to have the backend call methods in the frontend, and then that would required a frontend external API. The parser gives code to the back end, and then once the backend figures out all the commands, it calls a method in front end, like `display(listOfCommands)` and the front end can deicde how to show it to the users.
* First case involved frontend calling backend method for movements, while second case involved back end giving movements to the frontend.


#### Backend Internal API
The backend internal APIs would include all the public methods necessary to convert the code command into an array of movements.

```java
public <Collection> parseCode(String command){
  // parses the String command into a readable format for the backend
}
```


#### Frontend Internal API
The frontend would be able to update the position of the frontEndTurtle through internal APIs. For example, after receiving the new coordinates and orientation from the back-end, the front-end would be in charge of actually moving the frontEndTurtle and creating pen marks on the screen.

```java
public void updateTurtleMovement movement){
  // updates the position of the frontEndTurtle for each movement step
}
```
```java
public void display(List<Movement> movements){
  // translates the array of movement steps into a format that 
  // can be displayed in the view
}
```

### Design

#### Backend Design CRCs

| Parser                          |
| ---------------- | ------------ |
| Responsibilities | Collaborators|
| public Collection parseCode(String command) | Frontend Display，CommandCreator |

| CommandCreator                  |
| ---------------- | ------------ |
| Responsibilities | Collaborators|
| public Command createCommand(String command) | Parser would call this on a per command basis (will need to figure out how the parser knows how to break down a command in a per-command basis) |

| Turtle                          |
| ---------------- | ------------ |
| Responsibilities | Collaborators|
| public void determinePosition(double x, double y) | Parser |
| public void setRotation(double direction) | |
| public void lowerPen() | |
| public void raisePen() | |


#### Frontend Design CRCs

| TurtleView                      |
| ---------------- | ------------ |
| Responsibilities | Collaborators|
| public void moveTurtle(double x, double y) | Parser |
| public void rotateTurtle(double direction) | |
| public void drawLine() | |

| ConsoleDisplay                  |
| ---------------- | ------------ |
| Responsibilities | Collaborators|
| public showConsole() | Displays the text box for user to type, when user presses enter the console takes what is in the box and gives it off to backend|

| HistoryDisplay                  |
| ---------------- | ------------ |
| Responsibilities | Collaborators|
| public void showHistory() | Shows all the past inputs the user has run in the console|

#### Use Cases

* The user types 'fd 50' in the command window, sees the frontEndTurtle move in the display window leaving a trail, and has the command added to the environment's history.
    * Console passes the 'fd 50' to the backend parser.
    * Parser returns a list of Movement objects that represent the position, direction, and pen up/down. Between each item in the list, only one of these fields should be changed.
    * Returned list from parser is then used to update the frontEndTurtle movements by calling Turtle methods
    * The text in the console is passed to the history display

* The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.
  \*The method to pass the string to backend is in a try/catch statement
    * Console passes the '50 fd' string to the backend parser.
    * Backend throws an exception; frontend displays error message instead of updating the view

* The user types 'pu fd 50 pd fd 50' in the command window and sees the frontEndTurtle move twice (once without a trail and once with a trail)
    * The command is given to the parser
    * The parser returns a list of 4 elements, each one being a Movement object
    * the frontend uses each movement object to call a method in the frontEndTurtle class

* The user changes the color of the environment's background.
    * The front-end calls the controller to update the environment's background
    * The controller updates the color by changing the CSS/property file ran by the application