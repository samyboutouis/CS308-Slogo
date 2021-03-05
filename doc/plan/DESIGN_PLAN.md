# SLogo Design Plan
### Team Number: 7

### Names
Andre Wang (jw542),
Donghan Park (dp239),
Samy Boutouis (sb590),
Felix Jiang (fj32)

## Introduction

### Main Problem:
Designing a flexible SLogo IDE that allows users to interactively control a visualization turtle by inputting unique commands.

### Primary Design Goals:
- Allow users to experience visualizing basic Logo commands, as well as possibly create their own commands (requires a lot of flexibility)
- Create external and internal APIs between the user interface and the computational part of the program
- Creating new built-in commands should be possible without modifying much of the existing code

### Primary Architecture:
- Follow a MVC architecture with three distinct parts of the code, or potentially a model-view architecture depending on the project's needs
- Have the view control most of the user-interaction and display information of the application
- Have the model be in charge of the business logic and updates to the back-end
- Have the controller facilitate communication between the front and back-ends, updating either the view or model depending on the user input

## Overview

### General High-Level Design:
1. The user inputs a Logo command in some sort of console/terminal UI component in the view. The front-end then sends this data, in the form of a string, to the controller.
2. The controller then either parses this data or sends the information to the back-end to parse the data instead.
3. The back-end then interprets this data, completes all of the math/logic required to make sense of it, and then returns an array/list of movements. A movement is, essentially, an array with three elements in the following format: \{new position, new orientation, pen down/up}.
4. Front-end receives this list of movement steps and visualizes it using the turtle.

### APIs:

#### Front-end External:
None; we don't envision any part of our code calling public methods on the front end besides the front end itself.
#### Front-end Internal:
The front-end internal APIs will be used to collaborate and communicate between the various sections on the main page. For example, a section which changes the styling mode of the IDE would call an internal front-end API that changes the styling of the rest of the sections. Also, uses internal API to update its view continuously.
#### Back-end External:
Frontend will either call these methods itself or delgate to a controller, but either way the back-end external methods will provide the front end with a list of new coordinates to go to and whether the pen was up or down during those turtle movements
#### Back-end Internal:
These internal APIs will assist with decoding the user's command and provide a final list of movements the front end should make that correspond to what the user wants

### Implementations & Method Signatures:

#### Front-end External:
N/A
#### Front-end Internal:
```java
public void updateTurtle(Movement movement);
public void display(MovementList movements);
public Movement createMovement(double turtleX, double turtleY, double turtleRotation, boolean isPenDown);
```
Implementations:
* The turtle class encapsulates our turtle sprite, so in case we decide to change the implementation from an ImageView to a Polygon, no other piece of code should have to change much
#### Back-end External:
```java
public MovementList parseCommand(String command);
```
Implementations:
* Movements could be stored as an ArrayList, Array, or Map, but by keeping it as a class called MovementList rather than a List<Movement>, we have flexibility in changing the implementation of our List of Movements without making other classes make additional changes
#### Back-end Internal:
```java
public Movement calculateMove(Command command);
public Command convertCommand(String command);
```
Implementations:
* There are many possible ways to implement how our backend translates the commands a user types and the calculations we make as a result.
* After converting the typed command to a string in the front end, it is sent to the back-end and converted to its own command class, so that there may be more flexibility with adding new commands rather than having it set as a String or Enum or as a Map<String, Movement>

