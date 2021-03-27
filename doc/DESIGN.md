# DESIGN Document for Slogo
### Names
Andre Wang (jw542),
Donghan Park (dp239),
Samy Boutouis (sb590),
Felix Jiang (fj32)

## Role(s)
* Andre Wang (jw542)
    * Back-end
* Donghan Park (dp239)
    * Front-end, Controller
* Samy Boutouis (sb590)
    * Front-end
* Felix Jiang (fj32)
    * Back-end

## Design Goals
* Allow users to experience visualizing basic Logo commands, as well as possibly create their own commands (requires a lot of flexibility)
* Create external and internal APIs between the user interface and the computational part of the program
* Creating new built-in commands should be possible without modifying much of the existing code
* New features shouldn't require making changes in too many places.

## High-Level Design
1. The user inputs a Logo command in some sort of console/terminal UI component in the view. The front-end then sends this data, in the form of a string, to the controller.
2. The controller then sends the information to the back-end to parse the data.
3. The back-end then interprets this data, completes all of the math/logic required to make sense of it, and then returns a collection of commands to perform on the turtles. A command performs actions on a turtle such as making it move forward, backward, etc.
4. Front-end receives this list of movement steps and visualizes it using the frontEndTurtle.

## Assumptions or Simplifications
* Users can only add to the color palette through the program
* Grouping command's output is sum of each individual command output
* Different instances of the workspace are created in separate windows
* By creating separate windows, users are able to freely open and close different workspaces and adjust how they want it on the screen
* Animations are implemented but user cannot control the speed or pause them
* When telling multiple turtles to do a command, they must be inputted linearly with turtles that already exist (i.e. tell [ 1 2 ], not tell [ 1 5 ] if turtle 2 does not exist)
* If and IfElse commands have only one value to calculate in the expression, or at least we only calculate the first value, and the user can type more values (i.e. if [ fd 50 fd 0 ] [ fd 50 ]) but the second fd 0 is ignored.

## Changes from the Plan
* One aspect of the plan we had to change is that we had instead of creating a list of movements that specifies a turtle's new position and orientation, the back-end creates a list of commands to perform on a turtle that directly call a turtle's methods
* Additionally, we had to slightly change how the front-end receives data from the back-end because multiple turtles had to be incorporated into the complete program
    * Instead, we sent a map with a list of commands corresponding to each turtle ID so that the front-end can update the position and state of multiple turtles
    * By changing this from the original plan, we were able to incorporate multiple turtles
* The front-end and back-end had to modify their internal APIs to incorporate multiple turtles
    * Specifically, they had to create classes that managed all the turtles on the screen, changing the internal API to be more comprehensive of these changes
* A tree was built and each node of the tree created the command object to give the front end, rather than having a centralized method responsible for creating all of the command objects.

## How to Add New Features
* Adding a new command to the language
    * To add a new command to the language, one must add the command property and its text command to the language property file
    * Create a reference file for the command and add it to the reference folder in resources
    * Create a Node class that extends the Slogo Node class that implements the node command and also create a turtle command if it affects the turtle on the screen that implements the command interface
        * If the new command takes in brackets, it may be better to extend the BracketNode, and if the new command will affect each turtle, it may be better to extend the TurtleCommandNode (both subclasses of SlogoNode but provide more functionality).
    * In the Commands property file in the Parameters resource package, add the command property along with the number of parameters or bracket pairs it expects
* Adding a new component to the front end
    * If it is adding a new view, create a new class that extends the scrolling display class that includes all the components needed in the new view
    * If necessary, use the button factory to easily create new buttons and create the associated property files for them
    * In the Workspace class, create a new instance of the view and pass it into the ViewLayout class
    * Put the class in the ViewLayout viewNamesMap variable, so it can be shown in the dropdown and shown on the screen
* Adding an animation manager
    * For adding animations, one simply needs to create buttons that control the speed of the animation and pausing/starting the animation
    * Since our program already creates a timeline in the AnimationManager class, one should just use the button factory to create buttons representing the different actions the animation needs to perform, add methods to the ButtonMethods property files, and create the event handlers in the FrontEndController class
    