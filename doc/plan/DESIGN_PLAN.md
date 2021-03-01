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
```
```java
public void display(MovementList movements);
```
Two Implementations:
  * The turtle class encapsulates our turtle sprite, so in case we decide to change the implementation from an ImageView to a Polygon, no other piece of code should have to change much
#### Back-end External: 
```java
public MovementList parseCommand(String command);
```
Two Implementations:
  * Movements could be stored as an ArrayList, Array, or Map, but by keeping it as a class called MovementList rather than a List<Movement>, we have flexibility in changing the implementation of our List of Movements without making other classes make additional changes
#### Back-end Internal:
```java
public MovementList calculateMove(Command command);
public Command convertCommand(String command);
public Command simplifyCommand(Command command);
```
Two Implementations:
  * There are many possible ways to implement how our backend translates the comamnds a user types and the calculations we make as a result. 
  * After converting the typed command to a string in the front end, it is sent to the back-end and converted to its own command class, so that there may be more flexibility with adding new commands rather than having it set as a String or Enum or as a Map<String, Movement>

## User Interface

### UI Components
The view requires at least three components: 
1. Turtle window (UI screen where the turtle can move & draw)
2. Console/terminal (UI component where user can write the input commands)
3. History (UI component that displays a list of previous commandst )

### Possible Erroneous Situations
The following errors should be reported back to the user and NOT cause the program to crash:
- Bad input data
  - syntax error in the Logo command
  - incorrect image uploaded for turtle icon
- Empty data

## Design Details


## Test Plan


## Design Considerations


## Team Responsibilities

* Team Member #1

* Team Member #2

* Team Member #3

* Team Member #4