### Basic UML Diagram
![](https://i.imgur.com/S3zhjCm.png)

## User Interface

### UI Components
The view requires at least six major components:
1. Turtle Display (UI screen where the turtle can move & draw)
2. Console/terminal (UI component where user can write the input commands)
3. History (UI component that displays a list of previous commands inputted by the user)
4. Variables Display (UI component that displays all of the variables currently available in the IDE)
5. Commands Display (UI component that displays all of the user-defined commands currently available in the IDE)
6. Toolbar (UI component that holds different UI buttons to allow the user to change certain visual parameters)
    - Button to change background color
    - Button to set an image to use for the turtle
    - Button to set a color to use for the pen
    - Button to choose the language in which SLogo commands are understood
    - Button to access help about available commands

### Possible Erroneous Situations
The following errors should be reported back to the user and NOT cause the program to crash:
  - Bad input data
      - syntax error in the Logo command
      - incorrect image uploaded for turtle icon
  - Empty data

## Design Details
#### Front-end External:
We do not plan on using a front-end external API for this project (explained in Overview)

#### Front-end Internal:
1. Supports drawing the turtle in the correct locations and with or without the pen
    * Uses Movements from backend to display turtle for each change in direction/position
2. Supports visual UI changes by calling delegate methods within itself to change color or appearance of view
3. Multiple classes in the front end internal API for representing the turtle, windows, etc.
    * Allows for easier implementation of inheritance patterns depending on additional requirements

#### Back-end External:
1. Supports the parsing of commands sent from the front-end that the user typed in into a useable format for back-end calculations
    * Collaborates with controller class to factilitate communication between the front and back-ends
    * Also able to throw errors back to the front-end in case of bad user input, different types of errors can be added/extended
2. Supports the sending of Movements specifying new turtle position, orientation, and pen status from the back-end to the front-end
    * By creating a class to represent all the potential movements of the turtle and pen, it can be easily extended to incorporate different types of movement such as size change or color change
3. All of the commands and values the used typed will be encapsulated by the Movement object, so the front end does not care about the commands, only where to move the turtle

#### Back-end Internal:
1. Supports external back-end API for breaking down commands
    * Separate class ideally for breaking down a command from the string
    * If new commands are asked to be implemented or the user uses a complex sum of commands, only one class would be changed primarily (or extended)
2. Perhaps another class or method used to break down command into the resulting movement object (representing the new position, orientation, pen up/down of the turtle)
3. General Idea: use these methods in the back-end internal API to break down a string from the front end into a list of movements that the front end can easily handle
4. To write new commands and ways of interpreting them, a new delegate method should be written that handles the new command name, and some source code may need to be modified so that it is aware of this new command type (the method that delegates the command will need to know about this new type, otherwise an error would be thrown since it's unrecognized).

## Test Plan
### Strategies for Easier Testing
1. Method for returning one resulting Movement object given a basic command
    * By breaking down command into tiny pieces, we can test each one individually, and once that is working we know a list of movements returned should still be correct, and if it is incorrect, we know the bad code is somewhere in the method that returns the list of movements, not just the sole method that creates a single movement
2. Whenever a user submits bad input or incorrect code, an exception should be thrown
    * By throwing exceptions in the back-end parsing process, the application will be able to gracefully handle use cases in which lines of code written are not valid

### Test Scenarios

1. Interpret an initial set of SLogo commands
    1. Syntax error:
        - Expected outcome: program should catch the error and display an error message to the user without the program crashing
        - How design supports testing: a try/catch statement could be used; if an exception is thrown, the program would create an error diaglog box in the catch statement with the thrown exception name/description
    2. Empty command:
        - Expected outcome: program should not pass anything to the back-end; running an empty command should not have any effect on anything
        - How design supports testing: an if-statement could be used to check if the command is empty before passing it to the back-end
    3. Correct command:
        - Expected outcome: program should parse the data and move the turtle as expected from the SLogo command in the visualization window
        - How design supports testing: the appropriate command should move the turtle in the view as expected (e.g., "fd 10" should move the turtle in the view forward by 10 steps)

2. Setting a background color for the turtle's display area
    1. User inputs correctly formatted hex code
        - Expected outcome: background color changes to the color of the inputted hex code
        - How design supports testing: no exceptions are thrown, and the background color changes to the correct inputted hex code
    2. Incorrectly formatted hex code
        - Expected outcome: program should catch the error and display an error message to the user without the pgoram crashing
        - How design supports testing: a try/catch statement could be used; if an exception is thrown (incorrect format), the program would create an error diaglog box in the catch statement with the thrown exception name/description
    3. Same hex code
        - Expected outcome: background color stays the same
        - How design supports testing: no exceptions are thrown, and the background color stays the same

3. Set image for the turtle to use
    1. Upload standard turtle image:
        - Expected outcome: Once a user selects an image to represent the turtle, the turtle on the screen should be the selected image
        - How design supports testing: one can compare the UI display change with the expected display change
    2. Cancel upload:
        - Expected outcome: chooserscreen should close once users cancel an upload, waiting for user input to restart the process
        - How design supports testing: one can compare the UI display change with the expected change (no change in turtle image)
    3. Upload non-image file:
        - Expected outcome: user selects non-image file to upload, error message is displayed to ask user to upload an image file without having the program crash
        - How design supports testing: a try/catch statement could be used; if an exception is thrown, the program would create an error diaglog box in the catch statement with the thrown exception name/description

4. See history of commands
    1. User types in a valid command
        * Expected outcome: While not very relevant to this scenario, the command should be executed correctly. However, what's more important is that this valid command appears in the history window, in a way the user who typed it in can recognize that was their command
        * How design supports testing: our history of commands window will show the command that was just typed
    2. User types in an invalid command
        * Expected outcome: The turtle does not change position/direction and no changes are seen in the history window
        * How design supports testing: an error message will display to the user letting them know their command was invalid. If we see this, we will know the command did not go through, and should not appear on the window
    3. User types in a few valid commands, and then an invalid command
        * Expected outcome: the turtle does not move, and no change is shown in the history window
        * How design supports testing: an error message will display to show an invalid command existed, and then no changes should be shown on the history window

## Design Considerations
1.  One major design decision was to make the back-end return a complete list of all movements that the turtle goes through in one complete command cycle. For instance, if the user-inputted command was a for loop of making the turtle move forward and rotate for each step, the back-end would return a list of all the movements that the turtle takes before the loop ends. The front-end could then use this list to convert it into actual movement of the visualization turtle in the view.

    One alternative design that was initially discussed was to have the front-end call the back-end for every single step in the command loop. The back-end would then return the new movement object (formatted as described previously), and the front-end would use this new movement to move the visuaslization turtle.

    The team decided that the former was a better design because it was more versatile for different possible features. For instance, having a list of the complete set of movements that the turtle would have to take could allow for easier implementation of features regarding time, such as allowing the user to step through each move or animating the turtle instead of having it complete the entire command in one step.

2. Another design decision discussed is whether the front end should pass in the current location and orientation of the turtle or the back end should keep a variable representing the current location/position
    * We arrived at the conclusion that having two separate parts of code keeping track of the same thing may arise in concurrency issues
    * We decided to have the front end pass in the current location of the turtle for each command, so in case a new feature is allowed the user to position the turtle without a command, our code would not need any additional API calls to let the back end know the position changed

## Team Responsibilities

* Team Member #1: Andre Wang (jw542)
  * Back-end
* Team Member #2: Donghan Park (dp239)
  * Front-end
* Team Member #3: Samy Boutouis (sb590)
  * Controller, will switch between front-end and back-end depending on need
* Team Member #4: Felix Jiang (fj32)
  * Back-end
* We plan to all write our own tests, rather than having one person dedicated to it

